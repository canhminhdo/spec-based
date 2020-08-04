package checker.bmc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import application.model.ConsumerInfo;
import application.model.RabbitQueue;
import application.model.SystemInfo;
import config.CaseStudy;
import jpf.common.OC;
import mq.RabbitMQClient;
import mq.RunJPF;
import mq.api.RabbitMQManagementAPI;
import redis.api.RedisConsumerInfo;
import redis.api.RedisLock;
import redis.api.RedisQueueSet;
import redis.api.RedisSystemInfo;
import server.Application;
import server.ApplicationConfigurator;
import utils.GFG;

public class Consumer {
	
	protected static Logger logger = (Logger) LogManager.getLogger();
	public static Object lock = new Object();
	protected final static int SIZE = 3;
	protected HashMap<Integer, Integer> analyzer = new HashMap<Integer, Integer>();
	protected boolean isConsuming = false;
	protected Application app;
	protected String consumerTag;
	protected RedisQueueSet jedisSet;
	protected RedisSystemInfo jedisSysInfo;
	protected RedisConsumerInfo jedisConsumerInfo;
	protected RedisLock jedisLock;
	public boolean isMaster = false;
	protected RabbitMQClient rabbitClient;
	private Channel channel;
	private ConsumerInfo consumer;
	private SystemInfo sysInfo;
	private int currentDepth = 0;
	public static int current = 0;
	
	public Consumer() {
		app = ApplicationConfigurator.getInstance().getApplication();
		this.initialize();
		this.loadConfigFromJedis();
		this.getConsumerInfoAndUpdateToRedis();
	}

