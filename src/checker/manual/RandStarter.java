package checker.manual;

import java.io.IOException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.rabbitmq.client.Channel;

import application.model.SystemInfo;
import checker.factory.StarterFactory;
import config.CaseStudy;
import jpf.common.OC;
import mq.RabbitMQClient;
import redis.api.RedisLock;
import redis.api.RedisQueueSet;
import redis.api.RedisStoreStates;
import redis.api.RedisSystemInfo;
import utils.GFG;
import utils.SerializationUtilsExt;

public class RandStarter extends StarterFactory {
	protected static Logger logger = (Logger) LogManager.getLogger();
	RabbitMQClient rabbitClient;
	Channel channel;
	int currentDepth;
	double percentage;
	RedisQueueSet jedisSet = null;
	RedisStoreStates jedisHash = null;
	int current = 0;

	public RandStarter() {
		super();
	}
	
	public RandStarter(int currentDepth, double percentage) {
		super();
		this.currentDepth = currentDepth;
		this.percentage = percentage;
		jedisSet = new RedisQueueSet();
		jedisHash = new RedisStoreStates();
	}

	@Override
	public void start() {
		cleanUp();
		pushInitialJob();
//		saveInitialMessageToRedis();
//		initializeRedisSysInfo();
	}

	@Override
	public void cleanUp() {
		try {
			this.cleanRabbitMQAndLog();
			this.trunscateDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cleanRabbitMQAndLog() throws IOException, InterruptedException {
		if (!app.getServerFactory().isRemote()) {
			Process p1 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
					+ app.getRabbitMQ().getQueueName() + "0");

			Process p2 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
					+ app.getRabbitMQ().getQueueName() + "1");

			Process p3 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
					+ app.getRabbitMQ().getQueueName() + "2");

			p1.waitFor();
			p2.waitFor();
			p3.waitFor();
		}
	}

	@Override
	public void pushInitialJob() {
		try {
			// TODO :: we need to do pick randomly here and back up also
//			rabbitClient = RabbitMQClient.getInstance();
//			channel = rabbitClient.getChannel();
//			String queueName = app.getRabbitMQ().getQueueName() + current;
//			rabbitClient.queueDeclare(queueName);
			Set<String> states = jedisSet.smembers(jedisSet.getDepthSetName(currentDepth));
			for (String key : states) {
				String state = jedisHash.hget(jedisHash.getStoreNameAtDepth(currentDepth), key);
				OC message = SerializationUtilsExt.deserialize(state);
//				rabbitClient.basicPublish(queueName, SerializationUtils.serialize(message));
				logger.info("key = " + key);
				logger.info(" [x] Sent '" + message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public void proceedRandom() {
		assert percentage >= 0 && percentage <= 100 : "Percentage value is invalid";
		// save for back-up
		if (jedisSet.scard(jedisSet.getDepthSetBackupName(currentDepth)) == 0) {
			// create a backup set from a current depth set
			jedisSet.sdiffstore(jedisSet.getDepthSetBackupName(currentDepth), jedisSet.getDepthSetName(currentDepth));
		} else {
			// copy from a backup set to the current depth set
			jedisSet.sdiffstore(jedisSet.getDepthSetName(currentDepth), jedisSet.getDepthSetBackupName(currentDepth));
		}
		// build distinct states at currentDepth
		int depth = 0;
		while (depth < currentDepth) {
			jedisSet.sdiffstore(jedisSet.getDepthSetName(currentDepth), jedisSet.getDepthSetName(depth));
			depth += CaseStudy.DEPTH;
		}
		// calculate the number of removed states
		long numberOfStates = jedisSet.scard(jedisSet.getDepthSetName(currentDepth));
		long numberOfKeepStates  = (long) Math.ceil(percentage * (1.0 * numberOfStates / 100));
		long numberOfRemovedStates = numberOfStates - numberOfKeepStates;
		
		logger.debug("numberOfStates = " + numberOfStates);
		logger.debug("numberOfKeepStates = " + numberOfKeepStates);
		logger.debug("numberOfRemovedStates = " + numberOfRemovedStates);
		
		// remove some states
		jedisSet.spop(jedisSet.getDepthSetName(currentDepth), (int) numberOfRemovedStates);
	}

	@Override
	public void saveInitialMessageToRedis() {
		OC config = app.getCaseStudy().getInitialMessage();
		String configSha256 = GFG.getSHA(config.toString());
		RedisQueueSet jedis = new RedisQueueSet();
		RedisStoreStates jedisHash = new RedisStoreStates();
		// saving to set of hash of states at a depth
		jedis.sadd(jedis.getDepthSetName(config.getCurrentDepth()), configSha256);
		// saving to a hash table where key is the hash of a state, value is the encoded string of a state
		jedisHash.hset(jedisHash.getStoreNameAtDepth(config.getCurrentDepth()), configSha256, SerializationUtilsExt.serializeToStr(config));
	}

	@Override
	public void initializeRedisSysInfo() {
		RedisSystemInfo jedisSysInfo = new RedisSystemInfo();
		jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.MODE_KEY, SystemInfo.BMC_MODE);
		jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.CURRENT_DEPTH_KEY, "0");
		jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.CURRENT_LAYER_KEY, "1");
		RedisLock jedisLock = new RedisLock();
		jedisLock.releaseCS(RedisLock.LOCK_MODIFY_INFO_FIELD);
	}
}
