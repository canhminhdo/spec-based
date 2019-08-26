package database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

public class ConnectionFactory {
	
	public static final String URL = "jdbc:mysql://localhost:3306/env";
	public static final String USER = "root";
	public static final String PASS = "";
	
	public static Connection getConnection() {
		try {
			DriverManager.registerDriver(new Driver());
			return (Connection) DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException ex) {
			throw new RuntimeException("Error connecting to the database", ex);
		}
	}
	
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException ex) {
			throw new RuntimeException("Error closing the database", ex);
		}
	}
	
	/**
	 * Test Connection
	 */
	
	public static void main(String[] args) {
		Connection conn = ConnectionFactory.getConnection();
		System.out.println("Connected !!!");
	}
}
