package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
		lblNewLabel_3.setBounds(670, 24, 109, 125);
		frmLibraryapp.getContentPane().add(lblNewLabel_3);
		lblNewLabel_3.setMinimumSize(new Dimension(50, 50));
		lblNewLabel_3.setMaximumSize(new Dimension(100, 100));
		lblNewLabel_3.setIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\Untitled design.png"));
		
		JLabel lblNewLabel_4 = new JLabel("Username");
		lblNewLabel_4.setFont(new Font("Jost", Font.BOLD, 19));
		lblNewLabel_4.setBounds(537, 226, 97, 29);
		frmLibraryapp.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_4_1 = new JLabel("Password");
		lblNewLabel_4_1.setFont(new Font("Jost", Font.BOLD, 19));
		lblNewLabel_4_1.setBounds(537, 306, 97, 29);
		frmLibraryapp.getContentPane().add(lblNewLabel_4_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(537, 350, 246, 29);
		frmLibraryapp.getContentPane().add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(537, 266, 245, 29);
		frmLibraryapp.getContentPane().add(textField);
		textField.setColumns(10);
	}
}
