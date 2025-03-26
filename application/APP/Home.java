package APP;

import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Home{
    private JFrame frmDashboard;
    
    private void openWindow(JFrame newWindow) {
        frmDashboard.dispose();
        newWindow.setVisible(true);
    }
    
    public Home(String user , String level) {
    	
        frmDashboard = new JFrame("Home");
        frmDashboard.setTitle("Dashboard");
        frmDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmDashboard.setSize(1500, 900);
        frmDashboard.setLocationRelativeTo(null);
        frmDashboard.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 102));
        panel.setBounds(0, 0, 202, 1122);
        frmDashboard.getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setBounds(40, 11, 112, 80);
        lblNewLabel_1.setIcon(new ImageIcon("E:\\Projects\\LibraryApp\\resources\\images\\LMsmall.png"));
        panel.add(lblNewLabel_1);
        
        JButton addStudentButton = new JButton("");
        
        addStudentButton.setBackground(new Color(0, 102, 102));
        addStudentButton.setIcon(new ImageIcon("E:\\Projects\\LibraryApp\\resources\\images\\student.png"));
        addStudentButton.setBounds(40, 137, 112, 68);
        addStudentButton.setContentAreaFilled(false);
        addStudentButton.setBorderPainted(false); // Removes border
        addStudentButton.setFocusPainted(false); // Removes focus border
        addStudentButton.setOpaque(false);
        addStudentButton.setToolTipText("Add a student to the library's database");
        panel.add(addStudentButton);
        
        JLabel studentManagementLabel = new JLabel("Student Management");
        studentManagementLabel.setBounds(27, 216, 152, 21);
        studentManagementLabel.setFont(new Font("Jost", Font.BOLD, 15));
        studentManagementLabel.setForeground(new Color(255, 255, 255));
        panel.add(studentManagementLabel);
        
        JButton removeStudentButton = new JButton("");
        removeStudentButton.setIcon(new ImageIcon("E:\\Projects\\LibraryApp\\resources\\images\\books.png"));
        removeStudentButton.setBounds(40, 265, 112, 68);
        removeStudentButton.setContentAreaFilled(false);
        removeStudentButton.setBorderPainted(false); // Removes border
        removeStudentButton.setFocusPainted(false); // Removes focus border
        removeStudentButton.setOpaque(false);
        removeStudentButton.setToolTipText("Removes a student from the library's database");
        panel.add(removeStudentButton);
        
        JLabel addBookLabel = new JLabel("Loan and Return");
        addBookLabel.setVerticalAlignment(SwingConstants.TOP);
        addBookLabel.setBounds(43, 470, 129, 21);
        addBookLabel.setForeground(Color.WHITE);
        addBookLabel.setFont(new Font("Jost", Font.BOLD, 15));
        panel.add(addBookLabel);
        
        JButton removeBookButton = new JButton("");
        removeBookButton.setIcon(new ImageIcon("E:\\Projects\\LibraryApp\\resources\\images\\exclamation.png"));
        removeBookButton.setBounds(40, 523, 112, 68);
        removeBookButton.setContentAreaFilled(false);
        removeBookButton.setBorderPainted(false); // Removes border
        removeBookButton.setFocusPainted(false); // Removes focus border
        removeBookButton.setOpaque(false);
        removeBookButton.setToolTipText("Removes a book from the library's database");
        panel.add(removeBookButton);
        
        JLabel removeBookLabel = new JLabel("Report");
        removeBookLabel.setHorizontalAlignment(SwingConstants.CENTER);
        removeBookLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        removeBookLabel.setBounds(40, 604, 102, 21);
        removeBookLabel.setForeground(Color.WHITE);
        removeBookLabel.setFont(new Font("Jost", Font.BOLD, 15));
        panel.add(removeBookLabel);
        
        JButton borrowBookButton = new JButton("");
        borrowBookButton.setIcon(new ImageIcon("E:\\Projects\\LibraryApp\\resources\\images\\borrow_book.png"));
        borrowBookButton.setBounds(40, 395, 112, 68);
        borrowBookButton.setContentAreaFilled(false);
        borrowBookButton.setBorderPainted(false); // Removes border
        borrowBookButton.setFocusPainted(false); // Removes focus border
        borrowBookButton.setOpaque(false);
        borrowBookButton.setToolTipText("Logs a borrowed book to the database");
        panel.add(borrowBookButton);
        
        JLabel borrowBookLabel = new JLabel("Borrow Book");
        borrowBookLabel.setBounds(60, 750, 92, 21);
        borrowBookLabel.setForeground(Color.WHITE);
        borrowBookLabel.setFont(new Font("Jost", Font.BOLD, 14));
        panel.add(borrowBookLabel);
        
        JLabel booksManagementLabel = new JLabel("Books Management");
        booksManagementLabel.setForeground(Color.WHITE);
        booksManagementLabel.setFont(new Font("Jost", Font.BOLD, 15));
        booksManagementLabel.setBounds(40, 344, 132, 21);
        panel.add(booksManagementLabel);
        
        JLabel managementLabel = new JLabel(" \r\nManagement");
        managementLabel.setVerticalAlignment(SwingConstants.TOP);
        managementLabel.setForeground(Color.WHITE);
        managementLabel.setFont(new Font("Jost", Font.BOLD, 15));
        managementLabel.setBounds(53, 491, 92, 21);
        panel.add(managementLabel);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(145, 149, 153));
        panel_1.setBounds(201, 0, 1494, 88);
        frmDashboard.getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        JLabel connectedAsLabel = new JLabel("CONNECTED AS:");
        connectedAsLabel.setFont(new Font("Jost", Font.BOLD, 20));
        connectedAsLabel.setBounds(928, 11, 178, 16);
        panel_1.add(connectedAsLabel);
        
        JLabel accessLevelLabel = new JLabel("ACCESS LEVEL :");
        accessLevelLabel.setFont(new Font("Jost", Font.BOLD, 20));
        accessLevelLabel.setBounds(926, 48, 168, 16);
        panel_1.add(accessLevelLabel);
        
        
        JLabel usernameLabel = new JLabel(user);
        usernameLabel.setFont(new Font("Jost", Font.BOLD, 20));
        usernameLabel.setBounds(1116, 11, 120, 16);
        panel_1.add(usernameLabel);
        
        JLabel accessLevelLabel1 = new JLabel(level);
        accessLevelLabel1.setFont(new Font("Jost", Font.BOLD, 20));
        accessLevelLabel1.setBounds(1116, 48, 120, 16);
        panel_1.add(accessLevelLabel1);
        
        JButton addUserButton = new JButton("");
        addUserButton.addActionListener(e -> openWindow(new AdminPermissionsWindow(user, level)));
        addUserButton.setIcon(new ImageIcon("E:\\Projects\\LibraryApp\\resources\\images\\add_16321386.png"));
        addUserButton.setBounds(818, 11, 70, 66);
        addUserButton.setContentAreaFilled(false);
        addUserButton.setBorderPainted(false); // Removes border
        addUserButton.setFocusPainted(false); // Removes focus border
        addUserButton.setOpaque(false);
        addUserButton.setToolTipText("Create a new user");
        addUserButton.setVisible(level.equals("ADMIN"));
        panel_1.add(addUserButton);
        
        JButton logOutButton = new JButton("");
        logOutButton.setContentAreaFilled(false);
        logOutButton.setToolTipText("Log out from the app");
        logOutButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		frmDashboard.dispose();
        		LibraryApp.main(null);
        		
        	}
        });
        logOutButton.setIcon(new ImageIcon("E:\\Projects\\LibraryApp\\resources\\images\\log-out_10024482.png"));
        logOutButton.setBounds(1398, 773, 65, 65);
        frmDashboard.getContentPane().add(logOutButton);

        frmDashboard.setVisible(true);
    }
}
