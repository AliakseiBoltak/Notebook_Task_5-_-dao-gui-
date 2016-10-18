package by.epam.tc.notebook.service;

import java.util.ArrayList;

import by.epam.tc.notebook.entity.Note;
import by.epam.tc.notebook.service.exception.ServiceException;
/**
 * Created by Aliaksei Boltak on 08/10/2016.
 */

public interface NoteService {

	
	public boolean addNewNote (int user_id, String note) throws ServiceException;
	
	public boolean clearNotes (int user_id) throws ServiceException;
	
	public ArrayList<Note> showAllNotes (int user_id) throws ServiceException;
	
	public ArrayList<Note> findNotesByDate (int user_id , String date) throws ServiceException;
	
	public ArrayList<Note> findNotesByContent (int user_id , String content) throws ServiceException;
	
	public void update(Note note) throws ServiceException;
	
}
