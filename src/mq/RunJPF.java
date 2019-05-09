package mq;

import java.util.ArrayList;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import jpf.Configuration;
import jpf.SequenceState;

public class RunJPF extends Thread {
	private ArrayList<String> configList;

	public RunJPF(Configuration<String> config) {
		configList = new ArrayList<String>();
		configList.add("+classpath=/Users/canhdominh/eclipse-workspace/abp/bin");
		configList.add("main.TestABP");
		
		// 1st argument: packetsToBeSent
		configList.add(config.getPacketsToBeSentCommand());
		// 2nd argument: packetsReceived
		configList.add(config.getPacketsReceivedCommand());
		// 3rd argument: index of packetsToBeSent
		configList.add(config.getIndexCommand());
		// 4th argument: finish flag
		configList.add(config.getFinishCommand());
		// 5th argument: flag1
		configList.add(config.getFlag1Command());
		// 6th argument: flag2
		configList.add(config.getFlag2Command());
		// 7th argument: channel1
		configList.add(config.getChannel1Command());
		// 8th argument: channel2
		configList.add(config.getChannel2Command());
	}

	public void run() {
		try {
			String[] configString = configList.toArray(new String[configList.size()]);
			Config conf = JPF.createConfig(configString);
			conf.setProperty("report.console.finished", "result");
			JPF jpf = new JPF(conf);
			SequenceState seq = new SequenceState();
			jpf.addListener(seq);
			jpf.run();
			System.out.println("FINISHED RUNNING JOB");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
