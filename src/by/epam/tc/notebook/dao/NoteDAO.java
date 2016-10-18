package by.epam.tc.notebook.dao;

import java.util.ArrayList;
import by.epam.tc.notebook.dao.exception.DAOException;
import by.epam.tc.notebook.entity.Note;
/**
 * Created by Aliaksei Boltak on 08/10/2016.
 */

public interface NoteDAO  {
	
	public void insert (Note ob) throws DAOException;

	public boolean addNewNote (int user_id, String note) throws DAOException;
	
	public boolean clearNotes (int user_id) throws DAOException;
	
	public ArrayList<Note> showAllNotes (int user_id) throws DAOException;
	
	public ArrayList<Note> findNotesByDate (int user_id, String date) throws DAOException;
	
	public ArrayList<Note> findNotesByContent (int user_id, String content) throws DAOException;
	
	public void update(Note ob) throws DAOException;
	
	
}
