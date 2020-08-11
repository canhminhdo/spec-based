package mq;

import checker.manual.RandStarter;

/**
 * Starter program to kick-off environment by sending the initial message to
 * RabbitMQ master
 * 
 * @author OgataLab
 *
 */
public class StarterRand {
	
	public static void main(String[] argv) {
		RandStarter starter = new RandStarter();
		starter.start();
		System.exit(0);
	}
}
