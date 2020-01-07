package mq;

import java.util.HashMap;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import config.CaseStudy;
import jpf.common.OC;
import server.Application;
import server.ApplicationConfigurator;
import utils.DateUtil;

/**
 * Receiver program as RabbitMQ client Whenever receiving a message from
 * RabbitMQ master Start internally JPF program to generate state sequences
 * 
 * @author OgataLab
 *
 */
public class Receiver {

	/**
	 * Starting a RabbitMQ client.
	 * 
	 * @param argv Unsed.
	 * @throws Exception
	 */
	public static void main(String[] argv) throws Exception {
		// Initialize application with configuration
		Application app = ApplicationConfigurator.getInstance().getApplication();

		// rabbitMQ connection
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(app.getRabbitMQ().getHost());
		if (app.getServerFactory().isRemote()) {
			factory.setUsername(app.getRabbitMQ().getUserName());
			factory.setPassword(app.getRabbitMQ().getPassword());
		}
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		// setting prefetch count: how many messages are being sent to the consumer at the same time.
		channel.basicQos(1);
		
		channel.queueDeclare(app.getRabbitMQ().getQueueName(), false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
		// HashTable to analyze the number of state at each depth
		HashMap<Integer,Integer> analyzer = new HashMap<Integer,Integer>();
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			
			OC config = SerializationUtils.deserialize(delivery.getBody());
			System.out.println("JPF started at " + DateUtil.getDateTimeString());
			System.out.println(" [x] Received '" + config + " at depth " + config.getCurrentDepth());
			
			if (analyzer.containsKey(config.getCurrentDepth())) {
				analyzer.put(config.getCurrentDepth(), analyzer.get(config.getCurrentDepth()) + 1);
			} else {
				analyzer.put(config.getCurrentDepth(), 1);
			}
			
			System.out.println("--> Start Counter");
			for (int key: analyzer.keySet()) {
				System.out.println("Depth " + key + " = " + analyzer.get(key));
			}
			System.out.println("--> End Counter");
			
			if (CaseStudy.IS_BOUNDED_MODEL_CHECKING && config.getCurrentDepth() >= CaseStudy.MAX_DEPTH) {
				// Do not check these states anymore
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				return;
			}
			
			RunJPF runner = new RunJPF(config);
			runner.start();
			try {
				runner.join();
				System.out.println("JPF finished at " + DateUtil.getDateTimeString());
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				System.out.println("Sending ack");
			} catch (InterruptedException e) {
				System.out.println("waiting jpf and ack to rabbitmq " + e.getMessage());
				e.printStackTrace();
			}
		};

		boolean autoAck = false;
		channel.basicConsume(app.getRabbitMQ().getQueueName(), autoAck, deliverCallback, (consumerTag) -> {
			System.out.println("Receiver consumer cancelling " + consumerTag);
		});
	}

}
