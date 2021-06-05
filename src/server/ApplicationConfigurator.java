package server;

import abp.config.ABPStudy;
import nspk.config.NslpkStudy;
import nspk.config.NspkStudy;
import cloudsync.config.CloudSyncStudy;
import config.AppConfig;
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
			String caseStudy = AppConfig.getInstance().getConfig().getProperty("caseStudy");
			if (caseStudy.equals("nspk")) {
				_instance = new ApplicationConfigurator(new NspkStudy());
			} else if (caseStudy.equals("nslpk")) {
				_instance = new ApplicationConfigurator(new NslpkStudy());
			} else if (caseStudy.equals("cloudsync")) {
				_instance = new ApplicationConfigurator(new CloudSyncStudy());
			} else if (caseStudy.equals("abp")) {
				_instance = new ApplicationConfigurator(new ABPStudy());
			}
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
