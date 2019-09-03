package maude;

import java.util.ArrayList;

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
}
