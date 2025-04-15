package APP;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LoanManagementDashboard extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel panel = new JPanel();
		
	public LoanManagementDashboard(String user, String level) {
		
		setResizable(false);
		//private Connection connection;
		//this.connection = LibraryApp.getConnection();
		//String usernames = user;
		//String levels = level;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\resrc\\LMsmall.png"));
		setTitle("Emprunts Management");
		setSize(536, 435);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		panel.setBackground(new Color(182, 182, 182));
		panel.setBounds(0, 0, 1695, 1023);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel topColorPanel = new JPanel();
		topColorPanel.setBackground(new Color(0, 102, 102));
		topColorPanel.setBounds(-51, 0, 1746, 115);
		panel.add(topColorPanel);
		topColorPanel.setLayout(null);
		
		JLabel iconLabel = new JLabel("");
		iconLabel.setBounds(87, 11, 100, 100);
		iconLabel.setIcon(new ImageIcon("src\\resrc\\LMsmall.png"));
		topColorPanel.add(iconLabel);
		
		JButton addemprunt = new JButton("");
		addemprunt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new RegisterLoanWindow(user, level);
				dispose();
			}
		});
		addemprunt.setIcon(new ImageIcon("E:\\ECLIPSE-PROJECT\\BDD_APP\\src\\resrc\\handbook_18447619.png"));
		addemprunt.setBounds(51, 199, 64, 64);
		addemprunt.setBorderPainted(false); // Removes border
		addemprunt.setFocusPainted(false); // Removes focus border
		addemprunt.setOpaque(false);
		addemprunt.setContentAreaFilled(false);
		panel.add(addemprunt);
		
		JButton reviwreturns = new JButton("");
		reviwreturns.setOpaque(false);
		reviwreturns.setIcon(new ImageIcon("E:\\ECLIPSE-PROJECT\\BDD_APP\\src\\resrc\\bookreturn_11860727.png"));
		reviwreturns.setFocusPainted(false);
		reviwreturns.setBorderPainted(false);
		reviwreturns.setBounds(224, 199, 64, 64);
		reviwreturns.setContentAreaFilled(false);
		reviwreturns.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ReturnRegisteringWindow(user, level);
				dispose();
			}
		});
		panel.add(reviwreturns);
		
		JButton reviewloans = new JButton("");
		reviewloans.setOpaque(false);
		reviewloans.setIcon(new ImageIcon("E:\\ECLIPSE-PROJECT\\BDD_APP\\src\\resrc\\location-book_18588122.png"));
		reviewloans.setFocusPainted(false);
		reviewloans.setBorderPainted(false);
		reviewloans.setBounds(391, 199, 64, 64);
		reviewloans.setContentAreaFilled(false);
		reviewloans.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TrackLoansWindow(user, level);
				dispose();
			}
		});
		panel.add(reviewloans);
		
		JLabel lblNewLabel = new JLabel("Add Loan");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Jost", Font.BOLD, 13));
		lblNewLabel.setBounds(51, 286, 71, 14);
		panel.add(lblNewLabel);
		
		JLabel lblReturns = new JLabel("Returns");
		lblReturns.setForeground(new Color(255, 255, 255));
		lblReturns.setFont(new Font("Jost", Font.BOLD, 13));
		lblReturns.setBounds(234, 286, 71, 14);
		panel.add(lblReturns);
		
		JLabel lblTrackLoans = new JLabel("Track Loans");
		lblTrackLoans.setForeground(new Color(255, 255, 255));
		lblTrackLoans.setFont(new Font("Jost", Font.BOLD, 13));
		lblTrackLoans.setBounds(391, 287, 71, 14);
		panel.add(lblTrackLoans);
		
		JButton backHomeButton = new JButton("");
		backHomeButton.setOpaque(false);
		backHomeButton.setIcon(new ImageIcon("src\\resrc\\left-arrow_10117838.png"));
		backHomeButton.setFocusPainted(false);
		backHomeButton.setContentAreaFilled(false);
		backHomeButton.setBorderPainted(false);
		backHomeButton.setBounds(435, 341, 104, 68);
		panel.add(backHomeButton);
		
		backHomeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Home(user, level);
				dispose();
			}
		});
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LoanManagementDashboard("admin", "admin");
			}
		});
	}
}