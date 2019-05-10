package mq;

import config.Server;

public abstract class RabbitMQ extends Server {
	final static String QUEUE_NAME = "ABP";
	final static String USERNAME = "dev";
	final static String PASSWORD = "pdev";
}
