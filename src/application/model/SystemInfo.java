package application.model;

import java.util.Map;

public class SystemInfo {
	
	public static String BMC_MODE = "BMC";
	public static String BMC_RANDOM_MODE = "BMC_RANDOM";
	public static String NO_BMC_MODE = "NO_BMC";
	public static String MODE_KEY = "mode";
	public static String CURRENT_DEPTH_KEY = "currentDepth";
	public static String CURRENT_LAYER_KEY = "currentLayer";
	
	private String mode;
	private int currentLayer;
	private int currentDepth;

	public SystemInfo() {
		
	}

	public void loadFromMap(Map<String, String> sysInfoMap) {
		for (Map.Entry<String, String> entry : sysInfoMap.entrySet()) {
			if (entry.getKey().equals(MODE_KEY)) {
				this.mode = entry.getValue();
			} else if (entry.getKey().equals(CURRENT_DEPTH_KEY)) {
				this.currentDepth = Integer.valueOf(entry.getValue());
			} else if (entry.getKey().equals(CURRENT_LAYER_KEY)) {
				this.currentLayer = Integer.valueOf(entry.getValue());
			}
		}
	}
	
	public String getMode() {
		return mode;
	}
	
	public int getCurrentLayer() {
		return currentLayer;
	}

	public int getCurrentDepth() {
		return currentDepth;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public void setCurrentLayer(int currentLayer) {
		this.currentLayer = currentLayer;
	}

	public void setCurrentDepth(int currentDepth) {
		this.currentDepth = currentDepth;
	}

	@Override
	public String toString() {
		return "SystemInfo [mode=" + mode + ", currentLayer=" + currentLayer + ", currentDepth=" + currentDepth + "]";
	}
}
