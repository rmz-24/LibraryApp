package APP;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JDateChooser;

public class AddBookWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField bookIdField;
	private JTextField bookNameField;
	private JTextField bookAuthorField;
	private JFormattedTextField bookPriceField;
	private JSpinner copiesSpinner;
	private JDateChooser dateChooser;
	
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
	
	
	// MODIFIED: Enhanced insert method with new component support
	private void insertBookData() {
	    try {
	        // Get values from components
	        String bookId = bookIdField.getText().trim();
	        String bookName = bookNameField.getText().trim();
	        String bookAuthor = bookAuthorField.getText().trim();
	        double price = ((Number)bookPriceField.getValue()).doubleValue();
	        int copies = (Integer)copiesSpinner.getValue();
	        java.util.Date selectedDate = dateChooser.getDate();

	        // Validation
	        if (bookId.isEmpty() || bookName.isEmpty() || bookAuthor.isEmpty() || selectedDate == null) {
	            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        // Database insertion
	        String sql = "INSERT INTO bookslist (BOOKID, BOOKNAME, BOOKAUTHOR, BOOKPRICE, COPIESNBR, BOOKREGISTERDATE) "
	                   + "VALUES (?, ?, ?, ?, ?, ?)";

	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setString(1, bookId);
	            stmt.setString(2, bookName);
	            stmt.setString(3, bookAuthor);
	            stmt.setBigDecimal(4, BigDecimal.valueOf(price));
	            stmt.setInt(5, copies);
	            stmt.setDate(6, new java.sql.Date(selectedDate.getTime()));

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
	    bookPriceField.setValue(0.00);
	    copiesSpinner.setValue(1);
	    dateChooser.setDate(null);
	}
	
	
	public AddBookWindow(String user, String pass) {
		
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
        
        JLabel bookNameLabel = new JLabel("BOOK NAME");
        bookNameLabel.setForeground(Color.WHITE);
        bookNameLabel.setFont(new Font("Jost", Font.BOLD, 24));
        bookNameLabel.setBounds(30, 205, 216, 35);
        panel_1.add(bookNameLabel);
        
        JLabel authorNameLabel = new JLabel("AUTHOR NAME");
        authorNameLabel.setForeground(Color.WHITE);
        authorNameLabel.setFont(new Font("Jost", Font.BOLD, 24));
        authorNameLabel.setBounds(30, 321, 269, 35);
        panel_1.add(authorNameLabel);
        
        JLabel bookPriceLabel = new JLabel("BOOK PRICE");
        bookPriceLabel.setForeground(Color.WHITE);
        bookPriceLabel.setFont(new Font("Jost", Font.BOLD, 24));
        bookPriceLabel.setBounds(30, 432, 216, 35);
        panel_1.add(bookPriceLabel);
        
        JLabel copiesNumberLabel = new JLabel("COPIES NUMBER ");
        copiesNumberLabel.setForeground(Color.WHITE);
        copiesNumberLabel.setFont(new Font("Jost", Font.BOLD, 24));
        copiesNumberLabel.setBounds(367, 432, 216, 35);
        panel_1.add(copiesNumberLabel);
        
        JLabel bookRegisteryDate1 = new JLabel("REGISTERY DATE ");
        bookRegisteryDate1.setForeground(Color.WHITE);
        bookRegisteryDate1.setFont(new Font("Jost", Font.BOLD, 24));
        bookRegisteryDate1.setBounds(30, 545, 229, 35);
        panel_1.add(bookRegisteryDate1);
        
        bookIdField = new JTextField();
        bookIdField.setFont(new Font("Jost", Font.PLAIN, 22));
        bookIdField.setBounds(30, 140, 269, 43);
        panel_1.add(bookIdField);
        bookIdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) { 
                    e.consume(); // Ignore non-digit input
                }
            }
        });
        bookIdField.setColumns(10);
        
        bookNameField = new JTextField();
        bookNameField.setFont(new Font("Jost", Font.PLAIN, 22));
        bookNameField.setColumns(10);
        bookNameField.setBounds(30, 252, 269, 43);
        panel_1.add(bookNameField);
        
        bookAuthorField = new JTextField();
        bookAuthorField.setFont(new Font("Jost", Font.PLAIN, 22));
        bookAuthorField.setColumns(10);
        bookAuthorField.setBounds(30, 367, 269, 43);
        panel_1.add(bookAuthorField);
        
        
        NumberFormat priceFormat = NumberFormat.getNumberInstance();
        priceFormat.setMinimumFractionDigits(2);
        priceFormat.setMaximumFractionDigits(2);
        bookPriceField = new JFormattedTextField(priceFormat);
        bookPriceField.setBounds(30, 478, 269, 43);  // Adjust position
        bookPriceField.setFont(new Font("Jost", Font.PLAIN, 22));
        panel_1.add(bookPriceField);
      
        bookPriceField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == '.' || c == KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        
        
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(
            1,    // initial value
            1,    // minimum (per your CHECK constraint)
            99,   // maximum (NUMBER(2,0) limit)
            1     // step
        );
        copiesSpinner = new JSpinner(spinnerModel);
        copiesSpinner.setBounds(367, 478, 120, 43);  // Match your label position
        panel_1.add(copiesSpinner);

        // Style the spinner
        //JComponent editor = ((JSpinner.NumberEditor)copiesSpinner.getEditor());
        //editor.getTextField().setFont(new Font("Jost", Font.PLAIN, 22));
        
        dateChooser = new JDateChooser();
        dateChooser.setBounds(30, 591, 269, 43);
        dateChooser.setFont(new Font("Jost", Font.PLAIN, 22));
        panel_1.add(dateChooser);
        
        
        
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
