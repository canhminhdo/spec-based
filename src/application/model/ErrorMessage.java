package application.model;

public class ErrorMessage {
	private String from;
	private String to;
	private int index;
	private int depth;
	
	
	public ErrorMessage() {
		
	}
	
	public ErrorMessage(String from, String to, int index, int depth) {
		super();
		this.from = from;
		this.to = to;
		this.index = index;
		this.depth = depth;
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "ErrorMessage [from=" + from + ", to=" + to + ", index=" + index + ", depth=" + depth + "]";
	}
}
