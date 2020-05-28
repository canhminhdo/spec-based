package checker.nonbmc;

import checker.factory.ConsumerFactory;
import checker.factory.ModelChecker;
import checker.factory.SenderFactory;
import checker.factory.StarterFactory;
import jpf.NoneBmcStateSequence;
import jpf.StateSequence;
import jpf.common.OC;

public class NonBmcChecker implements ModelChecker {

	@Override
	public ConsumerFactory createConsumer() {
		ConsumerFactory consumer = new NonBmcConsumer();
		return consumer;
	}

	@Override
	public StarterFactory createStarter() {
		NonBmcStarter starter = new NonBmcStarter();
		return starter;
	}

	@Override
	public StateSequence createStateSequence(OC message) {
		StateSequence seq = new NoneBmcStateSequence();
		return seq;
	}
	
	@Override
	public SenderFactory createSender() {
		SenderFactory sender = NonBmcSender.getInstance();
		return sender;
	}
}
