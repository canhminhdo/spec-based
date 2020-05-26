package application.model;

public class RabbitConsumer {
	private String consumerTag;
	private String queueName;

	public RabbitConsumer(String consumerTag, String queueName) {
		super();
		this.consumerTag = consumerTag;
		this.queueName = queueName;
	}

	public String getConsumerTag() {
		return consumerTag;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setConsumerTag(String consumerTag) {
		this.consumerTag = consumerTag;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
}
