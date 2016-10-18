package by.epam.tc.notebook.dao.impl;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import by.epam.tc.notebook.dao.UserDAO;
import by.epam.tc.notebook.dao.exception.DAOException;
import by.epam.tc.notebook.db.ConnectionPool;
import by.epam.tc.notebook.entity.User;
import by.epam.tc.notebook.utils.StringUtils;

public class UserDAOImpl implements UserDAO {
	/**
	 * Created by Aliaksei Boltak on 08/10/2016.
	 */

	@Override
	public void insert(User ob) throws DAOException {

		PreparedStatement ps = null;
		Connection connection = null;
		
		try {
			connection = ConnectionPool.getPool().getConnection();
			ps = (PreparedStatement) connection.prepareStatement("insert into " + ob.getClass().getSimpleName()
					+ "(login, pass, role,  block_status)" + " values(?,?,?,?)");
			ps.setString(1, ob.getLogin());
			ps.setString(2, ob.getPass());
			ps.setInt(3, ob.getRole());
			ps.setInt(4, ob.getBlock_status());
			ps.execute();
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, ps);
		}
	}

	@Override
	public boolean registration(String login, String pass) throws DAOException {

		boolean valid = true;
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = ConnectionPool.getPool().getConnection();
			statement = (Statement) connection.createStatement();
			rs = statement.executeQuery("select * from user " + "where login='" + login + "'");

			if (rs != null) {
				if (rs.next()) {
					valid = false;
					;
				}
			}
		} catch (HeadlessException | SQLException e2) {
			valid = false;
			throw new DAOException(e2.getMessage());
		}

		if (valid) {
			PreparedStatement ps;
			try {
				ps = (PreparedStatement) connection
						.prepareStatement("insert into user (login, pass, role,  block_status)" + " values(?,?,?,?)");
				ps.setString(1, login);
				ps.setString(2, pass);
				ps.setInt(3, 0);
				ps.setInt(4, 0);
				ps.execute();
			} catch (SQLException e) {
				valid = false;
				throw new DAOException(e.getMessage());
			} finally {
				ConnectionPool.getPool().closeDbResources(connection, statement, rs);
			}
		}
		return valid;
	}

	@Override
	public User logination(String login, String pass) throws DAOException {

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = ConnectionPool.getPool().getConnection();
			statement = (Statement) connection.createStatement();
			rs = statement.executeQuery("select * from user where login ='" + login + "'");
			if (rs != null) {
				if (rs.next()) {
					if (rs.getString("pass").equals(pass)) {
						if (rs.getInt("role") == 0) {
							if (rs.getInt("block_status") == 0) {
								User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
										rs.getInt(5));
								return user;
							} else {
								if (rs.getInt("block_status") == 1) {
									User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
											1);
									return user;
								}
							}
						} else {
							if (rs.getInt("role") == 1) {
								User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
										rs.getInt(5));
								return user;
							}
						}
					}
				}
			}

		} catch (SQLException e1) {
			throw new DAOException(e1.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, statement, rs);
		}
		return null;
	}

	@Override
	public boolean blockUser(String users_id) throws DAOException {

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			connection = ConnectionPool.getPool().getConnection();

			statement = (Statement) connection.createStatement();

			rs = statement.executeQuery("select * from user " + "where users_id='" + Integer.valueOf(users_id) + "'");

			if (rs.next()) {

				statement.executeUpdate("update user set block_status=1 where users_id=" + Integer.valueOf(users_id));
				return true;

			}
		} catch (NumberFormatException | HeadlessException | SQLException e1) {

			throw new DAOException(e1.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, statement, rs);
		}

		return false;
	}

	@Override
	public boolean unBlockUser(String users_id) throws DAOException {

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			connection = ConnectionPool.getPool().getConnection();

			statement = (Statement) connection.createStatement();

			rs = statement.executeQuery("select * from user " + "where users_id='" + Integer.valueOf(users_id) + "'");

			if (rs.next()) {

				statement.executeUpdate("update user set block_status=0 where users_id=" + Integer.valueOf(users_id));
				return true;
			}
		} catch (NumberFormatException | HeadlessException | SQLException e1) {
			throw new DAOException(e1.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, statement, rs);
		}
		return false;
	}

	@Override
	public void update(User ob) throws DAOException {

		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = ConnectionPool.getPool().getConnection();
			ps = (PreparedStatement) connection.prepareStatement("update " + ob.getClass().getSimpleName()
					+ " set login=?, pass=?,role=?,  block_status=?" + " where users_id=" + ob.getUsers_id());
			ps.setString(1, ob.getLogin());
			ps.setString(2, ob.getPass());
			ps.setInt(3, ob.getRole());
			ps.setInt(4, ob.getBlock_status());
			ps.execute();
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, ps);
		}
	}

	@Override
	public ArrayList<User> getUsers() throws DAOException {

		ArrayList<User> listUsers = new ArrayList<User>();

		Connection connection = null;
		ResultSet rs = null;
		Statement statement = null;

		try {

			connection = ConnectionPool.getPool().getConnection();

			statement = (Statement) connection.createStatement();

			rs = statement.executeQuery("select * from users");

			while (rs.next()) {
				User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
				listUsers.add(user);
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, statement, rs);
		}
		return listUsers;
	}

}
