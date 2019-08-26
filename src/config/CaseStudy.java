package config;

import jpf.common.OC;

public abstract class CaseStudy {
	
	public final Boolean IS_REMOTE = false;
	public final Boolean JPF_MODE = true;
	
	// RABBITMQ
	public final String RABBITMQ_REMOTE_HOST = "45.32.43.1";
	public final String RABBITMQ_REMOTE_USERNAME = "dev";
	public final String RABBITMQ_REMOTE_PASSWORD = "pdev";
	public final String RABBITMQ_REMOTE_QUEUENAME = "ABP";
	
	public final String RABBITMQ_LOCAL_HOST = "localhost";
	public final String RABBITMQ_LOCAL_USERNAME = "";
	public final String RABBITMQ_LOCAL_PASSWORD = "";
	public final String RABBITMQ_LOCAL_QUEUENAME = "ABP";
	

	// REDIS
	public final String REDIS_REMOTE_HOST = "45.32.43.1";
	public final Integer REDIS_REMOTE_PORT = 6379;
	
	public final String REDIS_LOCAL_HOST = "localhost";
	public final Integer REDIS_LOCAL_PORT = 6379;
	
	
	
	public abstract String getMaudePath();
	public abstract String getClassPath();
	public abstract OC getInitialMessage();
	
	public Boolean isRemote() {
		return IS_REMOTE;
	}
	
	public Boolean isJPFMode() {
		return JPF_MODE;
	}
	
	// RabbitMQ informations
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
	
	
	// Redis informations
	public String getRedisHost() {
		return isRemote() ? REDIS_REMOTE_HOST : REDIS_LOCAL_HOST;
	};
	
	public Integer getRedisPort() {
		return isRemote() ? REDIS_REMOTE_PORT : REDIS_LOCAL_PORT;
	};
	
	
}
