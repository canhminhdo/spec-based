package application.model;

public class ConsumerInfo {
	
	private int id;
	private int current;
	
	public ConsumerInfo(int id, int current) {
		this.id = id;
		this.current = current;
	}
	
	public int getId() {
		return id;
	}

	public int getCurrent() {
		return current;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	@Override
	public String toString() {
		return "ConsumerInfo [id=" + id + ", current queue=" + current + "]";
	}
}
