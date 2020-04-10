package cloudsync.config;

import java.util.ArrayList;

import cloudsync.jpf.CloudSyncConfiguration;
import cloudsync.jpf.CloudSyncJPF;
import cloudsync.main.Cloud;
import cloudsync.main.Constants;
import cloudsync.main.PC;
import config.CaseStudy;
import jpf.common.HeapJPF;
import jpf.common.OC;

/**
 * CloudSync is a case study for testing concurrent programs at OgataLab This file is
 * specialized for CloudSync protocol. You need to revise in this file
 * 
 * @author OgataLab
 */
public class CloudSyncStudy extends CaseStudy {

	// Maude files
//	final String[] maude_files = { "/Users/ogataslab/Home/JAIST/maude/env/env-cloud.maude" };	// iMac
	final String[] maude_files = { "/Users/canhdominh/Home/JAIST/maude/env/env-cloud.maude" };	// Macbook Pro
	// Maude command
	final String command = "reduce checkConform('CLOUD, {{seq}}, {{depth}}) .\n";

	// main class to start NSPK program
	final String MAIN_CLASS = "cloudsync.main.TestCloudSync";

	// name of queue when using RabbitMQ
	final String QUEUE_NAME = "CLOUD";
	final String MAUDE_QUEUE = "CLOUD_MAUDE";

	// if you use "ogataslab" computer at lab
//	final String CLASS_PATH = "/Users/ogataslab/eclipse-workspace/spec-based/bin:/Users/ogataslab/eclipse-workspace/spec-based/lib/antlr-4.7.1-complete.jar";	// iMac
	final String CLASS_PATH = "/Users/canhdominh/eclipse-workspace/spec-based/bin:/Users/canhdominh/eclipse-workspace/spec-based/lib/antlr-4.7.1-complete.jar";	// Macbook Pro

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
		// init = {(cloud: < idlec,2 >) (pc[p1]: < idlep,1,0 >) (pc[p2]: < idlep,2,0 >) (pc[p3]: < idlep,3,0 >)}
		CloudSyncConfiguration config = new CloudSyncConfiguration();
		Cloud cloud = new Cloud(Cloud.LabelC.idlec, 2);
		PC p1 = new PC(Constants.p1, PC.LabelP.idlep, 1, 0, cloud);
		PC p2 = new PC(Constants.p2, PC.LabelP.idlep, 2, 0, cloud);
		PC p3 = new PC(Constants.p3, PC.LabelP.idlep, 3, 0, cloud);
		ArrayList<PC> pcList = new ArrayList<PC>();
		
		pcList.add(p1);
		pcList.add(p2);
		pcList.add(p3);
		
		config.setCloud(cloud);
		config.setPcList(pcList);

		return config;
	}

	@Override
	public ArrayList<String> getConfigList(OC config) {

		ArrayList<String> configList = new ArrayList<String>();
		configList.add("+classpath=" + this.CLASS_PATH);
		configList.add(this.MAIN_CLASS);

		configList.add(((CloudSyncConfiguration) config).getPassedMessage());
		
		return configList;
	}

	@Override
	public HeapJPF getHeapJPF() {
		return new CloudSyncJPF();
	}

	@Override
	public void printConfiguration() {
		System.out.println("This is " + this.getClass().getSimpleName());
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
