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
	private ImageIcon loadImage(String imageName) {
	    return new ImageIcon(getClass().getResource("/resrc/" + imageName));
	}
	
	
	private void loadUserData() {
	    DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
	    model.setRowCount(0); // Clear table before loading new data

	    String sql = "SELECT usernames, accesslevel FROM userstable";
	    
	    try (PreparedStatement stmt = connection.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            String username = rs.getString("usernames");
	            String accessLevel = rs.getString("accesslevel");
	            model.addRow(new Object[]{username, accessLevel});
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error loading users from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	
	private Connection connection;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable usersTable;
	private JTextField usernameTextField;
	
	
	// Log Message Method. Called Line 128
	private JLabel addedUser() { //TODO: Not Working
		JLabel logMessageLabel = new JLabel("Added User!");
		logMessageLabel.setForeground(new Color(22, 196, 127));
		logMessageLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		logMessageLabel.setBounds(133, 423, 105, 27);
		return logMessageLabel;
	}
	
	/**
	 * Create the frame.
	 */
	public AdminPermissionsWindow(String username, String accessLevel) {
		
		
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
				DefaultTableModel usersTableModel = (DefaultTableModel) usersTable.getModel();
				try {
				int selectedRowIndex = usersTable.getSelectedRow();
				
				String userToDelete = (String) usersTable.getValueAt(selectedRowIndex, 0);

				String sql = "DELETE FROM userstable WHERE usernames = ?";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				    stmt.setString(1, userToDelete);
				    stmt.executeUpdate();
				} catch (SQLException ex) {
				    //TODO JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
					ex.printStackTrace();
				}
				
				usersTableModel.removeRow(selectedRowIndex);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
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
		    new String[] {"User", "Permissions"}
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
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(10, 208, 181, 40);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel permissionLabel = new JLabel("Access Level:");
		permissionLabel.setFont(new Font("Jost", Font.BOLD, 22));
		permissionLabel.setBounds(10, 259, 139, 57);
		contentPane.add(permissionLabel);
		
		JRadioButton adminRadio = new JRadioButton("Admin");
		adminRadio.setFont(new Font("Jost", Font.PLAIN, 12));
		adminRadio.setBounds(10, 323, 109, 23);
		contentPane.add(adminRadio);
		
		JRadioButton staffRadio = new JRadioButton("Staff");
		staffRadio.setFont(new Font("Jost", Font.PLAIN, 12));
		staffRadio.setBounds(10, 353, 109, 23);
		contentPane.add(staffRadio);
		
		ButtonGroup accessLevelGroup = new ButtonGroup();
		accessLevelGroup.add(adminRadio);
		accessLevelGroup.add(staffRadio);
		
		JButton addUserButton = new JButton("Add User");
		addUserButton.setBackground(new Color(0, 64, 128));
		addUserButton.setForeground(new Color(255, 255, 255));
		addUserButton.setFont(new Font("Jost", Font.BOLD, 16));
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username=usernameTextField.getText();
				DefaultTableModel usersTableModel = (DefaultTableModel) usersTable.getModel();
				if(adminRadio.isSelected()) {
					if(username.isEmpty()) {
						//TODO JOptionPane.showMessageDialog(this, "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
					    return;
					}
					usersTableModel.addRow(new Object[]{usernameTextField.getText(), adminRadio.getText()});
					
					
					String sql = "INSERT INTO userstable (usernames, accesslevel) VALUES (?, ?)";
					try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					    stmt.setString(1, username);
					    stmt.setString(2, "ADMIN");
					    stmt.executeUpdate();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					//TODO: Find an alternative to get the accessLevel selected.
					contentPane.add(addedUser());
				}
				if(staffRadio.isSelected()) {
					if(username.isEmpty()) {
						//TODO JOptionPane.showMessageDialog(this, "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
					    return;
					}
					
					usersTableModel.addRow(new Object[]{usernameTextField.getText(), staffRadio.getText()});
					
					String sql = "INSERT INTO userstable (usernames, accesslevel) VALUES (?, ?)";
					try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					    stmt.setString(1, username);
					    stmt.setString(2, "STAFF");
					    stmt.executeUpdate();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					contentPane.add(addedUser());
				}
			}
		});
		addUserButton.setBounds(10, 383, 105, 40);
		contentPane.add(addUserButton);
		
		
		JButton btncommit = new JButton("Commit Changes");
		btncommit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Home(username,accessLevel);
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
