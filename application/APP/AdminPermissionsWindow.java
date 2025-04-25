package APP;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Properties;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class AdminPermissionsWindow extends JFrame {
	private boolean removeUser(String username, Integer userId) {
	    try {
	        connection.setAutoCommit(false);
	        
	        // 1. Revoke privileges
	        String revokeSQL = "REVOKE ALL PRIVILEGES FROM " + username;
	        try (Statement stmt = connection.createStatement()) {
	            stmt.execute(revokeSQL);
	        } catch (SQLException e) {
	            System.out.println("Note: Could not revoke privileges: " + e.getMessage());
	        }
	        
	        // 2. Drop the database user
	        String dropUserSQL = "DROP USER " + username + " CASCADE";
	        try (Statement stmt = connection.createStatement()) {
	            stmt.execute(dropUserSQL);
	        }
	        
	        // 3. Remove from userstable (using userid for more precise deletion)
	        String deleteSQL = "DELETE FROM userstable WHERE userid = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(deleteSQL)) {
	            stmt.setInt(1, userId);
	            int rowsAffected = stmt.executeUpdate();
	            
	            if (rowsAffected == 0) {
	                connection.rollback();
	                return false;
	            }
	        }
	        
	        connection.commit();
	        return true;
	        
	    } catch (SQLException e) {
	        try {
	            connection.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            connection.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	private boolean userExists(String username) {
	    String sql = "SELECT COUNT(*) FROM userstable WHERE usernames = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, username);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	private void loadUserData() {
	    DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
	    model.setRowCount(0); // Clear table before loading new data

	    String sql = "SELECT userid, usernames, accesslevel FROM userstable ORDER BY userid";
	    
	    try (PreparedStatement stmt = connection.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            int userId = rs.getInt("userid");
	            String username = rs.getString("usernames");
	            String accessLevel = rs.getString("accesslevel");
	            model.addRow(new Object[]{userId, username, accessLevel});
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, 
	            "Error loading users from database.", 
	            "Database Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	
	private Connection connection;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable usersTable;
	private JTextField usernamefield;
	private JTextField passwordfield;
	
	
	/**
	 * Create the frame.
	 */
	public AdminPermissionsWindow(String username, String accessLevel,ThemeToggleButton tg) {
		setResizable(false);
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\resrc\\LMsmall.png"));
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Properties props = LibraryApp.getProps();
		// Open HomeScreen
		try {
		    connection = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
		    
		    if (connection != null) {
		        System.out.println("Connected to the database successfully!");
		    } else {
		        System.out.println("Failed to connect to the database.");
		    }
		} catch (SQLException e1) {
		    e1.printStackTrace();
		    JOptionPane.showMessageDialog(null, "Database error. Contact support.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		
		
		setTitle("Users Managing Table");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 900);
		setSize(1500, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 102));
		panel.setBounds(0, 0, 1484, 115);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblconnectedas = new JLabel("CONNECTED AS:");
		lblconnectedas.setForeground(new Color(255, 255, 255));
		lblconnectedas.setBounds(1026, 11, 197, 26);
		panel.add(lblconnectedas);
		lblconnectedas.setFont(new Font("Jost", Font.BOLD, 20));
		
		JLabel lblusername = new JLabel(username);
		lblusername.setForeground(new Color(255, 255, 255));
		lblusername.setBounds(1233, 16, 120, 16);
		panel.add(lblusername);
		lblusername.setFont(new Font("Jost", Font.BOLD, 20));
		
		JLabel lblAccessLevel = new JLabel("ACCESS LEVEL :");
		lblAccessLevel.setForeground(new Color(255, 255, 255));
		lblAccessLevel.setBounds(1026, 68, 201, 16);
		panel.add(lblAccessLevel);
		lblAccessLevel.setFont(new Font("Jost", Font.BOLD, 20));
		
		JLabel lblacceslevel = new JLabel(accessLevel);
		lblacceslevel.setForeground(new Color(255, 255, 255));
		lblacceslevel.setBounds(1233, 68, 120, 16);
		panel.add(lblacceslevel);
		lblacceslevel.setFont(new Font("Jost", Font.BOLD, 20));
		
		JLabel lblNewLabel_1 = new JLabel("USERS TABLE");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(227, 27, 151, 57);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Jost", Font.BOLD, 22));
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon("src\\resrc\\LMsmall.png"));
		lblNewLabel_1_1.setBounds(32, 20, 112, 80);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("");
		lblNewLabel_1_2.setIcon(new ImageIcon("src\\resrc\\LMsmall.png"));
		lblNewLabel_1_2.setBounds(21, 20, 112, 80);
		panel.add(lblNewLabel_1_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(242, 194, 1104, 427);
		contentPane.add(scrollPane);
		
		
		JButton removeUserButton = new JButton("Remove Selected User");
		removeUserButton.setFont(new Font("Jost", Font.BOLD, 19));
		removeUserButton.setForeground(new Color(255, 255, 255));
		removeUserButton.setBackground(new Color(255, 87, 87));
		removeUserButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = usersTable.getSelectedRow();
		        
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(AdminPermissionsWindow.this, 
		                "Please select a user to remove", "No Selection", JOptionPane.WARNING_MESSAGE);
		            return;
		        }
		        
		        // Get userid (column 0) and username (column 1)
		        Integer userId = (Integer) usersTable.getValueAt(selectedRow, 0);
		        String usernamedel = (String) usersTable.getValueAt(selectedRow, 1);
		        String selectedUsername = (String) usersTable.getValueAt(selectedRow, 1);
		        
		        if (selectedUsername.equalsIgnoreCase(username)) {
		            JOptionPane.showMessageDialog(AdminPermissionsWindow.this, 
		                "You cannot delete your own account!", "Operation Not Allowed", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        int confirm = JOptionPane.showConfirmDialog(AdminPermissionsWindow.this, 
		            "Are you sure you want to permanently remove user '" + usernamedel + "'?", 
		            "Confirm Removal", JOptionPane.YES_NO_OPTION);
		        
		        if (confirm == JOptionPane.YES_OPTION) {
		        	if (username==usernamedel) {
			            JOptionPane.showMessageDialog(AdminPermissionsWindow.this, 
			                "You Cannot delete this user "," this is the current logged user", JOptionPane.WARNING_MESSAGE);
			            return;
			        }
		            if (removeUser(usernamedel, userId)) {  // Updated removeUser method
		                // Remove from JTable
		                DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
		                model.removeRow(selectedRow);
		                
		                JOptionPane.showMessageDialog(AdminPermissionsWindow.this, 
		                    "User '" + usernamedel + "' removed successfully", 
		                    "Success", JOptionPane.INFORMATION_MESSAGE);
		            } else {
		                JOptionPane.showMessageDialog(AdminPermissionsWindow.this, 
		                    "Failed to remove user '" + usernamedel + "'", 
		                    "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    }
		});
		removeUserButton.setBounds(1051, 647, 295, 40);
		contentPane.add(removeUserButton);
		
		
		usersTable = new JTable();
		scrollPane.setViewportView(usersTable);
		// Make table non-editable
		usersTable.setRowHeight(30);
		Font tableFont = new Font("Jost", Font.PLAIN, 18);
		usersTable.setFont(tableFont); 
		usersTable.getTableHeader().setFont(new Font("Jost", Font.BOLD, 20));
		usersTable.setModel(new DefaultTableModel(
		    new Object[][] {},
		    new String[] {"User Id" ,"User", "Permissions"}
		) {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		});
		loadUserData();

		// Add row selection listener
		usersTable.getSelectionModel().addListSelectionListener(e -> {
		    removeUserButton.setEnabled(usersTable.getSelectedRow() != -1);
		});
		usersTable.setBackground(new Color(192, 192, 192));
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Jost", Font.BOLD, 22));
		usernameLabel.setBounds(10, 169, 105, 20);
		contentPane.add(usernameLabel);
		
		usernamefield = new JTextField();
		usernamefield.setBounds(10, 208, 181, 40);
		contentPane.add(usernamefield);
		usernamefield.setColumns(10);
		
		
		passwordfield = new JTextField();
		passwordfield.setColumns(10);
		passwordfield.setBounds(10, 310, 181, 40);
		contentPane.add(passwordfield);
		
		JLabel passwordlabel = new JLabel("Password:");
		passwordlabel.setFont(new Font("Jost", Font.BOLD, 22));
		passwordlabel.setBounds(10, 271, 105, 20);
		contentPane.add(passwordlabel);
		
		JLabel permissionLabel = new JLabel("Access Level:");
		permissionLabel.setFont(new Font("Jost", Font.BOLD, 22));
		permissionLabel.setBounds(10, 366, 139, 57);
		contentPane.add(permissionLabel);
		
		JRadioButton adminRadio = new JRadioButton("Admin");
		adminRadio.setFont(new Font("Jost", Font.PLAIN, 12));
		adminRadio.setBounds(10, 430, 109, 23);
		contentPane.add(adminRadio);
		
		JRadioButton staffRadio = new JRadioButton("Staff");
		staffRadio.setFont(new Font("Jost", Font.PLAIN, 12));
		staffRadio.setBounds(10, 460, 109, 23);
		contentPane.add(staffRadio);
		
		ButtonGroup accessLevelGroup = new ButtonGroup();
		accessLevelGroup.add(adminRadio);
		accessLevelGroup.add(staffRadio);
		if(tg.isSelected()) {
			contentPane.setBackground(new Color(60, 63, 65)); // Light grayColor(60, 63, 65)
			//staffRadio.setForeground(Color.WHITE);
			//adminRadio.setForeground(Color.WHITE);
			permissionLabel.setForeground(Color.WHITE);
			passwordlabel.setForeground(Color.WHITE);
			usernameLabel.setForeground(Color.WHITE);

	  	}else {
	  		contentPane.setBackground(new Color(240, 240, 240));
	  		
	  	}
		
		JButton addUserButton = new JButton("Add User");
		addUserButton.setBackground(new Color(0, 64, 128));
		addUserButton.setForeground(new Color(255, 255, 255));
		addUserButton.setFont(new Font("Jost", Font.BOLD, 16));
		addUserButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String username = usernamefield.getText().trim();
		        String password = passwordfield.getText().trim();
		        
		        // Validate inputs
		        if (username.isEmpty()) {
		            JOptionPane.showMessageDialog(AdminPermissionsWindow.this, 
		                "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        
		        if (password.isEmpty()) {
		            JOptionPane.showMessageDialog(AdminPermissionsWindow.this, 
		                "Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        
		        // Check if user already exists
		        if (userExists(username)) {
		            JOptionPane.showMessageDialog(AdminPermissionsWindow.this, 
		                "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        
		        // Check access level selection
		        if (!adminRadio.isSelected() && !staffRadio.isSelected()) {
		            JOptionPane.showMessageDialog(AdminPermissionsWindow.this, 
		                "Please select an access level!", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        
		        String accessLevel = adminRadio.isSelected() ? "ADMIN" : "STAFF";
		        
		        try {
		            // Create database user
		            String createUserSQL = "CREATE USER " + username + " IDENTIFIED BY " + password;
		            try (Statement stmt = connection.createStatement()) {
		                stmt.execute(createUserSQL);
		            }
		            
		            // Grant appropriate role
		            String grantRoleSQL = "GRANT " + accessLevel + " TO " + username;
		            try (Statement stmt = connection.createStatement()) {
		                stmt.execute(grantRoleSQL);
		            }
		            
		            // Insert into userstable
		            String insertSQL = "INSERT INTO userstable (USERID, USERNAMES, ACCESSLEVEL, PASSWORD) " +
		                             "VALUES (user_id_seq.NEXTVAL, ?, ?, ?)";
		            try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
		                stmt.setString(1, username);
		                stmt.setString(2, accessLevel);
		                stmt.setString(3, password);
		                stmt.executeUpdate();
		            }
		            
		            // Update UI
		            DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
		            model.addRow(new Object[]{username, accessLevel});
		            
		            JOptionPane.showMessageDialog(AdminPermissionsWindow.this, 
		                "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		            
		            // Clear fields
		            usernamefield.setText("");
		            passwordfield.setText("");
		            accessLevelGroup.clearSelection();
		            
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(AdminPermissionsWindow.this, 
		                "Error adding user: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		addUserButton.setBounds(10, 490, 105, 40);
		contentPane.add(addUserButton);
		
		
		JButton btncommit = new JButton("Commit Changes");
		btncommit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Home(username,accessLevel,tg);
        		dispose();
				
			}
		});
		btncommit.setBackground(new Color(56, 194, 56));
		btncommit.setForeground(new Color(255, 255, 255));
		btncommit.setFont(new Font("Jost", Font.BOLD, 18));
		btncommit.setBounds(1165, 698, 181, 40);
		contentPane.add(btncommit);
		
		
		
		
		/*
		 * NOTE FOR AMINE:
		 * In order to retrieve the selected state:
		 * if (adminRadio.isSelected()) {
    		System.out.println("Admin selected");
 		   }else {
    		System.out.println("User selected");
		   }
		 */
		
		setVisible(true);
	}
}