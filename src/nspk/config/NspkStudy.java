package nspk.config;

import java.util.ArrayList;
import java.util.Arrays;

import abp.jpf.AbpJPF;
import config.CaseStudy;
import jpf.common.HeapJPF;
import jpf.common.OC;
import nspk.jpf.NspkConfiguration;
import nspk.jpf.NspkJPF;
import nspk.main.Constants;
import nspk.main.Controller;
import nspk.main.MultiSet;

/**
 * NSPK is a case study for testing concurrent programs at OgataLab This file is
 * specialized for NSPK protocol. You need to revise in this file
 * 
 * @author OgataLab
 */
public class NspkStudy extends CaseStudy {

	// Maude files
	final String[] maude_files = { CaseStudy.PROJECT_BASE + "/maude/env-nslpk.maude" };
	// Maude command
	final String command = "reduce checkConform('NSPK, {{seq}}, {{depth}}) .\n";

	// main class to start NSPK program
	final String MAIN_CLASS = "nspk.main.TestNSPK";

	// name of queue when using RabbitMQ
	final String QUEUE_NAME = "NSPK";
	final String MAUDE_QUEUE = "NSPK_MAUDE";

	@Override
	public String getQueueName() {
		return QUEUE_NAME;
	}

	@Override
	public String getMaudeQueue() {
		return MAUDE_QUEUE;
	}

	@Override
	public OC getInitialMessage() {
		NspkConfiguration config = new NspkConfiguration();
		// init = {(nw: emp) (rand: (r1 r2)) (nonces: emp) (prins: (p q intrdr))}
		
		MultiSet<String> nw = new MultiSet<String>(Constants.nw);
		MultiSet<String> rand = new MultiSet<String>(Constants.rand);
		rand.add(Constants.r1);
		rand.add(Constants.r2);
		MultiSet<String> nonces = new MultiSet<String>(Constants.nonces);
		MultiSet<String> prins = new MultiSet<String>(Constants.prins);
		prins.add(Constants.p);
		prins.add(Constants.q);
		prins.add(Constants.intrdr);
		
		Controller<String> pRwController = new Controller<String>(new ArrayList<String>(
			Arrays.asList(Constants.challenge, Constants.confirmation)
		));
		Controller<String> qRwController = new Controller<String>(new ArrayList<String>(
			Arrays.asList(Constants.response)
		));
		Controller<String> intrdrRwController = new Controller<String>(new ArrayList<String>(
			Arrays.asList(Constants.response, Constants.fake)
		));
		
		config.setNw(nw);
		config.setRand(rand);
		config.setNonces(nonces);
		config.setPrins(prins);
		config.setpRwController(pRwController);
		config.setqRwController(qRwController);
		config.setIntrdrRwController(intrdrRwController);
		config.setCurrentDepth(0);
		
		return config;
	}

	@Override
	public ArrayList<String> getConfigList(OC config) {

		ArrayList<String> configList = new ArrayList<String>();
		configList.add("+classpath=" + this.getClassPath());
		configList.add(this.MAIN_CLASS);

		configList.add(((NspkConfiguration) config).getPassedMessage());
		
		return configList;
	}

	@Override
	public HeapJPF getHeapJPF() {
		return new NspkJPF();
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
