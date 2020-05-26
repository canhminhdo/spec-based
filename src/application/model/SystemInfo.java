package application.model;

import java.util.Map;

public class SystemInfo {
	
	public static String BMC_MODE = "BMC";
	public static String BMC_RANDOM_MODE = "BMC_RANDOM";
	public static String NO_BMC_MODE = "NO_BMC";
	public static String MODE_KEY = "mode";
	public static String CURRENT_DEPTH_KEY = "currentDepth";
	public static String CURRENT_MAX_DEPTH_KEY = "currentMaxDepth";
	
	private String mode;
	private int currentDepth;
	private int currentMaxDepth;
	
	public SystemInfo() {
		
	}

	public void loadFromMap(Map<String, String> sysInfoMap) {
		for (Map.Entry<String, String> entry : sysInfoMap.entrySet()) {
			if (entry.getKey().equals(MODE_KEY)) {
				this.mode = entry.getValue();
			} else if (entry.getKey().equals(CURRENT_DEPTH_KEY)) {
				this.currentDepth = Integer.valueOf(entry.getValue());
			} else if (entry.getKey().equals(CURRENT_MAX_DEPTH_KEY)) {
				this.currentMaxDepth = Integer.valueOf(entry.getValue());
			}
		}
	}
	
	public String getMode() {
		return mode;
	}

	public int getCurrentDepth() {
		return currentDepth;
	}

	public int getCurrentMaxDepth() {
		return currentMaxDepth;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void setCurrentDepth(int currentDepth) {
		this.currentDepth = currentDepth;
	}

	public void setCurrentMaxDepth(int currentMaxDepth) {
		this.currentMaxDepth = currentMaxDepth;
	}
	

	@Override
	public String toString() {
		return "SystemInfo [mode=" + mode + ", currentDepth=" + currentDepth + ", currentMaxDepth=" + currentMaxDepth
				+ "]";
	}
}
