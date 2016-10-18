package by.epam.tc.notebook.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
/**
 * Created by Aliaksei Boltak on 17/10/2016.
 */

public class ConnectionPool {

	private static final String DB_DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/notebook?useSSL=false";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "sqlserver";

	static {
		try {
			Class.forName(DB_DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private static class PoolHolder {
		private static final ConnectionPool POOL = new ConnectionPool();
	}

	public static ConnectionPool getPool() {
		return PoolHolder.POOL;
	}

	private ConnectionPool() {
	}

	public Connection getConnection() {
		try {
			return (Connection) DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void closeDbResources(Connection connection, Statement statement, ResultSet resultSet) {
		closeResultSet(resultSet);
		closeStatement(statement);
		closeConnection(connection);
	}
	
	public void closeDbResources(Connection connection, PreparedStatement ps, ResultSet resultSet) {
		closeResultSet(resultSet);
		closeConnection(connection);
		closePrepareStatement(ps);
	}
	
	public void closeDbResources(Connection connection, PreparedStatement ps) {
		closeConnection(connection);
		closePrepareStatement(ps);
	}
	

	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}

	private void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
			}
		}
	}

	private void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
			}
		}
	}
	
	private void closePrepareStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
			}
		}
	}
}
