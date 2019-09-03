package server.factory;

import config.CaseStudy;
import server.instances.RabbitMQ;
import server.instances.Redis;

/**
 * Factory Method
 * 
 * @author OgataLab
 *
 */
public class ServerFactory {

	CaseStudy cs;

	public ServerFactory(CaseStudy cs) {
		this.cs = cs;
	}

	public Redis createRedis() {
		return new Redis(this.cs.getRedisHost(), this.cs.getRedisPort());
	}

	public RabbitMQ createRabbitMQ() {
		return new RabbitMQ(this.cs.getRabbitMQHost(), this.cs.getRabbitMQUsername(), this.cs.getRabbitMQPassword(),
				this.cs.getQueueName(), this.cs.getMaudeQueue());
	}

	public Boolean isRemote() {
		return this.cs.isRemote();
	}
}
