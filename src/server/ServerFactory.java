package server;

public interface ServerFactory {
	
	Boolean isRemote();
	Redis createRedis();
	RabbitMQ createRabbitMQ();
	
}
