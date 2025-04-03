package APP;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

public class AddBookWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField bookIdField;
    private JTextField bookNameField;
    private JTextField bookAuthorField;
    private JComboBox<String> Category;
    private JSpinner publishYearSpinner;
    private JSpinner availableQtySpinner;
	
	private Connection connection;
	
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
	 private void showError(String message) {
	        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	    }
	
	// MODIFIED: Enhanced insert method with new component support
	private void insertBookData() {
	    try {
	        // Get values from components
	    	String bookId = bookIdField.getText().trim();
            String bookName = bookNameField.getText().trim();
            String bookAuthor = bookAuthorField.getText().trim();
            String category = Category.getSelectedItem().toString().trim();
            int publishYear = (Integer) publishYearSpinner.getValue();
            int availableQty = (Integer) availableQtySpinner.getValue();

	        // Validation
	        if (!bookId.matches("BK\\d{5}")) {  // Ensures "BK" followed by exactly 5 digits
	            showError("Book ID must be in the format BKxxxxx (e.g., BK12345)");
	            clearForm();
	            return;
	        }
	        
	        if (bookId.isEmpty() || bookName.isEmpty() || bookAuthor.isEmpty() || category.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        // Database insertion
	        String sql = "INSERT INTO bookslist (BOOKID, BOOKNAME, BOOKAUTHOR, CATEGORIE, PUBLISH_YEAR, AVAILABLE_QTY) "
	                   + "VALUES (?, ?, ?, ?, ?, ?)";

	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        	stmt.setString(1, bookId);
                stmt.setString(2, bookName);
                stmt.setString(3, bookAuthor);
                stmt.setString(4, category);
                stmt.setInt(5, publishYear);
                stmt.setInt(6, availableQty);

	            int rowsInserted = stmt.executeUpdate();
	            if (rowsInserted > 0) {
	                JOptionPane.showMessageDialog(this, "Book added successfully!", "Success", 
	                    JOptionPane.INFORMATION_MESSAGE);
	                clearForm();
	            }
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, 
	            "Database Error: " + e.getMessage(), 
	            "Error", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, 
	            "Invalid input: " + e.getMessage(), 
	            "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	// NEW: Helper method to clear form
	private void clearForm() {
		bookIdField.setText("");
        bookNameField.setText("");
        bookAuthorField.setText("");
        Category.setSelectedItem(null);
        publishYearSpinner.setValue(2025);
        availableQtySpinner.setValue(1);
	}
	
	
	public AddBookWindow(String user, String pass) {
		setResizable(false);
		
		this.connection = LibraryApp.getConnection();
		
		setTitle("Book Registering Window");
        setSize(763, 772);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 102));
        panel.setBounds(-76, 0, 999, 68);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel topLabel = new JLabel("BOOK REGISTERING");
        topLabel.setForeground(new Color(255, 255, 255));
        topLabel.setBounds(321, 22, 257, 35);
        topLabel.setFont(new Font("Jost", Font.BOLD, 24));
        panel.add(topLabel);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(145, 149, 153));
        panel_1.setBounds(0, 0, 1174, 973);
        getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        JLabel bookIdLabel = new JLabel("BOOK ID");
        bookIdLabel.setForeground(Color.WHITE);
        bookIdLabel.setFont(new Font("Jost", Font.BOLD, 24));
        bookIdLabel.setBounds(30, 94, 216, 35);
        panel_1.add(bookIdLabel);
        
        bookIdField = new JTextField();
        bookIdField.setFont(new Font("Jost", Font.PLAIN, 22));
        bookIdField.setBounds(30, 140, 269, 43);
        panel_1.add(bookIdField);
        /*
        bookIdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) { 
                    e.consume(); // Ignore non-digit input
                }
            }
        });
        */
        bookIdField.setColumns(10);
        
        JLabel bookNameLabel = new JLabel("BOOK NAME");
        bookNameLabel.setForeground(Color.WHITE);
        bookNameLabel.setFont(new Font("Jost", Font.BOLD, 24));
        bookNameLabel.setBounds(30, 205, 216, 35);
        panel_1.add(bookNameLabel);
        
        bookNameField = new JTextField();
        bookNameField.setFont(new Font("Jost", Font.PLAIN, 22));
        bookNameField.setColumns(10);
        bookNameField.setBounds(30, 252, 269, 43);
        panel_1.add(bookNameField);
        
        JLabel authorNameLabel = new JLabel("AUTHOR NAME");
        authorNameLabel.setForeground(Color.WHITE);
        authorNameLabel.setFont(new Font("Jost", Font.BOLD, 24));
        authorNameLabel.setBounds(30, 317, 269, 35);
        panel_1.add(authorNameLabel);
        
        bookAuthorField = new JTextField();
        bookAuthorField.setFont(new Font("Dialog", Font.PLAIN, 22));
        bookAuthorField.setColumns(10);
        bookAuthorField.setBounds(30, 367, 269, 43);
        panel_1.add(bookAuthorField);
        
        String[] categories = {"MATHS", "PHYSIQUE", "ARCHI", "MDF"};
        
        JLabel categoryLabel = new JLabel("CATEGORY");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        categoryLabel.setBounds(30, 432, 269, 35);
        panel_1.add(categoryLabel);
        
        Category = new JComboBox<>(categories);
        Category.setFont(new Font("Dialog", Font.PLAIN, 22));
        Category.setBounds(30, 478, 200, 43);
        panel_1.add(Category);
        
        JLabel availableQtyLabel = new JLabel("AVAILABLE QUANTITY");
        availableQtyLabel.setForeground(Color.WHITE);
        availableQtyLabel.setFont(new Font("Jost", Font.BOLD, 24));
        availableQtyLabel.setBounds(367, 432, 308, 35);
        panel_1.add(availableQtyLabel);
        
        availableQtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        availableQtySpinner.setBounds(367, 478, 100, 30);
        panel_1.add(availableQtySpinner);
        
        JLabel publishYearLabel = new JLabel("PUBLISH YEAR");
        publishYearLabel.setForeground(Color.WHITE);
        publishYearLabel.setFont(new Font("Jost", Font.BOLD, 24));
        publishYearLabel.setBounds(30, 545, 229, 35);
        panel_1.add(publishYearLabel);
        
        publishYearSpinner = new JSpinner(new SpinnerNumberModel(2025, 1, 9999, 1));
        publishYearSpinner.setBounds(30, 591, 100, 30);
        panel_1.add(publishYearSpinner);
        
        JButton submitButton = new JButton("Register Book");
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(56, 194, 56));
        submitButton.setFont(new Font("Jost", Font.BOLD, 18));
        submitButton.setBounds(217, 667, 181, 40);
        submitButton.addActionListener(e -> insertBookData());
        panel_1.add(submitButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		dispose();
        	}
        });
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Jost", Font.BOLD, 18));
        cancelButton.setBackground(new Color(128, 0, 0));
        cancelButton.setBounds(410, 667, 123, 40);
        panel_1.add(cancelButton);
        
        setVisible(true);
		
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddBookWindow("user", "pass"));
    }
}