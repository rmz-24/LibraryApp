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
    
    public BookManagementDashboard(String user, String level ,ThemeToggleButton tg) {
        setResizable(false);
        
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
        
        // Top row buttons
        JButton addBookButton = new JButton("");
        addBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AddBookWindow(user,level,tg);
            }
        });
        addBookButton.setIcon(new ImageIcon("src\\resrc\\add_12146523.png"));
        addBookButton.setBounds(113, 145, 104, 68);
        addBookButton.setContentAreaFilled(false);
        addBookButton.setBorderPainted(false); // Removes border
        addBookButton.setFocusPainted(false); // Removes focus border
        addBookButton.setOpaque(false);
        panel.add(addBookButton);
        
        JButton removeBookButton = new JButton("");
        removeBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RemoveBookWindow(tg);
            }
        });
        removeBookButton.setOpaque(false);
        removeBookButton.setIcon(new ImageIcon("src\\resrc\\remove_12146882.png"));
        removeBookButton.setFocusPainted(false);
        removeBookButton.setContentAreaFilled(false);
        removeBookButton.setBorderPainted(false);
        removeBookButton.setBounds(294, 145, 104, 68);
        panel.add(removeBookButton);
        
        // Top row labels
        JLabel addBookLabel = new JLabel("Add book");
        addBookLabel.setForeground(new Color(255, 255, 255));
        addBookLabel.setFont(new Font("Jost", Font.BOLD, 13));
        addBookLabel.setBounds(132, 225, 77, 14);
        panel.add(addBookLabel);
        
        JLabel removeBookLabel = new JLabel("Remove book");
        removeBookLabel.setForeground(new Color(255, 255, 255));
        removeBookLabel.setFont(new Font("Jost", Font.BOLD, 13));
        removeBookLabel.setBounds(301, 225, 97, 14);
        panel.add(removeBookLabel);
        
        // Bottom row buttons
        JButton editBookButton = new JButton("");
        editBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new EditBookWindow(tg);
            }
        });
        editBookButton.setOpaque(false);
        editBookButton.setIcon(new ImageIcon("E:\\ECLIPSE-PROJECT\\BDD_APP\\src\\resrc\\textbook_18624674.png"));
        editBookButton.setFocusPainted(false);
        editBookButton.setContentAreaFilled(false);
        editBookButton.setBorderPainted(false);
        editBookButton.setBounds(113, 255, 104, 68);
        panel.add(editBookButton);
        
        JButton searchBookButton = new JButton("");
        searchBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SearchBookWindow(tg);
            }
        });
        searchBookButton.setOpaque(false);
        searchBookButton.setIcon(new ImageIcon("E:\\ECLIPSE-PROJECT\\BDD_APP\\src\\resrc\\SEARCHBOOK.png")); // Use an appropriate search icon
        searchBookButton.setFocusPainted(false);
        searchBookButton.setContentAreaFilled(false);
        searchBookButton.setBorderPainted(false);
        searchBookButton.setBounds(294, 255, 104, 68);
        panel.add(searchBookButton);
        
        // Bottom row labels
        JLabel editBookLabel = new JLabel("Edit book");
        editBookLabel.setForeground(new Color(255, 255, 255));
        editBookLabel.setFont(new Font("Jost", Font.BOLD, 13));
        editBookLabel.setBounds(132, 335, 77, 14);
        panel.add(editBookLabel);
        
        JLabel searchBookLabel = new JLabel("Search book");
        searchBookLabel.setForeground(new Color(255, 255, 255));
        searchBookLabel.setFont(new Font("Jost", Font.BOLD, 13));
        searchBookLabel.setBounds(301, 335, 97, 14);
        panel.add(searchBookLabel);
        if(tg.isSelected()) {
        	panel.setBackground(new Color(60, 63, 65)); // Light grayColor(60, 63, 65)

	  	}else {
	  		panel.setBackground(new Color(182, 182, 182));
	  		
	  	}
        // Back button
        JButton backHomeButton = new JButton("");
        backHomeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Home(user,level,tg);
                dispose();
            }
        });
        backHomeButton.setOpaque(false);
        backHomeButton.setIcon(new ImageIcon("src\\resrc\\left-arrow_10117838.png"));
        backHomeButton.setFocusPainted(false);
        backHomeButton.setContentAreaFilled(false);
        backHomeButton.setBorderPainted(false);
        backHomeButton.setBounds(433, 340, 104, 68);
        panel.add(backHomeButton);
        
        JButton removeStudentButton_1_1 = new JButton("");
        removeStudentButton_1_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new BookListWindow(tg);
        	}
        });
        removeStudentButton_1_1.setOpaque(false);
        removeStudentButton_1_1.setIcon(new ImageIcon("src\\resrc\\list_11916758.png"));
        removeStudentButton_1_1.setFocusPainted(false);
        removeStudentButton_1_1.setContentAreaFilled(false);
        removeStudentButton_1_1.setBorderPainted(false);
        removeStudentButton_1_1.setBounds(475, 126, 33, 33);
        panel.add(removeStudentButton_1_1);
        
        setVisible(true);
    }
    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new BookManagementDashboard("user","level"));
//    }
}