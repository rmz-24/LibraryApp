package APP;

import javax.swing.*;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
public class Home{
    private JFrame frmDashboard;
    
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
        lblNewLabel_1.setIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\LMsmall.png"));
        panel.add(lblNewLabel_1);
        
        JButton Addstudentbtn = new JButton("");
        
        Addstudentbtn.setBackground(new Color(0, 102, 102));
        Addstudentbtn.setIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\student.png"));
        Addstudentbtn.setBounds(40, 137, 112, 68);
        Addstudentbtn.setContentAreaFilled(false);
        Addstudentbtn.setBorderPainted(false); // Removes border
        Addstudentbtn.setFocusPainted(false); // Removes focus border
        Addstudentbtn.setOpaque(false);
        panel.add(Addstudentbtn);
        
        JLabel lblstudentmanagement = new JLabel("Student Management");
        lblstudentmanagement.setBounds(27, 216, 152, 21);
        lblstudentmanagement.setFont(new Font("Jost", Font.BOLD, 15));
        lblstudentmanagement.setForeground(new Color(255, 255, 255));
        panel.add(lblstudentmanagement);
        
        JButton removestudentbtn = new JButton("");
        removestudentbtn.setIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\books.png"));
        removestudentbtn.setBounds(40, 265, 112, 68);
        removestudentbtn.setContentAreaFilled(false);
        removestudentbtn.setBorderPainted(false); // Removes border
        removestudentbtn.setFocusPainted(false); // Removes focus border
        removestudentbtn.setOpaque(false);
        panel.add(removestudentbtn);
        
        JLabel lbladdbook = new JLabel("Loan and Return");
        lbladdbook.setVerticalAlignment(SwingConstants.TOP);
        lbladdbook.setBounds(43, 470, 129, 21);
        lbladdbook.setForeground(Color.WHITE);
        lbladdbook.setFont(new Font("Jost", Font.BOLD, 15));
        panel.add(lbladdbook);
        
        JButton removebook = new JButton("");
        removebook.setIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\exclamation.png"));
        removebook.setBounds(40, 523, 112, 68);
        removebook.setContentAreaFilled(false);
        removebook.setBorderPainted(false); // Removes border
        removebook.setFocusPainted(false); // Removes focus border
        removebook.setOpaque(false);
        panel.add(removebook);
        
        JLabel lblremovebook = new JLabel("Report");
        lblremovebook.setHorizontalAlignment(SwingConstants.CENTER);
        lblremovebook.setHorizontalTextPosition(SwingConstants.CENTER);
        lblremovebook.setBounds(40, 604, 102, 21);
        lblremovebook.setForeground(Color.WHITE);
        lblremovebook.setFont(new Font("Jost", Font.BOLD, 15));
        panel.add(lblremovebook);
        
        JButton borrowbook = new JButton("");
        borrowbook.setIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\borrow_book.png"));
        borrowbook.setBounds(40, 395, 112, 68);
        borrowbook.setContentAreaFilled(false);
        borrowbook.setBorderPainted(false); // Removes border
        borrowbook.setFocusPainted(false); // Removes focus border
        borrowbook.setOpaque(false);
        panel.add(borrowbook);
        
        JLabel lblborrowbook = new JLabel("Borrow Book");
        lblborrowbook.setBounds(60, 750, 92, 21);
        lblborrowbook.setForeground(Color.WHITE);
        lblborrowbook.setFont(new Font("Jost", Font.BOLD, 14));
        panel.add(lblborrowbook);
        
        JLabel lblBooksManagement = new JLabel("Books Management");
        lblBooksManagement.setForeground(Color.WHITE);
        lblBooksManagement.setFont(new Font("Jost", Font.BOLD, 15));
        lblBooksManagement.setBounds(40, 344, 132, 21);
        panel.add(lblBooksManagement);
        
        JLabel lblManagement = new JLabel(" \r\nManagement");
        lblManagement.setVerticalAlignment(SwingConstants.TOP);
        lblManagement.setForeground(Color.WHITE);
        lblManagement.setFont(new Font("Jost", Font.BOLD, 15));
        lblManagement.setBounds(53, 491, 92, 21);
        panel.add(lblManagement);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(145, 149, 153));
        panel_1.setBounds(201, 0, 1494, 88);
        frmDashboard.getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("CONNECTED AS:");
        lblNewLabel.setFont(new Font("Jost", Font.BOLD, 20));
        lblNewLabel.setBounds(928, 11, 178, 16);
        panel_1.add(lblNewLabel);
        
        JLabel lblAccessLevel = new JLabel("ACCESS LEVEL :");
        lblAccessLevel.setFont(new Font("Jost", Font.BOLD, 20));
        lblAccessLevel.setBounds(926, 48, 168, 16);
        panel_1.add(lblAccessLevel);
        
        
        JLabel lblusername = new JLabel(user);
        lblusername.setFont(new Font("Jost", Font.BOLD, 20));
        lblusername.setBounds(1116, 11, 120, 16);
        panel_1.add(lblusername);
        
        JLabel lblacceslevel = new JLabel(level);
        lblacceslevel.setFont(new Font("Jost", Font.BOLD, 20));
        lblacceslevel.setBounds(1116, 48, 120, 16);
        panel_1.add(lblacceslevel);
        
        JButton adduserbtn = new JButton("");
        adduserbtn.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new AdminPermissionsWindow(user,level);
        		frmDashboard.dispose();
        	}
        });
        adduserbtn.setIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\add_16321386.png"));
        adduserbtn.setBounds(818, 11, 70, 66);
        adduserbtn.setContentAreaFilled(false);
        adduserbtn.setBorderPainted(false); // Removes border
        adduserbtn.setFocusPainted(false); // Removes focus border
        adduserbtn.setOpaque(false);
        panel_1.add(adduserbtn);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		frmDashboard.dispose();
        		LibraryApp.main(null);
        		
        	}
        });
        btnNewButton.setIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\log-out_10024482.png"));
        btnNewButton.setBounds(1398, 773, 65, 65);
        frmDashboard.getContentPane().add(btnNewButton);

        frmDashboard.setVisible(true);
    }
}
