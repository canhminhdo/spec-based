package mq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import application.model.SystemInfo;
import application.service.SequenceStatesService;
import config.CaseStudy;
import database.RedisClient;
import jpf.common.OC;
import redis.api.RedisQueueSet;
import redis.api.RedisSystemInfo;
import redis.clients.jedis.Jedis;
import server.Application;
import server.ApplicationConfigurator;
import utils.GFG;

/**
 * Starter program to kick-off environment by sending the initial message to
 * RabbitMQ master
 * 
 * @author OgataLab
 *
 */
public class Starter {
	
	private static Logger logger = (Logger) LogManager.getLogger();
	private static Application app = ApplicationConfigurator.getInstance().getApplication();
	/**
	 * Connecting to RabbitMQ and send initial message
	 * 
	 * @param argv Unused.
	 */
	public static void main(String[] argv) {
		try {
			cleanUp();
			
			pushInitialJob();
			
			saveInitialMessageToRedis();
			
			initializeRedisSysInfo();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	
	public static void saveInitialMessageToRedis() {
		OC config = app.getCaseStudy().getInitialMessage();
		String configSha256 = GFG.getSHA(config.toString());
		Jedis jedis_instance = RedisClient.getInstance(app.getRedis().getHost(), app.getRedis().getPort()).getConnection();
		RedisQueueSet jedis = new RedisQueueSet(jedis_instance);
		jedis.sadd(jedis.getDepthSetName(config.getCurrentDepth()), configSha256);
	}
	
	public static void initializeRedisSysInfo() {
		Jedis jedis_instance = RedisClient.getInstance(app.getRedis().getHost(), app.getRedis().getPort()).getConnection();
		RedisSystemInfo jedisSysInfo = new RedisSystemInfo(jedis_instance);
		if (CaseStudy.IS_BOUNDED_MODEL_CHECKING) {
			jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.MODE_KEY, SystemInfo.BMC_MODE);
			jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.CURRENT_DEPTH_KEY, String.valueOf(CaseStudy.DEPTH));
			jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.CURRENT_MAX_DEPTH_KEY, String.valueOf(CaseStudy.MAX_DEPTH));
		} else {
			jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.MODE_KEY, SystemInfo.NO_BMC_MODE);
			jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.CURRENT_DEPTH_KEY, String.valueOf(CaseStudy.DEPTH));
			jedisSysInfo.hset(RedisSystemInfo.SYSTEM_KEY, SystemInfo.CURRENT_MAX_DEPTH_KEY, String.valueOf(Integer.MAX_VALUE));
		}
	}
	
	public static void pushInitialJob() throws IOException, TimeoutException {
		// Push a initial job to message queue
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(app.getRabbitMQ().getHost());
		if (app.getServerFactory().isRemote()) {
			factory.setUsername(app.getRabbitMQ().getUserName());
			factory.setPassword(app.getRabbitMQ().getPassword());
		}
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		// prepare to send a message to queue
		OC config = app.getCaseStudy().getInitialMessage();
		String queueName = app.getRabbitMQ().getQueueName() + config.getCurrentDepth();
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-queue-mode", "lazy");
		channel.queueDeclare(queueName, false, false, false, args);
		channel.basicPublish("", queueName, null, SerializationUtils.serialize(config));
		logger.info(" [x] Sent '" + config);
	}
	/**
	 * Clean up before starting environment
	 * 
	 * @param app
	 */
	public static void cleanUp() {
		try {
			// Clean up before kicking off environment

			// Purge queue from RabbitMQ
			if (!app.getServerFactory().isRemote()) {
				Process p1 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
						+ app.getRabbitMQ().getQueueName());
				
				Process p2 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
						+ app.getRabbitMQ().getMaudeQueue());
				
				Process p3 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
						+ app.getRabbitMQ().getQueueNameAtDepth());
				
				Process p4 = Runtime.getRuntime().exec("rm -rf " + CaseStudy.LOG4J_PATH);
				
				Process p5 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
						+ app.getRabbitMQ().getQueueName() + "0");
				
				Process p6 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
						+ app.getRabbitMQ().getQueueName() + "1");
				
				Process p7 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
						+ app.getRabbitMQ().getQueueName() + "2");
				
				p1.waitFor();
				p2.waitFor();
				p3.waitFor();
				p4.waitFor();
				p5.waitFor();
				p6.waitFor();
				p7.waitFor();
			}

			// Flush all keys and values from Redis server
			RedisClient.getInstance(app.getRedis().getHost(), app.getRedis().getPort()).getConnection().flushAll();
			
			// Truncate `sequence_states` table before running
			if (CaseStudy.MYSQL_IS_ENABLE && SequenceStatesService.truncate())
				System.out.println("Truncate successfully !!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
