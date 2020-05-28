package mq;

import checker.factory.ModelChecker;
import checker.factory.StarterFactory;
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
	
	public static void main(String[] argv) {
		Application app = ApplicationConfigurator.getInstance().getApplication();
		ModelChecker mc = app.getModelChecker();
		StarterFactory starter = mc.createStarter();
		starter.start();
		System.exit(0);
	}
}
