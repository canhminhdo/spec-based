package application.dao;

import java.sql.SQLException;
import java.util.List;

import application.model.SequenceStates;

public interface SequenceStatesDao {
	public List<SequenceStates> getAllSequenceStates() throws SQLException;
	public void insert(SequenceStates seq) throws SQLException;
	public void insertBatch(List<SequenceStates> list) throws SQLException;
	public void truncate() throws SQLException;
}
