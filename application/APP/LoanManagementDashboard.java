package APP;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoanManagementDashboard extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel panel = new JPanel();
		
	public LoanManagementDashboard(String user, String level,ThemeToggleButton togglebutton) {
		
		setResizable(false);
		//private Connection connection;
		//this.connection = LibraryApp.getConnection();
		//String usernames = user;
		//String levels = level;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\resrc\\LMsmall.png"));
		setTitle("Emprunts Management");
		setSize(536, 435);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		panel.setBackground(new Color(182, 182, 182));
		panel.setBounds(0, 0, 1695, 1023);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		panel.setBounds(0, 0, 1322, 894);
	  	if(togglebutton.isSelected()) {
	  		panel.setBackground(new Color(60, 63, 65)); // Light grayColor(60, 63, 65)

	  	}else {
	  		panel.setBackground(new Color(182, 182, 182));
	  		
	  	}
		
		
		JPanel topColorPanel = new JPanel();
		topColorPanel.setBackground(new Color(0, 102, 102));
		topColorPanel.setBounds(-51, 0, 1746, 115);
		panel.add(topColorPanel);
		topColorPanel.setLayout(null);
		
		JLabel iconLabel = new JLabel("");
		iconLabel.setBounds(87, 11, 100, 100);
		
		iconLabel.setIcon(loadImageIcon("/resrc/LMsmall.png"));
		topColorPanel.add(iconLabel);
		
		JButton addemprunt = new JButton("");
		addemprunt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new RegisterLoanWindow(user, level,togglebutton);
				dispose();
			}
		});
		
		addemprunt.setIcon(loadImageIcon("/resrc/handbook_18447619.png"));
		addemprunt.setBounds(51, 199, 64, 64);
		addemprunt.setBorderPainted(false); // Removes border
		addemprunt.setFocusPainted(false); // Removes focus border
		addemprunt.setOpaque(false);
		addemprunt.setContentAreaFilled(false);
		panel.add(addemprunt);
		
		JButton reviwreturns = new JButton("");
		reviwreturns.setOpaque(false);
		
		reviwreturns.setIcon(loadImageIcon("/resrc/bookreturn_11860727.png"));
		reviwreturns.setFocusPainted(false);
		reviwreturns.setBorderPainted(false);
		reviwreturns.setBounds(224, 199, 64, 64);
		reviwreturns.setContentAreaFilled(false);
		reviwreturns.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ReturnRegisteringWindow(user, level,togglebutton);
				dispose();
			}
		});
		panel.add(reviwreturns);
		
		JButton reviewloans = new JButton("");
		reviewloans.setOpaque(false);
		
		reviewloans.setIcon(loadImageIcon("/resrc/location-book_18588122.png"));
		reviewloans.setFocusPainted(false);
		reviewloans.setBorderPainted(false);
		reviewloans.setBounds(391, 199, 64, 64);
		reviewloans.setContentAreaFilled(false);
		reviewloans.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new TrackLoansWindow(user, level,togglebutton);
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
		
		backHomeButton.setIcon(loadImageIcon("/resrc/left-arrow_10117838.png"));
		backHomeButton.setFocusPainted(false);
		backHomeButton.setContentAreaFilled(false);
		backHomeButton.setBorderPainted(false);
		backHomeButton.setBounds(435, 341, 104, 68);
		panel.add(backHomeButton);
		
		backHomeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				new Home(user, level,togglebutton);
				dispose();
				
			}
		});
		
		setVisible(true);
	}
	private ImageIcon loadImageIcon(String path) {
        try {
            // First try loading from resources (works in JAR)
            URL imageUrl = getClass().getResource(path);
            if (imageUrl != null) {
                return new ImageIcon(imageUrl);
            }
            
            // Fallback for development (absolute path)
            String projectPath = System.getProperty("user.dir");
            String fullPath = projectPath + "/src/main/resources" + path;
            File imageFile = new File(fullPath);
            
            if (imageFile.exists()) {
                return new ImageIcon(fullPath);
            } else {
                System.err.println("Image not found at: " + path);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				new LoanManagementDashboard("admin", "admin",togglebutton);
//			}
//		});
//	}
}