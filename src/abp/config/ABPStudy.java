package abp.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import abp.jpf.AbpConfiguration;
import abp.jpf.AbpJPF;
import abp.main.Cell;
import abp.main.Pair;
import config.CaseStudy;
import jpf.common.HeapJPF;
import jpf.common.OC;

/**
 * ABP is a case study for testing concurrent programs at OgataLab This file is
 * specialized for ABP protocol. You need to revise in this file
 * 
 * @author OgataLab
 */
public class ABPStudy extends CaseStudy {

	// Maude files
	final String[] maude_files = { "/Users/ogataslab/Home/jaist/maude/env/env.maude" };
	// Maude command
	final String command = "reduce checkConform('ABP, {{seq}}, {{depth}}) .\n";

	// main class to start ABP program
	final String MAIN_CLASS = "abp.main.TestABP";
	// name of queue when using RabbitMQ
	final String QUEUE_NAME = "ABP";
	final String MAUDE_QUEUE = "ABP_MAUDE";
	// Path to abp program
//	final String CLASS_PATH = "/Users/canhdominh/eclipse-workspace/abp/bin";

	// if you use "student" computer at lab
//	final String CLASS_PATH = "/Users/student/eclipse-workspace/abp/bin";

	// if you use "ogataslab" computer at lab
	final String CLASS_PATH = "/Users/ogataslab/eclipse-workspace/spec-based/bin";

	// packets will send over network
	public static String PACKETS[] = { "0", "1", "2", "3" };
	// channel size
	public static int BOUND = 2;

	@Override
	public String getQueueName() {
		return QUEUE_NAME;
	}

	@Override
	public String getMaudeQueue() {
		return MAUDE_QUEUE;
	}

	@Override
	public String getClassPath() {
		return CLASS_PATH;
	}

	@Override
	public OC getInitialMessage() {
		AbpConfiguration<String> config = new AbpConfiguration<String>();
		List<String> sentPackets = Arrays.asList(ABPStudy.PACKETS);
		List<String> recPackets = new ArrayList<String>();
//        String packetsRecived[] = { "0" };
//        List<String> recPackets = Arrays.asList(packetsRecived);
		abp.main.Channel<Pair<String, Boolean>> ch1 = new abp.main.Channel<Pair<String, Boolean>>(ABPStudy.BOUND);
//		ch1.put(new Pair<String,Boolean>("0",false));
//		ch1.put(new Pair<String,Boolean>("2",true));
		abp.main.Channel<Boolean> ch2 = new abp.main.Channel<Boolean>(ABPStudy.BOUND);
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
		config.setCurrentDepth(0);
		
		return config;
	}

	@Override
	public ArrayList<String> getConfigList(OC config) {

		ArrayList<String> configList = new ArrayList<String>();
		configList.add("+classpath=" + this.CLASS_PATH);
		configList.add(this.MAIN_CLASS);

		// 1st argument: packetsToBeSent
		configList.add(((AbpConfiguration<String>) config).getPacketsToBeSentCommand());
		// 2nd argument: packetsReceived
		configList.add(((AbpConfiguration<String>) config).getPacketsReceivedCommand());
		// 3rd argument: index of packetsToBeSent
		configList.add(((AbpConfiguration<String>) config).getIndexCommand());
		// 4th argument: finish flag
		configList.add(((AbpConfiguration<String>) config).getFinishCommand());
		// 5th argument: flag1
		configList.add(((AbpConfiguration<String>) config).getFlag1Command());
		// 6th argument: flag2
		configList.add(((AbpConfiguration<String>) config).getFlag2Command());
		// 7th argument: channel1
		configList.add(((AbpConfiguration<String>) config).getChannel1Command());
		// 8th argument: channel2
		configList.add(((AbpConfiguration<String>) config).getChannel2Command());

		return configList;
	}

	@Override
	public HeapJPF getHeapJPF() {
		return new AbpJPF();
	}

	@Override
	public void printConfiguration() {
		super.printConfiguration();
		System.out.println("Channel size is " + ABPStudy.BOUND);
	}

	@Override
	public String[] getMaudeFiles() {
		return this.maude_files;
	}

	@Override
	public String getCommand() {
		return this.command;
	}

}
