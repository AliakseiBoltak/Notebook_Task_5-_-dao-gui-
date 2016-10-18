package by.epam.tc.notebook.user_interface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.mysql.jdbc.ResultSetMetaData;

public class MyTable extends JTable {

	public MyTable(ResultSet rs) throws SQLException {
		DefaultTableModel dtm = new DefaultTableModel();
		ResultSetMetaData rsmd;
		rsmd = (ResultSetMetaData) rs.getMetaData();

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			dtm.addColumn(rsmd.getColumnName(i)); 
		}
		while (rs.next()) {
			Vector <String> v = new Vector <String> ();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				v.add(rs.getString(i));
			}
			dtm.addRow(v);
		}
		setModel(dtm);
		setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
	}
}
