package APP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.sql.*;

public class RemoveStudentWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	/*private ImageIcon loadImage(String imageName) {
	    return new ImageIcon(getClass().getResource("/resrc/" + imageName));
	}*/
    private JTextField studentiddlt;
    private JTextArea Sname, Sfname, Slevel;
    private Connection conn;
    public RemoveStudentWindow(ThemeToggleButton tg) {
    	
        setTitle("Student Removing Window");
        setSize(638, 415);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        JPanel panelbg = new JPanel();
        if(tg.isSelected()) {
        	panelbg.setBackground(new Color(60, 63, 65)); // Light grayColor(60, 63, 65)

	  	}else {
	  		panelbg.setBackground(new Color(182, 182, 182));
	  		
	  	}
        setContentPane(panelbg);
        panelbg.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 102));
        panel.setBounds(-52, 0, 755, 66);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel topLabel = new JLabel("STUDENT DELETION");
        topLabel.setForeground(new Color(255, 255, 255));
        topLabel.setFont(new Font("Jost", Font.BOLD, 19));
        topLabel.setBounds(280, 24, 196, 31);
        panel.add(topLabel);

        JLabel searchStudentIdLabel = new JLabel("ENTER STUDENT ID");
        searchStudentIdLabel.setForeground(new Color(0, 0, 0));
        searchStudentIdLabel.setFont(new Font("Jost", Font.BOLD, 19));
        searchStudentIdLabel.setBounds(10, 106, 196, 31);
        getContentPane().add(searchStudentIdLabel);

        studentiddlt = new JTextField(16);
        studentiddlt.setFont(new Font("Jost", Font.PLAIN, 22));
        studentiddlt.setBounds(10, 141, 204, 43);
        getContentPane().add(studentiddlt);

        JButton btndeletestudent = new JButton("DELETE");
        btndeletestudent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String studentID = studentiddlt.getText().trim();
                
                if (studentID.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a Student ID!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    conn = LibraryApp.getConnection();
                    
                    // 1. Check blacklist status
                    String blacklistStatus = null;
                    boolean isBlacklisted = false;
                    String sqlCheckBlacklist = "SELECT ETAT FROM blacklist WHERE STUDENTID = ?";
                    try (PreparedStatement stmtBlacklist = conn.prepareStatement(sqlCheckBlacklist)) {
                        stmtBlacklist.setString(1, studentID);
                        try (ResultSet rs = stmtBlacklist.executeQuery()) {
                            if (rs.next()) {
                                blacklistStatus = rs.getString("ETAT");
                                // Assuming "BLOCKED", "BANNED", "RESTRICTED" etc. mean blacklisted
                                // and only "CLEAR" means not blacklisted
                                isBlacklisted = !"CLEAR".equalsIgnoreCase(blacklistStatus);
                            } else {
                                // Student not found in blacklist table at all
                                isBlacklisted = false;
                            }
                        }
                    }

                    // 2. Check if student has pending loans
                    boolean hasPendingLoans = false;
                    String sqlCheckLoans = "SELECT COUNT(*) AS loan_count FROM EMPRUNTS WHERE NUM_ETU = ? AND STATUT IN (0, 2)";
                    try (PreparedStatement stmtLoans = conn.prepareStatement(sqlCheckLoans)) {
                        stmtLoans.setString(1, studentID);
                        try (ResultSet rs = stmtLoans.executeQuery()) {
                            if (rs.next()) {
                                int count = rs.getInt("loan_count");
                                hasPendingLoans = (count > 0);
                                System.out.println("DEBUG: Found " + count + " pending/late loans"); // Debug output
                            }
                        }
                    }

                    // 2. Decision logic
                    

                    // 2. Only proceed if count is 0
                    

                    // 3. Decision logic
                    
                    if (blacklistStatus != null && !"clear".equals(blacklistStatus)) {
                        JOptionPane.showMessageDialog(null, 
                            "The student is Blacklisted. Please review the student's state", 
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    }else if (hasPendingLoans) {
                        JOptionPane.showMessageDialog(null, 
                                "This student has  pending/late loans. Resolve them before deletion.", 
                                "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        
                    } else {
                        // Proceed with deletion
                        String sqlDelete = "DELETE FROM studentlist WHERE STUDENTID = ?";
                        try (PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {
                            stmtDelete.setString(1, studentID);
                            int rowsAffected = stmtDelete.executeUpdate();
                            
                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(null, 
                                    "Student deleted successfully!", 
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                                // Clear input fields
                                studentiddlt.setText("");
                                Sname.setText("");
                                Sfname.setText("");
                                Slevel.setText("");
                            } else {
                                JOptionPane.showMessageDialog(null, 
                                    "Student not found!", 
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(RemoveStudentWindow.this, 
                        "Database Error: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } 
            }
        });
        btndeletestudent.setBackground(new Color(128, 0, 0));
        btndeletestudent.setForeground(new Color(255, 255, 255));
        btndeletestudent.setFont(new Font("Jost", Font.BOLD, 24));
        btndeletestudent.setBounds(10, 195, 140, 50);
        getContentPane().add(btndeletestudent);

        Sname = new JTextArea();
        Sname.setFont(new Font("Jost", Font.BOLD, 20));
        Sname.setBounds(407, 141, 184, 31);
        Sname.setEditable(false);
        getContentPane().add(Sname);

        JLabel lblnamedlt = new JLabel("Name");
        lblnamedlt.setForeground(Color.BLACK);
        lblnamedlt.setFont(new Font("Jost", Font.BOLD, 19));
        lblnamedlt.setBounds(407, 106, 196, 31);
        getContentPane().add(lblnamedlt);

        Sfname = new JTextArea();
        Sfname.setFont(new Font("Jost", Font.BOLD, 20));
        Sfname.setBounds(407, 230, 184, 31);
        Sfname.setEditable(false);
        getContentPane().add(Sfname);

        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setForeground(Color.BLACK);
        lblFirstName.setFont(new Font("Jost", Font.BOLD, 19));
        lblFirstName.setBounds(407, 195, 196, 31);
        getContentPane().add(lblFirstName);

        Slevel = new JTextArea();
        Slevel.setFont(new Font("Jost", Font.BOLD, 20));
        Slevel.setBounds(407, 311, 184, 31);
        Slevel.setEditable(false);
        getContentPane().add(Slevel);

        JLabel lblleveldlt = new JLabel("Level");
        lblleveldlt.setForeground(Color.BLACK);
        lblleveldlt.setFont(new Font("Jost", Font.BOLD, 19));
        lblleveldlt.setBounds(407, 276, 196, 31);
        getContentPane().add(lblleveldlt);

        JButton btnsearch = new JButton("");
        
        btnsearch.setIcon(loadImageIcon("/resrc/searchicon.png"));
        btnsearch.setBounds(160, 195, 50, 50);
        btnsearch.setFocusPainted(false);
        btnsearch.setContentAreaFilled(false);
        btnsearch.setBorderPainted(false);
        getContentPane().add(btnsearch);

        btnsearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchStudent();
            }
        });

        setVisible(true);
    }

    private void searchStudent() {
    	
    	conn = LibraryApp.getConnection();
        String studentID = studentiddlt.getText().trim();
        if (studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            
            String sql = "SELECT NAME, FIRSTNAME, STUDENT_LEVEL FROM studentlist WHERE STUDENTID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Sname.setText(rs.getString("NAME"));
                Sfname.setText(rs.getString("FIRSTNAME"));
                Slevel.setText(rs.getString("STUDENT_LEVEL"));
            } else {
                JOptionPane.showMessageDialog(this, "No student found with ID: " + studentID, "Not Found", JOptionPane.INFORMATION_MESSAGE);
                Sname.setText("");
                Sfname.setText("");
                Slevel.setText("");
            }

           // rs.close();
           // stmt.close();
            //conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
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

//    public static void main(String[] args) {
//        new RemoveStudentWindow();
//    }
}
