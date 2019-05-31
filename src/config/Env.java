package config;

public interface Env {
	public static Boolean IS_REMOTE = true;
	public static Boolean JPF_MODE = true;
	
	public static int BOUND = 3;
	public static String PACKETS[] = { "0", "1", "2", "3" };
	
	public static final String RABBITMQ_REMOTE_HOST = "45.32.43.1";
	public static final String RABBITMQ_REMOTE_USERNAME = "dev";
	public static final String RABBITMQ_REMOTE_PASSWORD = "pdev";
	public static final String RABBITMQ_REMOTE_QUEUENAME = "ABP";
	
	public static final String RABBITMQ_LOCAL_HOST = "localhost";
	public static final String RABBITMQ_LOCAL_USERNAME = "";
	public static final String RABBITMQ_LOCAL_PASSWORD = "";
	public static final String RABBITMQ_LOCAL_QUEUENAME = "ABP";
	
	public static final String REDIS_REMOTE_HOST = "45.32.43.1";
	public static final Integer REDIS_REMOTE_PORT = 6379;
	
	public static final String REDIS_LOCAL_HOST = "localhost";
	public static final Integer REDIS_LOCAL_PORT = 6379;
	
	
	// canhdominh
	public static String CLASS_PATH = "/Users/canhdominh/eclipse-workspace/abp/bin";
	public static String MAUDE_DATA_PATH = "/Users/canhdominh/eclipse-workspace/abp/maude/data*";
	
	// student
//	public static String CLASS_PATH = "/Users/student/eclipse-workspace/abp/bin";
//	public static String MAUDE_DATA_PATH = "/Users/student/eclipse-workspace/abp/maude/data*";
	
	// ogataslab
//	public static String CLASS_PATH = "/Users/ogataslab/eclipse-workspace/abp/bin";
//	public static String MAUDE_DATA_PATH = "/Users/ogataslab/eclipse-workspace/abp/maude/data*";
}
