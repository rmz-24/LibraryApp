package APP;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BookManagementDashboard extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JPanel panel = new JPanel();
	
	public BookManagementDashboard(String user, String level) {
		
		setTitle("Books Management");
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
        
        JButton addBookButton = new JButton("");
        addBookButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new AddBookWindow(user,level);
        	}
        });
        addBookButton.setIcon(new ImageIcon("src\\resrc\\profile_10655019.png"));
        addBookButton.setBounds(113, 195, 104, 68);
        addBookButton.setContentAreaFilled(false);
        addBookButton.setBorderPainted(false); // Removes border
        addBookButton.setFocusPainted(false); // Removes focus border
        addBookButton.setOpaque(false);
        panel.add(addBookButton);
        
        JButton removeBookButton = new JButton("");
        removeBookButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new RemoveBookWindow();
        	}
        });
        removeBookButton.setOpaque(false);
        removeBookButton.setIcon(new ImageIcon("src\\resrc\\delete-account_16321987.png"));
        removeBookButton.setFocusPainted(false);
        removeBookButton.setContentAreaFilled(false);
        removeBookButton.setBorderPainted(false);
        removeBookButton.setBounds(294, 195, 104, 68);
        panel.add(removeBookButton);
        
        JLabel addBookLabel = new JLabel("Add book");
        addBookLabel.setForeground(new Color(255, 255, 255));
        addBookLabel.setFont(new Font("Jost", Font.BOLD, 13));
        addBookLabel.setBounds(132, 285, 77, 14);
        panel.add(addBookLabel);
        
        JLabel removeBookLabel = new JLabel("Remove book");
        removeBookLabel.setForeground(new Color(255, 255, 255));
        removeBookLabel.setFont(new Font("Jost", Font.BOLD, 13));
        removeBookLabel.setBounds(301, 285, 97, 14);
        panel.add(removeBookLabel);
        
        JButton backHomeButton = new JButton("");
        backHomeButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new Home(user,level);
        		dispose();
        		
        	}
        });
        backHomeButton.setOpaque(false);
        backHomeButton.setIcon(new ImageIcon("src\\resrc\\left-arrow_10117838.png"));
        backHomeButton.setFocusPainted(false);
        backHomeButton.setContentAreaFilled(false);
        backHomeButton.setBorderPainted(false);
        backHomeButton.setBounds(433, 333, 104, 68);
        panel.add(backHomeButton);
        
        
        setVisible(true);
		
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookManagementDashboard("user","level"));
    }

	

}
