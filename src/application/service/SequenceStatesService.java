package application.service;

import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Connection;

import application.dao.SequenceStatesDao;
import application.dao.SequenceStatesDaoImpl;
import application.model.SequenceStates;
import database.ConnectionFactory;

public class SequenceStatesService {
	
	public static List<SequenceStates> getAllTestCases() {
		SequenceStatesDao sequenceStateDao = new SequenceStatesDaoImpl(ConnectionFactory.getConnection());
		try {
			return sequenceStateDao.getAllSequenceStates();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean insert(SequenceStates seq) {
		Connection conn = ConnectionFactory.getConnection();
		boolean result = false;
		try {
			conn.setAutoCommit(false);
			SequenceStatesDao sequenceStateDao = new SequenceStatesDaoImpl(conn);
			sequenceStateDao.insert(seq);
			conn.commit();
			result = true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
//			ConnectionFactory.closeConnection();
		}
		return result;
	}

	public static boolean insertBatch(List<SequenceStates> list) {
		Connection conn = ConnectionFactory.getConnection();
		boolean result = false;
		try {
			conn.setAutoCommit(false);
			SequenceStatesDao sequenceStateDao = new SequenceStatesDaoImpl(conn);
			sequenceStateDao.insertBatch(list);
			conn.commit();
			result = true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
//			ConnectionFactory.closeConnection();
		}
		return result;
	}

	public static boolean truncate() {
		Connection conn = ConnectionFactory.getConnection();
		boolean result = false;
		try {
			conn.setAutoCommit(false);
			SequenceStatesDao sequenceStateDao = new SequenceStatesDaoImpl(conn);
			sequenceStateDao.truncate();
			conn.commit();
			result = true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection();
		}
		return result;
	}
}
