package checker.nonbmc;

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
import redis.api.RedisQueueSet;
import redis.api.RedisSystemInfo;
import utils.GFG;

public class NonBmcStarter extends StarterFactory {
	
	protected static Logger logger = (Logger) LogManager.getLogger();
	RabbitMQClient rabbitClient;
	Channel channel;

	public NonBmcStarter() {
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

			Process p3 = Runtime.getRuntime().exec("rm -rf " + CaseStudy.LOG4J_PATH);

			p1.waitFor();
			p2.waitFor();
			p3.waitFor();
		}
	}

	@Override
	public void pushInitialJob() {
		try {
			rabbitClient = RabbitMQClient.getInstance();
			channel = rabbitClient.getChannel();
			String queueName = app.getRabbitMQ().getQueueName();
			rabbitClient.queueDeclare(queueName);
			OC config = app.getCaseStudy().getInitialMessage();
			rabbitClient.basicPublish(queueName, SerializationUtils.serialize(config));
			logger.info(" [x] Sent '" + config);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void saveInitialMessageToRedis() {
		OC config = app.getCaseStudy().getInitialMessage();
		String configSha256 = GFG.getSHA(config.toString());
		RedisQueueSet jedis = new RedisQueueSet();
		jedis.sadd(app.getRabbitMQ().getQueueName(), configSha256);
	}

	@Override
	public void initializeRedisSysInfo() {
		RedisSystemInfo jedisSysInfo = new RedisSystemInfo();
		jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.MODE_KEY, SystemInfo.NO_BMC_MODE);
		jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.CURRENT_DEPTH_KEY, String.valueOf(CaseStudy.DEPTH));
		jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.CURRENT_MAX_DEPTH_KEY, String.valueOf(Integer.MAX_VALUE));
	}
}
