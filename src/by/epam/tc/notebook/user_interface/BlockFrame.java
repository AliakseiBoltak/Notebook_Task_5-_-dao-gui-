package by.epam.tc.notebook.user_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import by.epam.tc.notebook.service.UserService;
import by.epam.tc.notebook.service.exception.ServiceException;
import by.epam.tc.notebook.service.factory.ServiceFactory;

/**
 * Created by Aliaksei Boltak on 09/10/2016.
 */
public class BlockFrame extends JFrame {

	private JPanel panel;
	private JLabel labelUser;
	private JTextField tfUser;
	private JButton bBlock, bBack;
	ResultSet rs = null;

	public BlockFrame() {
		setSize(270, 180);
		setTitle("Block user");
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
		bBlock = new JButton("Block");
		bBack = new JButton("Back");

		labelUser.setBounds(10, 10, 120, 20);
		tfUser.setBounds(130, 10, 50, 20);
		bBlock.setBounds(10, 50, 100, 20);
		bBack.setBounds(10, 100, 100, 20);

		panel.add(labelUser);
		panel.add(tfUser);
		panel.add(bBlock);
		panel.add(bBack);
		add(panel);
	}

	private void action() {

		bBlock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				UserService userService = ServiceFactory.getInstance().getUserService();
				
				try {
					
					if (userService.blockUser(tfUser.getText())) {
						JOptionPane.showMessageDialog(panel, "User ¹ " + tfUser.getText() + " is blocked", "Message",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
						AdminFrame a = new AdminFrame();
						a.updateTableUser();
					} else {
						JOptionPane.showMessageDialog(panel, "Incorrect id", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (ServiceException e1) {
					JOptionPane.showMessageDialog(panel, "Incorrect id", "Error", JOptionPane.ERROR_MESSAGE);
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
