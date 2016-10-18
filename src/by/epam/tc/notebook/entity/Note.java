package by.epam.tc.notebook.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
	/**
	 * Created by Aliaksei Boltak on 08/10/2016.
	 */

	private int id;
	private int user_id;
	private String note;
	private String date;
	private static final Date dateNew = new Date();
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	private static final String dateCurrent = dateFormat.format(dateNew);

	public Note(String note, String date) {
		this.note = note;
		this.date = date;
	}
	
	public Note(String note) {
		this.note = note;
		this.date = dateCurrent;
	}
	
	public static String getDatecurrent() {
		return dateCurrent;
	}

	public Note(int id, int user_id, String note) {
		this.id=id;
		this.user_id=user_id;
		this.note = note;
		this.date = dateCurrent;
	}

	public Note( int user_id, String note) {
		this.user_id=user_id;
		this.note = note;
		this.date = dateCurrent;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + user_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (user_id != other.user_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Note [user_id=" + user_id + ", note=" + note + ", date=" + date + "]";
	}
}
