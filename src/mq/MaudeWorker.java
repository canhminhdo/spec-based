package mq;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import config.CaseStudy;
import maude.OutputParser;
import server.Application;
import server.ApplicationConfigurator;
import utils.AES;
import utils.PrettyPrinter;

/**
 * Receiver program as RabbitMQ client Whenever receiving a message from
 * RabbitMQ master. Start checking such state sequence that conforms to the
 * given specification.
 * 
 * @author OgataLab
 *
 */
public class MaudeWorker {

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

		channel.queueDeclare(app.getRabbitMQ().getMaudeQueue(), false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
		// Get Maude instance and preload
		RunMaude maude = RunMaude.getInstance();
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String cipher = SerializationUtils.deserialize(delivery.getBody());
			String seq = AES.decrypt(cipher, CaseStudy.SECRETE_KEY);
			System.out.println(" [x] Received");
			maude.checkSeq(app.getCaseStudy().getCommand(), seq, 2);
			OutputParser output = new OutputParser(maude.getOutput());
			output.parsing();
			if (output.getFailure().size() > 0)
				PrettyPrinter.printList(output.getFailure());
			if (output.getError().size() > 0)
				PrettyPrinter.printList(output.getError());
		};

		boolean autoAck = false;
		channel.basicConsume(app.getRabbitMQ().getMaudeQueue(), autoAck, deliverCallback, consumerTag -> {
		});
	}

}
