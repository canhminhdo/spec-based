package mq;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import config.CaseStudy;
import jpf.common.OC;
import server.Application;
import server.ApplicationConfigurator;
import server.factory.ServerFactory;
import server.instances.RabbitMQ;
import utils.AES;

/**
 * Sending message back to RabbitMQ master from RabbitMQ client
 * 
 * @author OgataLab
 *
 */
public class Sender {
	private static Sender _instance = null;
	private Connection connection;
	private Channel channel;
	private RabbitMQ rabbitMQ;

	/**
	 * Connecting to RabbitMQ master
	 */
	private Sender() {
		try {
			Application app = ApplicationConfigurator.getInstance().getApplication();
			ServerFactory serverFactory = app.getServerFactory();
			this.rabbitMQ = app.getRabbitMQ();

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(this.rabbitMQ.getHost());
			if (serverFactory.isRemote()) {
				factory.setUsername(this.rabbitMQ.getUserName());
				factory.setPassword(this.rabbitMQ.getPassword());
			}
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(this.rabbitMQ.getQueueName(), false, false, false, null);
			channel.queueDeclare(this.rabbitMQ.getMaudeQueue(), false, false, false, null);
		} catch (Exception e) {
			System.out.println("Cannot create a connection to RabbitMQ server");
			e.printStackTrace();
		}
	}

	/**
	 * Get singleton Sender instance
	 * 
	 * @return {@link Sender}
	 */
	public static Sender getInstance() {
		if (_instance == null)
			_instance = new Sender();

		return _instance;
	}

	/**
	 * Send message to RabbitMQ master
	 * 
	 * @param config
	 * @throws Exception
	 */
	public void sendJob(OC config) {
		try {
			channel.basicPublish("", this.rabbitMQ.getQueueName(), null, SerializationUtils.serialize(config));
			System.out.println(" [x] Sent '" + config);
		} catch (Exception e) {
			System.out.println("Cannot send message on queue " + this.rabbitMQ.getQueueName());
			e.printStackTrace();
		}
	}
	
	/**
	 * Send message to RabbitMQ master for Maude program
	 * 
	 * @param config
	 * @throws Exception
	 */
	public void sendMaudeJob(String seq) {
		try {
			// Encrypt before sending
			String cipher = AES.encrypt(seq, CaseStudy.SECRETE_KEY);
			channel.basicPublish("", this.rabbitMQ.getMaudeQueue(), null, SerializationUtils.serialize(seq));
			System.out.println(" [x] Sent to Maude '" + seq);
		} catch (Exception e) {
			System.out.println("Cannot send message on queue " + this.rabbitMQ.getMaudeQueue());
			e.printStackTrace();
		}
		
	}

	/**
	 * Closing singleton Sender instance
	 * 
	 * @throws Exception
	 */
	public void close() {
		try {
			channel.close();
			connection.close();
			_instance = null;
		} catch (Exception e) {
			System.out.println("Cannot close the channel and connection");
			e.printStackTrace();
		}
	}
}
