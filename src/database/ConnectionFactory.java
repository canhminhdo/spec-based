package database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

import config.AppConfig;
import config.CaseStudy;

/**
 * Singleton MySQL instance
 * 
 * @author OgataLab
 *
 */
public class ConnectionFactory {
	
	public static String URL = null;
	public static String USER = null;
	public static String PASS = null;
	
	public static Connection conn = null;
	
	/**
	 * Get singleton MySQL connection
	 * 
	 * @return {@link Connection}
	 */
	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				getConfig();
				DriverManager.registerDriver(new Driver());
				conn = (Connection) DriverManager.getConnection(URL, USER, PASS);
			}
			return conn;
		} catch (SQLException ex) {
			throw new RuntimeException("Error connecting to the database", ex);
		}
	}
	
	public static void getConfig() {
		String host = null;
		String port = null;
		String db = null;
		if (CaseStudy.IS_REMOTE) {
			host = AppConfig.getInstance().getConfig().getProperty("mysql.remote.host");
			port = AppConfig.getInstance().getConfig().getProperty("mysql.remote.port");
			db = AppConfig.getInstance().getConfig().getProperty("mysql.remote.database");
			USER = AppConfig.getInstance().getConfig().getProperty("mysql.remote.username");
			PASS = AppConfig.getInstance().getConfig().getProperty("mysql.remote.password");
		} else {
			host = AppConfig.getInstance().getConfig().getProperty("mysql.local.host");
			port = AppConfig.getInstance().getConfig().getProperty("mysql.local.port");
			db = AppConfig.getInstance().getConfig().getProperty("mysql.local.database");
			USER = AppConfig.getInstance().getConfig().getProperty("mysql.local.username");
			PASS = AppConfig.getInstance().getConfig().getProperty("mysql.local.password");
		}
		URL = String.format("jdbc:mysql://%s:%s/%s", host, port, db);
	}
	
	/**
	 * Close MySQL connection
	 * 
	 * @param conn
	 */
	public static void closeConnection() {
		try {
			if (conn != null) conn.close();
		} catch (SQLException ex) {
			throw new RuntimeException("Error closing the database", ex);
		}
	}
	
	/**
	 * Test connection
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Connection connection = ConnectionFactory.getConnection();
		if (connection != null)
			System.out.println("Connected !!!");
		ConnectionFactory.closeConnection();
	}
}
