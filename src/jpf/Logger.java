package jpf;

enum LEVEL 
{ 
    DEBUG, INFO, FULL; 
}

public class Logger {
	public static final int LOG_LEVEL_LOG = 0x10000;
	public static final int LOG_LEVEL_INFO = 0x20000;
	public static final int LOG_LEVEL_ERROR = 0x40000;
	public static final int LOG_LEVEL_WARN = 0x80000;
	public static final int LOG_MODE = (LOG_LEVEL_LOG | LOG_LEVEL_INFO | LOG_LEVEL_ERROR | LOG_LEVEL_WARN);
	
	public static void log(Object msg) {
		if ((LOG_MODE & LOG_LEVEL_LOG) > 0) {
			System.out.println(msg);
		}
	}
	
	public static void info(Object msg) {
		if ((LOG_MODE & LOG_LEVEL_INFO) > 0) {
			System.out.println(msg);
		}
	}
	
	public static void error(Object msg) {
		if ((LOG_MODE & LOG_LEVEL_ERROR) > 0) {
			System.out.println(msg);
		}
	}
	
	public static void warn(Object msg) {
		if ((LOG_MODE & LOG_LEVEL_WARN) > 0) {
			System.out.println(msg);
		}
	}

}
