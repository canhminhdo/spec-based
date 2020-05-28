package redis.api;

import java.util.Map;

import application.model.SystemInfo;

public class RedisSystemInfo extends RedisHash {
	
	public static String SYSTEM_KEY = "system";

	public RedisSystemInfo() {
		super();
	}
	
	public SystemInfo getSystemInfo() {
		Map<String, String> sysInfoMap = this.hgetall(SYSTEM_KEY);
		SystemInfo sysInfo = new SystemInfo();
		sysInfo.loadFromMap(sysInfoMap);
		return sysInfo;
	}

}
