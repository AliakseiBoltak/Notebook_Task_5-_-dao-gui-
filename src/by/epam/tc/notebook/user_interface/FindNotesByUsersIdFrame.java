package by.epam.tc.notebook.user_interface;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import by.epam.tc.notebook.db.ConnectionPool;
import by.epam.tc.notebook.utils.StringUtils;

/**
 * Created by Aliaksei Boltak on 11/10/2016.
 */
public class FindNotesByUsersIdFrame extends JFrame {

	private JPanel panel;
	private JLabel labelUser;
	private JTextField tfUser;
	private JButton bFind, bBack;
	private String queryNote;
	private ResultSet rs = null;

	public FindNotesByUsersIdFrame() {
		setSize(270, 180);
		setTitle("Find note by Users Id");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
		action();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initComponents() {
		panel = new JPanel(null);
		labelUser = new JLabel("Id user : ");
		tfUser = new JTextField(20);
		bFind = new JButton("Find note ");
		bBack = new JButton("Back");

		labelUser.setBounds(10, 10, 120, 20);
		tfUser.setBounds(130, 10, 50, 20);
		bFind.setBounds(10, 50, 100, 20);
		bBack.setBounds(10, 100, 100, 20);

		panel.add(labelUser);
		panel.add(tfUser);
		panel.add(bFind);
		panel.add(bBack);
		add(panel);
	}

	private void action() {

		bFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					if (StringUtils.isInteger(tfUser.getText())) {
						
						queryNote = "select * from note where users_id=" + Integer.valueOf(tfUser.getText());
						
						Connection connection = ConnectionPool.getPool().getConnection();
						Statement statement = (Statement) connection.createStatement();
						
						rs = statement.executeQuery(queryNote);
						
						if (StringUtils.isValid(tfUser.getText()) && (rs.next())) {

							AdminFrame a = new AdminFrame();

							a.updateTableUser(queryNote);

						}
					}
					if (StringUtils.isInteger(tfUser.getText())) {
						if (!(rs.next()) && StringUtils.isValid(tfUser.getText())) {
							JOptionPane.showMessageDialog(panel, "This user has no notes", "Message",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
					if (StringUtils.isInteger(tfUser.getText())) {
						if (!StringUtils.isValid(tfUser.getText())) {
							JOptionPane.showMessageDialog(panel, "Incorrect Id", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					if (!StringUtils.isInteger(tfUser.getText())) {
						JOptionPane.showMessageDialog(panel, "Incorrect Id", "Error", JOptionPane.ERROR_MESSAGE);
					}

				} catch (NumberFormatException | HeadlessException | SQLException e1) {
					// JOptionPane.showMessageDialog(panel, "Lock error",
					// "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AdminFrame();
				dispose();
			}
		});

	}
}
