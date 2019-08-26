package server;

import config.CaseStudy;
import server.factory.ServerFactory;
import server.instances.RabbitMQ;
import server.instances.Redis;

public class Application {
	
	CaseStudy cs = null;
	ServerFactory serverFactory = null;
	Redis redis = null;
	RabbitMQ rabbitMQ = null;

	public Application(ServerFactory serverFactory, CaseStudy cs) {
		this.serverFactory = serverFactory;
		this.cs = cs;
		this.createServer();
	}
	
	private void createServer() {
		this.redis = this.serverFactory.createRedis();
		this.rabbitMQ = this.serverFactory.createRabbitMQ();
	}

	public ServerFactory getServerFactory() {
		return serverFactory;
	}

	public Redis getRedis() {
		return redis;
	}

	public RabbitMQ getRabbitMQ() {
		return rabbitMQ;
	}
	
	public CaseStudy getCaseStudy() {
		return cs;
	}
	
}
