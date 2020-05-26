package redis.api;

import redis.clients.jedis.Jedis;

public class RedisQueueSet extends RedisSet {
	
	public static String SEQ_SET = "seqSet";
	public static String STATE_SET = "stateSet";
	private String DEPTH_SET_PREFIX = "depth";
	
	public RedisQueueSet(Jedis jedis) {
		super(jedis);
	}
	
	public String getDepthSetName(int depth) {
		return DEPTH_SET_PREFIX + "-" + depth;
	}
}
