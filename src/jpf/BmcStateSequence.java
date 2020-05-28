package jpf;

import application.model.SystemInfo;
import config.CaseStudy;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import jpf.common.OC;
import utils.GFG;

public class BmcStateSequence extends StateSequence {
	
	protected boolean is_publish = true;
	protected int nextDepth = 0;
	
	public BmcStateSequence(Config conf, JPF jpf) {
		initialize();
		jedisSet.flushAll();
	}
	
	public BmcStateSequence(int currentDepth) {
		if (currentDepth + DEPTH >= CaseStudy.CURRENT_MAX_DEPTH) {
			DEPTH = CaseStudy.CURRENT_MAX_DEPTH - currentDepth;
			is_publish = false;
		}
		this.nextDepth = currentDepth + DEPTH;
		initialize();
	}
	
	@Override
	public void stateHandle() {
		OC lastElement = seq.get(seq.size() - 1);
		if (lastElement == null)
			return;
		String elementSha256 = GFG.getSHA(lastElement.toString());
		lastElement.setCurrentDepth(this.nextDepth);
		// if already existing is depth set
		if (jedisSet.sismember(jedisSet.getDepthSetName(this.nextDepth), elementSha256))
			return;
		
		if (!lastElement.isFinished() && lastElement.isReady()) {
			jedisSet.sadd(jedisSet.getDepthSetName(this.nextDepth), elementSha256);
			if (is_publish) {
				sender.sendJob(lastElement);
			} else {
				// if states located at the maximum depth
				if (CaseStudy.RANDOM_MODE && !CaseStudy.SYSTEM_MODE.equals(SystemInfo.BMC_RANDOM_MODE)) {
					sender.sendJob(getQueueNameAtDepth(), lastElement);
				}	
			}
		}
	}
	
	private String getQueueNameAtDepth() {
		return app.getRabbitMQ().getQueueNameAtDepth();
	}
}
