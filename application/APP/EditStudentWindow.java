package APP;

import java.awt.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class EditStudentWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn;
    private JTextField Ssearch;
    private JTextField  sid;          // Student ID display field
    private JTextField  stuname;      // Student name field
    private JTextField  stufname;     // Student first name field
    private JTextField  slevel;       // Student level field
    private JTextField  snumber;      // Student phone number field
    private JTextField  smail;        // Student email field
     // Search input field
    EditStudentWindow(){
    	conn = LibraryApp.getConnection();
    	initializeComponents();
    	
    	
    	setTitle("Edit Student");
        setSize(806, 547);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 102));
        panel.setBounds(-55, 0, 1666, 94);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel iconLabel = new JLabel("");
        iconLabel.setIcon(new ImageIcon("src\\resrc\\LMsmall.png"));
        iconLabel.setBounds(68, 0, 100, 100);
        panel.add(iconLabel);
        
        JLabel lblStudentEditing = new JLabel("STUDENT EDITING");
        lblStudentEditing.setForeground(new Color(255, 255, 255));
        lblStudentEditing.setFont(new Font("Jost", Font.BOLD, 19));
        lblStudentEditing.setBounds(346, 40, 196, 31);
        panel.add(lblStudentEditing);
        
        JButton btnSaveChanges = new JButton("SAVE");
        btnSaveChanges.setForeground(Color.WHITE);
        btnSaveChanges.setFont(new Font("Jost", Font.BOLD, 24));
        btnSaveChanges.setBackground(new Color(0, 128, 0));
        btnSaveChanges.setBounds(40, 236, 140, 50);
        getContentPane().add(btnSaveChanges);
        
        JButton btnsearch = new JButton("");
        btnsearch.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		searchStudent();
        		
        	}
        });
        btnsearch.setIcon(new ImageIcon("src\\resrc\\find_11916806.png"));
        btnsearch.setFocusPainted(false);
        btnsearch.setContentAreaFilled(false);
        btnsearch.setBorderPainted(false);
        btnsearch.setBounds(190, 236, 50, 50);
        getContentPane().add(btnsearch);
        
        
        
        JLabel lblStudentIddlt = new JLabel("STUDENT ID");
        lblStudentIddlt.setForeground(Color.BLACK);
        lblStudentIddlt.setFont(new Font("Jost", Font.BOLD, 19));
        lblStudentIddlt.setBounds(40, 147, 196, 31);
        getContentPane().add(lblStudentIddlt);
        
        JLabel lblStudentId = new JLabel("STUDENT ID");
        lblStudentId.setForeground(Color.BLACK);
        lblStudentId.setFont(new Font("Jost", Font.BOLD, 19));
        lblStudentId.setBounds(302, 147, 196, 31);
        getContentPane().add(lblStudentId);
        
       
        
        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setForeground(Color.BLACK);
        lblFirstName.setFont(new Font("Jost", Font.BOLD, 19));
        lblFirstName.setBounds(302, 236, 196, 31);
        getContentPane().add(lblFirstName);
        
       
        
        JLabel lblleveldlt = new JLabel("Level");
        lblleveldlt.setForeground(Color.BLACK);
        lblleveldlt.setFont(new Font("Jost", Font.BOLD, 19));
        lblleveldlt.setBounds(521, 147, 196, 31);
        getContentPane().add(lblleveldlt);
        
        
        
       
        
        JLabel lblEmail = new JLabel("EMAIL");
        lblEmail.setForeground(Color.BLACK);
        lblEmail.setFont(new Font("Jost", Font.BOLD, 19));
        lblEmail.setBounds(521, 317, 196, 31);
        getContentPane().add(lblEmail);
        
       
        JLabel lblPhoneNumber = new JLabel("PHONE NUMBER");
        lblPhoneNumber.setForeground(Color.BLACK);
        lblPhoneNumber.setFont(new Font("Jost", Font.BOLD, 19));
        lblPhoneNumber.setBounds(521, 236, 196, 31);
        getContentPane().add(lblPhoneNumber);
        
      
        
        JLabel lblnamedlt_1 = new JLabel("Name");
        lblnamedlt_1.setForeground(Color.BLACK);
        lblnamedlt_1.setFont(new Font("Jost", Font.BOLD, 19));
        lblnamedlt_1.setBounds(302, 317, 196, 31);
        
        btnSaveChanges.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		saveStudent();
        		
        	}
        });
        getContentPane().add(lblnamedlt_1);
    	
    	
        setVisible(true);
    }
    
    private void searchStudent() {
        String studentID = Ssearch.getText().trim();
        
        if (studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String sql = "SELECT NAME, FIRSTNAME, STUDENT_LEVEL, NUM_TEL, MAILETU FROM studentlist WHERE STUDENTID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                sid.setText(studentID);
                stuname.setText(rs.getString("NAME"));
                stufname.setText(rs.getString("FIRSTNAME"));
                slevel.setText(rs.getString("STUDENT_LEVEL"));
                snumber.setText(rs.getString("NUM_TEL"));  // Fixed to match your DB column
                smail.setText(rs.getString("MAILETU"));    // Fixed to match your DB column
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No student found with ID: " + studentID, 
                    "Not Found", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Database Error: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    private void clearFields() {
        sid.setText("");
        stuname.setText("");
        stufname.setText("");
        slevel.setText("");
        snumber.setText("");
        smail.setText("");
    }
    private void saveStudent() {
        String studentId = sid.getText().trim();
        String firstName = stufname.getText().trim();
        String lastName = stuname.getText().trim();
        String level = slevel.getText().trim();
        String phone = snumber.getText().trim();
        String email = smail.getText().trim();

        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Student ID cannot be empty!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String updateQuery = "UPDATE studentlist SET " +
                "FIRSTNAME = ?, " +
                "NAME = ?, " +
                "STUDENT_LEVEL = ?, " +
                "NUM_TEL = ?, " +
                "MAILETU = ? " +
                "WHERE STUDENTID = ?";

            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, level);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.setString(6, studentId);

            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Student updated successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No student found with ID: " + studentId, 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Database error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void initializeComponents() {
        // Initialize all text fields (using class variables, no type declaration)
        sid = new JTextField();
        configureTextField(sid, 302, 182);

        stuname = new JTextField();
        configureTextField(stuname, 302, 352);

        stufname = new JTextField();
        configureTextField(stufname, 302, 271);

        slevel = new JTextField();
        configureTextField(slevel, 521, 182);

        snumber = new JTextField();
        configureTextField(snumber, 521, 271);

        smail = new JTextField();
        configureTextField(smail, 521, 352);

        Ssearch = new JTextField(16);
        Ssearch.setFont(new Font("Jost", Font.PLAIN, 22));
        Ssearch.setBounds(40, 182, 204, 43);
        getContentPane().add(Ssearch);
        
    }
    private void configureTextField(JTextField field, int x, int y) {
        field.setFont(new Font("Jost", Font.PLAIN, 20));
        field.setEditable(true);
        field.setBounds(x, y, 184, 31);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        getContentPane().add(field);
    }
    
    
    public static void main(String[] args) {
        new EditStudentWindow();
    }
}