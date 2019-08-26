package mq;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import config.ABPStudy;
import config.CaseStudy;
import database.RedisClient;
import jpf.common.OC;
import server.Application;
import server.ApplicationConfigurator;

public class Starter {

	public static void main(String[] argv) {
		try {
			// Initialize application with configuration
			CaseStudy cs = new ABPStudy();
			Application app = ApplicationConfigurator.getInstance(cs).getApplication();
			
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
				OC config = cs.getInitialMessage();
				
				byte[] data = SerializationUtils.serialize(config);

				channel.basicPublish("", app.getRabbitMQ().getQueueName(), null, data);
				System.out.println(" [x] Sent '" + config);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cleanUp(Application app) {
		try {
			// Clean up before kicking off environment
	
			// Remove all old data in "maude" folder. If you write state sequences to file systems
			Process p1 = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", "rm " + app.getCaseStudy().getMaudePath() });
	
			// Purge queue
			Process p2 = null;
			if (!app.getServerFactory().isRemote())
				p2 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name=" + app.getRabbitMQ().getQueueName());
	
			// Flush all keys and values from redis server
			RedisClient.getInstance(app.getRedis().getHost(), app.getRedis().getPort()).getConnection().flushAll();
	
			// Wait
			p1.waitFor();
			if (!app.getServerFactory().isRemote())
				p2.waitFor();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
