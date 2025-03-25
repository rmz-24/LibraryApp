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

public class AdminPermissionsWindow extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable usersTable;
	private JTextField usernameTextField;

	

	// Log Message Method. Called Line 128
	private JLabel missingUsername() { //TODO: Not Working
		JLabel logMessageLabel = new JLabel("Missing Username!");
		logMessageLabel.setForeground(new Color(255, 0, 0));
		logMessageLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		logMessageLabel.setBounds(133, 423, 105, 27);
		return logMessageLabel;
	}
	
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
		
		JLabel lblNewLabel = new JLabel("CONNECTED AS:");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(1059, 11, 164, 26);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		
		JLabel lblusername = new JLabel(username);
		lblusername.setForeground(new Color(255, 255, 255));
		lblusername.setBounds(1233, 16, 120, 16);
		panel.add(lblusername);
		lblusername.setFont(new Font("Dialog", Font.BOLD, 20));
		
		JLabel lblAccessLevel = new JLabel("ACCESS LEVEL :");
		lblAccessLevel.setForeground(new Color(255, 255, 255));
		lblAccessLevel.setBounds(1059, 68, 168, 16);
		panel.add(lblAccessLevel);
		lblAccessLevel.setFont(new Font("Dialog", Font.BOLD, 20));
		
		JLabel lblacceslevel = new JLabel(accessLevel);
		lblacceslevel.setForeground(new Color(255, 255, 255));
		lblacceslevel.setBounds(1233, 68, 120, 16);
		panel.add(lblacceslevel);
		lblacceslevel.setFont(new Font("Dialog", Font.BOLD, 20));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(359, 194, 1104, 427);
		contentPane.add(scrollPane);
		
		usersTable = new JTable();
		scrollPane.setViewportView(usersTable);
		usersTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"User", "Permissions"
			}
		));
		usersTable.setBackground(new Color(192, 192, 192));
		
		JLabel lblNewLabel_1 = new JLabel("USERS TABLE");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNewLabel_1.setBounds(30, 126, 334, 57);
		contentPane.add(lblNewLabel_1);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		usernameLabel.setBounds(10, 244, 105, 20);
		contentPane.add(usernameLabel);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(137, 244, 169, 20);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel permissionLabel = new JLabel("Access Level:");
		permissionLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		permissionLabel.setBounds(10, 330, 139, 57);
		contentPane.add(permissionLabel);
		
		JRadioButton adminRadio = new JRadioButton("Admin");
		adminRadio.setBounds(157, 351, 109, 23);
		contentPane.add(adminRadio);
		
		JRadioButton staffRadio = new JRadioButton("Staff");
		staffRadio.setBounds(157, 381, 109, 23);
		contentPane.add(staffRadio);
		
		ButtonGroup accessLevelGroup = new ButtonGroup();
		accessLevelGroup.add(adminRadio);
		accessLevelGroup.add(staffRadio);
		
		JButton addUserButton = new JButton("Add User");
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel usersTableModel = (DefaultTableModel) usersTable.getModel();
				if(adminRadio.isSelected()) {
					if(adminRadio.getText().equals("")) {
						contentPane.add(missingUsername());
						return;
					}
					usersTableModel.addRow(new Object[]{usernameTextField.getText(), adminRadio.getText()}); //TODO: Find an alternative to get the accessLevel selected.
					contentPane.add(addedUser());
				}
				if(staffRadio.isSelected()) {
					if(staffRadio.getText().equals("")) {
						contentPane.add(missingUsername());
						return;
					}
					usersTableModel.addRow(new Object[]{usernameTextField.getText(), staffRadio.getText()}); //TODO: Find an alternative to get the accessLevel selected.
					contentPane.add(addedUser());
				}
			}
		});
		addUserButton.setBounds(137, 462, 89, 23);
		contentPane.add(addUserButton);
		
		JButton removeUserButton = new JButton("Remove Selected User");
		removeUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel usersTableModel = (DefaultTableModel) usersTable.getModel();
				try {
				int selectedRowIndex = usersTable.getSelectedRow();
				usersTableModel.removeRow(selectedRowIndex);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		removeUserButton.setBounds(75, 510, 207, 23);
		contentPane.add(removeUserButton);
		
		
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
