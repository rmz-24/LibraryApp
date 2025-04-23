package APP;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RemoveBookWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField bookIdField;
    private JTextArea bookName, bookAuthor, availableQty;
    private Connection connection;
    private JButton removeBookButton;
    private JButton searchButton;

    public RemoveBookWindow(ThemeToggleButton tg) {
    	setResizable(false);
        // Initialize connection once
        this.connection = LibraryApp.getConnection();
        
        setTitle("Book Removing Window");
        setSize(638, 415);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        setupUI(tg);
        setVisible(true);
    }

    private void setupUI(ThemeToggleButton tg) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 102));
        panel.setBounds(-52, 0, 755, 66);
        getContentPane().add(panel);
        panel.setLayout(null);
        JPanel panelbg = new JPanel();
        panelbg.setLayout(null);
        setContentPane(panelbg);
        if(tg.isSelected()) {
        	panelbg.setBackground(new Color(60, 63, 65)); // Light grayColor(60, 63, 65)

	  	}else {
	  		panelbg.setBackground(new Color(182, 182, 182));
	  		
	  	}
        
        JLabel topLabel = new JLabel("BOOK DELETION");
        topLabel.setForeground(new Color(255, 255, 255));
        topLabel.setFont(new Font("Jost", Font.BOLD, 19));
        topLabel.setBounds(280, 24, 196, 31);
        panel.add(topLabel);
        
        JLabel searchBookIdLabel = new JLabel("ENTER BOOK ID");
        searchBookIdLabel.setForeground(new Color(0, 0, 0));
        searchBookIdLabel.setFont(new Font("Jost", Font.BOLD, 19));
        searchBookIdLabel.setBounds(10, 106, 196, 31);
        getContentPane().add(searchBookIdLabel);
        
        bookIdField = new JTextField(16);
        bookIdField.setFont(new Font("Jost", Font.PLAIN, 22));
        bookIdField.setBounds(10, 141, 204, 43);
        bookIdField.addActionListener(e -> searchBook()); // Enable Enter key search
        getContentPane().add(bookIdField);
        
        // Search Button
        searchButton = new JButton("");
        searchButton.setIcon(new ImageIcon("src\\resrc\\find_11916806.png"));
        searchButton.setBounds(160, 195, 50, 50);
        searchButton.setFocusPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setBorderPainted(false);
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchBook();
            }
        });
        getContentPane().add(searchButton);

        // Delete Button
        removeBookButton = new JButton("DELETE");
        removeBookButton.setBackground(new Color(128, 0, 0));
        removeBookButton.setForeground(new Color(255, 255, 255));
        removeBookButton.setFont(new Font("Jost", Font.BOLD, 24));
        removeBookButton.setBounds(10, 195, 140, 50);
        removeBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                confirmAndDelete();
            }
        });
        getContentPane().add(removeBookButton);
        
        // Book Info Display
        bookName = new JTextArea();
        bookName.setFont(new Font("Jost", Font.BOLD, 20));
        bookName.setBounds(407, 141, 184, 31);
        bookName.setEditable(false);
        getContentPane().add(bookName);
        
        JLabel selectedBookNameLabel = new JLabel("BOOK NAME");
        selectedBookNameLabel.setForeground(Color.BLACK);
        selectedBookNameLabel.setFont(new Font("Jost", Font.BOLD, 19));
        selectedBookNameLabel.setBounds(407, 106, 196, 31);
        getContentPane().add(selectedBookNameLabel);
        
        bookAuthor = new JTextArea();
        bookAuthor.setFont(new Font("Jost", Font.BOLD, 20));
        bookAuthor.setBounds(407, 230, 184, 31);
        bookAuthor.setEditable(false);
        getContentPane().add(bookAuthor);
        
        JLabel selectedBookAuthorLabel = new JLabel("BOOK AUTHOR");
        selectedBookAuthorLabel.setForeground(Color.BLACK);
        selectedBookAuthorLabel.setFont(new Font("Jost", Font.BOLD, 19));
        selectedBookAuthorLabel.setBounds(407, 195, 196, 31);
        getContentPane().add(selectedBookAuthorLabel);
        
        availableQty = new JTextArea();
        availableQty.setFont(new Font("Jost", Font.BOLD, 20));
        availableQty.setBounds(407, 311, 184, 31);
        availableQty.setEditable(false);
        getContentPane().add(availableQty);
        
        JLabel selectedBookAvailableQtyLabel = new JLabel("AVAILABLE QUANTITY");
        selectedBookAvailableQtyLabel.setForeground(Color.BLACK);
        selectedBookAvailableQtyLabel.setFont(new Font("Jost", Font.BOLD, 19));
        selectedBookAvailableQtyLabel.setBounds(407, 276, 196, 31);
        getContentPane().add(selectedBookAvailableQtyLabel);
    }

    private void searchBook() {
        String bookId = bookIdField.getText().trim().toUpperCase(); // Ensure uppercase format

        // Input validation
        if (bookId.isEmpty()) {
            showError("Please enter a Book ID");
            return;
        }

        if (!bookId.matches("BK\\d{5}")) {  // Ensures "BK" followed by exactly 5 digits
            showError("Book ID must be in the format BKxxxxx (e.g., BK12345)");
            return;
        }
        

        setUIState(false); // Disable UI during operation

        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT BOOKNAME, BOOKAUTHOR, AVAILABLE_QTY FROM bookslist WHERE BOOKID = ?")) {

            stmt.setString(1, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                bookName.setText(rs.getString("BOOKNAME"));
                bookAuthor.setText(rs.getString("BOOKAUTHOR"));
                availableQty.setText(String.valueOf(rs.getInt("AVAILABLE_QTY")));
            } else {
                showInfo("No book found with ID: " + bookId);
                resetForm();
            }
        } catch (Exception ex) {
            showError("Database Error: " + ex.getMessage());
        } finally {
            setUIState(true); // Re-enable UI
        }
    }


    private void confirmAndDelete() {
        String bookId = bookIdField.getText().trim();
        if (bookId.isEmpty()) {
            showError("Please search for a book first");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Delete book: " + bookName.getText() + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;

        setUIState(false); // Disable UI during operation
        removeBookButton.setBackground(Color.GRAY); // Visual feedback
        
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM bookslist WHERE BOOKID = ?")) {
            
            stmt.setString(1, bookId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                showSuccess("Book deleted successfully!");
                resetForm();
            } else {
                showError("Book could not be deleted");
            }
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        } finally {
            setUIState(true); // Re-enable UI
            removeBookButton.setBackground(new Color(128, 0, 0)); // Restore color
        }
    }

    private void resetForm() {
        bookIdField.setText("");
        bookName.setText("");
        bookAuthor.setText("");
        availableQty.setText("");
        bookIdField.requestFocus();
    }

    private void setUIState(boolean enabled) {
        bookIdField.setEnabled(enabled);
        searchButton.setEnabled(enabled);
        removeBookButton.setEnabled(enabled);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

//    public static void main(String[] args) {
//        new RemoveBookWindow();
//    }
}