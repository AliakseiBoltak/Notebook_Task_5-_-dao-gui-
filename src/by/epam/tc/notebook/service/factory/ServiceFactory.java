package by.epam.tc.notebook.service.factory;

import by.epam.tc.notebook.service.NoteService;
import by.epam.tc.notebook.service.UserService;
import by.epam.tc.notebook.service.impl.NoteServiceImpl;
import by.epam.tc.notebook.service.impl.UserServiceImpl;

/**
 * Created by Aliaksei Boltak on 09/10/2016.
 */

public class ServiceFactory {

	private static final ServiceFactory INSTANCE = new ServiceFactory();

	private static final NoteService noteService = new NoteServiceImpl();

	private static final UserService userService = new UserServiceImpl();

	private ServiceFactory() {

	}

	public static ServiceFactory getInstance() {
		return INSTANCE;
	}

	public NoteService getNoteService() {
		return this.noteService;
	}

	public UserService getUserService() {
		return userService;
	}
}
