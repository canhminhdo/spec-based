package config;

import java.util.ArrayList;

import jpf.common.OC;

public abstract class CaseStudy {
	
	public final Boolean IS_REMOTE = false;
	public final Boolean JPF_MODE = true;
	
	// RABBITMQ
	// -> remote mode
	public final String RABBITMQ_REMOTE_HOST = "45.32.43.1";
	public final String RABBITMQ_REMOTE_USERNAME = "dev";
	public final String RABBITMQ_REMOTE_PASSWORD = "pdev";
	public final String RABBITMQ_REMOTE_QUEUENAME = "ABP";
	// -> local mode
	public final String RABBITMQ_LOCAL_HOST = "localhost";
	public final String RABBITMQ_LOCAL_USERNAME = "";
	public final String RABBITMQ_LOCAL_PASSWORD = "";
	public final String RABBITMQ_LOCAL_QUEUENAME = "ABP";
	

	// REDIS
	// -> remote mode
	public final String REDIS_REMOTE_HOST = "45.32.43.1";
	public final Integer REDIS_REMOTE_PORT = 6379;
	// -> local mode
	public final String REDIS_LOCAL_HOST = "localhost";
	public final Integer REDIS_LOCAL_PORT = 6379;
	
	
	// you must implement these kind of functions to conform with each case study 
	public abstract String getMaudePath();
	public abstract String getClassPath();
	public abstract OC getInitialMessage();
	public abstract ArrayList<String> getConfigList(OC config);
	
	public Boolean isRemote() {
		return IS_REMOTE;
	}
	
	public Boolean isJPFMode() {
		return JPF_MODE;
	}
	
	
	// RABBITMQ informations
	public String getRabbitMQHost() {
		return isRemote() ? RABBITMQ_REMOTE_HOST : RABBITMQ_LOCAL_HOST;
	};
	
	public String getRabbitMQUsername() {
		return isRemote() ? RABBITMQ_REMOTE_USERNAME : RABBITMQ_LOCAL_USERNAME;
	};
	
	public String getRabbitMQPassword() {
		return isRemote() ? RABBITMQ_REMOTE_PASSWORD : RABBITMQ_LOCAL_PASSWORD;
	};
	
	public abstract String getQueueName();
	
	
	// REDIS informations
	public String getRedisHost() {
		return isRemote() ? REDIS_REMOTE_HOST : REDIS_LOCAL_HOST;
	};
	
	public Integer getRedisPort() {
		return isRemote() ? REDIS_REMOTE_PORT : REDIS_LOCAL_PORT;
	}
}
