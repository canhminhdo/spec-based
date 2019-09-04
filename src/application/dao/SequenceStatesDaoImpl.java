package application.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import application.model.SequenceStates;

public class SequenceStatesDaoImpl implements SequenceStatesDao {
	private String tableName = "sequence_states";
	
	Connection conn;

	public SequenceStatesDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<SequenceStates> getAllSequenceStates() throws SQLException {
		List<SequenceStates> list = new ArrayList<SequenceStates>();
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tableName);
		while (rs.next()) {
			list.add(createModel(rs));
		}
		return list;
	}

	@Override
	public void insert(SequenceStates seq) throws SQLException {
		String sql = "INSERT INTO " + this.tableName + " (`seq`, `type`, `state_from`, `state_to`, `state_index`, `depth`, `result`, `runtime`)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);

		ps.setString(1, seq.getSeq());
		ps.setString(2, seq.getType());
		ps.setString(3, seq.getState_from());
		ps.setString(4, seq.getState_to());
		ps.setInt(5, seq.getState_index());
		ps.setInt(6, seq.getDepth());
		ps.setString(7, seq.getResult());
		ps.setString(8, seq.getRuntime());

		ps.executeUpdate();
		ps.close();
	}

	@Override
	public void insertBatch(List<SequenceStates> list) throws SQLException {
		String sql = "INSERT INTO " + this.tableName + " (`seq`, `type`, `state_from`, `state_to`, `state_index`, `depth`, `result`, `runtime`)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		for (SequenceStates seq : list) {
			ps.setString(1, seq.getSeq());
			ps.setString(2, seq.getType());
			ps.setString(3, seq.getState_from());
			ps.setString(4, seq.getState_to());
			ps.setInt(5, seq.getState_index());
			ps.setInt(6, seq.getDepth());
			ps.setString(7, seq.getResult());
			ps.setString(8, seq.getRuntime());
			ps.addBatch();
		}
		
		ps.executeBatch();
		ps.close();
	}

	@Override
	public void truncate() throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE " + this.tableName);
		ps.execute();
		ps.close();
	}

	public SequenceStates createModel(ResultSet rs) throws SQLException {
		SequenceStates seq = new SequenceStates();
		seq.setId(rs.getInt("id"));
		seq.setDepth(rs.getInt("depth"));

		return seq;
	}
}
