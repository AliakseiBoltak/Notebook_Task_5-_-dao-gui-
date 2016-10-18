package by.epam.tc.notebook.user_interface;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import by.epam.tc.notebook.service.UserService;
import by.epam.tc.notebook.service.exception.ServiceException;
import by.epam.tc.notebook.service.factory.ServiceFactory;

/**
 * Created by Aliaksei Boltak on 08/10/2016.
 */
public class RegistrationFrame extends JFrame {
	private JPanel panel;
	private JLabel labelLogin, labelPass;
	private JTextField tfLogin, pfPass;
	private JButton registration, back;

	public RegistrationFrame() {
		setSize(270, 200);
		setTitle("Registration");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
		action();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initComponents() {
		panel = new JPanel(null);
		labelLogin = new JLabel("Login");
		labelPass = new JLabel("Pass");
		tfLogin = new JTextField(20);
		pfPass = new JPasswordField(20);
		registration = new JButton("Registration");
		back = new JButton("Back");

		labelLogin.setBounds(10, 10, 150, 20);
		tfLogin.setBounds(60, 10, 150, 20);
		labelPass.setBounds(10, 40, 150, 20);
		pfPass.setBounds(60, 40, 150, 20);
		registration.setBounds(60, 70, 150, 20);
		back.setBounds(60, 110, 150, 20);

		panel.add(labelLogin);
		panel.add(tfLogin);
		panel.add(labelPass);
		panel.add(pfPass);
		panel.add(registration);
		panel.add(back);
		add(panel);
	}

	private void action() {
		registration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					UserService userService = ServiceFactory.getInstance().getUserService();

					if (userService.registration(tfLogin.getText(), pfPass.getText())) {

						JOptionPane.showMessageDialog(panel, "Registration successful!", "Message",
								JOptionPane.INFORMATION_MESSAGE);
						
						new LoginFrame();
						
						dispose();
						

					} else {
						JOptionPane.showMessageDialog(panel, "Incorrect login or password", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				} catch (ServiceException e1) {
					JOptionPane.showMessageDialog(panel, "Incorrect login or password", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new LoginFrame();
				dispose();

			}
		});
	}
}