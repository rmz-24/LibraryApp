package APP;


import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.sql.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import java.util.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class StudentsListWindow extends JFrame{
	  private final JPanel panel = new JPanel();
	  private Connection connection;
	  private final JPanel panel_1 = new JPanel();
	  private JTable usersTable;
	  
	  public StudentsListWindow() {
		    connection=LibraryApp.getConnection();
		    setResizable(false);
	        setIconImage(Toolkit.getDefaultToolkit().getImage("src\\resrc\\LMsmall.png"));
	        setTitle("Students List");
	        setSize(1262, 888);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed to DISPOSE_ON_CLOSE
	        setLocationRelativeTo(null);
	        getContentPane().setLayout(null);
	        
	        JPanel topColorPanel = new JPanel();
	        topColorPanel.setLayout(null);
	        topColorPanel.setBackground(new Color(0, 102, 102));
	        topColorPanel.setBounds(-356, 0, 1746, 89);
	        getContentPane().add(topColorPanel);
	        
	        JLabel iconLabel = new JLabel("");
	        iconLabel.setIcon(new ImageIcon("src\\resrc\\LMsmall.png"));
	        iconLabel.setBounds(87, 11, 100, 100);
	        topColorPanel.add(iconLabel);
	        
	        JLabel iconLabel_1 = new JLabel("");
	        iconLabel_1.setIcon(new ImageIcon("src\\resrc\\LMsmall.png"));
	        iconLabel_1.setBounds(87, 11, 100, 100);
	        topColorPanel.add(iconLabel_1);
	        
	        JLabel iconLabel_2 = new JLabel("");
	        iconLabel_2.setIcon(new ImageIcon("src\\resrc\\LMsmall.png"));
	        iconLabel_2.setBounds(372, 0, 100, 100);
	        topColorPanel.add(iconLabel_2);
	        
	        JScrollPane scrollPane = new JScrollPane();
	        scrollPane.setBounds(69, 149, 1104, 572);
	        getContentPane().add(scrollPane);
	        usersTable = new JTable();
			scrollPane.setViewportView(usersTable);
			// Make table non-editable
			usersTable.setRowHeight(30);
			Font tableFont = new Font("Jost", Font.PLAIN, 18);
			usersTable.setFont(tableFont); 
			usersTable.getTableHeader().setFont(new Font("Jost", Font.BOLD, 20));
			usersTable.setModel(new DefaultTableModel(
			    new Object[][] {},
			    new String[] {"Student Id" ,"Name", "First Name","Level","Signed In"}
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
			
			JButton btnGoBack = new JButton("GO BACK");
			btnGoBack.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String user =LibraryApp.getuser();
					String level =LibraryApp.getlevel();
					new StudentManagementDashboard(user,level);
					dispose();
				}
			});
			btnGoBack.setForeground(Color.WHITE);
			btnGoBack.setFont(new Font("Jost", Font.BOLD, 18));
			btnGoBack.setBackground(new Color(56, 194, 56));
			btnGoBack.setBounds(558, 764, 181, 40);
			getContentPane().add(btnGoBack);
			loadUserData();
		  
		  
		  
		  
		  setVisible(true);
	  }
	  private void loadUserData() {
		    DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
		    model.setRowCount(0); // Clear table before loading new data

		    String sql = "SELECT STUDENTID, NAME,FIRSTNAME,STUDENT_LEVEL,SIGNED_IN_DATE FROM studentlist order by SIGNED_IN_DATE";
		    
		    try (PreparedStatement stmt = connection.prepareStatement(sql);
		         ResultSet rs = stmt.executeQuery()) {

		        while (rs.next()) {
		            String studentId = rs.getString("STUDENTID");
		            String name = rs.getString("NAME");
		            String firstname = rs.getString("FIRSTNAME");
		            String level = rs.getString("STUDENT_LEVEL");
		            Date signing_date = rs.getDate("SIGNED_IN_DATE");
		            model.addRow(new Object[]{studentId, name, firstname,level,signing_date});
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(this, 
		            "Error loading users from database.", 
		            "Database Error", JOptionPane.ERROR_MESSAGE);
		    }
		}
	public static void main(String[] args) {
		new StudentsListWindow();
		
	}
}
