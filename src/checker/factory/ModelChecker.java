package checker.factory;

import jpf.StateSequence;
import jpf.common.OC;

public interface ModelChecker {
	
	public ConsumerFactory createConsumer();
	public StarterFactory createStarter();
	public StateSequence createStateSequence(OC message);
	public SenderFactory createSender();
}
