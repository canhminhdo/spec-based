package redis.api;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisSet extends RedisApi {

	public RedisSet(Jedis jedis) {
		super(jedis);
	}

	public boolean sismember(String setName, String member) {
		return jedis.sismember(setName, member);
	}

	public void sadd(String setName, String member) {
		jedis.sadd(setName, member);
	}

	public void srem(String setName, String member) {
		jedis.srem(setName, member);
	}

	public void sdiffstore(String dstkey, String... keys) {
		jedis.sdiffstore(dstkey, keys);
	}

	public Long scard(String setName) {
		return jedis.scard(setName);
	}

	public Set<String> spop(String key, int count) {
		return jedis.spop(key, count);
	}
}
