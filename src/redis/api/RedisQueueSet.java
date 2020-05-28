package redis.api;

public class RedisQueueSet extends RedisSet {
	
	public static String SEQ_SET = "seqSet";
	public static String STATE_SET = "stateSet";
	private String DEPTH_SET_PREFIX = "depth";
	
	public RedisQueueSet() {
		super();
	}
	
	public String getDepthSetName(int depth) {
		return DEPTH_SET_PREFIX + "-" + depth;
	}
	
	public String getStateSetQueue() {
		return SEQ_SET;
	}
}
