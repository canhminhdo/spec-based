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
	
	public void updateSystemInfo(SystemInfo sysInfo) {
		this.hset(SYSTEM_KEY, SystemInfo.MODE_KEY, sysInfo.getMode());
		this.hset(SYSTEM_KEY, SystemInfo.CURRENT_DEPTH_KEY, String.valueOf(sysInfo.getCurrentDepth()));
		this.hset(SYSTEM_KEY, SystemInfo.CURRENT_LAYER_KEY, String.valueOf(sysInfo.getCurrentLayer()));
	}
}
