package by.epam.tc.notebook.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import by.epam.tc.notebook.dao.NoteDAO;
import by.epam.tc.notebook.dao.exception.DAOException;
import by.epam.tc.notebook.db.ConnectionPool;
import by.epam.tc.notebook.entity.Note;

public class NoteDAOImpl implements NoteDAO {
	/**
	 * Created by Aliaksei Boltak on 08/10/2016.
	 */

	@Override
	public void insert(Note ob) throws DAOException {

		PreparedStatement ps = null;
		Connection connection = null;

		try {

			connection = ConnectionPool.getPool().getConnection();

			ps = (PreparedStatement) connection.prepareStatement(
					"insert into " + ob.getClass().getSimpleName() + "(users_id, note, date)" + " values(?,?,?)");
			ps.setInt(1, ob.getUser_id());
			ps.setString(2, ob.getNote());
			ps.setString(3, ob.getDate());
			ps.execute();
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, ps);
		}
	}

	@Override
	public boolean addNewNote(int user_id, String note) throws DAOException {

		boolean success = true;
		PreparedStatement ps = null;
		Connection connection = null;

		try {
			connection = ConnectionPool.getPool().getConnection();
			ps = (PreparedStatement) connection
					.prepareStatement("insert into note (users_id, note, date)" + " values(?,?,?)");
			ps.setInt(1, user_id);
			ps.setString(2, note);
			ps.setString(3, Note.getDatecurrent());
			ps.execute();
		} catch (SQLException e) {
			success = false;
			throw new DAOException(e.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, ps);
		}
		return success;
	}

	@Override
	public boolean clearNotes(int user_id) throws DAOException {

		boolean success = true;
		PreparedStatement ps = null;
		Connection connection = null;

		try {
			connection = ConnectionPool.getPool().getConnection();
			ps = (PreparedStatement) connection.prepareStatement("delete from note where users_id=" + user_id);
			ps.execute();
		} catch (SQLException e) {
			success = false;
			throw new DAOException(e.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, ps);
		}
		return success;
	}

	@Override
	public ArrayList<Note> showAllNotes(int user_id) throws DAOException {
		
		ArrayList<Note> listNotes = new ArrayList<Note>();
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = ConnectionPool.getPool().getConnection();
			statement = (Statement) connection.createStatement();
			rs = statement.executeQuery("select * from note where users_id=" + user_id);
			while (rs.next()) {
				Note note = new Note(rs.getInt(1), rs.getInt(2), rs.getString(3));
				listNotes.add(note);
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, statement, rs);
		}
		return listNotes;
	}
	
	@Override
	public ArrayList<Note> findNotesByDate (int user_id, String date) throws DAOException {
		
		ArrayList<Note> listNotes = new ArrayList<Note>();
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = ConnectionPool.getPool().getConnection();
			statement = (Statement) connection.createStatement();
			rs = statement.executeQuery("select * from note where users_id=" + user_id + " and date='" + date + "';");
			while (rs.next()) {
				Note note = new Note(rs.getInt(1), rs.getInt(2), rs.getString(3));
				listNotes.add(note);
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, statement, rs);
		}
		return listNotes;
	}
	
	@Override
	public ArrayList<Note> findNotesByContent (int user_id, String content) throws DAOException {
		
		ArrayList<Note> listNotes = new ArrayList<Note>();
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = ConnectionPool.getPool().getConnection();
			statement = (Statement) connection.createStatement();
			rs = statement.executeQuery("select * from note where users_id=" + user_id + " and note like '%" +content+ "%'");
			while (rs.next()) {
				Note note = new Note(rs.getInt(1), rs.getInt(2), rs.getString(3));
				listNotes.add(note);
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, statement, rs);
		}
		return listNotes;
	}
	
	@Override
	public void update(Note ob) throws DAOException {

		PreparedStatement ps = null;
		Connection connection = null;

		try {
			connection = ConnectionPool.getPool().getConnection();
			ps = (PreparedStatement) connection.prepareStatement("update " + ob.getClass().getSimpleName()
					+ " set user_id=?, note=?,date=?" + " where id=" + ob.getId());
			ps.setInt(1, ob.getUser_id());
			ps.setString(2, ob.getNote());
			ps.setString(3, ob.getDate());
			ps.execute();
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, ps);
		}
	}

}
