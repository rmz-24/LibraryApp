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
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

public class TrackLoansWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JTextField searchField;
    private JComboBox<String> filterComboBox;
    private JTable loansTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton backButton;
    
    private Connection connection;
    private String currentUser;
    private String accessLevel;
    
    public TrackLoansWindow(String user, String level,ThemeToggleButton tb) {
        setResizable(false);
        
        this.connection = LibraryApp.getConnection();
        this.currentUser = user;
        this.accessLevel = level;
        
        setIconImage(Toolkit.getDefaultToolkit().getImage("src\\resrc\\LMsmall.png"));
        setTitle("Track Loans");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        setupUI(tb);
        loadLoansData("ALL"); // Load all loans by default
        
        setVisible(true);
    }

        private void setupUI(ThemeToggleButton tb) {
            

        
      
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 102));
        topPanel.setBounds(0, 0, 1000, 68);
        getContentPane().add(topPanel);
        topPanel.setLayout(null);
        
        
        JLabel topLabel = new JLabel("TRACK LIBRARY LOANS");
        topLabel.setForeground(new Color(255, 255, 255));
        topLabel.setBounds(366, 22, 300, 35);
        topLabel.setFont(new Font("Jost", Font.BOLD, 24));
        topPanel.add(topLabel);
        
        JLabel iconLabel = new JLabel("");
        iconLabel.setBounds(10, 0, 101, 68);
        iconLabel.setIcon(new ImageIcon("src\\resrc\\LMsmall.png"));
        topPanel.add(iconLabel);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(145, 149, 153));
        mainPanel.setBounds(0, 0, 1000, 600);
        getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
        
        // Search field
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Jost", Font.BOLD, 16));
        searchLabel.setBounds(30, 80, 80, 25);
        mainPanel.add(searchLabel);
        
        searchField = new JTextField();
        searchField.setFont(new Font("Jost", Font.PLAIN, 14));
        searchField.setBounds(100, 80, 200, 30);
        mainPanel.add(searchField);
        
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Jost", Font.BOLD, 14));
        searchButton.setBounds(310, 80, 100, 30);
        searchButton.addActionListener(e -> performSearch());
        mainPanel.add(searchButton);
        
        // Filter combo box
        JLabel filterLabel = new JLabel("Filter by Status:");
        filterLabel.setForeground(Color.WHITE);
        filterLabel.setFont(new Font("Jost", Font.BOLD, 16));
        filterLabel.setBounds(430, 80, 120, 25);
        mainPanel.add(filterLabel);
        
        String[] filterOptions = {"ALL", "ACTIVE", "RETURNED", "LATE"};
        filterComboBox = new JComboBox<>(filterOptions);
        filterComboBox.setFont(new Font("Jost", Font.PLAIN, 14));
        filterComboBox.setBounds(560, 80, 150, 30);
        filterComboBox.addActionListener(e -> {
            String selectedFilter = (String) filterComboBox.getSelectedItem();
            loadLoansData(selectedFilter);
        });
        mainPanel.add(filterComboBox);
        
        // Refresh button
        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(0, 153, 0));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Jost", Font.BOLD, 14));
        refreshButton.setBounds(730, 80, 100, 30);
        refreshButton.addActionListener(e -> {
            String selectedFilter = (String) filterComboBox.getSelectedItem();
            loadLoansData(selectedFilter);
        });
        mainPanel.add(refreshButton);
        
        // Table setup
        String[] columnNames = {
            "Loan ID", "Student ID", "Book ID", "Loan Date", "Due Date", "Return Date", "Status"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        loansTable = new JTable(tableModel);
        loansTable.setFont(new Font("Jost", Font.PLAIN, 14));
        loansTable.getTableHeader().setFont(new Font("Jost", Font.BOLD, 14));
        loansTable.setRowHeight(25);
        loansTable.setAutoCreateRowSorter(true);
        
        JScrollPane scrollPane = new JScrollPane(loansTable);
        scrollPane.setBounds(30, 130, 940, 380);
        mainPanel.add(scrollPane);
        
        // Back button
        backButton = new JButton("Back to Dashboard");
        backButton.setBackground(new Color(128, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Jost", Font.BOLD, 14));
        backButton.setBounds(800, 520, 170, 35);
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoanManagementDashboard(currentUser, accessLevel,tb);
                dispose();
            }
        });
        mainPanel.add(backButton);
        //JPanel panel = new JPanel();
       // getContentPane().add(panel);
        //mainPanel.setLayout(null);
        
        ///mainPanel.setBounds(0, 0, 1322, 894);
        if(tb.isSelected()) {
        	mainPanel.setBackground(new Color(60, 63, 65)); // Light grayColor(60, 63, 65)

	  	}else {
	  		mainPanel.setBackground(new Color(182, 182, 182));
	  		
	  	}
    }
    
    private void loadLoansData(String filterType) {
        // Clear existing data
        tableModel.setRowCount(0);
        
        try {
            String sql;
            PreparedStatement stmt;
            
            switch (filterType) {
                case "ACTIVE":
                    sql = "SELECT * FROM EMPRUNTS WHERE STATUT = 1 ORDER BY DATE_RET_ASSUM";
                    stmt = connection.prepareStatement(sql);
                    break;
                case "RETURNED":
                    sql = "SELECT * FROM EMPRUNTS WHERE STATUT = 0 ORDER BY DATE_RET_ACTU DESC";
                    stmt = connection.prepareStatement(sql);
                    break;
                case "LATE":
                    sql = "SELECT * FROM EMPRUNTS WHERE STATUT = 2 ORDER BY DATE_RET_ACTU DESC";
                    stmt = connection.prepareStatement(sql);
                    break;
                default: // ALL
                    sql = "SELECT * FROM EMPRUNTS ORDER BY NUM_EMP DESC";
                    stmt = connection.prepareStatement(sql);
                    break;
            }
            
            ResultSet rs = stmt.executeQuery();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("NUM_EMP"));
                row.add(rs.getString("NUM_ETU"));
                row.add(rs.getString("BOOK_CODE"));
                
                // Format dates
                Date loanDate = rs.getDate("DATE_EMP");
                Date dueDate = rs.getDate("DATE_RET_ASSUM");
                Date returnDate = rs.getDate("DATE_RET_ACTU");
                
                row.add(loanDate != null ? dateFormat.format(loanDate) : "");
                row.add(dueDate != null ? dateFormat.format(dueDate) : "");
                row.add(returnDate != null ? dateFormat.format(returnDate) : "");
                
                // Convert status code to text
                int status = rs.getInt("STATUT");
                String statusText;
                switch (status) {
                    case 0:
                        statusText = "Returned On Time";
                        break;
                    case 1:
                        // Check if the loan is overdue (due date has passed)
                        if (dueDate != null && dueDate.before(new Date()) && returnDate == null) {
                            statusText = "Overdue";
                        } else {
                            statusText = "Active";
                        }
                        break;
                    case 2:
                        statusText = "Late";
                        break;
                    default:
                        statusText = "Unknown";
                }
                row.add(statusText);
                
                tableModel.addRow(row);
            }
            
            rs.close();
            stmt.close();
            
            // Auto-resize columns
            loansTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading loans data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performSearch() {
        String searchText = searchField.getText().trim().toLowerCase();
        
        if (searchText.isEmpty()) {
            // If search text is empty, reload all data based on current filter
            String selectedFilter = (String) filterComboBox.getSelectedItem();
            loadLoansData(selectedFilter);
            return;
        }
        
        try {
            // Search across multiple columns
            String sql = "SELECT * FROM EMPRUNTS WHERE " +
                    "LOWER(NUM_EMP) LIKE ? OR " +
                    "LOWER(NUM_ETU) LIKE ? OR " +
                    "LOWER(BOOK_CODE) LIKE ?";
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            String searchPattern = "%" + searchText + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            
            // Clear existing data
            tableModel.setRowCount(0);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("NUM_EMP"));
                row.add(rs.getString("NUM_ETU"));
                row.add(rs.getString("BOOK_CODE"));
                
                // Format dates
                Date loanDate = rs.getDate("DATE_EMP");
                Date dueDate = rs.getDate("DATE_RET_ASSUM");
                Date returnDate = rs.getDate("DATE_RET_ACTU");
                
                row.add(loanDate != null ? dateFormat.format(loanDate) : "");
                row.add(dueDate != null ? dateFormat.format(dueDate) : "");
                row.add(returnDate != null ? dateFormat.format(returnDate) : "");
                
                // Convert status code to text
                int status = rs.getInt("STATUT");
                String statusText;
                switch (status) {
                    case 0:
                        statusText = "Returned On Time";
                        break;
                    case 1:
                        // Check if the loan is overdue
                        if (dueDate != null && dueDate.before(new Date()) && returnDate == null) {
                            statusText = "Overdue";
                        } else {
                            statusText = "Active";
                        }
                        break;
                    case 2:
                        statusText = "Returned Late";
                        break;
                    default:
                        statusText = "Unknown";
                }
                row.add(statusText);
                
                tableModel.addRow(row);
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching loans: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new TrackLoansWindow("admin", "admin"));
//    }
}