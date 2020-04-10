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
	
	public static String PROJECT_BASE = AppConfig.getInstance().getConfig().getProperty("project.base");
	
	// runtime
	public static String RUNTIME = AppConfig.getInstance().getConfig().getProperty("version");
	
	// using JPF_MODE
	public static Boolean JPF_MODE = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("jpf.mode"));
	
	public static String SECRETE_KEY = AppConfig.getInstance().getConfig().getProperty("secreteKey");
	
	// Maude program information
	public static String MAUDE_PROGRAM = AppConfig.getInstance().getConfig().getProperty("maude.program");
	public static Integer MAUDE_DEPTH =  Integer.parseInt(AppConfig.getInstance().getConfig().getProperty("maude.depth"));
	public static Boolean MAUDE_WORKER_IS_ENABLE = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("maude.worker.isEnable"));
		
	// Using for state sequences generation by JPF
	public static int DEPTH = Integer.parseInt(AppConfig.getInstance().getConfig().getProperty("jpf.depth"));	// Depth for each sub state space running by a JPF instance
	public static int BOUND = Integer.parseInt(AppConfig.getInstance().getConfig().getProperty("jpf.bound"));	// Bound for each sub state space running by a JPF instance
	public static boolean DEPTH_FLAG = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("jpf.depth.isEnable"));
	public static boolean BOUND_FLAG = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("jpf.bound.isEnable"));
	
	// If you want to run with a Bounded Model Checking
	public static boolean IS_BOUNDED_MODEL_CHECKING = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("jpf.bmc.isEnable"));
	public static int MAX_DEPTH = 400;	// Maximum depth when you run Bounded Model Checking

	public static Boolean IS_REMOTE = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("isRemote"));

	// RABBITMQ
	// -> remote mode
	public final String RABBITMQ_REMOTE_HOST = AppConfig.getInstance().getConfig().getProperty("rabbitmq.remote.host");
	public final String RABBITMQ_REMOTE_USERNAME = AppConfig.getInstance().getConfig().getProperty("rabbitmq.remote.username");
	public final String RABBITMQ_REMOTE_PASSWORD = AppConfig.getInstance().getConfig().getProperty("rabbitmq.remote.password");
	// -> local mode
	public final String RABBITMQ_LOCAL_HOST = AppConfig.getInstance().getConfig().getProperty("rabbitmq.local.host");
	public final String RABBITMQ_LOCAL_USERNAME = AppConfig.getInstance().getConfig().getProperty("rabbitmq.local.username");
	public final String RABBITMQ_LOCAL_PASSWORD = AppConfig.getInstance().getConfig().getProperty("rabbitmq.local.password");

	// REDIS
	// -> remote mode
	public final String REDIS_REMOTE_HOST = AppConfig.getInstance().getConfig().getProperty("redis.remote.host");
	public final Integer REDIS_REMOTE_PORT = Integer.valueOf(AppConfig.getInstance().getConfig().getProperty("redis.remote.port"));
	// -> local mode
	public final String REDIS_LOCAL_HOST = AppConfig.getInstance().getConfig().getProperty("redis.local.host");;
	public final Integer REDIS_LOCAL_PORT = Integer.valueOf(AppConfig.getInstance().getConfig().getProperty("redis.local.port"));
	
	// MySQL
	public static Boolean MYSQL_IS_ENABLE = Boolean.valueOf(AppConfig.getInstance().getConfig().getProperty("mysql.isEnable"));
	
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
}
