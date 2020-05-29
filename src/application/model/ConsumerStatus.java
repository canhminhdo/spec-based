package application.model;

public class ConsumerStatus {
	
	public final static String WORKING_STATUS = "WORKING";
	public final static String STOP_STATUS = "STOP";
	
	private String id;
	private String status;
	
	public ConsumerStatus(String id, String status) {
		this.id = id;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setToStopStatus() {
		this.status = STOP_STATUS; 
	}
	
	public void setToWorkingStatus() {
		this.status = WORKING_STATUS;
	}
	
	public boolean isWorking() {
		return this.status.equals(WORKING_STATUS);
	}
	
	public boolean isStop() {
		return this.status.equals(STOP_STATUS);
	}

	@Override
	public String toString() {
		return "ConsumerStatus [id=" + id + ", status=" + status + "]";
	}
}
