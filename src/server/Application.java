package server;

public class Application {
	
	ServerFactory serverFactory = null;
	Redis redis = null;
	RabbitMQ rabbitMQ = null;

	public Application(ServerFactory serverFactory) {
		this.serverFactory = serverFactory;
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
	
}
