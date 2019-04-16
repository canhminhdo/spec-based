package dao;

import java.sql.SQLException;
import java.util.List;

import model.TestCase;

public interface TestCaseDao {
	public List<TestCase> getAllTestCases() throws SQLException;
	public void insert(TestCase testCase) throws SQLException;
	public void insertBatch(List<TestCase> list, int index) throws SQLException;
	public void truncate() throws SQLException;
}
