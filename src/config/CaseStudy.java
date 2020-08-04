package config;

import java.util.ArrayList;

import jpf.common.HeapJPF;
import jpf.common.OC;

/**
 * This file to cover all case studies what we will experiment. Using RabbitMQ
 * as message broker and Redis as cache
 * 
 * @author OgataLab
 */
public abstract class CaseStudy {
	static {
	    initStaticFields();
	}
	
	public static String SYSTEM_MODE;
	
	public static String PROJECT_BASE;
	
	// runtime
	public static String RUNTIME;
	
	// log4j file path
	public static String LOG4J_PATH;
	
	// using JPF_MODE
	public static Boolean JPF_MODE;
	
	public static String SECRETE_KEY;
	
	// Maude program information
	public static String MAUDE_PROGRAM;
	public static Integer MAUDE_DEPTH;
	public static Boolean MAUDE_WORKER_IS_ENABLE;
		
	// Using for state sequences generation by JPF
	public static int DEPTH;	// Depth for each sub state space running by a JPF instance
	public static int BOUND;	// Bound for each sub state space running by a JPF instance
	public static boolean DEPTH_FLAG;
	public static boolean BOUND_FLAG;
	
	// If you want to run with a Bounded Model Checking
	public static boolean IS_BOUNDED_MODEL_CHECKING;
	public static int MAX_DEPTH;	// Maximum depth when you run Bounded Model Checking
	public static ArrayList<Double> PERCENTAGES;
	
	public static Boolean IS_REMOTE;

	// RABBITMQ
	// -> remote mode
	public static String RABBITMQ_REMOTE_HOST;
	public static String RABBITMQ_REMOTE_USERNAME;
	public static String RABBITMQ_REMOTE_PASSWORD;
	// -> local mode
	public static String RABBITMQ_LOCAL_HOST;
	public static String RABBITMQ_LOCAL_USERNAME;
	public static String RABBITMQ_LOCAL_PASSWORD;

	// REDIS
	// -> remote mode
	public static String REDIS_REMOTE_HOST;
	public static Integer REDIS_REMOTE_PORT;
	// -> local mode
	public static String REDIS_LOCAL_HOST;
	public static Integer REDIS_LOCAL_PORT;
	
	// MySQL
	public static Boolean MYSQL_IS_ENABLE;
	
	static void initStaticFields() {
		PROJECT_BASE = AppConfig.getInstance().getConfig().getProperty("project.base");
		RUNTIME = AppConfig.getInstance().getConfig().getProperty("version");
		JPF_MODE = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("jpf.mode"));
		SECRETE_KEY = AppConfig.getInstance().getConfig().getProperty("secreteKey");
		
		LOG4J_PATH = AppConfig.getInstance().getConfig().getProperty("log4j.path");
		
