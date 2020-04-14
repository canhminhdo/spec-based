package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
	
	public final String configFileName = "config.properties";
	public static AppConfig _instance = null;
	public Properties appProps = null;

	public static AppConfig getInstance() {
		if (_instance == null) {
			_instance = new AppConfig();
		}
		return _instance;
	}

	private AppConfig() {
		// initialize the app configuration from config.properties file
		try {
			String rootPath = System.getProperty("user.dir");
			String appConfigPath = rootPath + File.separator + "config.properties";
			appProps = new Properties();
			appProps.load(new FileInputStream(appConfigPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Properties getConfig() {
		return appProps;
	}

	public static void main(String[] args) {
		AppConfig.getInstance();
	}
}
