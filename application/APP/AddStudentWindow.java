package APP;

import javax.swing.*;
import java.awt.*;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
import java.sql.Connection;
//import com.jgoodies.forms.layout.FormSpecs;
//import net.miginfocom.swing.MigLayout;
import com.toedter.calendar.JDateChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.sql.Statement;
import java.util.Properties;
import java.sql.Date;
//import java.sql.DriverManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.util.Properties;
//import java.sql.Connection;

public class AddStudentWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	
	/*private ImageIcon loadImage(String imageName) {
	    return new ImageIcon(getClass().getResource("/resrc/" + imageName));
	}*/
	
	
	private JTextField matriculefield;
	private JTextField STUDENTNAME;
	private JTextField STUDENTFNAME;
	
	
	private Connection connection;
	private JTextField numfield;
	private JTextField mailfield;
	
	
	public static Properties getProps() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("config.properties"));
			return props;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean authenticate(String username, String password) {
		Properties props = getProps();
		if((username.equals(props.getProperty("db.user"))) || (password.equals(props.getProperty("db.password")))) {
			return true;
		}
		return false;
	}
	
	private void insertStudentData(String studentId, String name, String firstName, String student_level, java.util.Date signedDate , int num,String email) {
        try {
        	String sql = "INSERT INTO studentlist (\"STUDENTID\", \"NAME\", \"FIRSTNAME\", \"STUDENT_LEVEL\", \"SIGNED_IN_DATE\",\"NUM_TEL\",\"MAILETU\") VALUES (?, ?, ?, ?, ?,?,?)";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, studentId);
            stmt.setString(2, name);
            stmt.setString(3, firstName);
            stmt.setString(4, student_level);
            stmt.setDate(5, new Date(signedDate.getTime()));
            stmt.setInt(6, num);
            stmt.setString(7, email);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting student into database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public AddStudentWindow(String user , String pass) {
    	
    	 this.connection = LibraryApp.getConnection();
		// Dummy authentication (Replace with real validation logic)
		
    	
        // Set frame properties
        setTitle("Student Registering Window");
        setSize(763, 772);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 102));
        panel.setBounds(-76, 0, 999, 68);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel topLabel = new JLabel("STUDENT REGISTERING");
        topLabel.setForeground(new Color(255, 255, 255));
        topLabel.setBounds(325, 22, 280, 35);
        topLabel.setFont(new Font("Jost", Font.BOLD, 24));
        panel.add(topLabel);
        
        
        String[] levels = {"Licence 1", "Licence 2", "Licence 3", "Master 1", "Master 2", "Doctorat" , "INJ1 ", "INJ2" ,"INJ3","INJ4","INJ5"};
        
        
        JDateChooser SIGNEDDATE;
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(145, 149, 153));
        panel_1.setBounds(0, 0, 1174, 973);
        getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblStudentId = new JLabel("STUDENT ID *");
        lblStudentId.setForeground(Color.WHITE);
        lblStudentId.setFont(new Font("Jost", Font.BOLD, 24));
        lblStudentId.setBounds(30, 94, 216, 35);
        panel_1.add(lblStudentId);
        
        JLabel lblStudentname = new JLabel("STUDENT NAME *");
        lblStudentname.setForeground(Color.WHITE);
        lblStudentname.setFont(new Font("Jost", Font.BOLD, 24));
        lblStudentname.setBounds(30, 205, 216, 35);
        panel_1.add(lblStudentname);
        
        JLabel lblStudentlevel = new JLabel("STUDENT LEVEL *");
        lblStudentlevel.setForeground(Color.WHITE);
        lblStudentlevel.setFont(new Font("Jost", Font.BOLD, 24));
        lblStudentlevel.setBounds(30, 432, 216, 35);
        panel_1.add(lblStudentlevel);
        
        JLabel lblStudentfname = new JLabel("STUDENT FIRSTNAME *");
        lblStudentfname.setForeground(Color.WHITE);
        lblStudentfname.setFont(new Font("Jost", Font.BOLD, 24));
        lblStudentfname.setBounds(30, 321, 269, 35);
        panel_1.add(lblStudentfname);
        
        JLabel lblStudentsigndate = new JLabel("SIGNED IN  *");
        lblStudentsigndate.setForeground(Color.WHITE);
        lblStudentsigndate.setFont(new Font("Jost", Font.BOLD, 24));
        lblStudentsigndate.setBounds(30, 545, 216, 35);
        panel_1.add(lblStudentsigndate);
        
        matriculefield = new JTextField();
        matriculefield.setFont(new Font("Jost", Font.PLAIN, 22));
        matriculefield.setBounds(30, 140, 269, 43);
        panel_1.add(matriculefield);
        matriculefield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) { 
                    e.consume(); // Ignore non-digit input
                }
            }
        });

        matriculefield.setColumns(10);
        
        STUDENTNAME = new JTextField();
        STUDENTNAME.setFont(new Font("Jost", Font.PLAIN, 22));
        STUDENTNAME.setColumns(10);
        STUDENTNAME.setBounds(30, 252, 269, 43);
        panel_1.add(STUDENTNAME);
        
        STUDENTFNAME = new JTextField();
        STUDENTFNAME.setFont(new Font("Jost", Font.PLAIN, 22));
        STUDENTFNAME.setColumns(10);
        STUDENTFNAME.setBounds(30, 367, 269, 43);
        panel_1.add(STUDENTFNAME);
        
        JComboBox<String> LEVEL = new JComboBox<>(levels);
        LEVEL.setFont(new Font("Jost", Font.BOLD, 17));
        LEVEL.setBounds(30, 478, 269, 43);
        panel_1.add(LEVEL);
        
        SIGNEDDATE = new JDateChooser();
        SIGNEDDATE.setBounds(30, 591, 269, 43);
        panel_1.add(SIGNEDDATE);
        SIGNEDDATE.setFont(new Font("Jost", Font.PLAIN, 22));
        numfield = new JTextField();
        numfield.setFont(new Font("Jost", Font.PLAIN, 22));
        numfield.setColumns(10);
        numfield.setBounds(386, 480, 269, 43);
        panel_1.add(numfield);
        numfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) { 
                    e.consume(); // Ignore non-digit input
                }
            }
        });
        
        JLabel lblPhoneNumber = new JLabel("PHONE NUMBER *");
        lblPhoneNumber.setForeground(Color.WHITE);
        lblPhoneNumber.setFont(new Font("Jost", Font.BOLD, 24));
        lblPhoneNumber.setBounds(386, 434, 216, 35);
        panel_1.add(lblPhoneNumber);
        
        mailfield = new JTextField();
        mailfield.setFont(new Font("Jost", Font.PLAIN, 22));
        mailfield.setColumns(10);
        mailfield.setBounds(386, 591, 269, 43);
        panel_1.add(mailfield);
        
        JLabel lblEmail = new JLabel("EMAIL");
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setFont(new Font("Jost", Font.BOLD, 24));
        lblEmail.setBounds(386, 545, 216, 35);
        panel_1.add(lblEmail);
        
        JButton btnAddStudent = new JButton("Add New Student");
        btnAddStudent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // Get and trim Student ID
                    String idText = matriculefield.getText().trim();
                    System.out.println("Student ID input: [" + idText + "]");  // Debugging

                    // Check if ID is empty
                    if (idText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Student ID cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Ensure ID contains only digits
                    if (!idText.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "Invalid Student ID! Enter only numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Convert to integer
                    String studentId = idText;

                    // Get other inputs
                    String name = STUDENTNAME.getText().trim();
                    String firstName = STUDENTFNAME.getText().trim();
                    String level = (String) LEVEL.getSelectedItem();
                    java.util.Date signedDate = SIGNEDDATE.getDate();
                    String numText = numfield.getText().trim();
                    if (numText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Number field cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Convert to integer after validation
                    int num = Integer.parseInt(numText);
                    String email = mailfield.getText().trim();

                    // Validate required fields
                    if (name.isEmpty() || firstName.isEmpty() || level == null || signedDate == null ) {
                        JOptionPane.showMessageDialog(null, "All ( * ) fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Convert date to SQL format
                    java.sql.Date sqlDate = new java.sql.Date(signedDate.getTime());

                    // Insert into database
                    
                    insertStudentData(studentId, name, firstName, level, sqlDate,num,email);

                    // Close form
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Student ID! Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        btnAddStudent.setForeground(Color.WHITE);
        btnAddStudent.setFont(new Font("Jost", Font.BOLD, 18));
        btnAddStudent.setBackground(new Color(56, 194, 56));
        btnAddStudent.setBounds(217, 667, 181, 40);
        panel_1.add(btnAddStudent);
        
        JButton btnAbort = new JButton("Abort");
        btnAbort.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		dispose();
        	}
        });
        btnAbort.setForeground(Color.WHITE);
        btnAbort.setFont(new Font("Jost", Font.BOLD, 18));
        btnAbort.setBackground(new Color(128, 0, 0));
        btnAbort.setBounds(410, 667, 123, 40);
        panel_1.add(btnAbort);
        
        

        

        // Make frame visible
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddStudentWindow("user", "pass"));
    }
}
