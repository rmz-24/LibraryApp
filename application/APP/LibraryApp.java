package APP;

import java.awt.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.geom.RoundRectangle2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Properties;

public class LibraryApp {
	private static String getAccessLevel(String username) {
	    // First check for benallal
	    if ("benallal".equalsIgnoreCase(username.trim())) {
	        return "ADMIN"; // Hardcode access level for benallal
	    }
	    
	    // For other users, check database
	    String sql = "SELECT accesslevel FROM userstable WHERE UPPER(USERNAMES) = UPPER(?)";
	    
	    try (Connection conn = DriverManager.getConnection(
	            getProps().getProperty("db.url"),
	            getProps().getProperty("db.user"),
	            getProps().getProperty("db.password"));
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setString(1, username.trim());
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getString("accesslevel");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return "STAFF"; // Default access level
	}
	private ImageIcon loadImage(String imageName) {
	    URL imageURL = getClass().getClassLoader().getResource("resrc/" + imageName);
	    if (imageURL != null) {
	        return new ImageIcon(imageURL);
	    }
	    
	    System.err.println("Image not found: " + imageName);
	    return null; // Avoid NullPointerException
	}

	
	//private static Connection connection;
	private static Connection connection;
	public static Properties getProps() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("config.properties"));
			return props;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean authenticate(String username, String password) {
	    // First check for benallal/amine
	    if ("benallal".equalsIgnoreCase(username.trim()) && "amine".equals(password)) {
	        return true;
	    }
	    
	    // If not benallal/amine, check against userstable
	    String sql = "SELECT COUNT(*) FROM userstable WHERE UPPER(USERNAMES) = UPPER(?) AND PASSWORD = ?";
	    
	    try (Connection conn = DriverManager.getConnection(
	            getProps().getProperty("db.url"),
	            getProps().getProperty("db.user"),
	            getProps().getProperty("db.password"));
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setString(1, username.trim());
	        stmt.setString(2, password);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, 
	            "Database error during authentication", 
	            "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    return false;
	}
	public static Connection getConnection() {
        try {
			return connection;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
    }
	
	// ani nsyi nktb letter
	
	
	private JFrame frmLibraryapp;
	private JPasswordField passwordField;
	/*class RoundedTextField extends JTextField {
		private static final long serialVersionUID = 1L;
		private int cornerRadius;

	    public RoundedTextField(int columns, int cornerRadius) {
	        super(columns);
	        this.cornerRadius = cornerRadius;
	        setOpaque(false);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	        g2d.setColor(Color.WHITE);
	        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

	        super.paintComponent(g);
	    }

	    @Override
	    protected void paintBorder(Graphics g) {
	        g.setColor(Color.GRAY);
	        ((Graphics2D) g).draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
	    }
	}*/

	/*class RoundedPasswordField extends JPasswordField {
		private static final long serialVersionUID = 1L;
		private int cornerRadius;

	    public RoundedPasswordField(int columns, int cornerRadius) {
	        super(columns);
	        this.cornerRadius = cornerRadius;
	        setOpaque(false);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	        g2d.setColor(Color.WHITE);
	        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

	        super.paintComponent(g);
	    }

	    @Override
	    protected void paintBorder(Graphics g) {
	        g.setColor(Color.GRAY);
	        ((Graphics2D) g).draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
	    }
	}*/
	
	
	/*class RoundedButton extends JButton {
		private static final long serialVersionUID = 1L;
		private int cornerRadius;
        
        public RoundedButton(String text, int cornerRadius) {
            super(text);
            this.cornerRadius = cornerRadius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background color
            g2d.setColor(getBackground());
            g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

            // Draw the button text
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No border
        }
    }*/

    

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibraryApp window = new LibraryApp();
					window.frmLibraryapp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LibraryApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */


	private void initialize() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frmLibraryapp = new JFrame();
		frmLibraryapp.getContentPane().setForeground(new Color(255, 255, 255));
		frmLibraryapp.getContentPane().setBackground(new Color(192, 192, 192));
		frmLibraryapp.setResizable(false);
		frmLibraryapp.setTitle("LibraryApp");
		frmLibraryapp.setBounds(100, 100, 1500, 900);
		frmLibraryapp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLibraryapp.getContentPane().setLayout(null);

		Panel panel = new Panel();
		panel.setBackground(new Color(0, 102, 102));
		panel.setBounds(0, 0, 372, 937);
		frmLibraryapp.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("WELCOME TO \r\n");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setFont(new Font("Jost", Font.BOLD, 27));
		lblNewLabel_1.setBounds(94, 413, 213, 40);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("USTHB'S LIBRARY MANAGER");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Jost", Font.BOLD, 21));
		lblNewLabel_2.setBounds(40, 464, 322, 46);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setBounds(133, 48, 99, 46);
		panel.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Jost", Font.BOLD, 31));
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(147, 295, 49, 14);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel((String) null);
		//lblNewLabel_6.setIcon(loadImage("LM.png"));
		
		lblNewLabel_6.setSize(new Dimension(10, 10));
		lblNewLabel_6.setIcon(new ImageIcon("src\\resrc\\LM.png"));
		lblNewLabel_6.setBounds(40, 146, 297, 251);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(957, 105, 109, 125);
		frmLibraryapp.getContentPane().add(lblNewLabel_3);
		lblNewLabel_3.setMinimumSize(new Dimension(50, 50));
		lblNewLabel_3.setMaximumSize(new Dimension(100, 100));
		lblNewLabel_3.setIcon(new ImageIcon("src\\resrc\\Untitled design.png"));
		
		JLabel lblusername = new JLabel("Username");
		lblusername.setForeground(new Color(255, 255, 255));
		lblusername.setFont(new Font("Jost", Font.BOLD, 26));
		lblusername.setBounds(826, 329, 133, 35);
		frmLibraryapp.getContentPane().add(lblusername);
		
		JLabel lblpassword = new JLabel("Password");
		lblpassword.setForeground(new Color(255, 255, 255));
		lblpassword.setFont(new Font("Jost", Font.BOLD, 26));
		lblpassword.setBounds(826, 468, 133, 35);
		frmLibraryapp.getContentPane().add(lblpassword);
		
		//RoundedPasswordField passwordField = new RoundedPasswordField(20, 20);
		passwordField = new JPasswordField();
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setBackground(new Color(145, 149, 153));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		passwordField.setBounds(826, 520, 360, 49);
		passwordField.setBorder(BorderFactory.createEmptyBorder()); // Removes the border
		frmLibraryapp.getContentPane().add(passwordField);
		
		//RoundedTextField textField = new RoundedTextField(20, 20);
		JTextField textArea = new JTextField();
		textArea.setForeground(new Color(255, 255, 255));
		textArea.setBackground(new Color(145, 149, 153));
		textArea.setFont(new Font("Jost", Font.PLAIN, 26));
		textArea.setBounds(826, 376, 360, 49);
		textArea.setBorder(BorderFactory.createEmptyBorder());
		frmLibraryapp.getContentPane().add(textArea);
		textArea.setColumns(10);
		 
		//RoundedButton button = new RoundedButton("LOG IN", 20);
		JButton button = new JButton("LOG IN");
		
		button.setBackground(new Color(0, 102, 102));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Jost", Font.BOLD, 27));
        button.setBounds(120, 100, 150, 50);
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String user = textArea.getText();
                    String pass = String.valueOf(passwordField.getPassword());

                    if (authenticate(user, pass)) {
                        String accessLevel = getAccessLevel(user);
                        
                        Properties props = getProps();
                        try {
                            connection = DriverManager.getConnection(
                                props.getProperty("db.url"), 
                                props.getProperty("db.user"), 
                                props.getProperty("db.password"));
                            
                            new Home(user, accessLevel);
                            frmLibraryapp.dispose();
                            
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(null, 
                                "Database connection error", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, 
                            "Invalid username or password", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String user = textArea.getText().trim();
                String pass = String.valueOf(passwordField.getPassword());

                if (authenticate(user, pass)) {
                    String accessLevel = getAccessLevel(user);
                    
                    Properties props = getProps();
                    try {
                        connection = DriverManager.getConnection(
                            props.getProperty("db.url"), 
                            props.getProperty("db.user"), 
                            props.getProperty("db.password"));
                        
                        new Home(user, accessLevel);
                        frmLibraryapp.dispose();
                        
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, 
                            "Database connection error", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Invalid username or password", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
		button.setForeground(new Color(255, 255, 255));
		button.setBackground(new Color(0, 102, 102));
		button.setFont(new Font("Jost", Font.BOLD, 19));
		button.setBounds(957, 653, 134, 49);
		frmLibraryapp.getContentPane().add(button);
		
	
		
		
	}
}
