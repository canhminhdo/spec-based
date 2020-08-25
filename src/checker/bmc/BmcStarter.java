package checker.bmc;

import java.io.IOException;

import org.apache.commons.lang3.SerializationUtils;
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

public class BmcStarter extends StarterFactory {
	protected static Logger logger = (Logger) LogManager.getLogger();
	RabbitMQClient rabbitClient;
	Channel channel;

	public BmcStarter() {
		super();
	}

	@Override
	public void start() {
		cleanUp();
		pushInitialJob();
		saveInitialMessageToRedis();
		initializeRedisSysInfo();
	}

	@Override
	public void cleanUp() {
		try {
			this.cleanRabbitMQAndLog();
			this.cleanRedis();
			this.trunscateDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cleanRabbitMQAndLog() throws IOException, InterruptedException {
		if (!app.getServerFactory().isRemote()) {
			Process p1 = Runtime.getRuntime().exec(
					"/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name=" + app.getRabbitMQ().getQueueName());
			
			Process p2 = Runtime.getRuntime().exec(
					"/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name=" + app.getRabbitMQ().getMaudeQueue());

			Process p3 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
					+ app.getRabbitMQ().getQueueName() + "0");

			Process p4 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
					+ app.getRabbitMQ().getQueueName() + "1");

			Process p5 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
					+ app.getRabbitMQ().getQueueName() + "2");
			
			Process p6 = Runtime.getRuntime().exec("rm -rf " + CaseStudy.LOG4J_PATH);

			p1.waitFor();
			p2.waitFor();
			p3.waitFor();
			p4.waitFor();
			p5.waitFor();
			p6.waitFor();
		}
	}

	@Override
	public void pushInitialJob() {
		try {
			rabbitClient = RabbitMQClient.getInstance();
			channel = rabbitClient.getChannel();
			OC message = app.getCaseStudy().getInitialMessage();
			String queueName = app.getRabbitMQ().getQueueName() + message.getCurrentDepth();
			rabbitClient.queueDeclare(queueName);
			rabbitClient.basicPublish(queueName, SerializationUtils.serialize(message));
			logger.info(" [x] Sent '" + message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

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
		if (app.getCaseStudy().isStoreStatesInRedis()) {
			jedisHash.hset(jedisHash.getStoreNameAtDepth(config.getCurrentDepth()), configSha256, SerializationUtilsExt.serializeToStr(config));
		}
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
