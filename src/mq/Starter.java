package mq;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import application.service.SequenceStatesService;
import database.RedisClient;
import jpf.common.OC;
import server.Application;
import server.ApplicationConfigurator;

/**
 * Starter program to kick-off environment by sending the initial message to
 * RabbitMQ master
 * 
 * @author OgataLab
 *
 */
public class Starter {

	/**
	 * Connecting to RabbitMQ and send initial message
	 * 
	 * @param argv Unused.
	 */
	public static void main(String[] argv) {
		try {
			// Initialize application with configuration
			Application app = ApplicationConfigurator.getInstance().getApplication();

			cleanUp(app);

			// Push a initial job to message queue
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(app.getRabbitMQ().getHost());
			if (app.getServerFactory().isRemote()) {
				factory.setUsername(app.getRabbitMQ().getUserName());
				factory.setPassword(app.getRabbitMQ().getPassword());
			}

			try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
				channel.queueDeclare(app.getRabbitMQ().getQueueName(), false, false, false, null);

				// prepare to send a message to queue
				OC config = app.getCaseStudy().getInitialMessage();

				byte[] data = SerializationUtils.serialize(config);

				channel.basicPublish("", app.getRabbitMQ().getQueueName(), null, data);
				System.out.println(" [x] Sent '" + config);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clean up before starting environment
	 * 
	 * @param app
	 */
	public static void cleanUp(Application app) {
		try {
			// Clean up before kicking off environment

			// Purge queue from RabbitMQ
			if (!app.getServerFactory().isRemote()) {
				Process p1 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
						+ app.getCaseStudy().getQueueName());
				
				Process p2 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name="
						+ app.getCaseStudy().getMaudeQueue());
				
				p1.waitFor();
				p2.waitFor();
			}

			// Flush all keys and values from Redis server
			RedisClient.getInstance(app.getRedis().getHost(), app.getRedis().getPort()).getConnection().flushAll();
			
			// Truncate `sequence_states` table before running
			if (SequenceStatesService.truncate())
				System.out.println("Truncate successfully !!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
