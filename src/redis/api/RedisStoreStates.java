package redis.api;

public class RedisStoreStates extends RedisHash {
	
	public static String PREFIX_STATE_STORES_NAME = "store";
	
	public RedisStoreStates() {
		super();
	}
	
	public String getStoreNameAtDepth(int depth) {
		return PREFIX_STATE_STORES_NAME + '-' + depth;
	}
	
	public String getStoreErrorNameAtDepth(int depth) {
		return PREFIX_STATE_STORES_NAME + '-' + depth + "-error";
	}
	
}
