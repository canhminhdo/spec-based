package checker.factory;

import application.service.SequenceStatesService;
import config.CaseStudy;
import database.RedisClient;
import server.Application;
import server.ApplicationConfigurator;

public abstract class StarterFactory {
	
	protected Application app;
	
	protected StarterFactory() {
		app = ApplicationConfigurator.getInstance().getApplication();
	}
	
	public abstract void cleanUp();
	public abstract void start();
	public abstract void initializeRedisSysInfo();
	public abstract void pushInitialJob();
	public abstract void saveInitialMessageToRedis();
	
	public void cleanRedis() {
		RedisClient.getInstance(app.getRedis().getHost(), app.getRedis().getPort()).getConnection().flushAll();
	}
	
	public void trunscateDatabase() {
		if (CaseStudy.MYSQL_IS_ENABLE && SequenceStatesService.truncate())
			System.out.println("Truncate successfully !!!");
	}
}
