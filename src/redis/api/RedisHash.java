package redis.api;

import java.util.Map;

public class RedisHash extends RedisApi {

	public RedisHash() {
		super();
	}
	
	public boolean hexist(String key, String field) {
		return jedis.hexists(key, field);
	}
	
	public Map<String, String> hgetall(String key) {
		return jedis.hgetAll(key);
	}
	
	public void hset(String key, String field, String value) {
		jedis.hset(key, field, value);
	}
	
	public String hget(String key, String field) {
		return jedis.hget(key, field);
	}
	
	public int hlen(String key) {
		return jedis.hlen(key).intValue();
	}
	
}
