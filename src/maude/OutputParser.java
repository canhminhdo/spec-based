package maude;

import java.util.ArrayList;

import application.model.ErrorMessage;

public class OutputParser {
	
	private ArrayList<String> output;
	private ArrayList<String> success;
	private ArrayList<String> failure;
	private ArrayList<String> error;

	public OutputParser(ArrayList<String> output) {
		this.output = output;
		this.success = new ArrayList<String>();
		this.failure = new ArrayList<String>();
		this.error = new ArrayList<String>();
	}

	public void parsing() {

		boolean flag_start = false;

		for (int i = 0; i < this.output.size(); i++) {
			if (this.output.get(i).startsWith("result Result4Conform")) {

				if (this.output.get(i).contains("(success).Result4Conform")) {
					// success
					this.success.add(output.get(i));
				} else {
					// failure
					flag_start = true;
					this.failure.add(output.get(i));
				}

			} else if (this.output.get(i).startsWith("Warning")) {
				// parse error
				this.error.add(this.output.get(i));

			} else if (this.output.get(i).startsWith("Maude>")) {
				// stop
				flag_start = false;
			} else {
				if (flag_start == true) {
					this.failure.add(this.output.get(i));
				}
			}

		}
	}

	public ArrayList<String> getOutput() {
		return output;
	}

	public ArrayList<String> getSuccess() {
		return success;
	}

	public ArrayList<String> getFailure() {
		return failure;
	}

	public ArrayList<String> getError() {
		return error;
	}
	
	public String getSuccessString() {
		return this.getString(success);
	}
	
	public String getFailureString() {
		return this.getString(failure);
	}
	
	public String getErrorString() {
		return this.getString(error);
	}
	
	public String getString(ArrayList<String> list) {
		if (list.size() == 0)
			return null;
		String str = list.get(0);
		for (int i = 1; i < list.size(); i ++) {
			str += " " + list.get(i).trim();
		}
		return str;
	}
	
	public ErrorMessage parseError() {
		if (failure.size() == 0)
			return null;
		
		ErrorMessage msg = new ErrorMessage();
		
		String str = getFailureString();
		
		// get "from"
		String[] parts = str.split(",from:");
		if (parts.length != 2)
			return msg;
		String[] parts_from = parts[1].split(",to:");
		if (parts_from.length != 2)
			return msg;
		msg.setFrom(parts_from[0].trim());
		
		// get "to"
		String[] parts_to = parts_from[1].split(",index:");
		if (parts_to.length != 2)
			return msg;
		msg.setTo(parts_to[0].trim());
		
		// get "index"
		String[] parts_index = parts_to[1].split(",bound:");
		if (parts_index.length != 2)
			return msg;
		msg.setIndex(Integer.parseInt(parts_index[0].trim()));
		
		// get "depth"
		String[] parts_bound = parts_index[1].split("}");
		if (parts_bound.length == 0)
			return msg;
		msg.setDepth(Integer.parseInt(parts_bound[0].trim()));
		
		return msg;
	}
	
	public static void main(String[] argv) {
		String str = "result Result4Conform?: {msg: \"Failure\",from: {sb: true data: d(2) rb: true buf: (d(0) | d(1)) dc: < d(2),true > ac: nil},to: {sb: true data: d(2) rb: false buf: (d(0) | d(1) | d(3)) dc: nil ac: nil},index: 19,bound: 2}";
		ErrorMessage msg = new ErrorMessage();
		
		// get "from"
		String[] parts = str.split(",from:");
		String[] parts_from = parts[1].split(",to:");
		msg.setFrom(parts_from[0].trim());
		
		// get "to"
		String[] parts_to = parts_from[1].split(",index:");
		msg.setTo(parts_to[0].trim());
		
		// get "index"
		String[] parts_index = parts_to[1].split(",bound:");
		msg.setIndex(Integer.parseInt(parts_index[0].trim()));
		
		// get "depth"
		String[] parts_bound = parts_index[1].split("}");
		msg.setDepth(Integer.parseInt(parts_bound[0].trim()));
		
		System.out.println(msg);
		
		System.out.println(str);
	}
}
