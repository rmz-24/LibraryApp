package APP;

import java.awt.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LibraryApp {

	private JFrame frmLibraryapp;
	private JPasswordField passwordField;
	private JTextField textField;
	private JPasswordField passwordField_1;
	class RoundedTextField extends JTextField {
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
	}

	class RoundedPasswordField extends JPasswordField {
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
	}
	/**
	 * Launch the application.
	 */
	class RoundedButton extends JButton {
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
		lblNewLabel_6.setDisabledIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\LM.png"));
		lblNewLabel_6.setSize(new Dimension(10, 10));
		lblNewLabel_6.setIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\LM.png"));
		lblNewLabel_6.setBounds(40, 146, 297, 251);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(957, 105, 109, 125);
		frmLibraryapp.getContentPane().add(lblNewLabel_3);
		lblNewLabel_3.setMinimumSize(new Dimension(50, 50));
		lblNewLabel_3.setMaximumSize(new Dimension(100, 100));
		lblNewLabel_3.setIcon(new ImageIcon("E:\\L2 ACAD C\\S4\\BDD\\TP\\Untitled design.png"));
		
		JLabel lblNewLabel_4 = new JLabel("Username");
		lblNewLabel_4.setForeground(new Color(255, 255, 255));
		lblNewLabel_4.setFont(new Font("Jost", Font.BOLD, 26));
		lblNewLabel_4.setBounds(826, 329, 133, 35);
		frmLibraryapp.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_4_1 = new JLabel("Password");
		lblNewLabel_4_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_4_1.setFont(new Font("Jost", Font.BOLD, 26));
		lblNewLabel_4_1.setBounds(826, 468, 133, 35);
		frmLibraryapp.getContentPane().add(lblNewLabel_4_1);
		
		//RoundedPasswordField passwordField = new RoundedPasswordField(20, 20);
		passwordField = new JPasswordField();
		
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setBackground(new Color(145, 149, 153));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		passwordField.setBounds(826, 520, 360, 49);
		frmLibraryapp.getContentPane().add(passwordField);
		
		//RoundedTextField textField = new RoundedTextField(20, 20);
		JTextArea textField = new JTextArea();
		textField.setForeground(new Color(255, 255, 255));
		textField.setBackground(new Color(145, 149, 153));
		textField.setFont(new Font("Jost", Font.PLAIN, 26));
		textField.setBounds(826, 376, 360, 49);
		frmLibraryapp.getContentPane().add(textField);
		textField.setColumns(10);
		 
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
					String user = textField.getText();
					String pass = String.valueOf(passwordField.getPassword());

					// Dummy authentication (Replace with real validation logic)
					if (user.equals("amine") && pass.equals("1234")) {

						String level="ADMIN";
						// Open HomeScreen
						new Home(user,level);

						// Close Login Window
						frmLibraryapp.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
					} // Simulates a button click
		        }
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String user = textField.getText();
				String pass = String.valueOf(passwordField.getPassword());

				// Dummy authentication (Replace with real validation logic)
				if (user.equals("amine") && pass.equals("1234")) {

					String level="ADMIN";
					// Open HomeScreen
					new Home(user,level);

					// Close Login Window
					frmLibraryapp.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
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
