package mq;

import checker.factory.ConsumerFactory;
import checker.factory.ModelChecker;
import server.Application;
import server.ApplicationConfigurator;

/**
 * Receiver program as RabbitMQ client Whenever receiving a message from
 * RabbitMQ master Start internally JPF program to generate state sequences
 * 
 * @author OgataLab
 *
 */
public class Receiver {
	
	public static void main(String[] args) throws Exception {
		Application app = ApplicationConfigurator.getInstance().getApplication();
		ModelChecker mc = app.getModelChecker();
		ConsumerFactory consumer = mc.createConsumer();
		consumer.start();
	}
}
