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
	
	// runtime
	public static String RUNTIME = "1.0";
	
	// the current version
	public static String version = "1.0";
	// using JPF_MODE ?
	public static Boolean JPF_MODE = true;
	
	public static String SECRETE_KEY = "OgataLab";
	
	// Maude program information
	public static String MAUDE_PROGRAM = "/Users/ogataslab/Downloads/Applications/Maude-2.7.1-osx/maude.darwin64";
	
	// Using for state sequences generation by JPF
	public static int DEPTH = 100;
	public static int BOUND = 1000;
	public static boolean DEPTH_FLAG = true;
	public static boolean BOUND_FLAG = false;

	public final Boolean IS_REMOTE = false;

	// RABBITMQ
	// -> remote mode
	public final String RABBITMQ_REMOTE_HOST = "45.32.43.1";
	public final String RABBITMQ_REMOTE_USERNAME = "dev";
	public final String RABBITMQ_REMOTE_PASSWORD = "pdev";
	// -> local mode
	public final String RABBITMQ_LOCAL_HOST = "localhost";
	public final String RABBITMQ_LOCAL_USERNAME = "";
	public final String RABBITMQ_LOCAL_PASSWORD = "";

	// REDIS
	// -> remote mode
	public final String REDIS_REMOTE_HOST = "45.32.43.1";
	public final Integer REDIS_REMOTE_PORT = 6379;
	// -> local mode
	public final String REDIS_LOCAL_HOST = "localhost";
	public final Integer REDIS_LOCAL_PORT = 6379;

	/**
	 * Get CLASS_PATH where you program is locate
	 * 
	 * @return {@link String}
	 */
	public abstract String getClassPath();

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
		System.out.println("Case Study");
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