		MAUDE_PROGRAM = AppConfig.getInstance().getConfig().getProperty("maude.program");
		MAUDE_DEPTH =  Integer.parseInt(AppConfig.getInstance().getConfig().getProperty("maude.depth"));
		MAUDE_WORKER_IS_ENABLE = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("maude.worker.isEnable"));
			
		// Using for state sequences generation by JPF
		DEPTH = Integer.parseInt(AppConfig.getInstance().getConfig().getProperty("jpf.depth"));	// Depth for each sub state space running by a JPF instance
		BOUND = Integer.parseInt(AppConfig.getInstance().getConfig().getProperty("jpf.bound"));	// Bound for each sub state space running by a JPF instance
		DEPTH_FLAG = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("jpf.depth.isEnable"));
		BOUND_FLAG = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("jpf.bound.isEnable"));
		
		// If you want to run with a Bounded Model Checking
		IS_BOUNDED_MODEL_CHECKING = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("jpf.bmc.isEnable"));
		MAX_DEPTH = Integer.parseInt(AppConfig.getInstance().getConfig().getProperty("jpf.bmc.max_depth"));	// Maximum depth when you run Bounded Model Checking
		String percentagesConfig = AppConfig.getInstance().getConfig().getProperty("jpf.percentages");
		String[] percentages = percentagesConfig.split(" ");
		PERCENTAGES = new ArrayList<Double>();
		for (int i = 0; i < percentages.length; i ++) {
			PERCENTAGES.add(Double.parseDouble(percentages[i]));
		}
		
		IS_REMOTE = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("env.isRemote"));

		// RABBITMQ
		// -> remote mode
		RABBITMQ_REMOTE_HOST = AppConfig.getInstance().getConfig().getProperty("rabbitmq.remote.host");
		RABBITMQ_REMOTE_USERNAME = AppConfig.getInstance().getConfig().getProperty("rabbitmq.remote.username");
		RABBITMQ_REMOTE_PASSWORD = AppConfig.getInstance().getConfig().getProperty("rabbitmq.remote.password");
		// -> local mode
		RABBITMQ_LOCAL_HOST = AppConfig.getInstance().getConfig().getProperty("rabbitmq.local.host");
		RABBITMQ_LOCAL_USERNAME = AppConfig.getInstance().getConfig().getProperty("rabbitmq.local.username");
		RABBITMQ_LOCAL_PASSWORD = AppConfig.getInstance().getConfig().getProperty("rabbitmq.local.password");

		// REDIS
		// -> remote mode
		REDIS_REMOTE_HOST = AppConfig.getInstance().getConfig().getProperty("redis.remote.host");
		REDIS_REMOTE_PORT = Integer.valueOf(AppConfig.getInstance().getConfig().getProperty("redis.remote.port"));
		// -> local mode
		REDIS_LOCAL_HOST = AppConfig.getInstance().getConfig().getProperty("redis.local.host");;
		REDIS_LOCAL_PORT = Integer.valueOf(AppConfig.getInstance().getConfig().getProperty("redis.local.port"));
		
		// MySQL
		MYSQL_IS_ENABLE = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("mysql.isEnable"));
	}
	/**
	 * Get CLASS_PATH where you program is locate
	 * 
	 * @return {@link String}
	 */
	public String getClassPath() {
		return System.getProperty("java.class.path");
	}

	/**
	 * This is to get the initial message to send to the master queue
	 * 
	 * @return {@link OC}
	 */
	public abstract OC getInitialMessage();

	/**
	 * Building configList from a Observer Component
	 * 
	 * @param config This is a observer component object value
	 * @return {@link ArrayList<String>}
	 */
	public abstract ArrayList<String> getConfigList(OC config);

	/**
	 * Are you using remote mode ?
	 * 
	 * @return {@link Boolean}
	 */
	public Boolean isRemote() {
		return IS_REMOTE;
	}

	/**
	 * Get RabbitMQ Host
	 * 
	 * @return {@link String}
	 */
	public String getRabbitMQHost() {
		return isRemote() ? RABBITMQ_REMOTE_HOST : RABBITMQ_LOCAL_HOST;
	};

	/**
	 * Get RabbitMQ Username
	 * 
	 * @return {@link String}
	 */
	public String getRabbitMQUsername() {
		return isRemote() ? RABBITMQ_REMOTE_USERNAME : RABBITMQ_LOCAL_USERNAME;
	};

	/**
	 * Get RabbitMQ Password
	 * 
	 * @return {@link String}
	 */
	public String getRabbitMQPassword() {
		return isRemote() ? RABBITMQ_REMOTE_PASSWORD : RABBITMQ_LOCAL_PASSWORD;
	};

	/**
	 * Get queue name when using RabbitMQ
	 * 
	 * @return {@link String}
	 */
	public abstract String getQueueName();
	
	/**
	 * Get queue name of maude when using RabbitMQ
	 * 
	 * @return {@link String}
	 */
	public abstract String getMaudeQueue();

	/**
	 * Get Redis Host
	 * 
	 * @return {@link String}
	 */
	public String getRedisHost() {
		return isRemote() ? REDIS_REMOTE_HOST : REDIS_LOCAL_HOST;
	};

	/**
	 * Get Redis port
	 * 
	 * @return {@link String}
	 */
	public Integer getRedisPort() {
		return isRemote() ? REDIS_REMOTE_PORT : REDIS_LOCAL_PORT;
	}

	/**
	 * Get a HeapJPF object
	 * 
	 * @return {@link HeapJPF}
	 */
	public abstract HeapJPF getHeapJPF();

	/**
	 * Print your case study before state sequences generation
	 */
	public void printConfiguration() {
		System.out.println("This is " + this.getClass().getSimpleName());
	}
	
	/**
	 * Get all maude files what you need to pre-load
	 * 
	 * @return [@link String[]]
	 */
	public abstract String[] getMaudeFiles();
	
	/**
	 * Get command to feed into Maude program
	 * 
	 * @return [@link String]
	 */
	public abstract String getCommand();
	
	public boolean isBmcModelChecking() {
		return this.IS_BOUNDED_MODEL_CHECKING;
	}
}
