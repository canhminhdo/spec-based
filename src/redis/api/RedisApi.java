package redis.api;

import redis.clients.jedis.Jedis;

public abstract class RedisApi {
	
	protected Jedis jedis;
	
	public RedisApi(Jedis jedis) {
		this.jedis = jedis;
	}
	
	public void flushAll() {
		jedis.flushAll();
	}
}
