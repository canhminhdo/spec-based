package jpf;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import jpf.common.OC;
import utils.GFG;

public class NoneBmcStateSequence extends StateSequence {
	
	public NoneBmcStateSequence(Config conf, JPF jpf) {
		initialize();
		jedisSet.flushAll();
	}
	
	public NoneBmcStateSequence() {
		initialize();
	}

	@Override
	public void stateHandle() {
		OC lastElement = seq.get(seq.size() - 1);
		if (lastElement == null)
			return;
		String elementSha256 = GFG.getSHA(lastElement.toString());
		// if already existing is depth set
		if (jedisSet.sismember(app.getRabbitMQ().getQueueName(), elementSha256))
			return;
		
		if (!lastElement.isFinished() && lastElement.isReady()) {
			jedisSet.sadd(app.getRabbitMQ().getQueueName(), elementSha256);
			sender.sendJob(lastElement);
		}
	}
}
