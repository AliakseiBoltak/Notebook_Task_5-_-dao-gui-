package by.epam.tc.notebook.user_interface;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import by.epam.tc.notebook.db.*;
import by.epam.tc.notebook.entity.User;
import by.epam.tc.notebook.service.UserService;
import by.epam.tc.notebook.service.exception.ServiceException;
import by.epam.tc.notebook.service.factory.ServiceFactory;

/**
 * Created by Aliaksei Boltak on 08/10/2016.
 */

public class LoginFrame extends JFrame {

	private JPanel panel;
	private JLabel labelLogin, labelPass;
	private JTextField tfLogin;
	private JPasswordField tfPass;
	private JButton enter, registration;
	private LoginFrame login;
	private JMenuBar menuBar;
	private JMenu about;
	private JMenuItem iAbout;

	public LoginFrame() {
		setSize(320, 220);
		setTitle("Enter Account");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
		action();
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		login = this;
	}

	private void initComponents() {

		panel = new JPanel(null);
		labelLogin = new JLabel("Login");
		labelPass = new JLabel("Password");
		tfLogin = new JTextField("Admin", 20);
		tfPass = new JPasswordField("12345", 20);
		enter = new JButton("Enter");
		registration = new JButton("Registration");

		menuBar = new JMenuBar();
		about = new JMenu("About");
		iAbout = new JMenuItem("About");

		labelLogin.setBounds(10, 10, 150, 20);
		tfLogin.setBounds(80, 10, 150, 20);
		labelPass.setBounds(10, 40, 150, 20);
		tfPass.setBounds(80, 40, 150, 20);
		enter.setBounds(80, 70, 150, 20);
		registration.setBounds(80, 110, 150, 20);

		panel.add(labelLogin);
		panel.add(tfLogin);
		panel.add(labelPass);
		panel.add(tfPass);
		panel.add(enter);
		panel.add(registration);
		about.add(iAbout);
		menuBar.add(about);
		setJMenuBar(menuBar);
		add(panel);
	}

	private void action() {

		registration.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new RegistrationFrame();

				dispose();

			}
		});

		enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				User user = null;
				boolean connection = true;

				UserService userService = ServiceFactory.getInstance().getUserService();

				try {
					user = userService.logination(tfLogin.getText(), String.valueOf(tfPass.getPassword()));

				} catch (ServiceException e1) {

					JOptionPane.showMessageDialog(panel, "No connection to DB, please check if DB is installed",
							"Error", JOptionPane.ERROR_MESSAGE);
					connection = false;

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(panel, "No connection to DB, please check if DB is installed",
							"Error", JOptionPane.ERROR_MESSAGE);
					connection = false;
				}

				if (connection) {
					if (user == null) {

						JOptionPane.showMessageDialog(panel, "Incorrect login or password", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {

						if (user.getBlock_status() == 1) {

							JOptionPane.showMessageDialog(panel, "User is blocked", "Error", JOptionPane.ERROR_MESSAGE);

						} else {
							if (user.getRole() == 1) {
								new AdminFrame();
								dispose();
							} else {
								new UserFrame(user.getUsers_id());
								dispose();
							}
						}
					}
				}

			}
		});

		iAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(panel, "Before start, please run sql_script to create DB and check : \n"
						+ "MySQL service is working \n" + "DB_URL = jdbc:mysql://localhost:3306/notebook \n"
						+ "DB_USER = root \n " + "DB_PASSWORD = change in code (Connection Pool class)  on yours \n ");
			}
		});
	}
}
