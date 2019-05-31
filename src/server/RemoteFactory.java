package server;

import config.Env;

public class RemoteFactory implements ServerFactory {

	@Override
	public Redis createRedis() {
		return new RemoteRedis(Env.REDIS_REMOTE_HOST, Env.REDIS_REMOTE_PORT);
	}

	@Override
	public RabbitMQ createRabbitMQ() {
		return new RemoteRabbitMQ(Env.RABBITMQ_REMOTE_HOST, Env.RABBITMQ_REMOTE_USERNAME, Env.RABBITMQ_REMOTE_PASSWORD, Env.RABBITMQ_REMOTE_QUEUENAME);
	}

	@Override
	public Boolean isRemote() {
		return true;
	}
}