	public void initialize() {
		try {
			buildRabbiMQClient();
			buildRedisClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buildRabbiMQClient() throws IOException {
		rabbitClient = RabbitMQClient.getInstance();
		channel = rabbitClient.getChannel();
		for (int i = 0; i < SIZE; i++)
			rabbitClient.queueDeclare(app.getRabbitMQ().getQueueName() + i);
		rabbitClient.setDeliverCallBack(getDeliverCallback());
	}
	
	public void buildRedisClient() {
		jedisSet = new RedisQueueSet();
		jedisSysInfo = new RedisSystemInfo();
		jedisConsumerInfo = new RedisConsumerInfo();
		jedisLock = new RedisLock();
	}
	
	public synchronized boolean isConsuming() {
		return this.isConsuming;
	}
	
	public synchronized void setConsuming(boolean isConsuming) {
		this.isConsuming = isConsuming;
	}
	
	public void loadConfigFromJedis() {
		sysInfo = jedisSysInfo.getSystemInfo();
		currentDepth = sysInfo.getCurrentDepth();
	}
	
	public void getConsumerInfoAndUpdateToRedis() {
		int nConsumers = jedisConsumerInfo.getNumberOfConsumer();
		if (nConsumers > 0) {
			// TODO :: need to improve here
			current = 1;
			currentDepth += CaseStudy.DEPTH;
		}
		int id = UUID.randomUUID().hashCode();
		this.consumer = new ConsumerInfo(id, current);
		jedisConsumerInfo.updateConsumerInfo(consumer);
	}
	
	public DeliverCallback getDeliverCallback() {
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			OC config = SerializationUtils.deserialize(delivery.getBody());
			int depth = config.getCurrentDepth();
			logger.info(" [x] Received " + config + " at depth " + depth + ", queue " + getCurrentQueueName());
			
			this.jedisLock.requestCS(RedisLock.LOCK_MODIFY_INFO_FIELD);
			
			String configSha256 = GFG.getSHA(config.toString());
			if (!jedisSet.sismember(jedisSet.getDepthSetName(depth), configSha256)) {
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				logger.info("Dropped state with at depth " + depth);
				this.setConsuming(false);
				this.jedisLock.releaseCS(RedisLock.LOCK_MODIFY_INFO_FIELD);
				return;
			}
			
			if (isCheckedMessage(config)) {
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				logger.info("Duplicate state in previous layers at depth " + depth);
				this.setConsuming(false);
				this.jedisLock.releaseCS(RedisLock.LOCK_MODIFY_INFO_FIELD);
				return;
			}

			if (depth >= CaseStudy.MAX_DEPTH) {
				// Do not check these states anymore
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				logger.info("Should not have this state in " + getCurrentQueueName() + " at depth " + depth + " MAX_DEPTH " + CaseStudy.MAX_DEPTH);
				this.setConsuming(false);
				this.jedisLock.releaseCS(RedisLock.LOCK_MODIFY_INFO_FIELD);
				return;
			}
			this.saveToConsumingQueue(config);
			this.jedisLock.releaseCS(RedisLock.LOCK_MODIFY_INFO_FIELD);
			this.setConsuming(true);
			
			RunJPF runner = new RunJPF(config);
			runner.start();
			try {
				runner.join();
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				this.setConsuming(false);
				logger.info("Sending ack");
				synchronized (BmcConsumer.LOCK) {
					BmcConsumer.LOCK.notify();
				}
			} catch (InterruptedException e) {
				logger.error("waiting jpf and ack to rabbitmq " + e.getMessage());
				e.printStackTrace();
			}
		};
		return deliverCallback;
	}
	
	public void saveToConsumingQueue(OC config) {
		double percentage = getPercentageAtLayer();
		if (percentage >= 100) {
			return;
		}
		sysInfo = jedisSysInfo.getSystemInfo();
		
		// Need to improve here. please think
		if (sysInfo.getCurrentDepth() < currentDepth && currentDepth < CaseStudy.MAX_DEPTH) {
			logger.debug("Saving to consuming queue at " + sysInfo.getCurrentDepth());
			String configSha256 = GFG.getSHA(config.toString());
			jedisSet.sadd(jedisSet.getDepthSetConsumingName(currentDepth), configSha256);
		}
	}

	public void handle() {
		consumerTag = rabbitClient.basicConsume(getCurrentQueueName());
	}
	
	public void checkToChangeQueueOrNot() throws InterruptedException, IOException {
		logger.debug("Checking queues information at " + getCurrentQueueName());
		HashMap<String, RabbitQueue> queues = RabbitMQManagementAPI.getInstance().getQueueInfo();
		if (isEmtpyQueues(queues)) {
			System.exit(0);
		}
		if (allowToChangeQueue(queues) && currentDepth < CaseStudy.MAX_DEPTH) {
			jedisLock.requestCS(RedisLock.LOCK_MODIFY_INFO_FIELD);
			// increase the currentDepth
			currentDepth += CaseStudy.DEPTH;
			if (lastWorkerChangeQueue(queues)) {
				proceedRandom();
				// only last work update system information
				// moving to the next layer
				sysInfo.setCurrentLayer(sysInfo.getCurrentLayer() + 1);
				// update sysInfo in redis
				sysInfo.setCurrentDepth(currentDepth);
				jedisSysInfo.updateSystemInfo(sysInfo);
			}
			logger.debug("From: " + getCurrentQueueName());
			// stop consuming the current queue and move to the next queue
			tryToCancelConsumerTagAndReset();
			setNextCurrent();
			logger.debug("To " + getCurrentQueueName());
			// handle with new queue
			handle();
			jedisLock.releaseCS(RedisLock.LOCK_MODIFY_INFO_FIELD);
		}
	}
	
	public void proceedRandom() {
		double percentage = getPercentageAtLayer();
		assert percentage >= 0 && percentage <= 100 : "Percentage value is invalid";
		if (percentage == 100) {
			return;
		}
		logger.debug("Percentage " + percentage + " at depth " + currentDepth);
		
		// save for back-up
		jedisSet.sdiffstore(jedisSet.getDepthSetBackupName(currentDepth), jedisSet.getDepthSetName(currentDepth));
		// build distinct states at currentDepth
		int depth = 0;
		while (depth >= currentDepth) {
			jedisSet.sdiffstore(jedisSet.getDepthSetName(currentDepth), jedisSet.getDepthSetName(currentDepth), jedisSet.getDepthSetConsumingName(depth));
			depth += CaseStudy.DEPTH;
		}
		// calculate the number of removed states
		long numberOfStates = jedisSet.scard(jedisSet.getDepthSetName(currentDepth));
		long numberOfConsumingStates = jedisSet.scard(jedisSet.getDepthSetConsumingName(currentDepth));
		long numberOfKeepStates  = (long) Math.ceil(percentage * (1.0 * numberOfStates / 100));
		long numberOfRemovedStates = numberOfStates - numberOfKeepStates;
		
		logger.debug("numberOfStates = " + numberOfStates);
		logger.debug("numberOfConsumingStates = " + numberOfConsumingStates);
		logger.debug("numberOfKeepStates = " + numberOfKeepStates);
		logger.debug("numberOfRemovedStates = " + numberOfRemovedStates);
		
		if (numberOfConsumingStates < numberOfKeepStates) {
			jedisSet.sdiffstore(jedisSet.getDepthSetName(currentDepth), jedisSet.getDepthSetName(currentDepth), jedisSet.getDepthSetConsumingName(currentDepth));
			// remove some states
			jedisSet.spop(jedisSet.getDepthSetName(currentDepth), (int) numberOfRemovedStates);
		} else {
			// discard all states
			jedisSet.spop(jedisSet.getDepthSetName(currentDepth), (int) numberOfStates);
		}
		// unnecessary task ???
		// jedisSet.sunion(jedisSet.getDepthSetName(currentDepth), jedisSet.getDepthSetConsumingName(currentDepth));
	}
	
	public double getPercentageAtLayer() {
		int layer = sysInfo.getCurrentLayer();
		int size = CaseStudy.PERCENTAGES.size();
		if (size == 0) {
			return 100;
		}
		if (size < layer) {
			return CaseStudy.PERCENTAGES.get(size - 1);
		}
		return CaseStudy.PERCENTAGES.get(layer - 1);
	}
	
	
	public boolean lastWorkerChangeQueue(HashMap<String, RabbitQueue> queues) {
		ArrayList<ConsumerInfo> consumerList = jedisConsumerInfo.getConsumerInfo();
		int count = 0;
		for (int i = 0; i < consumerList.size(); i ++) {
			if (consumerList.get(i).getCurrent() == current) {
				count ++;
			}
		}
		return count == 1;
	}
	
	public boolean allowToChangeQueue(HashMap<String, RabbitQueue> queues) {
		RabbitQueue currentQueue = queues.get(getCurrentQueueName());
		RabbitQueue previousQueue = queues.get(getPreviousQueueName());
		if (!this.isConsuming() && currentQueue.isEmptyReadyMessages() && !previousQueue.containUnackMessages()) {
			return true;
		}
		return false;
	}			
	
	public String getPreviousQueueName() {
		return app.getRabbitMQ().getQueueName() + ((current + 2) % SIZE);
	}
	
	public String getCurrentQueueName() {
		return app.getRabbitMQ().getQueueName() + current;
	}
	
	public void resetConsumerTag() {
		consumerTag = "";
	}
	
	public void setNextCurrent() {
		current = (current + 1 ) % SIZE;
		consumer.setCurrent(current);
		jedisConsumerInfo.updateConsumerInfo(this.consumer);
	}

	public void tryToCancelConsumerTagAndReset() throws IOException {
		rabbitClient.cancelConsume(consumerTag);
		resetConsumerTag();
	}
	
	public boolean isEmtpyQueues(HashMap<String, RabbitQueue> queues) {
		for (int i = 0; i < SIZE; i ++) {
			if (!queues.get(app.getRabbitMQ().getQueueName() + i).isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isCheckedMessage(OC config) {
		int depth = config.getCurrentDepth();
		if (depth == 0)
			return false;
		String configSha256 = GFG.getSHA(config.toString());
		return checkExistingInPreviousLayers(configSha256, depth, 0, CaseStudy.DEPTH);
	}
	
	public boolean checkExistingInPreviousLayers(String member, int currDepth, int startDepth, int stepDepth) {
		// TODO :: need to improve when using various depths
		while (currDepth - stepDepth >= startDepth) {
			currDepth -= stepDepth;
			if (jedisSet.sismember(jedisSet.getDepthSetName(startDepth), member))
				return true;
		}
		return false;
	}
}
