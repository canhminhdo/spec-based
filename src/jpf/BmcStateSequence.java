package jpf;

import config.CaseStudy;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import jpf.common.OC;
import utils.GFG;
import utils.SerializationUtilsExt;

public class BmcStateSequence extends StateSequence {
	
	protected boolean is_publish = true;
	protected int nextDepth = 0;
	
	public BmcStateSequence(Config conf, JPF jpf) {
		initialize();
		jedisSet.flushAll();
	}
	
	public BmcStateSequence(int currentDepth) {
		if (currentDepth + DEPTH >= CaseStudy.MAX_DEPTH) {
			DEPTH = CaseStudy.MAX_DEPTH - currentDepth;
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
			// saving to set of hash of states at a depth
			jedisSet.sadd(jedisSet.getDepthSetName(this.nextDepth), elementSha256);
			// saving to a hash table where key is the hash of a state, value is the encoded string of a state
			jedisHash.hset(jedisHash.getStoreNameAtDepth(this.nextDepth), elementSha256, SerializationUtilsExt.serializeToStr(lastElement));
			if (is_publish) {
				sender.sendJob(lastElement);
			}
		}
	}
}
