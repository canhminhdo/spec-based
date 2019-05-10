package mq;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import jpf.Configuration;

public class Sender extends RabbitMQ {
	private static Sender _instance = null;
	private Connection connection;
	private Channel channel;

	private Sender() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(getHost());
			if (isRemote()) {
				factory.setUsername(USERNAME);
				factory.setPassword(PASSWORD);
			}
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Sender getInstance() {
		if (_instance == null)
			_instance = new Sender();

		return _instance;
	}

	public void sendJob(Configuration<String> config) throws Exception {
		channel.basicPublish("", QUEUE_NAME, null, SerializationUtils.serialize(config));
		System.out.println(" [x] Sent '" + config);
	}
	
	public void close() throws Exception {
		_instance = null;
		channel.close();
		connection.close();
	}
}
