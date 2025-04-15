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
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import java.util.*;

public class RegisterLoanWindow extends JFrame {
    private final JPanel panel = new JPanel();
    private JTextField sid;
    private JTextField sname;
    private JDateChooser dateEmpChooser;
    private JDateChooser dateRetAssumChooser;
    private Connection connection;
    public RegisterLoanWindow(String user,String level) {
    	connection=LibraryApp.getConnection();
    	setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("src\\resrc\\LMsmall.png"));
        setTitle("Add Loans");
        setSize(761, 686);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed to DISPOSE_ON_CLOSE
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        panel.setBackground(new Color(182, 182, 182));
        panel.setBounds(0, 0, 1695, 1023);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JPanel topColorPanel = new JPanel();
        topColorPanel.setBackground(new Color(0, 102, 102));
        topColorPanel.setBounds(-51, 0, 1746, 115);
        panel.add(topColorPanel);
        topColorPanel.setLayout(null);
        
        JLabel iconLabel = new JLabel("");
        iconLabel.setBounds(87, 11, 100, 100);
        iconLabel.setIcon(new ImageIcon("src\\resrc\\LMsmall.png"));
        topColorPanel.add(iconLabel);
        
        JLabel lblStudentId = new JLabel("STUDENT ID *");
        lblStudentId.setForeground(Color.WHITE);
        lblStudentId.setFont(new Font("Jost", Font.BOLD, 24));
        lblStudentId.setBounds(52, 167, 216, 35);
        panel.add(lblStudentId);
        
        sid = new JTextField();
        sid.setFont(new Font("Jost", Font.PLAIN, 22));
        sid.setColumns(10);
        sid.setBounds(52, 213, 269, 43);
        panel.add(sid);
        
        JLabel lblStudentname = new JLabel("BOOK ID *");
        lblStudentname.setForeground(Color.WHITE);
        lblStudentname.setFont(new Font("Jost", Font.BOLD, 24));
        lblStudentname.setBounds(52, 278, 216, 35);
        panel.add(lblStudentname);
        
        sname = new JTextField();
        sname.setFont(new Font("Jost", Font.PLAIN, 22));
        sname.setColumns(10);
        sname.setBounds(52, 325, 269, 43);
        panel.add(sname);
        String[] categorie = {
        	    "MATHS",          // Mathématiques
        	    "PHYSIQUE",       // Physique
        	    "CHIMIE",         // Chimie
        	    "INFORMATIQUE",   // Informatique
        	    "ELECTRONIQUE",   // Électronique
        	    "AUTOMATIQUE",    // Automatique
        	    "TELECOMS",       // Télécommunications
        	    "GENIE CIVIL",    // Génie Civil
        	    "MECANIQUE",      // Mécanique
        	    "ELECTROTECHNIQUE", // Électrotechnique
        	    "ARCHI",          // Architecture
        	    "GEOLOGIE",       // Géologie
        	    "BIOLOGIE",       // Biologie
        	    "MDF",            // Méthodes de Fabrication
        	    "ROBOTIQUE",      // Robotique
        	    "ENERGIE",        // Énergies renouvelables
        	    "SCIENCES DE LA TERRE", // Sciences de la Terre
        	    "GESTION",        // Gestion et économie
        	    "LANGUE",         // Langues (Français, Anglais, etc.)
        	    "HISTOIRE",       // Histoire des sciences
        	    "PHILOSOPHIE"     // Philosophie des sciences
        	};
        
