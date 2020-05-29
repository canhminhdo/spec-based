package checker.bmc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import application.model.ConsumerStatus;
import application.model.RabbitConsumer;
import application.model.RabbitQueue;
import application.model.SystemInfo;
import config.CaseStudy;
import jpf.common.OC;
import mq.RabbitMQClient;
import mq.RunJPF;
import mq.api.RabbitMQManagementAPI;
import redis.api.RedisConsumerStatus;
import redis.api.RedisQueueSet;
import redis.api.RedisSystemInfo;
import server.Application;
import server.ApplicationConfigurator;
import utils.GFG;

public class Consumer {
	
	protected static Logger logger = (Logger) LogManager.getLogger();
	public static Object lock = new Object();
	public static int current = 0;
	protected final static int SIZE = 3;
	protected HashMap<Integer, Integer> analyzer = new HashMap<Integer, Integer>();
	protected boolean isConsuming = false;
	protected Application app;
	protected String consumerTag;
	protected RedisQueueSet jedisSet;
	protected RedisSystemInfo jedisSysInfo;
	protected RedisConsumerStatus jedisConsumerStatus;
	public boolean isMaster = false;
	protected RabbitMQClient rabbitClient;
	private Channel channel;
	private ConsumerStatus consumer;
	private SystemInfo sysInfo;
	
	public Consumer() {
		app = ApplicationConfigurator.getInstance().getApplication();
		this.initialize();
		this.loadConfigFromJedis();
		this.getConsumerStatusAndUpdateToRedis();
		this.setCurrent();
		if (this.isMaster) logger.debug("I am master worker");
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
		jedisConsumerStatus = new RedisConsumerStatus();
	}
	
	public synchronized boolean isConsuming() {
		return this.isConsuming;
	}
	
	public synchronized void setConsuming(boolean isConsuming) {
		this.isConsuming = isConsuming;
	}
	
	public void loadConfigFromJedis() {
		sysInfo = jedisSysInfo.getSystemInfo();
		CaseStudy.SYSTEM_MODE = sysInfo.getMode();
		CaseStudy.CURRENT_DEPTH = sysInfo.getCurrentDepth();
		CaseStudy.CURRENT_MAX_DEPTH = sysInfo.getCurrentMaxDepth();
	}
	
	public void getConsumerStatusAndUpdateToRedis() {
		int nConsumers = jedisConsumerStatus.getNumberOfConsumer();
		String id = String.valueOf(nConsumers + 1);
		this.consumer = new ConsumerStatus(id, ConsumerStatus.WORKING_STATUS);
		jedisConsumerStatus.updateConsumerStatus(consumer);
	}
	
