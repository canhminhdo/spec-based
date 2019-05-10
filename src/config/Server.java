package config;

public abstract class Server {
	public static final Boolean IS_REMOTE = false;
	// Redis and Message Queue are the same server
	public static final String REMOTE = "45.32.43.1";
	public static final String LOCAL = "localhost";
	
	public static String getHost() {
		if (IS_REMOTE)
			return REMOTE;
		return LOCAL;
	}
	
	public static Boolean isRemote() {
		return IS_REMOTE;
	}
}
