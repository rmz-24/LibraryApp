package application.APP;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LibraryApp {

	private JFrame frmLibraryapp;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibraryApp window = new LibraryApp();
					window.frmLibraryapp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LibraryApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLibraryapp = new JFrame();
		frmLibraryapp.setResizable(false);
		frmLibraryapp.setTitle("LibraryApp");
		frmLibraryapp.setBounds(100, 100, 1100, 600);
		frmLibraryapp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLibraryapp.getContentPane().setLayout(null);
		
		Panel panel = new Panel();
		panel.setBackground(new Color(0, 102, 102));
		panel.setBounds(0, 0, 372, 937);
		frmLibraryapp.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("WELCOME TO \r\n");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setFont(new Font("Jost", Font.BOLD, 27));
		lblNewLabel_1.setBounds(86, 301, 214, 40);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("USTHB'S LIBRARY MANAGER");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Jost", Font.BOLD, 21));
		lblNewLabel_2.setBounds(40, 352, 322, 46);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setBounds(133, 48, 99, 46);
		panel.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Jost", Font.BOLD, 31));
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(672, 0, 109, 125);
		frmLibraryapp.getContentPane().add(lblNewLabel_3);
		lblNewLabel_3.setMinimumSize(new Dimension(50, 50));
		lblNewLabel_3.setMaximumSize(new Dimension(100, 100));
		lblNewLabel_3.setIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\Untitled design.png"));
		
		JLabel lblNewLabel_4 = new JLabel("Username");
		lblNewLabel_4.setFont(new Font("Jost", Font.BOLD, 26));
		lblNewLabel_4.setBounds(547, 195, 133, 35);
		frmLibraryapp.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_4_1 = new JLabel("Password");
		lblNewLabel_4_1.setFont(new Font("Jost", Font.BOLD, 26));
		lblNewLabel_4_1.setBounds(547, 306, 133, 35);
		frmLibraryapp.getContentPane().add(lblNewLabel_4_1);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 27));
		passwordField.setBounds(547, 352, 287, 49);
		frmLibraryapp.getContentPane().add(passwordField);
		
		textField = new JTextField();
		textField.setFont(new Font("Jost", Font.PLAIN, 27));
		textField.setBounds(547, 253, 287, 49);
		frmLibraryapp.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("LOG IN");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String user = textField.getText();
				String pass = String.valueOf(passwordField.getPassword());

				// Dummy authentication (Replace with real validation logic)
				if (user.equals("amine") && pass.equals("benallal")) {


					// Open HomeScreen
					new Home();

					// Close Login Window
					frmLibraryapp.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 102, 102));
		btnNewButton.setFont(new Font("Jost", Font.BOLD, 19));
		btnNewButton.setBounds(631, 442, 134, 49);
		frmLibraryapp.getContentPane().add(btnNewButton);
	}
}
