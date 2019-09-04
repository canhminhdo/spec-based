package application.model;

/**
 * Sequence states model
 * 
 * @author OgataLab
 *
 */

public class SequenceStates {

	private int id;
	private String seq;
	private String state_from;
	private String state_to;
	private int state_index;
	private int depth;
	private String result;
	private String created;
	private String runtime;
	private String type;
	
	public SequenceStates() {
	}
	
	public SequenceStates(int id, String seq, String state_from, String state_to, int state_index, int depth,
			String result, String created, String runtime, String type) {
		super();
		this.id = id;
		this.seq = seq;
		this.state_from = state_from;
		this.state_to = state_to;
		this.state_index = state_index;
		this.depth = depth;
		this.result = result;
		this.created = created;
		this.runtime = runtime;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getState_from() {
		return state_from;
	}

	public void setState_from(String state_from) {
		this.state_from = state_from;
	}

	public String getState_to() {
		return state_to;
	}

	public void setState_to(String state_to) {
		this.state_to = state_to;
	}

	public int getState_index() {
		return state_index;
	}

	public void setState_index(int state_index) {
		this.state_index = state_index;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
