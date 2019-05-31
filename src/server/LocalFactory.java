package server;

import config.Env;

public class LocalFactory implements ServerFactory {

	@Override
	public Redis createRedis() {
		return new LocalRedis(Env.REDIS_LOCAL_HOST, Env.REDIS_LOCAL_PORT);
	}

	@Override
	public RabbitMQ createRabbitMQ() {
		return new LocalRabbitMQ(Env.RABBITMQ_LOCAL_HOST, Env.RABBITMQ_LOCAL_USERNAME, Env.RABBITMQ_LOCAL_PASSWORD, Env.RABBITMQ_LOCAL_QUEUENAME);
	}
	
	@Override
	public Boolean isRemote() {
		return false;
	}
}
