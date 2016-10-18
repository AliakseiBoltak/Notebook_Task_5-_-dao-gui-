package by.epam.tc.notebook.user_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.epam.tc.notebook.service.NoteService;
import by.epam.tc.notebook.service.exception.ServiceException;
import by.epam.tc.notebook.service.factory.ServiceFactory;

/**
 * Created by Aliaksei Boltak on 09/10/2016.
 */

public class FindNoteByContentFrame extends JFrame {
	private JPanel panel;
	private JLabel labelUser;
	private JTextField tfUser;
	private JButton bFind, bBack;
	private int user_id;
	private String query;

	public FindNoteByContentFrame(int user_id) {
		this.user_id = user_id;
		setSize(270, 180);
		setTitle("Find note by content");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
		action();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initComponents() {
		panel = new JPanel(null);
		labelUser = new JLabel("Note : ");
		tfUser = new JTextField(20);
		bFind = new JButton("Find");
		bBack = new JButton("Back");

		labelUser.setBounds(10, 10, 120, 20);
		tfUser.setBounds(130, 10, 120, 20);
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

				NoteService noteService = ServiceFactory.getInstance().getNoteService();
				
				try {
					if (noteService.findNotesByContent(user_id, tfUser.getText()) != null) {
						query = "select * from note where users_id=" + user_id + " and note like '%" + tfUser.getText()
								+ "%'";

						dispose();

						UserFrame a = new UserFrame(user_id);

						a.updateTableUser(query);
					}
				} catch (ServiceException e1) {
					JOptionPane.showMessageDialog(panel, "Error in DB", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UserFrame(user_id);
				dispose();
			}
		});

	}
}
