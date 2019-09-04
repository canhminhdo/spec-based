package database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

/**
 * Singleton MySQL instance
 * 
 * @author OgataLab
 *
 */
public class ConnectionFactory {
	
	public static final String URL = "jdbc:mysql://localhost:3306/ogatalab";
	public static final String USER = "root";
	public static final String PASS = "";
	
	public static Connection conn = null;
	
	/**
	 * Get singleton MySQL connection
	 * 
	 * @return {@link Connection}
	 */
	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				DriverManager.registerDriver(new Driver());
				return (Connection) DriverManager.getConnection(URL, USER, PASS);
			}
			return conn;
		} catch (SQLException ex) {
			throw new RuntimeException("Error connecting to the database", ex);
		}
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
