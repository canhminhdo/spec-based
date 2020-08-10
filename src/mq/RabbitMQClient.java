package mq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.MessageProperties;

import server.Application;
import server.ApplicationConfigurator;

public class RabbitMQClient {
	
	protected static Logger logger = (Logger) LogManager.getLogger();
	private static RabbitMQClient _instance;
	private Application app;
	private Connection connection;
	private Channel channel;
	private DeliverCallback deliverCallback;

	private RabbitMQClient() {
		try {
			app = ApplicationConfigurator.getInstance().getApplication();
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(app.getRabbitMQ().getHost());
			if (app.getServerFactory().isRemote()) {
				factory.setUsername(app.getRabbitMQ().getUserName());
				factory.setPassword(app.getRabbitMQ().getPassword());
			}
			connection = factory.newConnection();
			channel = connection.createChannel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static RabbitMQClient getInstance() {
		if (_instance == null) {
			_instance = new RabbitMQClient();
		}
		return _instance;
	}

	public void queueDeclare(String[] queueList) throws IOException {
		// prefetch count
		channel.basicQos(1);
		// enable lazy queues
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-queue-mode", "lazy");
		boolean durable = true;
		for (int i = 0; i < queueList.length; i++) {
			channel.queueDeclare(queueList[i], durable, false, false, args);
		}
	}

	public void queueDeclare(String queueName) throws IOException {
		channel.basicQos(1);
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-queue-mode", "lazy");
		boolean durable = true;
		channel.queueDeclare(queueName, durable, false, false, args);
	}

	public void setDeliverCallBack(DeliverCallback deliverCallback) {
		this.deliverCallback = deliverCallback;
	}

	public String basicConsume(String queueName) {
		String consumerTag = "";
		assert deliverCallback != null : "DeliverCallback needs be set beforehand";
		try {
			consumerTag = channel.basicConsume(queueName, false, deliverCallback, (conTag) -> {
				logger.warn("Receiver consumer cancelling " + conTag);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumerTag;
	}
	
	public void basicPublish(String queueName, byte[] body) throws IOException {
		channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, body);
	}

	public void cancelConsume(String consumerTag) {
		try {
			if (!consumerTag.isEmpty())
				channel.basicCancel(consumerTag);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Channel getChannel() {
		return this.channel;
	}

	public void close() {
		try {
			this.channel.close();
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
