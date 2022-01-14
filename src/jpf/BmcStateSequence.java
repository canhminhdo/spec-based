package jpf;

import config.CaseStudy;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.search.Search;
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
		System.out.println("Depth = " + DEPTH + ", currentDepth = " + currentDepth + ", nextDepth = " + this.nextDepth);
		initialize();
	}
	
	@Override
	public void stateHandle() {
		if (!is_publish && !app.getCaseStudy().isStoreStatesInRedis()) {
			return;
		}
		OC lastElement = seq.get(seq.size() - 1);
		if (lastElement == null)
			return;
		String elementSha256 = GFG.getSHA(lastElement.toString());
		lastElement.setCurrentDepth(this.nextDepth);
		// if existing in current layer and previous layers
		int depth = this.nextDepth;
		while (depth >= 0) {
			if (jedisSet.sismember(jedisSet.getDepthSetName(depth), elementSha256)) {
				System.out.println("Hit cache at depth " + depth);
				return;
			}
			depth -= DEPTH;
		}
		if (jedisSet.sismember(jedisSet.getDepthSetName(this.nextDepth), elementSha256)) {
			return;
		}
		
//		if (!lastElement.isFinished() && lastElement.isReady()) {
//			// saving to set of hash of states at a depth
//			jedisSet.sadd(jedisSet.getDepthSetName(this.nextDepth), elementSha256);
//			// saving to a hash table where key is the hash of a state, value is the encoded string of a state
//			if (app.getCaseStudy().isStoreStatesInRedis()) {
//				jedisHash.hset(jedisHash.getStoreNameAtDepth(this.nextDepth), elementSha256, SerializationUtilsExt.serializeToStr(lastElement));
//			}
//			if (is_publish) {
//				sender.sendJob(lastElement);
//			}
//		}
	}

	@Override
	public void propertyViolated(Search search) {
		super.propertyViolated(search);
		if (app.getCaseStudy().isStoreStatesInRedis()) {
			OC config = heapJPF.getConfiguration(search);
			if (config == null)
				return;
			config.setCurrentDepth(this.nextDepth);
			String elementSha256 = GFG.getSHA(config.toString());
			if (jedisSet.sismember(jedisSet.getDepthSetError(this.nextDepth), elementSha256))
				return;
			jedisSet.sadd(jedisSet.getDepthSetError(this.nextDepth), elementSha256);
			jedisHash.hset(jedisHash.getStoreErrorNameAtDepth(this.nextDepth), elementSha256, SerializationUtilsExt.serializeToStr(config));
		}
	}
}
