package APP;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import com.toedter.calendar.JDateChooser;

public class ReturnRegisteringWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JTextField loanIdField;
    private JTextField studentIdField;
    private JTextField bookIdField;
    private JTextField loanDateField;
    private JTextField dueDateField;
    private JDateChooser returnDateChooser;
    private JButton searchButton;
    private JButton registerReturnButton;
    private JButton cancelButton;
    private JLabel statusLabel;
    
    private Connection connection;
    private String currentUser;
    private String accessLevel;
    
    public ReturnRegisteringWindow(String user, String level,ThemeToggleButton togglebutton) {
        setResizable(false);
        
        this.connection = LibraryApp.getConnection();
        this.currentUser = user;
        this.accessLevel = level;
        
        setIconImage(Toolkit.getDefaultToolkit().getImage("src\\resrc\\LMsmall.png"));
        setTitle("Register Book Return");
        setSize(763, 772);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        setupUI(togglebutton);
        
        setVisible(true);
    }
    
    private void setupUI(ThemeToggleButton togglebutton) {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 102));
        topPanel.setBounds(-76, 0, 999, 68);
        getContentPane().add(topPanel);
        topPanel.setLayout(null);
        
        JLabel topLabel = new JLabel("REGISTER BOOK RETURN");
        topLabel.setForeground(new Color(255, 255, 255));
        topLabel.setBounds(321, 22, 350, 35);
        topLabel.setFont(new Font("Jost", Font.BOLD, 24));
        topPanel.add(topLabel);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(145, 149, 153));
        mainPanel.setBounds(0, 0, 1174, 973);
        getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
        
        // Loan ID section
        JLabel loanIdLabel = new JLabel("LOAN ID");
        loanIdLabel.setForeground(Color.WHITE);
        loanIdLabel.setFont(new Font("Jost", Font.BOLD, 24));
        loanIdLabel.setBounds(30, 94, 216, 35);
        mainPanel.add(loanIdLabel);
        
        loanIdField = new JTextField();
        loanIdField.setFont(new Font("Jost", Font.PLAIN, 22));
        loanIdField.setBounds(30, 140, 269, 43);
        loanIdField.addActionListener(e -> searchLoan());
        mainPanel.add(loanIdField);
        loanIdField.setColumns(10);
        
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
                searchLoan();
            }
        });
        mainPanel.add(searchButton);
        
        // Student ID section
        JLabel studentIdLabel = new JLabel("STUDENT ID");
        studentIdLabel.setForeground(Color.WHITE);
        studentIdLabel.setFont(new Font("Jost", Font.BOLD, 24));
        studentIdLabel.setBounds(30, 205, 216, 35);
        mainPanel.add(studentIdLabel);
        
        studentIdField = new JTextField();
        studentIdField.setFont(new Font("Jost", Font.PLAIN, 22));
        studentIdField.setColumns(10);
        studentIdField.setBounds(30, 252, 269, 43);
        studentIdField.setEditable(false);
        mainPanel.add(studentIdField);
        
        // Book ID section
        JLabel bookIdLabel = new JLabel("BOOK ID");
        bookIdLabel.setForeground(Color.WHITE);
        bookIdLabel.setFont(new Font("Jost", Font.BOLD, 24));
        bookIdLabel.setBounds(30, 317, 269, 35);
        mainPanel.add(bookIdLabel);
        
        bookIdField = new JTextField();
        bookIdField.setFont(new Font("Dialog", Font.PLAIN, 22));
        bookIdField.setColumns(10);
        bookIdField.setBounds(30, 367, 269, 43);
        bookIdField.setEditable(false);
        mainPanel.add(bookIdField);
        
        // Loan Date section
        JLabel loanDateLabel = new JLabel("LOAN DATE");
        loanDateLabel.setForeground(Color.WHITE);
        loanDateLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        loanDateLabel.setBounds(30, 432, 269, 35);
        mainPanel.add(loanDateLabel);
        
        loanDateField = new JTextField();
        loanDateField.setFont(new Font("Dialog", Font.PLAIN, 22));
        loanDateField.setColumns(10);
        loanDateField.setBounds(30, 478, 269, 43);
        loanDateField.setEditable(false);
        mainPanel.add(loanDateField);
        
        // Due Date section
        JLabel dueDateLabel = new JLabel("DUE DATE");
        dueDateLabel.setForeground(Color.WHITE);
        dueDateLabel.setFont(new Font("Jost", Font.BOLD, 24));
        dueDateLabel.setBounds(367, 432, 308, 35);
        mainPanel.add(dueDateLabel);
        
        dueDateField = new JTextField();
        dueDateField.setFont(new Font("Dialog", Font.PLAIN, 22));
        dueDateField.setColumns(10);
        dueDateField.setBounds(367, 478, 269, 43);
        dueDateField.setEditable(false);
        mainPanel.add(dueDateField);
        
        // Status Label
        statusLabel = new JLabel("");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Jost", Font.BOLD, 20));
        statusLabel.setBounds(367, 367, 300, 35);
        mainPanel.add(statusLabel);
        
        // Return Date section
        JLabel returnDateLabel = new JLabel("RETURN DATE");
        returnDateLabel.setForeground(Color.WHITE);
        returnDateLabel.setFont(new Font("Jost", Font.BOLD, 24));
        returnDateLabel.setBounds(30, 545, 229, 35);
        mainPanel.add(returnDateLabel);
        
        returnDateChooser = new JDateChooser();
        returnDateChooser.setFont(new Font("Jost", Font.PLAIN, 22));
        returnDateChooser.setBounds(30, 591, 269, 43);
        returnDateChooser.setDate(new Date()); // Default to today
        returnDateChooser.setEnabled(false);
        mainPanel.add(returnDateChooser);
        
        // Register Return button
        registerReturnButton = new JButton("Register Return");
        registerReturnButton.setForeground(Color.WHITE);
        registerReturnButton.setBackground(new Color(56, 194, 56));
        registerReturnButton.setFont(new Font("Jost", Font.BOLD, 18));
        registerReturnButton.setBounds(217, 667, 181, 40);
        registerReturnButton.addActionListener(e -> registerReturn());
        registerReturnButton.setEnabled(false);
        mainPanel.add(registerReturnButton);
        
        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoanManagementDashboard(currentUser, accessLevel ,togglebutton);
                dispose();
            }
        });
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Jost", Font.BOLD, 18));
        cancelButton.setBackground(new Color(128, 0, 0));
        cancelButton.setBounds(410, 667, 123, 40);
        mainPanel.add(cancelButton);
        if(togglebutton.isSelected()) {
        	mainPanel.setBackground(new Color(60, 63, 65)); // Light grayColor(60, 63, 65)

	  	}else {
	  		mainPanel.setBackground(new Color(182, 182, 182));
	  		
	  	}
    }
    
    private void searchLoan() {
        String loanId = loanIdField.getText().trim();
        
        // Validation
        if (loanId.isEmpty()) {
            showError("Please enter a Loan ID");
            return;
        }
        
        try {
            Integer.parseInt(loanId); // Basic validation - ensure it's a number
        } catch (NumberFormatException e) {
            showError("Loan ID must be a number");
            return;
        }
        
        try {
            String sql = "SELECT e.NUM_ETU, e.BOOK_CODE, e.DATE_EMP, e.DATE_RET_ASSUM, e.STATUT " +
                         "FROM EMPRUNTS e WHERE e.NUM_EMP = ?";
            
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(loanId));
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    // Check if loan is already returned
                    int status = rs.getInt("STATUT");
                    if (status == 0 ) {
                        showError("This loan has already been returned.");
                        clearForm();
                        return;
                    }
                    if (status == 2 ) {
                        showError("Warning : Late Return !");
                        studentIdField.setText(rs.getString("NUM_ETU"));
                        bookIdField.setText(rs.getString("BOOK_CODE"));
                        
                        // Format dates
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date loanDate = rs.getDate("DATE_EMP");
                        Date dueDate = rs.getDate("DATE_RET_ASSUM");
                        
                        loanDateField.setText(dateFormat.format(loanDate));
                        dueDateField.setText(dateFormat.format(dueDate));
                        
                        // Set status label
                        statusLabel.setText("STATUS: LATE ");
                        statusLabel.setForeground(new Color(255, 0, 0));
                        
                        // Enable return date and button
                        returnDateChooser.setEnabled(true);
                        registerReturnButton.setEnabled(true);
                    }
                    if (status == 1 ) {
                    // Populate fields with loan data
                    studentIdField.setText(rs.getString("NUM_ETU"));
                    bookIdField.setText(rs.getString("BOOK_CODE"));
                    
                    // Format dates
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date loanDate = rs.getDate("DATE_EMP");
                    Date dueDate = rs.getDate("DATE_RET_ASSUM");
                    
                    loanDateField.setText(dateFormat.format(loanDate));
                    dueDateField.setText(dateFormat.format(dueDate));
                    
                    // Set status label
                    statusLabel.setText("STATUS: ACTIVE LOAN");
                    statusLabel.setForeground(new Color(0, 128, 255));
                    
                    // Enable return date and button
                    returnDateChooser.setEnabled(true);
                    registerReturnButton.setEnabled(true);
                    }
                } else {
                    showError("No active loan found with ID: " + loanId);
                    clearForm();
                }
            }
        } catch (SQLException e) {
            showError("Database Error: " + e.getMessage());
        }
    }
    
    private void registerReturn() {
        try {
            int loanId = Integer.parseInt(loanIdField.getText().trim());
            String bookId = bookIdField.getText();
            Date returnDate = returnDateChooser.getDate();
            Date dueDate = null;
            
            // Validate return date
            if (returnDate == null) {
                showError("Please select a return date");
                return;
            }
            
            // Parse due date
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dueDate = dateFormat.parse(dueDateField.getText());
            } catch (Exception e) {
                showError("Error parsing due date");
                return;
            }
            
            // Determine status based on return date vs due date
            int newStatus;
            if (returnDate.after(dueDate)) {
                newStatus = 2; // Late return
            } else {
                newStatus = 0; // On time return
            }
            
            // Update loan record
            String updateLoanSql = "UPDATE EMPRUNTS SET DATE_RET_ACTU = ?, STATUT = ? WHERE NUM_EMP = ?";
            
            connection.setAutoCommit(false); // Start transaction
            
            try (PreparedStatement stmt = connection.prepareStatement(updateLoanSql)) {
                stmt.setDate(1, new java.sql.Date(returnDate.getTime()));
                stmt.setInt(2, newStatus);
                stmt.setInt(3, loanId);
                
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated <= 0) {
                    throw new SQLException("Failed to update loan record");
                }
                
                // Update book inventory (increase available quantity)
                String updateBookSql = "UPDATE BOOKSLIST SET AVAILABLE_QTY = AVAILABLE_QTY + 1 WHERE BOOKID = ?";
                try (PreparedStatement bookStmt = connection.prepareStatement(updateBookSql)) {
                    bookStmt.setString(1, bookId);
                    int bookRowsUpdated = bookStmt.executeUpdate();
                    
                    if (bookRowsUpdated <= 0) {
                        throw new SQLException("Failed to update book inventory");
                    }
                }
                
                // Commit transaction
                connection.commit();
                
                String statusMessage = newStatus == 2 ? "Book returned late!" : "Book returned on time!";
                JOptionPane.showMessageDialog(this, "Return registered successfully! " + statusMessage, 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                clearForm();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            showError("Database Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            showError("Invalid Loan ID format");
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }
    
    private void clearForm() {
        loanIdField.setText("");
        studentIdField.setText("");
        bookIdField.setText("");
        loanDateField.setText("");
        dueDateField.setText("");
        returnDateChooser.setDate(new Date());  // Reset to today
        returnDateChooser.setEnabled(false);
        registerReturnButton.setEnabled(false);
        statusLabel.setText("");
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new ReturnRegisteringWindow("admin", "admin"));
//    }
}