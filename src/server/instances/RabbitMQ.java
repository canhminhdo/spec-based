package server.instances;

/**
 * RabbitMQ instance configuration
 * 
 * @author OgataLab
 *
 */
public class RabbitMQ {

	String host = null;
	String userName = null;
	String password = null;
	String queueName = null;
	String maudeQueue = null;

	public RabbitMQ(String host, String userName, String password, String queueName, String maudeQueue) {
		this.host = host;
		this.userName = userName;
		this.password = password;
		this.queueName = queueName;
		this.maudeQueue = maudeQueue;
	}

	public String getHost() {
		return host;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getQueueName() {
		return queueName;
	}
	
	public String getMaudeQueue() {
		return maudeQueue;
	}

}
