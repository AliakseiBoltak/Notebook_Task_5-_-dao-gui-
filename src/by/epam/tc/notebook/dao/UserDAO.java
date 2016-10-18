package by.epam.tc.notebook.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import by.epam.tc.notebook.dao.exception.DAOException;
import by.epam.tc.notebook.entity.User;
/**
 * Created by Aliaksei Boltak on 08/10/2016.
 */

public interface UserDAO {
	
	public void insert (User ob) throws DAOException;

	public boolean registration(String login, String pass) throws DAOException;

	public User logination(String login, String pass)throws DAOException;

	public boolean blockUser(String users_id) throws DAOException;

	public boolean unBlockUser(String users_id) throws DAOException;

	public void update(User ob) throws DAOException;

	public ArrayList <User> getUsers() throws DAOException;
	
}
