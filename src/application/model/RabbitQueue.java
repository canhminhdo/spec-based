package application.model;

public class RabbitQueue {
	
	private String name;
	private int messages;
	private int messages_ready;
	private int messages_unacknowledged;
	
	public RabbitQueue(String name, int messages, int messages_ready, int messages_unacknowledged) {
		this.name = name;
		this.messages = messages;
		this.messages_ready = messages_ready;
		this.messages_unacknowledged = messages_unacknowledged;
	}

	public boolean isEmpty() {
		return messages == 0;
	}
	
	public boolean isEmptyReadyMessages() {
		return messages_ready == 0;
	}
	
	public boolean isWorking() {
		return messages_ready > 0 && messages_unacknowledged > 0;
	}
	
	public boolean containUnackMessages() {
		return messages_unacknowledged > 0;
	}
	
	public String getName() {
		return name;
	}

	public int getMessages() {
		return messages;
	}

	public int getMessages_ready() {
		return messages_ready;
	}

	public int getMessages_unacknowledged() {
		return messages_unacknowledged;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMessages(int messages) {
		this.messages = messages;
	}

	public void setMessages_ready(int messages_ready) {
		this.messages_ready = messages_ready;
	}

	public void setMessages_unacknowledged(int messages_unacknowledged) {
		this.messages_unacknowledged = messages_unacknowledged;
	}
}
