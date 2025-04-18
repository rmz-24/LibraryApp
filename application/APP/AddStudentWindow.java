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
import java.util.Calendar;
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
	
	
	private JTextField matriculeField;
	private JTextField stdNameField;
	private JTextField stdSurnameField;
	
	
	private Connection connection;
	private JTextField stdPhoneNbrField;
	private JTextField stdMailField;
	
	
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
        
        
        JDateChooser stdRegisterDateField;
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(145, 149, 153));
        panel_1.setBounds(0, 0, 1174, 973);
        getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        JLabel stdIdLabel = new JLabel("STUDENT ID *");
        stdIdLabel.setForeground(Color.WHITE);
        stdIdLabel.setFont(new Font("Jost", Font.BOLD, 24));
        stdIdLabel.setBounds(30, 94, 216, 35);
        panel_1.add(stdIdLabel);
        
        JLabel stdNameLabel = new JLabel("STUDENT NAME *");
        stdNameLabel.setForeground(Color.WHITE);
        stdNameLabel.setFont(new Font("Jost", Font.BOLD, 24));
        stdNameLabel.setBounds(30, 205, 216, 35);
        panel_1.add(stdNameLabel);
        
        JLabel stdLevelLabel = new JLabel("STUDENT LEVEL *");
        stdLevelLabel.setForeground(Color.WHITE);
        stdLevelLabel.setFont(new Font("Jost", Font.BOLD, 24));
        stdLevelLabel.setBounds(30, 432, 216, 35);
        panel_1.add(stdLevelLabel);
        
        JLabel stdSurnameLabel = new JLabel("STUDENT SURNAME *");
        stdSurnameLabel.setForeground(Color.WHITE);
        stdSurnameLabel.setFont(new Font("Jost", Font.BOLD, 24));
        stdSurnameLabel.setBounds(30, 321, 269, 35);
        panel_1.add(stdSurnameLabel);
        
        JLabel stdRegisterDateLabel = new JLabel("REGISTERED THE  *");
        stdRegisterDateLabel.setForeground(Color.WHITE);
        stdRegisterDateLabel.setFont(new Font("Jost", Font.BOLD, 24));
        stdRegisterDateLabel.setBounds(30, 545, 269, 35);
        panel_1.add(stdRegisterDateLabel);
        
        matriculeField = new JTextField();
        matriculeField.setFont(new Font("Jost", Font.PLAIN, 22));
        matriculeField.setBounds(30, 140, 269, 43);
        panel_1.add(matriculeField);
        matriculeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) { 
                    e.consume(); // Ignore non-digit input
                }
            }
        });

        matriculeField.setColumns(10);
        
        stdNameField = new JTextField();
        stdNameField.setFont(new Font("Jost", Font.PLAIN, 22));
        stdNameField.setColumns(10);
        stdNameField.setBounds(30, 252, 269, 43);
        panel_1.add(stdNameField);
        
        stdSurnameField = new JTextField();
        stdSurnameField.setFont(new Font("Jost", Font.PLAIN, 22));
        stdSurnameField.setColumns(10);
        stdSurnameField.setBounds(30, 367, 269, 43);
        panel_1.add(stdSurnameField);
        
        JComboBox<String> stdLevelField = new JComboBox<>(levels);
        stdLevelField.setFont(new Font("Jost", Font.BOLD, 17));
        stdLevelField.setBounds(30, 478, 269, 43);
        panel_1.add(stdLevelField);
        
        stdRegisterDateField = new JDateChooser();
        stdRegisterDateField.setBounds(30, 591, 269, 43);
        panel_1.add(stdRegisterDateField);
        stdRegisterDateField.setFont(new Font("Jost", Font.PLAIN, 22));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);  // Set to midnight
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MILLISECOND, 0);
        stdRegisterDateField.setDate(cal.getTime());
        stdPhoneNbrField = new JTextField();
        stdPhoneNbrField.setFont(new Font("Jost", Font.PLAIN, 22));
        stdPhoneNbrField.setColumns(10);
        stdPhoneNbrField.setBounds(386, 480, 269, 43);
        panel_1.add(stdPhoneNbrField);
        stdPhoneNbrField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) { 
                    e.consume(); // Ignore non-digit input
                }
            }
        });
        
        JLabel stdPhoneNbrLabel = new JLabel("PHONE NUMBER *");
        stdPhoneNbrLabel.setForeground(Color.WHITE);
        stdPhoneNbrLabel.setFont(new Font("Jost", Font.BOLD, 24));
        stdPhoneNbrLabel.setBounds(386, 434, 216, 35);
        panel_1.add(stdPhoneNbrLabel);
        
        stdMailField = new JTextField();
        stdMailField.setFont(new Font("Jost", Font.PLAIN, 22));
        stdMailField.setColumns(10);
        stdMailField.setBounds(386, 591, 269, 43);
        panel_1.add(stdMailField);
        
        JLabel stdMailLabel = new JLabel("EMAIL");
        stdMailLabel.setForeground(Color.WHITE);
        stdMailLabel.setFont(new Font("Jost", Font.BOLD, 24));
        stdMailLabel.setBounds(386, 545, 216, 35);
        panel_1.add(stdMailLabel);
        
        JButton addStdButton = new JButton("Add New Student");
        addStdButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // Get and trim Student ID
                    String idText = matriculeField.getText().trim();
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
                    String name = stdNameField.getText().trim();
                    String firstName = stdSurnameField.getText().trim();
                    String level = (String) stdLevelField.getSelectedItem();
                    java.util.Date signedDate = stdRegisterDateField.getDate();
                    String numText = stdPhoneNbrField.getText().trim();
                    if (numText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Number field cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Convert to integer after validation
                    int num = Integer.parseInt(numText);
                    String email = stdMailField.getText().trim();

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


        addStdButton.setForeground(Color.WHITE);
        addStdButton.setFont(new Font("Jost", Font.BOLD, 18));
        addStdButton.setBackground(new Color(56, 194, 56));
        addStdButton.setBounds(217, 667, 181, 40);
        panel_1.add(addStdButton);
        
        JButton abortButton = new JButton("Abort");
        abortButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		dispose();
        	}
        });
        abortButton.setForeground(Color.WHITE);
        abortButton.setFont(new Font("Jost", Font.BOLD, 18));
        abortButton.setBackground(new Color(128, 0, 0));
        abortButton.setBounds(410, 667, 123, 40);
        panel_1.add(abortButton);
        
        

        

        // Make frame visible
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddStudentWindow("user", "pass"));
    }
}
