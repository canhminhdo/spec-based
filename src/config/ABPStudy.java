package config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jpf.abp.Configuration;
import jpf.common.OC;
import main.Cell;
import main.Pair;

public class ABPStudy extends CaseStudy {
	final String MAIN_CLASS = "main.TestABP";
	final String QUEUE_NAME = "ABP";
	final String MAUDE_PATH = "/Users/canhdominh/eclipse-workspace/abp/maude/data*";
	final String CLASS_PATH = "/Users/canhdominh/eclipse-workspace/abp/bin";
	
	// student
//		final String CLASS_PATH = "/Users/student/eclipse-workspace/abp/bin";
//		final String MAUDE_DATA_PATH = "/Users/student/eclipse-workspace/abp/maude/data*";
		
		// ogataslab
//		final String CLASS_PATH = "/Users/ogataslab/eclipse-workspace/abp/bin";
//		final String MAUDE_DATA_PATH = "/Users/ogataslab/eclipse-workspace/abp/maude/data*";

	final String PACKETS[] = { "0", "1", "2", "3" };
	final int BOUND = 3;

	@Override
	public String getQueueName() {
		return QUEUE_NAME;
	}

	@Override
	public String getMaudePath() {
		return MAUDE_PATH;
	}

	@Override
	public String getClassPath() {
		return CLASS_PATH;
	}
	
	@Override
	public OC getInitialMessage() {
		Configuration<String> config = new Configuration<String>();
		List<String> sentPackets = Arrays.asList(this.PACKETS);
		List<String> recPackets = new ArrayList<String>();
//        String packetsRecived[] = { "0" };
//        List<String> recPackets = Arrays.asList(packetsRecived);
		main.Channel<Pair<String, Boolean>> ch1 = new main.Channel<Pair<String, Boolean>>(this.BOUND);
//		ch1.put(new Pair<String,Boolean>("0",false));
//		ch1.put(new Pair<String,Boolean>("2",true));
		main.Channel<Boolean> ch2 = new main.Channel<Boolean>(this.BOUND);
//		ch2.put(true);
//		ch2.put(false);
		Cell<Boolean> f = new Cell<Boolean>(false);

		// 1st argument: packetsToBeSent
		config.setPacketsToBeSent(sentPackets);
		// 2nd argument: packetsReceived
		config.setPacketsReceived(recPackets);
		// 3rd argument: index of packetsToBeSent
		config.setIndex(0);
		// 4th argument: finish flag
		config.setFinish(f);
		// 5th argument: flag1
		config.setFlag1(true);
		// 6th argument: flag2
		config.setFlag2(true);
		// 7th argument: channel1
		config.setChannel1(ch1);
		// 8th argument: channel2
		config.setChannel2(ch2);

		return config;
	}
	
	@Override
	public ArrayList<String> getConfigList(OC config) {
			
		ArrayList<String> configList = new ArrayList<String>();
		configList.add("+classpath=" + this.CLASS_PATH);
		configList.add(this.MAIN_CLASS);
		
		// 1st argument: packetsToBeSent
		configList.add(((Configuration<String>) config).getPacketsToBeSentCommand());
		// 2nd argument: packetsReceived
		configList.add(((Configuration<String>) config).getPacketsReceivedCommand());
		// 3rd argument: index of packetsToBeSent
		configList.add(((Configuration<String>) config).getIndexCommand());
		// 4th argument: finish flag
		configList.add(((Configuration<String>) config).getFinishCommand());
		// 5th argument: flag1
		configList.add(((Configuration<String>) config).getFlag1Command());
		// 6th argument: flag2
		configList.add(((Configuration<String>) config).getFlag2Command());
		// 7th argument: channel1
		configList.add(((Configuration<String>) config).getChannel1Command());
		// 8th argument: channel2
		configList.add(((Configuration<String>) config).getChannel2Command());
		
		return configList;
	}

}
