package mq;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import config.CaseStudy;
import server.Application;
import server.ApplicationConfigurator;

/**
 * Receiver program as RabbitMQ client Whenever receiving a message from
 * RabbitMQ master. Start checking such state sequence that conforms to the
 * given specification.
 * 
 * @author OgataLab
 *
 */
public class RunMaude {

	private static RunMaude _instance = null;
	private ProcessBuilder builder;
	private Process process;
	private InputStream stdout;
	private OutputStream stdin;

	/**
	 * Get singleton Maude instance
	 * 
	 * @return {@link RunMaude}
	 */
	public static RunMaude getInstance() {
		if (_instance == null)
			_instance = new RunMaude();

		return _instance;
	}

	private RunMaude() {
		try {
			// Load maude application and ready to interact
			Application app = ApplicationConfigurator.getInstance().getApplication();
			ArrayList<String> command = new ArrayList<String>();
			// Maude program will be loaded
			command.add(CaseStudy.MAUDE_PROGRAM);
			// Maude files will be loaded
			for (String maude_file : app.getCaseStudy().getMaudeFiles()) {
				command.add(maude_file);
			}
			builder = new ProcessBuilder(command);
			builder.redirectErrorStream(true); // we can ignore the error stream by merging error stream with input stream

			process = builder.start();

			// Prepare stdin & stdout
			stdout = process.getInputStream(); // it connects to the output stream of process
			stdin = process.getOutputStream(); // it connects to the input stream of process
			// Make sure nothing withhold in stdin
			stdin.flush();
			// Get all output for launching
			getOutput();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Check whether process isAlive
	 * 
	 * @return {@link Boolean}
	 */
	public boolean isAlive() {
		try {
			process.exitValue();
			return false;
		} catch (IllegalThreadStateException e) {
			return true;
		}
	}

	/**
	 * Get output from a command
	 * 
	 * @return {@link ArrayList<String>}
	 */
	public ArrayList<String> getOutput() {
		ArrayList<String> output = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
			int ch = -1;
			StringBuilder sb = new StringBuilder();
			while ((ch = reader.read()) > -1) {
				if (ch == '\r' || ch == '\n') {
					output.add(sb.toString());
					sb = new StringBuilder();
				} else {
					sb.append((char) ch);
					if (sb.length() == 7 && sb.toString().equals("Maude> ")) {
						output.add(sb.toString());
						break;
					}
				}
			}
			return output;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Feeding command to check a state sequence that conforms to a given
	 * specification
	 * 
	 * @param command
	 * @param seq
	 * @param depth
	 */
	public void checkSeq(String command, String seq, String depth) {
		try {
			command = command.replace("{{depth}}", depth);
			command = command.replace("{{seq}}", seq);
			byte[] bytes = command.getBytes();
			this.stdin.write(bytes);
			this.stdin.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Kill the subprocess
	 */
	public void kill() {
		process.destroy();
	}
}
