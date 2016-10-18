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

/**
 * Created by Aliaksei Boltak on 09/10/2016.
 */

public class AdminFrame extends JFrame {

	private JPanel panel;
	private JScrollPane scrollUser;
	private MyTable tableUsers;
	private KeyListener key;
	private JButton bFind, bUsers, bNote, bBlock, bUnblock, bBack;
	private String query = "select * from user";
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem iOpen, iSave;
	private JFileChooser fileChooser;
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet set = null;

	public AdminFrame() {

		setSize(530, 300);
		setTitle("Admin Panel");
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
			tableUsers = new MyTable(set);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "Error in DB", "Error", JOptionPane.ERROR_MESSAGE);
		}finally {
			ConnectionPool.getPool().closeDbResources(connection, statement,set);
		}
		
		scrollUser = new JScrollPane(tableUsers);
		bUsers = new JButton("Users");
		bNote = new JButton("Notes");
		bBack = new JButton("Back");
		bFind = new JButton("Find note by Users Id");
		bBlock = new JButton("Block User");
		bUnblock = new JButton("Unblock User");
		menuBar = new JMenuBar();
		file = new JMenu("File");
		iSave = new JMenuItem("save table to  file");
		iOpen = new JMenuItem("open file");
		fileChooser = new JFileChooser();

		scrollUser.setBounds(10, 10, 500, 150);
		bBlock.setBounds(10, 170, 160, 20);
		bUnblock.setBounds(180, 170, 160, 20);
		bFind.setBounds(350, 170, 160, 20);
		bUsers.setBounds(10, 200, 160, 20);
		bNote.setBounds(180, 200, 160, 20);
		bBack.setBounds(350, 200, 160, 20);

		panel.add(scrollUser);
		panel.add(bUsers);
		panel.add(bNote);
		panel.add(bBlock);
		panel.add(bUnblock);
		panel.add(bBack);
		panel.add(bFind);
		file.add(iOpen);
		file.add(iSave);
		file.addSeparator();
		menuBar.add(file);
		setJMenuBar(menuBar);
		add(panel);
	}

	private void action() {

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

						for (int i = 0; i < tableUsers.getColumnCount(); i++) {
							sb.append(tableUsers.getColumnName(i) + "|");
						}
						sb.append("\r\n");
						for (int i = 0; i < tableUsers.getRowCount(); i++) {
							for (int j = 0; j < tableUsers.getColumnCount(); j++) {
								sb.append(tableUsers.getValueAt(i, j) + "|");
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

		bNote.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				query = "select * from note";
				updateTableUser();
			}
		});

		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoginFrame();
				dispose();
			}
		});

		bUsers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				query = "select * from user";
				updateTableUser();
			}
		});

		bFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FindNotesByUsersIdFrame();
				dispose();
			}
		});

		bBlock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new BlockFrame();
				dispose();

			}
		});

		bUnblock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UnBlockFrame();
				dispose();

			}
		});
	}

	public void updateTableUser() {
		panel.remove(scrollUser);
		try {
			 connection = ConnectionPool.getPool().getConnection();
			 statement = (Statement) connection.createStatement();
			 set = statement.executeQuery(query);
			tableUsers = new MyTable(set);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "Error in DB", "Error", JOptionPane.ERROR_MESSAGE);
		}finally {
			ConnectionPool.getPool().closeDbResources(connection, statement,set);
		}
		scrollUser = new JScrollPane(tableUsers);
		scrollUser.setBounds(10, 10, 500, 150);
		panel.add(scrollUser);
		panel.updateUI();
	}

	public void updateTableUser(String query) {
		panel.remove(scrollUser);
		try {
			connection = ConnectionPool.getPool().getConnection();
			statement = (Statement) connection.createStatement();
			set = statement.executeQuery(query);
			tableUsers = new MyTable(set);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(panel, "Error in DB", "Error", JOptionPane.ERROR_MESSAGE);
		}finally {
			ConnectionPool.getPool().closeDbResources(connection, statement,set);
		}
		scrollUser = new JScrollPane(tableUsers);
		scrollUser.setBounds(10, 10, 500, 150);
		panel.add(scrollUser);
		panel.updateUI();
	}

}
