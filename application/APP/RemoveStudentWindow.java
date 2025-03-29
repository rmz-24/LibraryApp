package APP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class RemoveStudentWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	/*private ImageIcon loadImage(String imageName) {
	    return new ImageIcon(getClass().getResource("/resrc/" + imageName));
	}*/
    private JTextField studentiddlt;
    private JTextArea Sname, Sfname, Slevel;
    private Connection conn;
    public RemoveStudentWindow() {
    	
        setTitle("Student Removing Window");
        setSize(638, 415);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 102));
        panel.setBounds(-52, 0, 755, 66);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel topLabel = new JLabel("STUDENT DELETION");
        topLabel.setForeground(new Color(255, 255, 255));
        topLabel.setFont(new Font("Jost", Font.BOLD, 19));
        topLabel.setBounds(280, 24, 196, 31);
        panel.add(topLabel);

        JLabel searchStudentIdLabel = new JLabel("ENTER STUDENT ID");
        searchStudentIdLabel.setForeground(new Color(0, 0, 0));
        searchStudentIdLabel.setFont(new Font("Jost", Font.BOLD, 19));
        searchStudentIdLabel.setBounds(10, 106, 196, 31);
        getContentPane().add(searchStudentIdLabel);

        studentiddlt = new JTextField(16);
        studentiddlt.setFont(new Font("Jost", Font.PLAIN, 22));
        studentiddlt.setBounds(10, 141, 204, 43);
        getContentPane().add(studentiddlt);

        JButton btndeletestudent = new JButton("DELETE");
        btndeletestudent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String studentID = studentiddlt.getText().trim();
                if (studentID.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a Student ID!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    conn = LibraryApp.getConnection(); // Ensure connection is established
                    String sql = "DELETE FROM studentlist WHERE STUDENTID = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, studentID);

                    int rowsAffected = stmt.executeUpdate(); // Use executeUpdate() for DELETE

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        studentiddlt.setText(""); // Clear input field
                        Sname.setText("");
                        Sfname.setText("");
                        Slevel.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    //stmt.close();
                    //conn.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(RemoveStudentWindow.this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btndeletestudent.setBackground(new Color(128, 0, 0));
        btndeletestudent.setForeground(new Color(255, 255, 255));
        btndeletestudent.setFont(new Font("Jost", Font.BOLD, 24));
        btndeletestudent.setBounds(10, 195, 140, 50);
        getContentPane().add(btndeletestudent);

        Sname = new JTextArea();
        Sname.setFont(new Font("Jost", Font.BOLD, 20));
        Sname.setBounds(407, 141, 184, 31);
        Sname.setEditable(false);
        getContentPane().add(Sname);

        JLabel lblnamedlt = new JLabel("Name");
        lblnamedlt.setForeground(Color.BLACK);
        lblnamedlt.setFont(new Font("Jost", Font.BOLD, 19));
        lblnamedlt.setBounds(407, 106, 196, 31);
        getContentPane().add(lblnamedlt);

        Sfname = new JTextArea();
        Sfname.setFont(new Font("Jost", Font.BOLD, 20));
        Sfname.setBounds(407, 230, 184, 31);
        Sfname.setEditable(false);
        getContentPane().add(Sfname);

        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setForeground(Color.BLACK);
        lblFirstName.setFont(new Font("Jost", Font.BOLD, 19));
        lblFirstName.setBounds(407, 195, 196, 31);
        getContentPane().add(lblFirstName);

        Slevel = new JTextArea();
        Slevel.setFont(new Font("Jost", Font.BOLD, 20));
        Slevel.setBounds(407, 311, 184, 31);
        Slevel.setEditable(false);
        getContentPane().add(Slevel);

        JLabel lblleveldlt = new JLabel("Level");
        lblleveldlt.setForeground(Color.BLACK);
        lblleveldlt.setFont(new Font("Jost", Font.BOLD, 19));
        lblleveldlt.setBounds(407, 276, 196, 31);
        getContentPane().add(lblleveldlt);

        JButton btnsearch = new JButton("");
        btnsearch.setIcon(new ImageIcon("src\\resrc\\find_11916806.png"));
        btnsearch.setBounds(160, 195, 50, 50);
        btnsearch.setFocusPainted(false);
        btnsearch.setContentAreaFilled(false);
        btnsearch.setBorderPainted(false);
        getContentPane().add(btnsearch);

        btnsearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchStudent();
            }
        });

        setVisible(true);
    }

    private void searchStudent() {
    	
    	conn = LibraryApp.getConnection();
        String studentID = studentiddlt.getText().trim();
        if (studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            
            String sql = "SELECT NAME, FIRSTNAME, STUDENT_LEVEL FROM studentlist WHERE STUDENTID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Sname.setText(rs.getString("NAME"));
                Sfname.setText(rs.getString("FIRSTNAME"));
                Slevel.setText(rs.getString("STUDENT_LEVEL"));
            } else {
                JOptionPane.showMessageDialog(this, "No student found with ID: " + studentID, "Not Found", JOptionPane.INFORMATION_MESSAGE);
                Sname.setText("");
                Sfname.setText("");
                Slevel.setText("");
            }

           // rs.close();
           // stmt.close();
            //conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new RemoveStudentWindow();
    }
}
