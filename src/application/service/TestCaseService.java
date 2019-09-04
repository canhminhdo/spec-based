package application.service;

import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Connection;

import application.dao.TestCaseDao;
import application.dao.TestCaseDaoImpl;
import application.model.TestCase;
import database.ConnectionFactory;

public class TestCaseService {
	
	public static List<TestCase> getAllTestCases() {
		TestCaseDao testCaseDao = new TestCaseDaoImpl(ConnectionFactory.getConnection());
		try {
			return testCaseDao.getAllTestCases();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean insert(TestCase testCase) {
		Connection conn = ConnectionFactory.getConnection();
		boolean result = false;
		try {
			conn.setAutoCommit(false);
			TestCaseDao testCaseDao = new TestCaseDaoImpl(ConnectionFactory.getConnection());
			testCaseDao.insert(testCase);
			conn.commit();
			result = true;
		} catch (SQLException ex) {
			ex.printStackTrace(); 
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static boolean insertBatch(List<TestCase> list, int index) {
		Connection conn = ConnectionFactory.getConnection();
		boolean result = false;
		try {
			conn.setAutoCommit(false);
			TestCaseDao testCaseDao = new TestCaseDaoImpl(conn);
			testCaseDao.insertBatch(list, index);
			conn.commit();
			result = true;
		} catch (SQLException ex) {
			ex.printStackTrace(); 
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static boolean truncate() {
		Connection conn = ConnectionFactory.getConnection();
		boolean result = false;
		try {
			conn.setAutoCommit(false);
			TestCaseDao testCaseDao = new TestCaseDaoImpl(conn);
			testCaseDao.truncate();
			conn.commit();
			result = true;
		} catch (SQLException ex) {
			ex.printStackTrace(); 
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
