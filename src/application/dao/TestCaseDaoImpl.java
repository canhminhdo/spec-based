package application.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import application.model.TestCase;

public class TestCaseDaoImpl implements TestCaseDao {
	Connection conn;

	public TestCaseDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<TestCase> getAllTestCases() throws SQLException {
		List<TestCase> list = new ArrayList<TestCase>();
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM test_cases");
		while (rs.next()) {
			list.add(createModel(rs));
		}
		return list;
	}

	@Override
	public void insert(TestCase testCase) throws SQLException {
		String sql = "INSERT INTO test_cases (seqNo, stateId, depth, packetsToBeSent, packetsReceived, channel1, channel2, `index`, finish, flag1, flag2)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);

		ps.setInt(1, testCase.getSeqNo());
		ps.setInt(2, testCase.getDepth());
		ps.setInt(3, testCase.getStateId());
		ps.setString(4, testCase.getPacketsToBeSent());
		ps.setString(5, testCase.getPacketsReceived());
		ps.setString(6, testCase.getChannel1());
		ps.setString(7, testCase.getChannel2());
		ps.setInt(8, testCase.getIndex());
		ps.setString(9, testCase.getFinish());

		if (testCase.isFlag1() != null)
			ps.setString(10, testCase.isFlag1().toString());
		else
			ps.setString(10, null);

		if (testCase.isFlag2() != null)
			ps.setString(11, testCase.isFlag2().toString());
		else
			ps.setString(11, null);

//			System.out.println(ps);
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public void insertBatch(List<TestCase> list, int index) throws SQLException {
		String sql = "INSERT INTO test_cases (seqNo, stateId, depth, packetsToBeSent, packetsReceived, channel1, channel2, `index`, finish, flag1, flag2)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		for (TestCase testCase : list) {
			ps.setInt(1, index);
			ps.setInt(2, testCase.getStateId());
			ps.setInt(3, testCase.getDepth());
			ps.setString(4, testCase.getPacketsToBeSent());
			ps.setString(5, testCase.getPacketsReceived());
			ps.setString(6, testCase.getChannel1());
			ps.setString(7, testCase.getChannel2());
			ps.setInt(8, testCase.getIndex());
			ps.setString(9, testCase.getFinish());

			if (testCase.isFlag1() != null)
				ps.setString(10, testCase.isFlag1().toString());
			else
				ps.setString(10, null);

			if (testCase.isFlag2() != null)
				ps.setString(11, testCase.isFlag2().toString());
			else
				ps.setString(11, null);

			ps.addBatch();
		}
		ps.executeBatch();
		ps.close();
	}

	@Override
	public void truncate() throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE test_cases");
		ps.execute();
		ps.close();
	}

	public TestCase createModel(ResultSet rs) throws SQLException {
		TestCase testCase = new TestCase();
		testCase.setId(rs.getInt("id"));
		testCase.setStateId(rs.getInt("stateId"));
		testCase.setDepth(rs.getInt("depth"));

		return testCase;
	}
}
