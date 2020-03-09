package server;

import abp.config.ABPStudy;
import nspk.config.NspkStudy;
import cloudsync.config.CloudSyncStudy;
import config.CaseStudy;
import server.factory.ServerFactory;

/**
 * Bootstrap environment from here. You need configure your case study in this
 * file
 * 
 * @author OgataLab
 *
 */
public class ApplicationConfigurator {

	private static ApplicationConfigurator _instance = null;
	private Application app = null;

	/**
	 * Configure your case study in this function
	 * 
	 * @return
	 */
	public static ApplicationConfigurator getInstance() {
		if (_instance == null) {
//			_instance = new ApplicationConfigurator(new CloudSyncStudy());
//			_instance = new ApplicationConfigurator(new NspkStudy());
			_instance = new ApplicationConfigurator(new ABPStudy());
		}

		return _instance;
	}

	public ApplicationConfigurator(CaseStudy cs) {
		this.app = new Application(new ServerFactory(cs), cs);
	}

	public Application getApplication() {
		return this.app;
	}
}
