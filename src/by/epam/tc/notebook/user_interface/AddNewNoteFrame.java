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

public class AddNewNoteFrame extends JFrame {

	private JPanel panel;
	private JLabel labelUser;
	private JTextField tfUser;
	private JButton bAdd, bBack;
	private int user_id;

	public AddNewNoteFrame( int user_id) {
		this.user_id = user_id;
		setSize(270, 180);
		setTitle("Add new note");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
		action();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initComponents() {
		panel = new JPanel(null);
		labelUser = new JLabel("New note : ");
		tfUser = new JTextField(100);
		bAdd = new JButton("Add");
		bBack = new JButton("Back");

		labelUser.setBounds(10, 10, 120, 20);
		tfUser.setBounds(130, 10, 120, 20);
		bAdd.setBounds(10, 50, 100, 20);
		bBack.setBounds(10, 100, 100, 20);

		panel.add(labelUser);
		panel.add(tfUser);
		panel.add(bAdd);
		panel.add(bBack);
		add(panel);
	}

	private void action() {
		bAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					NoteService noteService = ServiceFactory.getInstance().getNoteService();

					if (noteService.addNewNote(user_id, tfUser.getText())) {

						JOptionPane.showMessageDialog(panel, "Note " + tfUser.getText().trim() + " was added",
								"Message", JOptionPane.INFORMATION_MESSAGE);
						dispose();
						UserFrame a = new UserFrame( user_id);
						a.updateTableUser();
					} else {
						JOptionPane.showMessageDialog(panel, "Incorrect note", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (ServiceException e1) {
					JOptionPane.showMessageDialog(panel, "Incorrect note", "Error", JOptionPane.ERROR_MESSAGE);
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
