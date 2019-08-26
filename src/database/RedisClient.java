package database;

import redis.clients.jedis.Jedis;

public class RedisClient {
	private static RedisClient _instance = null;
	private Jedis jedis = null;

	public static RedisClient getInstance(String host, Integer port) {
		if (_instance == null)
			_instance = new RedisClient(host, port);

		return _instance;
	}

	private RedisClient(String host, Integer port) {
		this.jedis = new Jedis(host, port);
	}

	public Jedis getConnection() {
		return this.jedis;
	}
}
