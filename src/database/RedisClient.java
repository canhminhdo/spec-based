package database;

import redis.clients.jedis.Jedis;

public class RedisClient {
	private static RedisClient _instance = null;
	private Jedis jedis = null; 
	
	public static RedisClient getInstance() {
		if (_instance == null)
			_instance = new RedisClient();
		
		return _instance;
	}
	
	private RedisClient() {
		this.jedis = new Jedis("localhost");
	}
	
	public Jedis getConnection() {
		return this.jedis;
	}
}
