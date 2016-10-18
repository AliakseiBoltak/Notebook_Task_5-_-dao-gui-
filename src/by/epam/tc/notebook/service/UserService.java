package by.epam.tc.notebook.service;

import java.util.ArrayList;
import by.epam.tc.notebook.entity.User;
import by.epam.tc.notebook.service.exception.ServiceException;

public interface UserService {

	public boolean registration(String login, String pass) throws ServiceException;

	public User logination(String login, String pass)throws ServiceException;

	public boolean blockUser(String users_id) throws ServiceException;

	public boolean unBlockUser(String users_id) throws ServiceException;

	public void update(User ob) throws ServiceException;

	public ArrayList <User> getUsers() throws ServiceException;
	
}
