package by.epam.tc.notebook.dao.factory;

import by.epam.tc.notebook.dao.NoteDAO;
import by.epam.tc.notebook.dao.UserDAO;
import by.epam.tc.notebook.dao.impl.NoteDAOImpl;
import by.epam.tc.notebook.dao.impl.UserDAOImpl;
/**
 * Created by Aliaksei Boltak on 17/10/2016.
 */
public class DAOFactory {

	private static final DAOFactory INSTANCE = new DAOFactory();

	private static final UserDAO userDAO = new UserDAOImpl();

	private static final NoteDAO noteDAO = new NoteDAOImpl();

	private DAOFactory() {

	}

	public static DAOFactory getInstance() {
		return INSTANCE;
	}

	public NoteDAO getNoteDAO() {
		return noteDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

}
