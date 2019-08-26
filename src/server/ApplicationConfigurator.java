package server;

import config.CaseStudy;
import server.factory.ServerFactory;

public class ApplicationConfigurator {
	
	private static ApplicationConfigurator _instance = null;
	private Application app = null;
	
	public static ApplicationConfigurator getInstance(CaseStudy cs) {
		if (_instance == null)
			_instance = new ApplicationConfigurator(cs);
		
		return _instance;
	}
	
	public static ApplicationConfigurator getInstance() {
		return _instance;
	}
	

	public ApplicationConfigurator(CaseStudy cs) {
		this.app = new Application(new ServerFactory(cs), cs);
	}
	
	public Application getApplication() {
		return this.app;
	}
}
