package mq;

import java.util.ArrayList;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import jpf.SequenceState;
import jpf.common.OC;
import server.Application;
import server.ApplicationConfigurator;

/**
 * This file show how to start JPF program from internal java program
 * 
 * @author OgataLab
 *
 */
public class RunJPF extends Thread {

	private ArrayList<String> configList;

	/**
	 * RunJPF constructor to generate configList for each case study. Change another
	 * case study, replace the current one to another
	 * 
	 * @param config
	 */
	public RunJPF(OC config) {
		// TODO: Customize with each case study here.
		Application app = ApplicationConfigurator.getInstance().getApplication();
		this.configList = app.getCaseStudy().getConfigList(config);
		app.setHeapJPF(app.getCaseStudy().getHeapJPF());
	}

	/**
	 * Running JPF program with configuration that is built from a Observer
	 * Component object
	 */
	public void run() {
		try {
			String[] configString = configList.toArray(new String[configList.size()]);
			Config conf = JPF.createConfig(configString);
			conf.setProperty("report.console.finished", "result");
			JPF jpf = new JPF(conf);
			SequenceState seq = new SequenceState();
//			SimpleDot seq = new SimpleDot(conf, jpf);
			jpf.addListener(seq);
			jpf.run();
			System.out.println("FINISHED RUNNING JOB");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
