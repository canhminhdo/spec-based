package server;

import config.Env;

public class ApplicationConfigurator {
	private static ApplicationConfigurator _instance = null;
	private Application app = null;
	
	public static ApplicationConfigurator getInstance() {
		if (_instance == null)
			_instance = new ApplicationConfigurator();
		
		return _instance;
	}
	

	public ApplicationConfigurator() {
		if (Env.IS_REMOTE) {
			this.app = new Application(new RemoteFactory());
		} else {
			this.app = new Application(new LocalFactory());
		}
	}
	
	public Application getApplication() {
		return this.app;
	}
}
