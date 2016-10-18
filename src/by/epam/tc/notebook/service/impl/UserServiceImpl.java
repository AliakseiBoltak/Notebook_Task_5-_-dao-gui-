package by.epam.tc.notebook.service.impl;

import java.util.ArrayList;

import by.epam.tc.notebook.dao.UserDAO;
import by.epam.tc.notebook.dao.exception.DAOException;
import by.epam.tc.notebook.dao.factory.DAOFactory;
import by.epam.tc.notebook.entity.User;
import by.epam.tc.notebook.service.UserService;
import by.epam.tc.notebook.service.exception.ServiceException;
import by.epam.tc.notebook.utils.StringUtils;

/**
 * Created by Aliaksei Boltak on 09/10/2016.
 */

public class UserServiceImpl implements UserService {

	@Override
	public boolean registration(String login, String pass) throws ServiceException {
		if (!StringUtils.isValid(login) || !StringUtils.isValid(pass)) {
			throw new ServiceException("Incorrect parametres");
		} else {
			UserDAO daoUser = DAOFactory.getInstance().getUserDAO();
			try {
				return (daoUser.registration(login, pass));
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage());
			}
		}
	}

	@Override
	public User logination(String login, String pass) throws ServiceException {
		User user = null;
		if (!StringUtils.isValid(login) || !StringUtils.isValid(pass)) {
			throw new ServiceException("Incorrect parametres");
		} else {
			UserDAO daoUser = DAOFactory.getInstance().getUserDAO();
			try {
				user = daoUser.logination(login, pass);
			} catch (DAOException e1) {
				throw new ServiceException(e1.getMessage());
			}
		}
		return user;
	}

	@Override
	public boolean blockUser(String users_id) throws ServiceException {
		if (!StringUtils.isValid(users_id) || !StringUtils.isInteger(users_id)) {
			throw new ServiceException("Incorrect parametres");
		} else {
			UserDAO daoUser = DAOFactory.getInstance().getUserDAO();
			try {
				return daoUser.blockUser(users_id);
			} catch (DAOException e1) {
				throw new ServiceException(e1.getMessage());
			}
		}
	}

	@Override
	public boolean unBlockUser(String users_id) throws ServiceException {
		if (!StringUtils.isValid(users_id) || !StringUtils.isInteger(users_id)) {
			throw new ServiceException("Incorrect parametres");
		} else {
			UserDAO daoUser = DAOFactory.getInstance().getUserDAO();
			try {
				return daoUser.unBlockUser(users_id);
			} catch (DAOException e1) {
				throw new ServiceException(e1.getMessage());
			}
		}
	}

	@Override
	public void update(User user) throws ServiceException {
		if (user == null) {
			throw new ServiceException("Incorrect parametres");
		} else {
			UserDAO daoUser = DAOFactory.getInstance().getUserDAO();
			try {
				daoUser.update(user);
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<User> getUsers() throws ServiceException {
		ArrayList<User> users = null;
		try {
			UserDAO daoUser = DAOFactory.getInstance().getUserDAO();
			users = daoUser.getUsers();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return users;
	}
}
