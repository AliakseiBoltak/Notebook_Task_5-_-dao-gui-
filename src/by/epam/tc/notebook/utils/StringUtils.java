package by.epam.tc.notebook.utils;
/**
 * Created by Aliaksei Boltak on 09/10/2016.
 */

public class StringUtils {

	public static boolean isValid(String note) {
		if (note.equals("") || note == null || note.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}

