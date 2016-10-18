package by.epam.tc.notebook.user_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import by.epam.tc.notebook.db.ConnectionPool;
import by.epam.tc.notebook.service.NoteService;
import by.epam.tc.notebook.service.UserService;
import by.epam.tc.notebook.service.exception.ServiceException;
import by.epam.tc.notebook.service.factory.ServiceFactory;

/**
 * Created by Aliaksei Boltak on 09/10/2016.
 */

public class UserFrame extends JFrame {

	private int user_id;
	private JPanel panel;
	private JScrollPane scrollUser;
	private MyTable tableNotes;
	private String query;
	private JButton bAdd, bClear, bFind_Date, bFind_Content, bShowALLNotes, bBack;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem iOpen, iSave;
	private JFileChooser fileChooser;
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet set = null;

	public UserFrame(int user_id) {

		this.user_id = user_id;
		query = "select * from note where users_id=" + user_id;
		setSize(530, 300);
		setTitle("User Panel");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
		action();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initComponents() {
		panel = new JPanel(null);
		try {
			connection = ConnectionPool.getPool().getConnection();
			statement = (Statement) connection.createStatement();
			set = statement.executeQuery(query);
			tableNotes = new MyTable(set);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "Error in DB", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, statement, set);
		}
		scrollUser = new JScrollPane(tableNotes);
		bAdd = new JButton("Add Note");
		bClear = new JButton("Clear Notes");
		bBack = new JButton("Back");
		bFind_Date = new JButton("Find notes by date");
		bFind_Content = new JButton("Find notes by content");
		bShowALLNotes = new JButton("Show all notes");
		menuBar = new JMenuBar();
		file = new JMenu("File");
		iSave = new JMenuItem("save table to  file");
		iOpen = new JMenuItem("open file");
		fileChooser = new JFileChooser();

		scrollUser.setBounds(10, 10, 500, 150);
		bAdd.setBounds(10, 170, 160, 20);
		bClear.setBounds(180, 170, 160, 20);
		bFind_Date.setBounds(350, 170, 160, 20);
		bFind_Content.setBounds(10, 200, 160, 20);
		bShowALLNotes.setBounds(180, 200, 160, 20);
		bBack.setBounds(350, 200, 160, 20);

		panel.add(scrollUser);
		panel.add(bAdd);
		panel.add(bClear);
		panel.add(bFind_Date);
		panel.add(bFind_Content);
		panel.add(bBack);
		panel.add(bShowALLNotes);
		file.add(iOpen);
		file.add(iSave);
		file.addSeparator();
		menuBar.add(file);
		setJMenuBar(menuBar);
		add(panel);
	}

	private void action() {

		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoginFrame();
				dispose();
			}
		});

		bShowALLNotes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();

				UserFrame a = new UserFrame(user_id);

				NoteService noteService = ServiceFactory.getInstance().getNoteService();

				try {
					if (noteService.showAllNotes(user_id) != null) {

						a.updateTableUser();

					} else {
						JOptionPane.showMessageDialog(panel, "This user has no notes", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (ServiceException e1) {
					JOptionPane.showMessageDialog(panel, "This user has no notes", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

		bClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				NoteService noteService = ServiceFactory.getInstance().getNoteService();

				try {
					if (noteService.clearNotes(user_id)) {
						updateNote();
					}
				} catch (ServiceException e1) {
					JOptionPane.showMessageDialog(panel, "Error in DB", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		bAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddNewNoteFrame(user_id);
				dispose();
			}
		});

		bFind_Content.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FindNoteByContentFrame(user_id);
				dispose();
			}
		});

		bFind_Date.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FindNoteByDateFrame(user_id);
				dispose();
			}
		});

		iSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int x = fileChooser.showSaveDialog(panel);
					if (x == 1) {
						JOptionPane.showMessageDialog(panel, "File was not choosen");
					} else {
						FileWriter fw = new FileWriter(fileChooser.getSelectedFile());
						StringBuilder sb = new StringBuilder();

						for (int i = 0; i < tableNotes.getColumnCount(); i++) {
							sb.append(tableNotes.getColumnName(i) + "|");
						}
						sb.append("\r\n");
						for (int i = 0; i < tableNotes.getRowCount(); i++) {
							for (int j = 0; j < tableNotes.getColumnCount(); j++) {
								sb.append(tableNotes.getValueAt(i, j) + "|");
							}
							sb.append("\r\n");
						}
						fw.write(sb.toString());
						fw.close();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		iOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int x = fileChooser.showOpenDialog(panel);
				if (x == 1) {
					JOptionPane.showMessageDialog(panel, "File was not choosen");
				} else {
					FileReader fr;
					try {
						fr = new FileReader(fileChooser.getSelectedFile());
						BufferedReader br = new BufferedReader(fr);
						String sAll = "", s = "";
						do {
							s = br.readLine();
							if (s != null) {
								s = s.replaceAll(";", "   ");
								sAll = sAll + s + "\n";
							}
						} while (s != null);
						JOptionPane.showMessageDialog(panel, sAll);
						br.close();
						fr.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

	}


	public void updateTableUser(String query) {
		panel.remove(scrollUser);
		try {
			connection = ConnectionPool.getPool().getConnection();
			statement = (Statement) connection.createStatement();
			set = statement.executeQuery(query);
			tableNotes = new MyTable(set);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "Error in DB", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, statement, set);
		}
		scrollUser = new JScrollPane(tableNotes);
		scrollUser.setBounds(10, 10, 500, 150);
		panel.add(scrollUser);
		panel.updateUI();
	}

	public void updateTableUser() {
		panel.remove(scrollUser);
		try {
			connection = ConnectionPool.getPool().getConnection();
			statement = (Statement) connection.createStatement();
			set = statement.executeQuery(query);
			tableNotes = new MyTable(set);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "Error in DB", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, statement, set);
		}
		scrollUser = new JScrollPane(tableNotes);
		scrollUser.setBounds(10, 10, 500, 150);
		panel.add(scrollUser);
		panel.updateUI();
	}

	public void updateNote() {

		panel.remove(scrollUser);

		try {
			connection = ConnectionPool.getPool().getConnection();
			statement = (Statement) connection.createStatement();
			set = statement.executeQuery(query);

			tableNotes = new MyTable(set);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "Error in DB", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConnectionPool.getPool().closeDbResources(connection, statement, set);
		}
		scrollUser = new JScrollPane(tableNotes);
		scrollUser.setBounds(10, 10, 500, 150);
		panel.add(scrollUser);
		panel.updateUI();
	}
}
