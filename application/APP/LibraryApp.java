package APP;

import java.awt.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
//import java.awt.geom.RoundRectangle2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Properties;

public class LibraryApp {
	
	public static String user;
	public static String level;
	public String pass;
	
    
    public static String getuser() {
        return user;
    }
    public static String getlevel() {
        return level;
    }
    
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
	
	private static Connection connection;
	public static Properties getProps() {
		 Properties props = new Properties();
		    
		try (InputStream input = LibraryApp.class.getResourceAsStream("config.properties")) {
	        if (input != null) {
	            props.load(input);
	            return props;
	        }
	    } catch (IOException e) {
	        System.err.println("Warning: Error reading classpath config - " + e.getMessage());
	    }
	    
	    // If not found in classpath, try filesystem
	    try (InputStream input = new FileInputStream("config.properties")) {
	        props.load(input);
	    } catch (FileNotFoundException e) {
	        System.err.println("Warning: config.properties not found in classpath or working directory (" + 
	            System.getProperty("user.dir") + ") - using empty properties");
	    } catch (IOException e) {
	        System.err.println("Warning: Error reading config.properties - " + e.getMessage());
	    }
	    
	    return props;
	}
	
	
	public static boolean authenticate(String username, String password) {
	    // First check for benallal/amine
	    if ("benallal".equalsIgnoreCase(username.trim()) && "amine".equals(password)) {
	        return true;
	    }
	    if (getProps() == null) {
	        JOptionPane.showMessageDialog(null, 
	            "Configuration file is missing or invalid", 
	            "Error", JOptionPane.ERROR_MESSAGE);
	        return false;
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
	
	public static void checkOverdueLoans(Connection connection) {
	    try {
	        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

	        
	        String overdueQuery = "SELECT Num_Etu FROM emprunts WHERE Date_ret_assum < ? AND statut != 2";
	        PreparedStatement overdueStmt = connection.prepareStatement(overdueQuery);
	        overdueStmt.setDate(1, today);
	        ResultSet overdueRs = overdueStmt.executeQuery();

	        while (overdueRs.next()) {
	            String studentId = overdueRs.getString("Num_Etu");

	            
	            String checkBlacklist = "SELECT attempts FROM blacklist WHERE studentid = ?";
	            PreparedStatement checkStmt = connection.prepareStatement(checkBlacklist);
	            checkStmt.setString(1, studentId);
	            ResultSet checkRs = checkStmt.executeQuery();

	            if (checkRs.next()) {
	                
	                int attempts = checkRs.getInt("attempts") + 1;

	                
	                if (attempts > 3) attempts = 3;

	                String updateBlacklist = "UPDATE blacklist SET attempts = ?, etat = CASE WHEN ? = 3 THEN 'black' ELSE 'clear' END WHERE studentid = ?";
	                PreparedStatement updateStmt = connection.prepareStatement(updateBlacklist);
	                updateStmt.setInt(1, attempts);
	                updateStmt.setInt(2, attempts);
	                updateStmt.setString(3, studentId);
	                updateStmt.executeUpdate();
	                updateStmt.close();
	            } else {
	                
	                String insertBlacklist = "INSERT INTO blacklist (studentid, attempts, etat) VALUES (?, 1, 'clear')";
	                PreparedStatement insertStmt = connection.prepareStatement(insertBlacklist);
	                insertStmt.setString(1, studentId);
	                insertStmt.executeUpdate();
	                insertStmt.close();
	            }

	            checkRs.close();
	            checkStmt.close();

	            
	            String updateLoan = "UPDATE emprunts SET statut = 2 WHERE Num_Etu = ? AND Date_ret_assum < ? AND statut != 2";
	            PreparedStatement updateLoanStmt = connection.prepareStatement(updateLoan);
	            updateLoanStmt.setString(1, studentId);
	            updateLoanStmt.setDate(2, today);
	            updateLoanStmt.executeUpdate();
	            updateLoanStmt.close();
	        }

	        overdueRs.close();
	        overdueStmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
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
	class RoundedTextField extends JTextField {
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

	        g2d.setColor(Color.GRAY);
	        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

	        super.paintComponent(g);
	    }

	    @Override
	    protected void paintBorder(Graphics g) {
	        g.setColor(Color.BLACK);
	        ((Graphics2D) g).draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
	    }
	}

	class RoundedPasswordField extends JPasswordField {
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

	        g2d.setColor(Color.GRAY);
	        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

	        super.paintComponent(g);
	    }

	    @Override
	    protected void paintBorder(Graphics g) {
	        g.setColor(Color.BLACK);
	        ((Graphics2D) g).draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
	    }
	}
	
	
	class RoundedButton extends JButton {
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
    }
	

    

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
		JPanel mainBackgroundPanel = new JPanel();
        mainBackgroundPanel = new JPanel();
        mainBackgroundPanel.setLayout(null);
        mainBackgroundPanel.setBackground(new Color(240, 240, 240)); 
        frmLibraryapp.setContentPane(mainBackgroundPanel);
		
		Panel panel = new Panel();
		panel.setBackground(new Color(0, 102, 102));
		panel.setBounds(0, 0, 372, 937);
		frmLibraryapp.getContentPane().add(panel);
		//apres hnzid hna ta3 ywli lelor
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
		lblNewLabel_6.setIcon(loadImage("/resrc/LM.png"));
		lblNewLabel_6.setBounds(40, 146, 297, 251);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(957, 105, 109, 125);
		frmLibraryapp.getContentPane().add(lblNewLabel_3);
		lblNewLabel_3.setMinimumSize(new Dimension(50, 50));
		lblNewLabel_3.setMaximumSize(new Dimension(100, 100));
		lblNewLabel_3.setIcon(loadImage("/resrc/Untitled design.png"));
		
		JLabel lblusername = new JLabel("Username");
		lblusername.setForeground(Color.BLACK);
		lblusername.setFont(new Font("Jost", Font.BOLD, 26));
		lblusername.setBounds(826, 329, 133, 35);
		frmLibraryapp.getContentPane().add(lblusername);
		
		JLabel lblpassword = new JLabel("Password");
		lblpassword.setForeground(Color.BLACK);
		lblpassword.setFont(new Font("Jost", Font.BOLD, 26));
		lblpassword.setBounds(826, 468, 133, 35);
		frmLibraryapp.getContentPane().add(lblpassword);
		ThemeToggleButton toggleButton = new ThemeToggleButton(
        	    mainBackgroundPanel,
        	    lblpassword,
        	    lblusername
        	    
        	);
        
        	toggleButton.setBounds(1400, 99, 60, 30);
        	mainBackgroundPanel.add(toggleButton);

		RoundedPasswordField passwordField = new RoundedPasswordField(20, 20);
		//passwordField = new JPasswordField();
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setBackground(new Color(145, 149, 153));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		passwordField.setBounds(826, 520, 360, 49);
		passwordField.setBorder(BorderFactory.createEmptyBorder()); // Removes the border
		frmLibraryapp.getContentPane().add(passwordField);
		
		RoundedTextField textArea = new RoundedTextField(20, 20);
		//JTextField textArea = new JTextField();
		textArea.setForeground(new Color(255, 255, 255));
		textArea.setBackground(new Color(145, 149, 153));
		textArea.setFont(new Font("Jost", Font.PLAIN, 26));
		textArea.setBounds(826, 376, 360, 49);
		textArea.setBorder(BorderFactory.createEmptyBorder());
		frmLibraryapp.getContentPane().add(textArea);
		textArea.setColumns(10);
		 
		RoundedButton button = new RoundedButton("LOG IN", 20);
		//JButton button = new JButton("LOG IN");
		
		button.setBackground(new Color(0, 102, 102));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Jost", Font.BOLD, 27));
        button.setBounds(120, 100, 150, 50);
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                     user = textArea.getText();
                     pass = String.valueOf(passwordField.getPassword());

                    if (authenticate(user, pass)) {
                       level = getAccessLevel(user);
                        
                        Properties props = getProps();
                        try {
                            connection = DriverManager.getConnection(
                                props.getProperty("db.url"), 
                                props.getProperty("db.user"), 
                                props.getProperty("db.password"));
                            
                            checkOverdueLoans(connection);
                            
                            new Home(user, level,toggleButton);
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
                user = textArea.getText().trim();
                pass = String.valueOf(passwordField.getPassword());

                if (authenticate(user, pass)) {
                    level = getAccessLevel(user);
                    
                    Properties props = getProps();
                    try {
                        connection = DriverManager.getConnection(
                            props.getProperty("db.url"), 
                            props.getProperty("db.user"), 
                            props.getProperty("db.password"));
                        
                        checkOverdueLoans(connection);
                        
                        new Home(user, level, toggleButton);
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
	private ImageIcon loadImage(String path) {
	    try {
	        // Load from resources (works in JAR)
	        URL imageUrl = getClass().getResource(path);
	        if (imageUrl != null) {
	            return new ImageIcon(imageUrl);
	        }
	        
	        // Debug output
	        System.out.println("Failed to load: " + path);
	        System.out.println("Trying absolute path...");
	        
	        // Fallback for development (absolute path)
	        String projectPath = System.getProperty("user.dir");
	        String fullPath = projectPath + "/src/main/resources" + path;
	        File imageFile = new File(fullPath);
	        
	        if (imageFile.exists()) {
	            return new ImageIcon(fullPath);
	        } else {
	            System.err.println("Image not found at: " + fullPath);
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
