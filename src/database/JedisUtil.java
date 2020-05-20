package database;

import redis.clients.jedis.Jedis;
import server.Application;
import server.ApplicationConfigurator;
import server.instances.Redis;

public class JedisUtil {

	private static String SEQ_SET_NAME = "seqSet";
	private static String STATE_SET_NAME = "stateSet";
	private static String STATE_AT_DEPTH_SET_NAME = "stateAtDepthSet";
	public static final int SEQ_TYPE = 0;
	public static final int STATE_TYPE = 1;
	public static final int STATE_AT_DEPTH_TYPE = 2;

	public static boolean exists(Jedis redis, int setType, String member) {
		switch (setType) {
			case SEQ_TYPE:
				return redis.sismember(SEQ_SET_NAME, member);
			case STATE_TYPE:
				return redis.sismember(STATE_SET_NAME, member);
			case STATE_AT_DEPTH_TYPE:
				return redis.sismember(STATE_AT_DEPTH_SET_NAME, member);
			default:
				return false;
		}
	}

	public static void add(Jedis redis, int setType, String member) {
		switch (setType) {
			case SEQ_TYPE:
				redis.sadd(SEQ_SET_NAME, member);
				break;
			case STATE_TYPE:
				redis.sadd(STATE_SET_NAME, member);
				break;
			case STATE_AT_DEPTH_TYPE:
				redis.sadd(STATE_AT_DEPTH_SET_NAME, member);
				break;
		}
	}
	
	public static void remove(Jedis redis, int setType, String member) {
		switch (setType) {
			case SEQ_TYPE:
				redis.srem(SEQ_SET_NAME, member);
				break;
			case STATE_TYPE:
				redis.srem(STATE_SET_NAME, member);
				break;
			case STATE_AT_DEPTH_TYPE:
				redis.srem(STATE_AT_DEPTH_SET_NAME, member);
				break;
		}
	}

}
