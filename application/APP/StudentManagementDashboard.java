package APP;

import javax.swing.*;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.sql.Connection;
import java.io.File;
import java.net.URL;

public class StudentManagementDashboard extends JFrame {
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
	/*private ImageIcon loadImage(String imageName) {
	    return new ImageIcon(getClass().getResource("/resrc/" + imageName));
	}*/
	
	
	private static final long serialVersionUID = 1L;
	private final JPanel panel = new JPanel();
    public StudentManagementDashboard(String user , String level,ThemeToggleButton tg) {
    	setResizable(false);
    	//private Connection connection;
    	//this.connection = LibraryApp.getConnection();
    	 //String usernames =user;
    	 //String levels =level;
    	
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
        
        JPanel topColorPanel = new JPanel();
        topColorPanel.setBackground(new Color(0, 102, 102));
        topColorPanel.setBounds(-51, 0, 1746, 115);
        panel.add(topColorPanel);
        topColorPanel.setLayout(null);
        
        JLabel iconLabel = new JLabel("");
        iconLabel.setBounds(87, 11, 100, 100);
        
        iconLabel.setIcon(loadImageIcon("/resrc/LMsmall.png"));
        topColorPanel.add(iconLabel);
        
        JLabel iconLabel_1 = new JLabel("");
        iconLabel_1.setIcon(loadImageIcon("/resrc/LMsmall.png"));
        iconLabel_1.setBounds(87, 11, 100, 100);
        topColorPanel.add(iconLabel_1);
        
        JButton addStudentButton = new JButton("");
        addStudentButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new AddStudentWindow(user,level,tg);
        	}
        });
       
        addStudentButton.setIcon(loadImageIcon("/resrc/profile_10655019.png"));
        addStudentButton.setBounds(22, 196, 104, 68);
        addStudentButton.setContentAreaFilled(false);
        addStudentButton.setBorderPainted(false); // Removes border
        addStudentButton.setFocusPainted(false); // Removes focus border
        addStudentButton.setOpaque(false);
        panel.add(addStudentButton);
        
        JButton removeStudentButton = new JButton("");
        removeStudentButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new RemoveStudentWindow(tg);
        	}
        });
        removeStudentButton.setOpaque(false);
        
        removeStudentButton.setIcon(loadImageIcon("/resrc/delete-account_16321987.png"));
        removeStudentButton.setFocusPainted(false);
        removeStudentButton.setContentAreaFilled(false);
        removeStudentButton.setBorderPainted(false);
        removeStudentButton.setBounds(203, 196, 104, 68);
        panel.add(removeStudentButton);
        
        JLabel addStudentLabel = new JLabel("Add student ");
        addStudentLabel.setForeground(new Color(255, 255, 255));
        addStudentLabel.setFont(new Font("Jost", Font.BOLD, 13));
        addStudentLabel.setBounds(41, 286, 77, 14);
        panel.add(addStudentLabel);
        
        JLabel removeStudentLabel = new JLabel("Remove student ");
        removeStudentLabel.setForeground(new Color(255, 255, 255));
        removeStudentLabel.setFont(new Font("Jost", Font.BOLD, 13));
        removeStudentLabel.setBounds(210, 286, 97, 14);
        panel.add(removeStudentLabel);
        
        JButton backHomeButton = new JButton("");
        backHomeButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new Home(user,level,tg);
        		dispose();
        		
        	}
        });
        backHomeButton.setOpaque(false);
        
        backHomeButton.setIcon(loadImageIcon("/resrc/left-arrow_10117838.png"));
        backHomeButton.setFocusPainted(false);
        backHomeButton.setContentAreaFilled(false);
        backHomeButton.setBorderPainted(false);
        backHomeButton.setBounds(433, 333, 104, 68);
        panel.add(backHomeButton);
        
        JLabel editStudentLabel = new JLabel("Edit student ");
        editStudentLabel.setForeground(Color.WHITE);
        editStudentLabel.setFont(new Font("Jost", Font.BOLD, 13));
        editStudentLabel.setBounds(409, 286, 83, 14);
        panel.add(editStudentLabel);
        
        JButton removeStudentButton_1 = new JButton("");
        removeStudentButton_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new EditStudentWindow(tg);
        		
        		
        	}
        });
        removeStudentButton_1.setOpaque(false);
       
        removeStudentButton_1. setIcon(loadImageIcon("/resrc/user_16784047.png"));
        removeStudentButton_1.setFocusPainted(false);
        removeStudentButton_1.setContentAreaFilled(false);
        removeStudentButton_1.setBorderPainted(false);
        removeStudentButton_1.setBounds(390, 196, 104, 68);
        panel.add(removeStudentButton_1);
        
        JButton removeStudentButton_1_1 = new JButton("");
        removeStudentButton_1_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new StudentsListWindow(tg);
        		dispose();
        	}
        });
        removeStudentButton_1_1.setOpaque(false);
        
        removeStudentButton_1_1.setIcon(loadImageIcon("/resrc/list_11916758.png"));
        removeStudentButton_1_1.setFocusPainted(false);
        removeStudentButton_1_1.setContentAreaFilled(false);
        removeStudentButton_1_1.setBorderPainted(false);
        removeStudentButton_1_1.setBounds(481, 126, 33, 33);
        panel.add(removeStudentButton_1_1);
        if(tg.isSelected()) {
        	panel.setBackground(new Color(60, 63, 65)); // Light grayColor(60, 63, 65)

	  	}else {
	  		panel.setBackground(new Color(182, 182, 182));
	  		
	  	}

        setVisible(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new StudentManagementDashboard("user","level"));
//    }
}