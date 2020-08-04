package redis.api;

public class RedisLock extends RedisHash {
	
	public static String LOCK_KEY = "lock";
	public static String LOCK_MODIFY_INFO_FIELD = "modify_info";
	public static String LOCK_SWITCH_QUEUE_FIELD = "switch_queue";
	public static String LOCK_RANDOM_QUEUE_FIELD = "random_queue";
	
	public RedisLock() {
		super();
	}
	
	public void requestCS(String field) {
		while (true) {
			boolean lock = Boolean.parseBoolean(this.hget(LOCK_KEY, field));
			if (!lock)
				break;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void releaseCS(String field) {
		this.hset(LOCK_KEY, field, "false");
	}
	
}