        JButton btnAbort = new JButton("Abort");
        btnAbort.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new LoanManagementDashboard(user,level);
        		dispose();
        	
        	}
        });
        btnAbort.setForeground(Color.WHITE);
        btnAbort.setFont(new Font("Jost", Font.BOLD, 18));
        btnAbort.setBackground(new Color(128, 0, 0));
        btnAbort.setBounds(406, 558, 123, 40);
        panel.add(btnAbort);
        
        JButton btnAddLoan = new JButton("Add New Loan");
        
        btnAddLoan.setForeground(Color.WHITE);
        btnAddLoan.setFont(new Font("Jost", Font.BOLD, 18));
        btnAddLoan.setBackground(new Color(56, 194, 56));
        btnAddLoan.setBounds(213, 558, 181, 40);
       
        btnAddLoan.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		addnewloan();
        		
        	}
        });
        panel.add(btnAddLoan);
        
        dateRetAssumChooser = new JDateChooser();
        dateRetAssumChooser.setFont(new Font("Jost", Font.PLAIN, 22));
        dateRetAssumChooser.setBounds(398, 213, 269, 43);
        panel.add(dateRetAssumChooser);
        
        dateEmpChooser = new JDateChooser();
        dateEmpChooser.setFont(new Font("Jost", Font.PLAIN, 22));
        dateEmpChooser.setBounds(52, 440, 269, 43);
        panel.add(dateEmpChooser);
        
        JLabel lblExpectedReturn = new JLabel("EXPECTED RETURN  *");
        lblExpectedReturn.setForeground(Color.WHITE);
        lblExpectedReturn.setFont(new Font("Jost", Font.BOLD, 24));
        lblExpectedReturn.setBounds(398, 167, 269, 35);
        panel.add(lblExpectedReturn);
        
        JLabel lblStudentsigndate_2 = new JLabel("LOANED IN  *");
        lblStudentsigndate_2.setForeground(Color.WHITE);
        lblStudentsigndate_2.setFont(new Font("Jost", Font.BOLD, 24));
        lblStudentsigndate_2.setBounds(52, 394, 216, 35);
        panel.add(lblStudentsigndate_2);
        
        setVisible(true);
    }
    
    // Proper main method implementation
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegisterLoanWindow("user","access");
            }
        });
    }
    
    private void  addnewloan() {
    	String numEtu = sid.getText();
        String bookCode = sname.getText();
        java.util.Date dateEmp = dateEmpChooser.getDate();
        java.util.Date dateRetAssum = dateRetAssumChooser.getDate();
        
        String checkQuery = "SELECT AVAILABLE_QTY FROM BOOKSLIST WHERE BOOKID = ?";
        PreparedStatement checkStmt;
		try {
			checkStmt = connection.prepareStatement(checkQuery);
		
        checkStmt.setString(1, bookCode);
        ResultSet rs = checkStmt.executeQuery();

        if (!rs.next()) {
            JOptionPane.showMessageDialog(this, "Book not found in database!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int availableQuantity = rs.getInt("AVAILABLE_QTY");
        if (availableQuantity <= 0) {
            JOptionPane.showMessageDialog(this, "This book is currently unavailable!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        
        try {
        	if(numEtu.isEmpty() || bookCode.isEmpty() || dateEmp==null||dateRetAssum==null) {
        		JOptionPane.showMessageDialog(this, "Please fill all required fields!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
        		
        	}
        	String query = "INSERT INTO EMPRUNTS (NUM_EMP, NUM_ETU, BOOK_CODE, DATE_EMP, DATE_RET_ASSUM, STATUT) VALUES (emprunts_seq.NEXTVAL, ?, ?, ?, ?, 0)";
        	PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, numEtu);
            pstmt.setString(2, bookCode);
            pstmt.setDate(3, new java.sql.Date(dateEmp.getTime()));
            pstmt.setDate(4, new java.sql.Date(dateRetAssum.getTime()));
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Loan added successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            String updateQuery = "UPDATE BOOKSLIST SET AVAILABLE_QTY = AVAILABLE_QTY - 1 WHERE BOOKID = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
            updateStmt.setString(1, bookCode);
            int updated = updateStmt.executeUpdate();
            updateStmt.close();

            if (updated <= 0) {
                connection.rollback();
                JOptionPane.showMessageDialog(this, "Failed to update book quantity!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            clearForm();
            pstmt.close();
       
        	
        }catch(Exception ex){
        	 ex.printStackTrace(); // Log to console
        	    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
        	        "Database Error", JOptionPane.ERROR_MESSAGE);
        	
        }
        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    private void clearForm() {
        sid.setText("");
        sname.setText("");
        dateEmpChooser.setDate(null);
        dateRetAssumChooser.setDate(null);
    }
}