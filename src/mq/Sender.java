package mq;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import jpf.abp.Configuration;
import server.Application;
import server.ApplicationConfigurator;
import server.factory.ServerFactory;
import server.instances.RabbitMQ;

public class Sender {
	private static Sender _instance = null;
	private Connection connection;
	private Channel channel;
	private RabbitMQ rabbitMQ;

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
		channel.basicPublish("", this.rabbitMQ.getQueueName(), null, SerializationUtils.serialize(config));
		System.out.println(" [x] Sent '" + config);
	}
	
	public void close() throws Exception {
		_instance = null;
		channel.close();
		connection.close();
	}
}
