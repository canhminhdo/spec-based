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
		configList.add("+classpath=/Users/student/eclipse-workspace/tas/bin");
		configList.add("main.TestABP");
		// 1st argument: packetsToBeSent
		String packetsToBeSent;
		for (int i = 0; i < config.getPacketsToBeSent().size(); i ++) {
			
		}
		
//		configList.add(String.valueOf(((HWMutex)config.getLock()).getLockFlag().getValue()));
//		for(int i = 0; i < config.getThreads().size(); i ++) {
//			configList.add(config.getThreads().get(i).getLoc());
//		}
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