	public DeliverCallback getDeliverCallback() {
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			OC config = SerializationUtils.deserialize(delivery.getBody());
			int currentDepth = config.getCurrentDepth();
			logger.info(" [x] Received " + config + " at depth " + currentDepth);
			
			if (CaseStudy.RANDOM_MODE && currentDepth == CaseStudy.MAX_DEPTH) {
				String configSha256 = GFG.getSHA(config.toString());
				if (!jedisSet.sismember(jedisSet.getDepthSetName(currentDepth), configSha256)) {
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
					logger.info("Dropped state with at depth " + currentDepth);
					this.setConsuming(false);
					return;
				}
			}
			
			if (isCheckedMessage(config)) {
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				logger.info("Duplicate state in previous layers at depth " + currentDepth);
				this.setConsuming(false);
				return;
			}

			if (currentDepth >= CaseStudy.CURRENT_MAX_DEPTH) {
				// Do not check these states anymore
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				logger.info("Should not have this state in " + getCurrentQueueName() + " at depth " + currentDepth + " CURRENT_MAX_DEPTH " + CaseStudy.CURRENT_MAX_DEPTH);
				this.setConsuming(false);
				return;
			}

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

	public void setCurrent() {
		if (consumer.getId().equals("1")) {
			current = 0;
			this.isMaster = true;
		}
		ArrayList<RabbitConsumer> consumers = RabbitMQManagementAPI.getInstance().getConsumerInfo();
		current = getMaxCurrentQueue(consumers);
//		HashMap<String, RabbitQueue> queues = RabbitMQManagementAPI.getInstance().getQueueInfo();
//		if (allowToChangeQueue(queues))
//			moveToNextCurrent();
	}
	
	public int getMaxCurrentQueue(ArrayList<RabbitConsumer> consumers) {
		int maxCurrent = 0;
		for (int i = 0; i < consumers.size(); i ++) {
			String queueName = consumers.get(i).getQueueName();
			if (matchedQueueName(queueName)) {
				String strCurrent = queueName.substring(app.getRabbitMQ().getQueueName().length());
				int current = Integer.valueOf(strCurrent);
				if (maxCurrent < current)
					maxCurrent = current;
			}
		}
		return maxCurrent;
	}
	
	public boolean matchedQueueName(String queueName) {
		return queueName.matches("^[a-zA-Z]+[0-9]+$");
	}
	
	public void handle() {
		consumerTag = rabbitClient.basicConsume(getCurrentQueueName());
	}
	
	public void checkToChangeQueueOrNot() throws InterruptedException, IOException {
		logger.debug("Checking queues information at " + getCurrentQueueName());
		HashMap<String, RabbitQueue> queues = RabbitMQManagementAPI.getInstance().getQueueInfo();
		if (isEmtpyQueues(queues)) {
			if (!CaseStudy.RANDOM_MODE)
				return;
			
			// BMC with Random Mode
			if (this.isMaster && !CaseStudy.SYSTEM_MODE.equals(SystemInfo.BMC_RANDOM_MODE)) {
				if (consumer.isWorking()) {
					// Should do only one time.
					tryToCancelConsumerTagAndReset();
					updateConsumerToStopStatus();
					buildDistinctStateSet();
					buildRandomStates();
					logger.info(consumer);
				}
				
				if (consumer.isStop() && checkAllConsumerStopping()){
					// If all consumers are stopping, should do only one time
					resetCurrent();
					updateConsumerToWorkingStatus();
					moveMessagesToCurrentQueue();
					saveRandomConfigToRedis();
					loadConfigFromJedis();
					handle();
					logger.info(consumer);
					logger.info("Starting random mode at " + getCurrentQueueName());
				}
			}
			
			if (!this.isMaster) {
				if (consumer.isWorking() && !CaseStudy.SYSTEM_MODE.equals(SystemInfo.BMC_RANDOM_MODE)) {
					// Should do only one time
					tryToCancelConsumerTagAndReset();
					updateConsumerToStopStatus();
					logger.info(consumer);
				}
				
				if (consumer.isStop()) {
					loadConfigFromJedis();
					if (CaseStudy.SYSTEM_MODE.equals(SystemInfo.BMC_RANDOM_MODE)) {
						updateConsumerToWorkingStatus();
						setCurrent();
						handle();
						logger.info(consumer);
						logger.info("Starting random mode at " + getCurrentQueueName());
					}
				}
			}
			return;
		}
		
		if (allowToChangeQueue(queues)) {
			logger.debug("From: " + getCurrentQueueName());
			tryToCancelConsumerTagAndReset();
			moveToNextCurrent();
			logger.debug("To " + getCurrentQueueName());
			handle();
		}
	}
	
	public boolean checkAllConsumerStopping() {
		if (consumer.isWorking())
			return false;
		ArrayList<ConsumerStatus> consumerList = jedisConsumerStatus.getConsumerStatus();
		for (int i = 0; i < consumerList.size(); i++) {
			if (consumerList.get(i).isWorking())
				return false;
		}
		return true;
	}
	
	public void updateConsumerToStopStatus() {
		consumer.setToStopStatus();
		jedisConsumerStatus.updateConsumerStatus(consumer);
	}
	
	public void updateConsumerToWorkingStatus() {
		consumer.setToWorkingStatus();
		jedisConsumerStatus.updateConsumerStatus(consumer);
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
	
	public void resetCurrent() {
		current = 0;
	}
	
	public void resetConsumerTag() {
		consumerTag = "";
	}
	
	public void moveToNextCurrent() {
		current = (current + 1) % SIZE;
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
		int currentDepth = config.getCurrentDepth();
		if (currentDepth == 0)
			return false;
		if (!CaseStudy.IS_BOUNDED_MODEL_CHECKING)
			return false;
		String configSha256 = GFG.getSHA(config.toString());
		if (currentDepth <= CaseStudy.MAX_DEPTH) {
			return checkExistingInPreviousLayers(configSha256, currentDepth, 0, CaseStudy.DEPTH);
		}
		return checkExistingInPreviousLayers(configSha256, currentDepth, CaseStudy.MAX_DEPTH, CaseStudy.RANDOM_DEPTH) || 
				checkExistingInPreviousLayers(configSha256, currentDepth, 0, CaseStudy.DEPTH);
	}
	
	public boolean checkExistingInPreviousLayers(String member, int currentDepth, int startDepth, int stepDepth) {
		while (currentDepth - stepDepth >= startDepth) {
			currentDepth -= stepDepth;
			if (jedisSet.sismember(jedisSet.getDepthSetName(startDepth), member))
				return true;
		}
		return false;
	}
	
	public void buildDistinctStateSet() {
		int stepSize = CaseStudy.DEPTH;
		int startDepth = 0;
		int maxDepth = CaseStudy.CURRENT_MAX_DEPTH;
		while (startDepth <= maxDepth) {
			buildDistinctStateAtDepth(startDepth);
			startDepth += stepSize;
		}
	}
	
	public void buildDistinctStateAtDepth(int maxDepth) {
		int stepSize = CaseStudy.DEPTH;
		int startDepth = 0;
		ArrayList<String> setKeys = new ArrayList<String>();
		setKeys.add(jedisSet.getDepthSetName(maxDepth));
		while (startDepth < maxDepth) {
			setKeys.add(jedisSet.getDepthSetName(startDepth));
			startDepth += stepSize;
		}
		String[] keysParams = setKeys.toArray(new String[0]);
		jedisSet.sdiffstore(jedisSet.getDepthSetName(maxDepth), keysParams);
	}
	
	public void buildRandomStates() {
		int maxDepth = CaseStudy.MAX_DEPTH;
		long numberOfState = jedisSet.scard(jedisSet.getDepthSetName(maxDepth));
		int percentage = CaseStudy.RANDOM_PERCENTAGE;
		assert percentage >= 0 && percentage <= 100 : "percentage is invalid";
		int numberOfRemovedState = (int) Math.round((100 - percentage) * numberOfState / 100);
		jedisSet.sdiffstore(jedisSet.getDepthSetName(maxDepth) + "-backup", jedisSet.getDepthSetName(maxDepth));
		jedisSet.spop(jedisSet.getDepthSetName(maxDepth), numberOfRemovedState);
	}
	
	public void moveMessagesToCurrentQueue() throws ClientProtocolException, IOException {
		String sourceQueue = app.getRabbitMQ().getQueueNameAtDepth();
		String destQueue = getCurrentQueueName();
		RabbitMQManagementAPI.getInstance().moveMessagesBetweenQueues(sourceQueue, destQueue);
	}

	public void saveRandomConfigToRedis() {
		sysInfo.setMode(SystemInfo.BMC_RANDOM_MODE);
		sysInfo.setCurrentDepth(CaseStudy.RANDOM_DEPTH);
		sysInfo.setCurrentMaxDepth(CaseStudy.RANDOM_MAX_DEPTH);
		jedisSysInfo.updateSystemInfo(sysInfo);
	}
}
