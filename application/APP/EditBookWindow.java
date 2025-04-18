package APP;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
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

public class EditBookWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JTextField bookIdField;
    private JTextField bookNameField;
    private JTextField bookAuthorField;
    private JComboBox<String> categoryComboBox;
    private JSpinner publishYearSpinner;
    private JSpinner availableQtySpinner;
    private JButton searchButton;
    private JButton updateButton;
    private JButton cancelButton;
    
    private Connection connection;
    
    public EditBookWindow() {
        setResizable(false);
        
        this.connection = LibraryApp.getConnection();
        
        setTitle("Book Editing Window");
        setSize(763, 772);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        setupUI();
        
        setVisible(true);
    }
    
    private void setupUI() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 102));
        topPanel.setBounds(-76, 0, 999, 68);
        getContentPane().add(topPanel);
        topPanel.setLayout(null);
        
        JLabel topLabel = new JLabel("BOOK EDITING");
        topLabel.setForeground(new Color(255, 255, 255));
        topLabel.setBounds(321, 22, 257, 35);
        topLabel.setFont(new Font("Jost", Font.BOLD, 24));
        topPanel.add(topLabel);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(145, 149, 153));
        mainPanel.setBounds(0, 0, 1174, 973);
        getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
        
        // Book ID section
        JLabel bookIdLabel = new JLabel("BOOK ID");
        bookIdLabel.setForeground(Color.WHITE);
        bookIdLabel.setFont(new Font("Jost", Font.BOLD, 24));
        bookIdLabel.setBounds(30, 94, 216, 35);
        mainPanel.add(bookIdLabel);
        
        bookIdField = new JTextField();
        bookIdField.setFont(new Font("Jost", Font.PLAIN, 22));
        bookIdField.setBounds(30, 140, 269, 43);
        bookIdField.addActionListener(_ -> searchBook());
        mainPanel.add(bookIdField);
        bookIdField.setColumns(10);
        
        // Search button
        searchButton = new JButton("");
        searchButton.setIcon(new ImageIcon("src\\resrc\\find_11916806.png"));
        searchButton.setBounds(309, 140, 50, 43);
        searchButton.setFocusPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setBorderPainted(false);
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchBook();
            }
        });
        mainPanel.add(searchButton);
        
        // Book Name section
        JLabel bookNameLabel = new JLabel("BOOK NAME");
        bookNameLabel.setForeground(Color.WHITE);
        bookNameLabel.setFont(new Font("Jost", Font.BOLD, 24));
        bookNameLabel.setBounds(30, 205, 216, 35);
        mainPanel.add(bookNameLabel);
        
        bookNameField = new JTextField();
        bookNameField.setFont(new Font("Jost", Font.PLAIN, 22));
        bookNameField.setColumns(10);
        bookNameField.setBounds(30, 252, 269, 43);
        bookNameField.setEditable(false);
        mainPanel.add(bookNameField);
        
        // Author Name section
        JLabel authorNameLabel = new JLabel("AUTHOR NAME");
        authorNameLabel.setForeground(Color.WHITE);
        authorNameLabel.setFont(new Font("Jost", Font.BOLD, 24));
        authorNameLabel.setBounds(30, 317, 269, 35);
        mainPanel.add(authorNameLabel);
        
        bookAuthorField = new JTextField();
        bookAuthorField.setFont(new Font("Dialog", Font.PLAIN, 22));
        bookAuthorField.setColumns(10);
        bookAuthorField.setBounds(30, 367, 269, 43);
        bookAuthorField.setEditable(false);
        mainPanel.add(bookAuthorField);
        
        // Category section
        String[] categories = {"MATHS", "PHYSIQUE", "ARCHI", "MDF"};
        
        JLabel categoryLabel = new JLabel("CATEGORY");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        categoryLabel.setBounds(30, 432, 269, 35);
        mainPanel.add(categoryLabel);
        
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setFont(new Font("Dialog", Font.PLAIN, 22));
        categoryComboBox.setBounds(30, 478, 200, 43);
        categoryComboBox.setEnabled(false);
        mainPanel.add(categoryComboBox);
        
        // Available Quantity section
        JLabel availableQtyLabel = new JLabel("AVAILABLE QUANTITY");
        availableQtyLabel.setForeground(Color.WHITE);
        availableQtyLabel.setFont(new Font("Jost", Font.BOLD, 24));
        availableQtyLabel.setBounds(367, 432, 308, 35);
        mainPanel.add(availableQtyLabel);
        
        availableQtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        availableQtySpinner.setBounds(367, 478, 100, 30);
        availableQtySpinner.setEnabled(false);
        mainPanel.add(availableQtySpinner);
        
        // Publish Year section
        JLabel publishYearLabel = new JLabel("PUBLISH YEAR");
        publishYearLabel.setForeground(Color.WHITE);
        publishYearLabel.setFont(new Font("Jost", Font.BOLD, 24));
        publishYearLabel.setBounds(30, 545, 229, 35);
        mainPanel.add(publishYearLabel);
        
        publishYearSpinner = new JSpinner(new SpinnerNumberModel(2025, 1, 9999, 1));
        publishYearSpinner.setBounds(30, 591, 100, 30);
        publishYearSpinner.setEnabled(false);
        mainPanel.add(publishYearSpinner);
        
        // Update button
        updateButton = new JButton("Update Book");
        updateButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(56, 194, 56));
        updateButton.setFont(new Font("Jost", Font.BOLD, 18));
        updateButton.setBounds(217, 667, 181, 40);
        updateButton.addActionListener(_ -> updateBookData());
        updateButton.setEnabled(false);
        mainPanel.add(updateButton);
        
        // Cancel button
        cancelButton = new JButton("Cancel");
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
        mainPanel.add(cancelButton);
    }
    
    private void searchBook() {
        String bookId = bookIdField.getText().trim();
        
        // Validation
        if (bookId.isEmpty()) {
            showError("Please enter a Book ID");
            return;
        }
        
        if (!bookId.matches("BK\\d{4}")) {
            showError("Book ID must be in the format BKxxxx (e.g., BK1234)");
            return;
        }
        
        try {
            String sql = "SELECT BOOKNAME, BOOKAUTHOR, CATEGORIE, PUBLISH_YEAR, AVAILABLE_QTY FROM bookslist WHERE BOOKID = ?";
            
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, bookId);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    // Populate fields with book data
                    bookNameField.setText(rs.getString("BOOKNAME"));
                    bookAuthorField.setText(rs.getString("BOOKAUTHOR"));
                    categoryComboBox.setSelectedItem(rs.getString("CATEGORIE"));
                    publishYearSpinner.setValue(rs.getInt("PUBLISH_YEAR"));
                    availableQtySpinner.setValue(rs.getInt("AVAILABLE_QTY"));
                    
                    // Enable editing
                    setFieldsEditable(true);
                    updateButton.setEnabled(true);
                } else {
                    showError("No book found with ID: " + bookId);
                    clearForm();
                }
            }
        } catch (SQLException e) {
            showError("Database Error: " + e.getMessage());
        }
    }
    
    private void updateBookData() {
        try {
            String bookId = bookIdField.getText().trim();
            String bookName = bookNameField.getText().trim();
            String bookAuthor = bookAuthorField.getText().trim();
            String category = categoryComboBox.getSelectedItem().toString().trim();
            int publishYear = (Integer) publishYearSpinner.getValue();
            int availableQty = (Integer) availableQtySpinner.getValue();
            
            // Validation
            if (bookName.isEmpty() || bookAuthor.isEmpty() || category.isEmpty()) {
                showError("Please fill all fields");
                return;
            }
            
            // Database update
            String sql = "UPDATE bookslist SET BOOKNAME = ?, BOOKAUTHOR = ?, CATEGORIE = ?, " +
                         "PUBLISH_YEAR = ?, AVAILABLE_QTY = ? WHERE BOOKID = ?";
            
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, bookName);
                stmt.setString(2, bookAuthor);
                stmt.setString(3, category);
                stmt.setInt(4, publishYear);
                stmt.setInt(5, availableQty);
                stmt.setString(6, bookId);
                
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Book updated successfully!", "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                } else {
                    showError("Failed to update book. Please try again.");
                }
            }
        } catch (SQLException e) {
            showError("Database Error: " + e.getMessage());
        } catch (Exception e) {
            showError("Invalid input: " + e.getMessage());
        }
    }
    
    private void setFieldsEditable(boolean editable) {
        bookNameField.setEditable(editable);
        bookAuthorField.setEditable(editable);
        categoryComboBox.setEnabled(editable);
        publishYearSpinner.setEnabled(editable);
        availableQtySpinner.setEnabled(editable);
    }
    
    private void clearForm() {
        bookIdField.setText("");
        bookNameField.setText("");
        bookAuthorField.setText("");
        categoryComboBox.setSelectedIndex(0);
        publishYearSpinner.setValue(2025);
        availableQtySpinner.setValue(1);
        setFieldsEditable(false);
        updateButton.setEnabled(false);
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EditBookWindow());
    }
}