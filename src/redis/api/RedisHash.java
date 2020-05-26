package redis.api;

import java.util.Map;

import redis.clients.jedis.Jedis;

public class RedisHash extends RedisApi {

	public RedisHash(Jedis jedis) {
		super(jedis);
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
	
}
