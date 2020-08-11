package redis.api;

import java.util.Set;

import database.RedisClient;
import server.Application;
import server.ApplicationConfigurator;

public class RedisSet extends RedisApi {

	public RedisSet() {
		Application app = ApplicationConfigurator.getInstance().getApplication();
		this.jedis = RedisClient.getInstance(app.getRedis().getHost(), app.getRedis().getPort()).getConnection();
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
	
	public void sunion(String dstkey, String... keys) {
		jedis.sunionstore(dstkey, keys);
	}
	
	public Set<String> smembers(String key) {
		return jedis.smembers(key);
	}
}
