package by.epam.tc.notebook.service.impl;

import java.util.ArrayList;

import by.epam.tc.notebook.dao.NoteDAO;
import by.epam.tc.notebook.dao.exception.DAOException;
import by.epam.tc.notebook.dao.factory.DAOFactory;
import by.epam.tc.notebook.entity.Note;
import by.epam.tc.notebook.service.NoteService;
import by.epam.tc.notebook.service.exception.ServiceException;
import by.epam.tc.notebook.utils.StringUtils;

/**
 * Created by Aliaksei Boltak on 09/10/2016.
 */

public class NoteServiceImpl implements NoteService {

	@Override
	public boolean addNewNote(int user_id, String note) throws ServiceException {
		if (!StringUtils.isValid(note) ||  user_id <= 0) {
			throw new ServiceException("Incorrect parametres");
		} else {
			NoteDAO daoNote = DAOFactory.getInstance().getNoteDAO();
			try {
				return daoNote.addNewNote(user_id, note);
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage());
			}
		}
	}
	
	@Override
	public boolean clearNotes(int user_id) throws ServiceException {
		if ( user_id <= 0) {
			throw new ServiceException("Incorrect parametres");
		} else {
			NoteDAO daoNote = DAOFactory.getInstance().getNoteDAO();
			try {
				return daoNote.clearNotes(user_id);
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage());
			}
		}
	}
	
	@Override
	public ArrayList<Note>  showAllNotes(int user_id) throws ServiceException {
		if ( user_id <= 0) {
			throw new ServiceException("Incorrect parametres");
		} else {
			NoteDAO daoNote = DAOFactory.getInstance().getNoteDAO();
			try {
				return daoNote.showAllNotes(user_id);
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage());
			}
		}
	}
	
	@Override
	public ArrayList<Note>  findNotesByDate(int user_id, String date) throws ServiceException {
		if ( user_id <= 0 || ! StringUtils.isValid(date)) {
			throw new ServiceException("Incorrect parametres");
		} else {
			NoteDAO daoNote = DAOFactory.getInstance().getNoteDAO();
			try {
				return daoNote.findNotesByDate(user_id, date);
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<Note>  findNotesByContent (int user_id, String content) throws ServiceException {
		if ( user_id <= 0 || ! StringUtils.isValid(content)) {
			throw new ServiceException("Incorrect parametres");
		} else {
			NoteDAO daoNote = DAOFactory.getInstance().getNoteDAO();
			try {
				return daoNote.findNotesByContent(user_id, content);
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage());
			}
		}
	}
	
	
	@Override
	public void update(Note note) throws ServiceException {
		if (note == null ) {
			throw new ServiceException("Incorrect parametres");
		} else {
			NoteDAO daoNote = DAOFactory.getInstance().getNoteDAO();
			try {
				daoNote.update(note);
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage());
			}
		}
	}


}
