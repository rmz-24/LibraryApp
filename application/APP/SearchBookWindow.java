package APP;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SearchBookWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JTextField searchTextField;
    private JComboBox<String> searchCriteriaComboBox;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private JButton searchButton;
    private JButton clearButton;
    private JButton closeButton;
    
    private Connection connection;
    
    public SearchBookWindow(ThemeToggleButton tg) {
        setResizable(false);
        
        this.connection = LibraryApp.getConnection();
        
        setTitle("Book Search Window");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        setupUI(tg);
        setVisible(true);
    }
    
    private void setupUI(ThemeToggleButton tg) {
        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 102));
        topPanel.setBounds(0, 0, 850, 68);
        getContentPane().add(topPanel);
        topPanel.setLayout(null);
        
        JLabel topLabel = new JLabel("BOOK SEARCH");
        topLabel.setForeground(new Color(255, 255, 255));
        topLabel.setBounds(350, 22, 257, 35);
        topLabel.setFont(new Font("Jost", Font.BOLD, 24));
        topPanel.add(topLabel);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(145, 149, 153));
        mainPanel.setBounds(0, 0, 850, 600);
        getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
        if(tg.isSelected()) {
        	mainPanel.setBackground(new Color(60, 63, 65)); // Light grayColor(60, 63, 65)

	  	}else {
	  		mainPanel.setBackground(new Color(182, 182, 182));
	  		
	  	}
        // Search criteria selector
        JLabel criteriaLabel = new JLabel("SEARCH BY:");
        criteriaLabel.setForeground(Color.WHITE);
        criteriaLabel.setFont(new Font("Jost", Font.BOLD, 18));
        criteriaLabel.setBounds(30, 90, 120, 30);
        mainPanel.add(criteriaLabel);
        
        searchCriteriaComboBox = new JComboBox<>();
        searchCriteriaComboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Book ID", "Book Name", "Author", "Category"}));
        searchCriteriaComboBox.setFont(new Font("Jost", Font.PLAIN, 16));
        searchCriteriaComboBox.setBounds(150, 90, 150, 30);
        mainPanel.add(searchCriteriaComboBox);
        
        // Search text field
        JLabel searchTextLabel = new JLabel("SEARCH TEXT:");
        searchTextLabel.setForeground(Color.WHITE);
        searchTextLabel.setFont(new Font("Jost", Font.BOLD, 18));
        searchTextLabel.setBounds(320, 90, 140, 30);
        mainPanel.add(searchTextLabel);
        
        searchTextField = new JTextField();
        searchTextField.setFont(new Font("Jost", Font.PLAIN, 16));
        searchTextField.setBounds(460, 90, 230, 30);
        searchTextField.addActionListener(e -> performSearch());
        mainPanel.add(searchTextField);
        
        // Search button
        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(0, 102, 102));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Jost", Font.BOLD, 16));
        searchButton.setBounds(700, 90, 100, 30);
        searchButton.addActionListener(e -> performSearch());
        mainPanel.add(searchButton);
        
        // Results table
        String[] columnNames = {"Book ID", "Book Name", "Author", "Category", "Publish Year", "Available Qty"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells read-only
            }
        };
        
        resultsTable = new JTable(tableModel);
        resultsTable.setFont(new Font("Jost", Font.PLAIN, 14));
        resultsTable.getTableHeader().setFont(new Font("Jost", Font.BOLD, 14));
        resultsTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBounds(30, 140, 770, 350);
        mainPanel.add(scrollPane);
        
        // Clear and Close buttons
        clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(128, 128, 128));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Jost", Font.BOLD, 16));
        clearButton.setBounds(300, 510, 100, 35);
        clearButton.addActionListener(e -> clearSearch());
        mainPanel.add(clearButton);
        
        closeButton = new JButton("Close");
        closeButton.setBackground(new Color(128, 0, 0));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Jost", Font.BOLD, 16));
        closeButton.setBounds(430, 510, 100, 35);
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        mainPanel.add(closeButton);
    }
    
    private void performSearch() {
        String searchText = searchTextField.getText().trim();
        if (searchText.isEmpty()) {
            showError("Please enter search text");
            return;
        }
        
        // Clear previous results
        clearTableData();
        
        String searchCriteria = searchCriteriaComboBox.getSelectedItem().toString();
        String sqlColumn;
        
        switch (searchCriteria) {
            case "Book ID":
                sqlColumn = "BOOKID";
                break;
            case "Book Name":
                sqlColumn = "BOOKNAME";
                break;
            case "Author":
                sqlColumn = "BOOKAUTHOR";
                break;
            case "Category":
                sqlColumn = "CATEGORIE";
                break;
            default:
                sqlColumn = "BOOKNAME";
        }
        
        try {
            String sql = "SELECT BOOKID, BOOKNAME, BOOKAUTHOR, CATEGORIE, PUBLISH_YEAR, AVAILABLE_QTY " +
                         "FROM bookslist WHERE UPPER(" + sqlColumn + ") LIKE UPPER(?)";
            
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, "%" + searchText + "%");
                ResultSet rs = stmt.executeQuery();
                
                boolean hasResults = false;
                
                while (rs.next()) {
                    hasResults = true;
                    String bookId = rs.getString("BOOKID");
                    String bookName = rs.getString("BOOKNAME");
                    String bookAuthor = rs.getString("BOOKAUTHOR");
                    String category = rs.getString("CATEGORIE");
                    int publishYear = rs.getInt("PUBLISH_YEAR");
                    int availableQty = rs.getInt("AVAILABLE_QTY");
                    
                    tableModel.addRow(new Object[]{bookId, bookName, bookAuthor, category, publishYear, availableQty});
                }
                
                if (!hasResults) {
                    JOptionPane.showMessageDialog(this, 
                            "No books found matching your search criteria.", 
                            "No Results", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            showError("Database Error: " + e.getMessage());
        }
    }
    
    private void clearSearch() {
        searchTextField.setText("");
        searchCriteriaComboBox.setSelectedIndex(0);
        clearTableData();
    }
    
    private void clearTableData() {
        tableModel.setRowCount(0);
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new SearchBookWindow());
//    }
}