package redis.api;

import database.RedisClient;
import redis.clients.jedis.Jedis;
import server.Application;
import server.ApplicationConfigurator;

public abstract class RedisApi {
	
	protected Jedis jedis;
	
	public RedisApi() {
		Application app = ApplicationConfigurator.getInstance().getApplication();
		this.jedis = RedisClient.getInstance(app.getRedis().getHost(), app.getRedis().getPort()).getConnection();
	}
	
	public void flushAll() {
		jedis.flushAll();
	}
	
	public void deleteKey(String key) {
		jedis.del(key);
	}
}
