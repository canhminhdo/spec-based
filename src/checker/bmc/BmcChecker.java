package checker.bmc;

import checker.factory.ConsumerFactory;
import checker.factory.ModelChecker;
import checker.factory.SenderFactory;
import checker.factory.StarterFactory;
import jpf.BmcStateSequence;
import jpf.StateSequence;
import jpf.common.OC;

public class BmcChecker implements ModelChecker {

	@Override
	public ConsumerFactory createConsumer() {
		ConsumerFactory consumer = new BmcConsumer();
		return consumer;
	}

	@Override
	public StarterFactory createStarter() {
		BmcStarter starter = new BmcStarter();
		return starter;
	}

	@Override
	public StateSequence createStateSequence(OC message) {
		StateSequence seq = new BmcStateSequence(message.getCurrentDepth());
		return seq;
	}

	@Override
	public SenderFactory createSender() {
		SenderFactory sender = BmcSender.getInstance();
		return sender;
	}
	
}
