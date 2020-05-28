package mq;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import checker.factory.ModelChecker;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import jpf.StateSequence;
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
	
	private static Logger logger = (Logger) LogManager.getLogger();
	private ArrayList<String> configList;
	private Application app;
	private OC message;

	/**
	 * RunJPF constructor to generate configList for each case study. Change another
	 * case study, replace the current one to another
	 * 
	 * @param config
	 */
	public RunJPF(OC message) {
		app = ApplicationConfigurator.getInstance().getApplication();
		this.configList = app.getCaseStudy().getConfigList(message);
		app.setHeapJPF(app.getCaseStudy().getHeapJPF());
		this.message = message;
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
			ModelChecker mc = app.getModelChecker();
			StateSequence seq = mc.createStateSequence(message);
//			SimpleDot seq = new SimpleDot(conf, jpf);
			jpf.addListener(seq);
			jpf.run();
			logger.info("FINISHED RUNNING JOB");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
