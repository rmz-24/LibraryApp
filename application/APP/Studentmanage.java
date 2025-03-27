package APP;

import javax.swing.*;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class Studentmanage extends JFrame {
	private ImageIcon loadImage(String imageName) {
	    return new ImageIcon(getClass().getResource("/resrc/" + imageName));
	}
	
	
	private final JPanel panel = new JPanel();
    public Studentmanage(String user , String level) {
    	//private Connection connection;
    	//this.connection = LibraryApp.getConnection();
    	 String usernames =user;
    	 String levels =level;
    	
    	setIconImage(Toolkit.getDefaultToolkit().getImage("src\\resrc\\LMsmall.png"));
        setTitle("Student Management");
        setSize(536, 435);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        panel.setBackground(new Color(182, 182, 182));
        panel.setBounds(0, 0, 1695, 1023);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(0, 102, 102));
        panel_1.setBounds(-51, 0, 1746, 115);
        panel.add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setBounds(87, 11, 100, 100);
        lblNewLabel_1.setIcon(new ImageIcon("src\\resrc\\LMsmall.png"));
        panel_1.add(lblNewLabel_1);
        
        JButton btnAddnewstudent = new JButton("");
        btnAddnewstudent.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new Studentforum(user,level);
        	}
        });
        btnAddnewstudent.setIcon(new ImageIcon("src\\resrc\\profile_10655019.png"));
        btnAddnewstudent.setBounds(113, 195, 104, 68);
        btnAddnewstudent.setContentAreaFilled(false);
        btnAddnewstudent.setBorderPainted(false); // Removes border
        btnAddnewstudent.setFocusPainted(false); // Removes focus border
        btnAddnewstudent.setOpaque(false);
        panel.add(btnAddnewstudent);
        
        JButton deletestudent = new JButton("");
        deletestudent.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new Deletestudent();
        	}
        });
       deletestudent.setOpaque(false);
       deletestudent.setIcon(new ImageIcon("src\\resrc\\delete-account_16321987.png"));
       deletestudent.setFocusPainted(false);
       deletestudent.setContentAreaFilled(false);
       deletestudent.setBorderPainted(false);
       deletestudent.setBounds(294, 195, 104, 68);
        panel.add(deletestudent);
        
        JLabel lbladdstudent = new JLabel("Add student ");
        lbladdstudent.setForeground(new Color(255, 255, 255));
        lbladdstudent.setFont(new Font("Jost", Font.BOLD, 13));
        lbladdstudent.setBounds(132, 285, 77, 14);
        panel.add(lbladdstudent);
        
        JLabel lblRemmoveStudent = new JLabel("Remove student ");
        lblRemmoveStudent.setForeground(new Color(255, 255, 255));
        lblRemmoveStudent.setFont(new Font("Jost", Font.BOLD, 13));
        lblRemmoveStudent.setBounds(301, 285, 97, 14);
        panel.add(lblRemmoveStudent);
        
        JButton btnbacktosm = new JButton("");
        btnbacktosm.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new Home(user,level);
        		dispose();
        		
        	}
        });
        btnbacktosm.setOpaque(false);
        btnbacktosm.setIcon(new ImageIcon("resrc\\left-arrow_10117838.png"));
        btnbacktosm.setFocusPainted(false);
        btnbacktosm.setContentAreaFilled(false);
        btnbacktosm.setBorderPainted(false);
        btnbacktosm.setBounds(433, 333, 104, 68);
        panel.add(btnbacktosm);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Studentmanage("user","level"));
    }
}
