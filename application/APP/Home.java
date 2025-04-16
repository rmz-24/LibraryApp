package APP;

import javax.swing.*;

import com.sun.jdi.connect.spi.Connection;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.sql.*;

public class Home {
	public class RoundedPanel extends JPanel {
	    private int cornerRadius;

	    public RoundedPanel(int cornerRadius) {
	        super();
	        this.cornerRadius = cornerRadius;
	        setOpaque(false);
	    }

	    public RoundedPanel(LayoutManager layout, int cornerRadius) {
	        super(layout);
	        this.cornerRadius = cornerRadius;
	        setOpaque(false);
	    }

	    public RoundedPanel(LayoutManager layout, boolean isDoubleBuffered, int cornerRadius) {
	        super(layout, isDoubleBuffered);
	        this.cornerRadius = cornerRadius;
	        setOpaque(false);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        
	        // Paint background with rounded corners
	        g2d.setColor(getBackground());
	        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
	        
	        super.paintComponent(g);
	    }

	    @Override
	    protected void paintBorder(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        
	        // Paint border with rounded corners
	        g2d.setColor(getForeground());
	        g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
	    }
	}
	
	JLabel bookLabel, bookLabel_1, bookLabel_2, bookLabel_3, bookLabel_4,
    bookLabel_5, bookLabel_6, bookLabel_7, bookLabel_8, bookLabel_9,
    bookLabel_10, bookLabel_11, bookLabel_12, bookLabel_13, bookLabel_14,
    bookLabel_15, bookLabel_16, bookLabel_17, bookLabel_18, bookLabel_19;
	
	JLabel empruntLabel, empruntLabel_1, empruntLabel_2, empruntLabel_3, empruntLabel_4,
    empruntLabel_5, empruntLabel_6, empruntLabel_7, empruntLabel_8, empruntLabel_9,
    empruntLabel_10, empruntLabel_11, empruntLabel_12, empruntLabel_13, empruntLabel_14,
    empruntLabel_15, empruntLabel_16, empruntLabel_17, empruntLabel_18, empruntLabel_19;
	
	JLabel empruntidLabel, empruntidLabel_1, empruntidLabel_2, empruntidLabel_3, empruntidLabel_4,
    empruntidLabel_5, empruntidLabel_6, empruntidLabel_7, empruntidLabel_8, empruntidLabel_9;
	
	JLabel bookNumberLabel, bookNumberLabel_1, bookNumberLabel_2, bookNumberLabel_3, bookNumberLabel_4,
    bookNumberLabel_5, bookNumberLabel_6, bookNumberLabel_7, bookNumberLabel_8, bookNumberLabel_9;
	private void loadRecentBooks() {
	    JLabel[] bookNameLabels = {
	        
	        
	        bookLabel, bookLabel_3, bookLabel_5, bookLabel_7, bookLabel_9,
	        bookLabel_11, bookLabel_13, bookLabel_15, bookLabel_17, bookLabel_19
	    };

	    JLabel[] bookPriceLabels = {
	    		bookLabel_1, bookLabel_2, bookLabel_4, bookLabel_6, bookLabel_8,
		        bookLabel_10, bookLabel_12, bookLabel_14, bookLabel_16, bookLabel_18
	        
	    };

	    java.sql.Connection conn = LibraryApp.getConnection();
	    final String query = "SELECT BOOKNAME, AVAILABLE_QTY FROM (SELECT BOOKNAME, AVAILABLE_QTY, ROW_NUMBER() OVER (ORDER BY BOOKID  DESC) AS rn FROM BOOKSLIST) WHERE rn <= 10";

	    try (PreparedStatement stmtn = conn.prepareStatement(query)) {
	        ResultSet rs = stmtn.executeQuery();

	        int bookCount = 0;
	        while (rs.next() && bookCount < bookNameLabels.length) {
	            String bookName = rs.getString("BOOKNAME");
	            String bookcopies = "  " + rs.getInt("AVAILABLE_QTY");

	            System.out.println("Book: " + bookName + ", copies: " + bookcopies);

	            bookNameLabels[bookCount].setText(bookName);
	            bookPriceLabels[bookCount].setText(bookcopies);

	            bookNameLabels[bookCount].revalidate();
	            bookNameLabels[bookCount].repaint();
	            bookPriceLabels[bookCount].revalidate();
	            bookPriceLabels[bookCount].repaint();

	            bookCount++;
	        }

	        // Clear remaining labels if fewer than 10 books are loaded
	        for (int i = bookCount; i < bookNameLabels.length; i++) {
	            bookNameLabels[i].setText("");
	            bookPriceLabels[i].setText("");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(frmDashboard, 
	            "Error loading recent books: " + e.getMessage(), 
	            "Database Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	private void loadLateEmprunts() {
	    // Left labels for student names
	    JLabel[]  bookTitleLabels= {
	        empruntLabel_1, empruntLabel_3, empruntLabel_5, empruntLabel_7, empruntLabel_9,
	        empruntLabel_11, empruntLabel_13, empruntLabel_15, empruntLabel_17, empruntLabel_19
	    };

	    // Right labels for book titles
	    JLabel[] studentNameLabels  = {
	    		empruntLabel,empruntLabel_2, empruntLabel_4, empruntLabel_6, empruntLabel_8, empruntLabel_10,
	        empruntLabel_12, empruntLabel_14, empruntLabel_16, empruntLabel_18
	    };

	    JLabel[] IDLabels = {
	        empruntidLabel, empruntidLabel_1, empruntidLabel_2, empruntidLabel_3, empruntidLabel_4,
	        empruntidLabel_5, empruntidLabel_6, empruntidLabel_7, empruntidLabel_8, empruntidLabel_9
	    };

	    java.sql.Connection conn = LibraryApp.getConnection();
	    final String lateLoansQuery = "SELECT num_etu, book_code FROM emprunts WHERE statut = 2 ORDER BY date_ret_assum ASC";

	    try (PreparedStatement stmt = conn.prepareStatement(lateLoansQuery)) {
	        ResultSet rs = stmt.executeQuery();

	        int count = 0;
	        while (rs.next() && count < studentNameLabels.length) {
	            String studentId = rs.getString("num_etu");
	            String bookCode = rs.getString("book_code");

	            // Get student name
	            String studentName = getStudentName(conn, studentId);
	            
	            // Get book title
	            String bookTitle = getBookTitle(conn, bookCode);

	            // Set student name in left label
	            studentNameLabels[count].setText(studentName != null ? studentName : "Student ID: " + studentId);
	            
	            // Set book title in right label
	            bookTitleLabels[count].setText(bookTitle);
	            
	            // Set ID number
	            IDLabels[count].setText(String.valueOf(count + 1));

	            count++;
	        }

	        // Clear remaining labels
	        for (int i = count; i < studentNameLabels.length; i++) {
	            studentNameLabels[i].setText("");
	            bookTitleLabels[i].setText("");
	            IDLabels[i].setText("");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(frmDashboard, 
	            "Error loading late loans: " + e.getMessage(), 
	            "Database Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	// Helper method to get student name
	private String getStudentName(java.sql.Connection conn, String studentId) {
	    final String query = "SELECT name, firstname FROM studentlist WHERE studentid = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, studentId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                String lastName = rs.getString("name");
	                String firstName = rs.getString("firstname");
	                return firstName + " " + lastName;
	            }
	            return null;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	// Helper method to get book title
	private String getBookTitle(java.sql.Connection conn, String bookCode) {
	    final String query = "SELECT bookname FROM bookslist WHERE bookid = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, bookCode);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getString("bookname"); // Just return the book title
	            }
	            return null;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	
	private void loadRecentStudents() {
		// New field mapping that properly pairs first and last names
		int[] fieldMapping = {
		    0, 1,    // Student 1: first name (0), last name (1)
		    2, 3,    // Student 2: first name (2), last name (3)
		    4, 5,    // Student 3: first name (4), last name (5)
		    6, 7,    // Student 4: first name (6), last name (7)
		    8, 9,    // Student 5: first name (8), last name (9)
		    10, 11,  // Student 6: first name (10), last name (11)
		    12, 13,  // Student 7: first name (12), last name (13)
		    14, 15,  // Student 8: first name (14), last name (15)
		    16, 17,  // Student 9: first name (16), last name (17)
		    18, 19   // Student 10: first name (18), last name (19)
		};
	    
	    JLabel[] allLabels = {
	        studentLabel, studentLabel_1, studentLabel_2, studentLabel_3, studentLabel_4,
	        studentLabel_5, studentLabel_6, studentLabel_7, studentLabel_8, studentLabel_9,
	        studentLabel_10, studentLabel_11, studentLabel_12, studentLabel_13, studentLabel_14,
	        studentLabel_15, studentLabel_16, studentLabel_17, studentLabel_18, studentLabel_19,
	        numberLabel, numberLabel_1, numberLabel_2, numberLabel_3, numberLabel_4,
	        numberLabel_5, numberLabel_6, numberLabel_7, numberLabel_8, numberLabel_9
	    };

	    final String RECENT_STUDENTS_QUERY = 
	        "SELECT * FROM ("
	        + "  SELECT NAME, FIRSTNAME FROM studentlist "  // Using your actual column names
	        + "  ORDER BY SIGNED_IN_DATE DESC"              // Using date column for ordering
	        + ") WHERE ROWNUM <= 10";

	   java.sql.Connection connection=LibraryApp.getConnection();
		try (PreparedStatement stmt = connection.prepareStatement(RECENT_STUDENTS_QUERY);
	         ResultSet rs = stmt.executeQuery()) {
	        
	        int studentCount = 0;
	        while (rs.next() && studentCount < 10) {
	            String lastName = rs.getString("NAME");      // Using NAME column for last name
	            String firstName = rs.getString("FIRSTNAME"); // Using FIRSTNAME column
	            
	            System.out.println("Loaded student: " + firstName + " " + lastName);

	            int firstNameLabelIndex = fieldMapping[studentCount * 2];
	            int lastNameLabelIndex = fieldMapping[studentCount * 2 + 1];
	            int numberLabelIndex = 20 + studentCount;
	            
	            allLabels[firstNameLabelIndex].setText(firstName);
	            allLabels[lastNameLabelIndex].setText(lastName);
	            allLabels[numberLabelIndex].setText(String.valueOf(studentCount + 1));
	            
	            studentCount++;
	        }
	        
	        // Clear remaining fields if less than 10 students
	        for (int i = studentCount * 2; i < fieldMapping.length; i++) {
	            allLabels[fieldMapping[i]].setText("");
	        }
	        for (int i = 20 + studentCount; i < 30; i++) {
	            allLabels[i].setText("");
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(frmDashboard, 
	            "Error loading recent students: " + e.getMessage(), 
	            "Database Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	////////////////////////////////////////////////////////////////////////////////////
	///
	
	
	
	
    
    private JFrame frmDashboard;
    // Student name labels
    private JLabel studentLabel;
    private JLabel studentLabel_1;
    private JLabel studentLabel_2;
    private JLabel studentLabel_3;
    private JLabel studentLabel_4;
    private JLabel studentLabel_5;
    private JLabel studentLabel_6;
    private JLabel studentLabel_7;
    private JLabel studentLabel_8;
    private JLabel studentLabel_9;
    private JLabel studentLabel_10;
    private JLabel studentLabel_11;
    private JLabel studentLabel_12;
    private JLabel studentLabel_13;
    private JLabel studentLabel_14;
    private JLabel studentLabel_15;
    private JLabel studentLabel_16;
    private JLabel studentLabel_17;
    private JLabel studentLabel_18;
    private JLabel studentLabel_19;
    // Number labels
    private JLabel numberLabel;
    private JLabel numberLabel_1;
    private JLabel numberLabel_2;
    private JLabel numberLabel_3;
    private JLabel numberLabel_4;
    private JLabel numberLabel_5;
    private JLabel numberLabel_6;
    private JLabel numberLabel_7;
    private JLabel numberLabel_8;
    private JLabel numberLabel_9;
    
    private void openWindow(JFrame newWindow) {
        frmDashboard.dispose();
        newWindow.setVisible(true);
    }
    
    public Home(String user, String level) {
        frmDashboard = new JFrame("Home");
        frmDashboard.setResizable(false);
        frmDashboard.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\resrc\\LMsmall.png"));
        frmDashboard.setTitle("Dashboard");
        frmDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmDashboard.setSize(1662, 946);
        frmDashboard.setLocationRelativeTo(null);
        frmDashboard.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 102));
        panel.setBounds(0, 0, 202, 1122);
        frmDashboard.getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lbllogo = new JLabel("");
        lbllogo.setBounds(40, 11, 112, 80);
        lbllogo.setIcon(new ImageIcon("src\\resrc\\LMsmall.png"));
        panel.add(lbllogo);
        
        JButton studentManagementButton = new JButton("");
        studentManagementButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new StudentManagementDashboard(user,level);
                frmDashboard.dispose();
            }
        });
        
        studentManagementButton.setBackground(new Color(0, 102, 102));
        studentManagementButton.setIcon(new ImageIcon("src\\resrc\\student.png"));
        studentManagementButton.setBounds(40, 137, 112, 68);
        studentManagementButton.setContentAreaFilled(false);
        studentManagementButton.setBorderPainted(false);
        studentManagementButton.setFocusPainted(false);
        studentManagementButton.setOpaque(false);
        studentManagementButton.setToolTipText("Opens a window to the student management dashboard");
        panel.add(studentManagementButton);
        
        JLabel studentManagementLabel = new JLabel("Student Management");
        studentManagementLabel.setBounds(10, 216, 152, 21);
        studentManagementLabel.setFont(new Font("Jost", Font.BOLD, 15));
        studentManagementLabel.setForeground(new Color(255, 255, 255));
        panel.add(studentManagementLabel);
        
        JButton bookManagementButton = new JButton("");
        bookManagementButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new BookManagementDashboard(user,level);
                frmDashboard.dispose();
            }
        });
        bookManagementButton.setIcon(new ImageIcon("src\\resrc\\books.png"));
        bookManagementButton.setBounds(40, 280, 112, 68);
        bookManagementButton.setContentAreaFilled(false);
        bookManagementButton.setBorderPainted(false);
        bookManagementButton.setFocusPainted(false);
        bookManagementButton.setOpaque(false);
        bookManagementButton.setToolTipText("Opens a window to the book management dashboard");
        panel.add(bookManagementButton);
        
        JLabel addBookLabel = new JLabel("Loan and Return");
        addBookLabel.setVerticalAlignment(SwingConstants.TOP);
        addBookLabel.setBounds(10, 536, 129, 21);
        addBookLabel.setForeground(Color.WHITE);
        addBookLabel.setFont(new Font("Jost", Font.BOLD, 15));
        panel.add(addBookLabel);
        
        JButton removeBookButton = new JButton("");
        removeBookButton.setIcon(new ImageIcon("E:\\ECLIPSE-PROJECT\\BDD_APP\\src\\resrc\\exclamation.png"));
        removeBookButton.setBounds(40, 717, 112, 68);
        removeBookButton.setContentAreaFilled(false);
        removeBookButton.setBorderPainted(false);
        removeBookButton.setFocusPainted(false);
        removeBookButton.setOpaque(false);
        removeBookButton.setToolTipText("Removes a book from the library's database");
        panel.add(removeBookButton);
        
        JLabel removeBookLabel = new JLabel("Report");
        removeBookLabel.setHorizontalAlignment(SwingConstants.CENTER);
        removeBookLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        removeBookLabel.setBounds(-17, 796, 102, 21);
        removeBookLabel.setForeground(Color.WHITE);
        removeBookLabel.setFont(new Font("Jost", Font.BOLD, 15));
        panel.add(removeBookLabel);
        //getClass().getResource("/resrc/borrow_book.png")
        JButton borrowBookButton = new JButton("");
        borrowBookButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		new LoanManagementDashboard(user,level);
        		 frmDashboard.dispose();
        	}
        });
        borrowBookButton.setIcon(new ImageIcon("E:\\ECLIPSE-PROJECT\\BDD_APP\\src\\resrc\\borrow_book.png"));
        borrowBookButton.setBounds(40, 438, 112, 68);
        borrowBookButton.setContentAreaFilled(false);
        borrowBookButton.setBorderPainted(false);
        borrowBookButton.setFocusPainted(false);
        borrowBookButton.setOpaque(false);
        borrowBookButton.setToolTipText("Logs a borrowed book to the database");
        panel.add(borrowBookButton);
        
        JLabel booksManagementLabel = new JLabel("Books Management");
        booksManagementLabel.setForeground(Color.WHITE);
        booksManagementLabel.setFont(new Font("Jost", Font.BOLD, 15));
        booksManagementLabel.setBounds(7, 371, 132, 21);
        panel.add(booksManagementLabel);
        
        JLabel managementLabel = new JLabel(" \r\nManagement");
        managementLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        managementLabel.setHorizontalAlignment(SwingConstants.LEFT);
        managementLabel.setForeground(Color.WHITE);
        managementLabel.setFont(new Font("Jost", Font.BOLD, 15));
        managementLabel.setBounds(10, 557, 102, 21);
        panel.add(managementLabel);
        
       
        
        RoundedPanel panel_2 = new RoundedPanel(new BorderLayout(), 100);
        panel_2.setBackground(new Color(168, 216, 185));
        panel_2.setBounds(-70, 702, 210, 95);
        panel.add(panel_2);
        
        RoundedPanel panel_3 = new RoundedPanel(new BorderLayout(), 100);
        panel_3.setBackground(new Color(168, 216, 185));
        panel_3.setBounds(-70, 423, 210, 95);
        panel.add(panel_3);
        RoundedPanel panel_4 = new RoundedPanel(new BorderLayout(), 100);
        panel_4.setBackground(new Color(168, 216, 185));
        panel_4.setBounds(-70, 270, 210, 95);
        panel.add(panel_4);
        RoundedPanel panel_5 = new RoundedPanel(new BorderLayout(), 100);
        panel_5.setBackground(new Color(168, 216, 185));
        panel_5.setBounds(-70, 123, 210, 95);
        panel.add(panel_5);
        
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(145, 149, 153));
        panel_1.setBounds(201, 0, 1494, 88);
        frmDashboard.getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        JLabel connectedAsLabel = new JLabel("CONNECTED AS:");
        connectedAsLabel.setFont(new Font("Jost", Font.BOLD, 20));
        connectedAsLabel.setBounds(1112, 11, 178, 16);
        panel_1.add(connectedAsLabel);
        
        JLabel accessLevelLabel = new JLabel("ACCESS LEVEL :");
        accessLevelLabel.setFont(new Font("Jost", Font.BOLD, 20));
        accessLevelLabel.setBounds(1110, 48, 168, 16);
        panel_1.add(accessLevelLabel);
        
        JLabel usernameLabel = new JLabel(user);
        usernameLabel.setFont(new Font("Jost", Font.BOLD, 20));
        usernameLabel.setBounds(1300, 11, 120, 16);
        panel_1.add(usernameLabel);
        
        JLabel accessLevelLabel1 = new JLabel(level);
        accessLevelLabel1.setFont(new Font("Jost", Font.BOLD, 20));
        accessLevelLabel1.setBounds(1300, 48, 120, 16);
        panel_1.add(accessLevelLabel1);
        
        JButton addUserButton = new JButton("");
        addUserButton.addActionListener(e -> openWindow(new AdminPermissionsWindow(user, level)));
        addUserButton.setIcon(new ImageIcon("src\\resrc\\add_16321386.png"));
        addUserButton.setBounds(1002, 11, 70, 66);
        addUserButton.setContentAreaFilled(false);
        addUserButton.setBorderPainted(false);
        addUserButton.setFocusPainted(false);
        addUserButton.setOpaque(false);
        addUserButton.setToolTipText("Create a new user");
        addUserButton.setVisible(level.equals("ADMIN"));
        panel_1.add(addUserButton);
       /*
        JButton logOutButton = new JButton("");
        logOutButton.setContentAreaFilled(false);
        
        logOutButton.setToolTipText("Log out from the app");
        
        logOutButton.setIcon(new ImageIcon("src\\resrc\\log-out_10024482.png"));
        logOutButton.setBounds(1411, 971, 65, 65);
        logOutButton.setContentAreaFilled(false);
        logOutButton.setBorderPainted(false);
        logOutButton.setFocusPainted(false);
        logOutButton.setOpaque(false);
        logOutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frmDashboard.dispose();
                LibraryApp.main(null);
            }
        });
        frmDashboard.getContentPane().add(logOutButton);
        */
        
        JButton backHomeButton = new JButton("");
        backHomeButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		 LibraryApp.main(null);
        		 frmDashboard.dispose();
        		 
        		}
        });
        backHomeButton.setOpaque(false);
        backHomeButton.setIcon(new ImageIcon("E:\\ECLIPSE-PROJECT\\BDD_APP\\src\\resrc\\log-out_10024482.png"));
        backHomeButton.setFocusPainted(false);
        backHomeButton.setContentAreaFilled(false);
        backHomeButton.setBorderPainted(false);
        backHomeButton.setBounds(1560, 830, 78, 68);
        frmDashboard.getContentPane().add(backHomeButton);
        JPanel studentsdisplay = new JPanel();
        studentsdisplay.setBackground(new Color(0, 165, 207));
        studentsdisplay.setBounds(228, 192, 479, 566);
        frmDashboard.getContentPane().add(studentsdisplay);
        studentsdisplay.setLayout(null);
        
        // Create and configure student name labels
        studentLabel = createStudentLabel(70, 31, 181, 39);
        studentsdisplay.add(studentLabel);
        
        studentLabel_1 = createStudentLabel(261, 31, 181, 39);
        studentsdisplay.add(studentLabel_1);
        
        studentLabel_2 = createStudentLabel(261, 81, 181, 39);
        studentsdisplay.add(studentLabel_2);
        
        studentLabel_3 = createStudentLabel(70, 81, 181, 39);
        studentsdisplay.add(studentLabel_3);
        
        studentLabel_4 = createStudentLabel(261, 131, 181, 39);
        studentsdisplay.add(studentLabel_4);
        
        studentLabel_5 = createStudentLabel(70, 131, 181, 39);
        studentsdisplay.add(studentLabel_5);
        
        studentLabel_6 = createStudentLabel(261, 181, 181, 39);
        studentsdisplay.add(studentLabel_6);
        
        studentLabel_7 = createStudentLabel(70, 181, 181, 39);
        studentsdisplay.add(studentLabel_7);
        
        studentLabel_8 = createStudentLabel(261, 231, 181, 39);
        studentsdisplay.add(studentLabel_8);
        
        studentLabel_9 = createStudentLabel(70, 231, 181, 39);
        studentsdisplay.add(studentLabel_9);
        
        studentLabel_10 = createStudentLabel(261, 281, 181, 39);
        studentsdisplay.add(studentLabel_10);
        
        studentLabel_11 = createStudentLabel(70, 281, 181, 39);
        studentsdisplay.add(studentLabel_11);
        
        studentLabel_12 = createStudentLabel(261, 331, 181, 39);
        studentsdisplay.add(studentLabel_12);
        
        studentLabel_13 = createStudentLabel(70, 331, 181, 39);
        studentsdisplay.add(studentLabel_13);
        
        studentLabel_14 = createStudentLabel(261, 381, 181, 39);
        studentsdisplay.add(studentLabel_14);
        
        studentLabel_15 = createStudentLabel(70, 381, 181, 39);
        studentsdisplay.add(studentLabel_15);
        
        studentLabel_16 = createStudentLabel(261, 431, 181, 39);
        studentsdisplay.add(studentLabel_16);
        
        studentLabel_17 = createStudentLabel(70, 431, 181, 39);
        studentsdisplay.add(studentLabel_17);
        
        studentLabel_18 = createStudentLabel(261, 481, 181, 39);
        studentsdisplay.add(studentLabel_18);
        
        studentLabel_19 = createStudentLabel(70, 481, 181, 39);
        studentsdisplay.add(studentLabel_19);
        
        // Create and configure number labels
        numberLabel = createNumberLabel(19, 33, 39, 39);
        studentsdisplay.add(numberLabel);
        
        numberLabel_1 = createNumberLabel(19, 83, 39, 39);
        studentsdisplay.add(numberLabel_1);
        
        numberLabel_2 = createNumberLabel(19, 133, 39, 39);
        studentsdisplay.add(numberLabel_2);
        
        numberLabel_3 = createNumberLabel(19, 183, 39, 39);
        studentsdisplay.add(numberLabel_3);
        
        numberLabel_4 = createNumberLabel(19, 233, 39, 39);
        studentsdisplay.add(numberLabel_4);
        
        numberLabel_5 = createNumberLabel(19, 283, 39, 39);
        studentsdisplay.add(numberLabel_5);
        
        numberLabel_6 = createNumberLabel(19, 333, 39, 39);
        studentsdisplay.add(numberLabel_6);
        
        numberLabel_7 = createNumberLabel(19, 383, 39, 39);
        studentsdisplay.add(numberLabel_7);
        
        numberLabel_8 = createNumberLabel(19, 433, 39, 39);
        studentsdisplay.add(numberLabel_8);
        
        numberLabel_9 = createNumberLabel(19, 483, 39, 39);
        studentsdisplay.add(numberLabel_9);
        
        JPanel booksdisplay = new JPanel();
        booksdisplay.setBounds(748, 192, 391, 566);
        frmDashboard.getContentPane().add(booksdisplay);
        booksdisplay.setBackground(new Color(37, 161, 142));
        booksdisplay.setLayout(null);
        
       

        // Create and configure book name labels
        bookLabel = createBookLabel(70, 31, 235, 39);
        booksdisplay.add(bookLabel);

        bookLabel_1 = createBookLabel(320, 31, 50, 39);
        booksdisplay.add(bookLabel_1);

        bookLabel_2 = createBookLabel(320, 81, 50, 39);
        booksdisplay.add(bookLabel_2);

        bookLabel_3 = createBookLabel(70, 81, 235, 39);
        booksdisplay.add(bookLabel_3);

        bookLabel_4 = createBookLabel(320, 131, 50, 39);
        booksdisplay.add(bookLabel_4);

        bookLabel_5 = createBookLabel(70, 131, 235, 39);
        booksdisplay.add(bookLabel_5);

        bookLabel_6 = createBookLabel(320, 181, 50, 39);
        booksdisplay.add(bookLabel_6);

        bookLabel_7 = createBookLabel(70, 181, 235, 39);
        booksdisplay.add(bookLabel_7);

        bookLabel_8 = createBookLabel(320, 231, 50, 39);
        booksdisplay.add(bookLabel_8);

        bookLabel_9 = createBookLabel(70, 231, 235, 39);
        booksdisplay.add(bookLabel_9);

        bookLabel_10 = createBookLabel(320, 281, 50, 39);
        booksdisplay.add(bookLabel_10);

        bookLabel_11 = createBookLabel(70, 281, 235, 39);
        booksdisplay.add(bookLabel_11);

        bookLabel_12 = createBookLabel(320, 331, 50, 39);
        booksdisplay.add(bookLabel_12);

        bookLabel_13 = createBookLabel(70, 331, 235, 39);
        booksdisplay.add(bookLabel_13);

        bookLabel_14 = createBookLabel(320, 381, 50, 39);
        booksdisplay.add(bookLabel_14);

        bookLabel_15 = createBookLabel(70, 381, 235, 39);
        booksdisplay.add(bookLabel_15);

        bookLabel_16 = createBookLabel(320, 431, 50, 39);
        booksdisplay.add(bookLabel_16);

        bookLabel_17 = createBookLabel(70, 431, 235, 39);
        booksdisplay.add(bookLabel_17);

        bookLabel_18 = createBookLabel(320, 481, 50 ,39);
        booksdisplay.add(bookLabel_18);

        bookLabel_19 = createBookLabel(70, 481, 235, 39);
        booksdisplay.add(bookLabel_19);

        // Create and configure book number labels
        bookNumberLabel = createBookNumberLabel(19, 33, 39, 39);
        booksdisplay.add(bookNumberLabel);

        bookNumberLabel_1 = createBookNumberLabel(19, 83, 39, 39);
        booksdisplay.add(bookNumberLabel_1);

        bookNumberLabel_2 = createBookNumberLabel(19, 133, 39, 39);
        booksdisplay.add(bookNumberLabel_2);

        bookNumberLabel_3 = createBookNumberLabel(19, 183, 39, 39);
        booksdisplay.add(bookNumberLabel_3);

        bookNumberLabel_4 = createBookNumberLabel(19, 233, 39, 39);
        booksdisplay.add(bookNumberLabel_4);

        bookNumberLabel_5 = createBookNumberLabel(19, 283, 39, 39);
        booksdisplay.add(bookNumberLabel_5);

        bookNumberLabel_6 = createBookNumberLabel(19, 333, 39, 39);
        booksdisplay.add(bookNumberLabel_6);

        bookNumberLabel_7 = createBookNumberLabel(19, 383, 39, 39);
        booksdisplay.add(bookNumberLabel_7);

        bookNumberLabel_8 = createBookNumberLabel(19, 433, 39, 39);
        booksdisplay.add(bookNumberLabel_8);

        bookNumberLabel_9 = createBookNumberLabel(19, 483, 39, 39);
        booksdisplay.add(bookNumberLabel_9);
        
        JPanel empruntdisplay = new JPanel();
        empruntdisplay.setBackground(new Color(0, 78, 100));
        empruntdisplay.setBounds(1181, 192, 441, 566);
        frmDashboard.getContentPane().add(empruntdisplay);
        empruntdisplay.setLayout(null);
     // Create and configure emprunt labels
        empruntLabel = createEmpruntLabel(70, 31, 181, 39);
        empruntdisplay.add(empruntLabel);

        empruntLabel_1 = createEmpruntLabel(261, 31, 181, 39);
        empruntdisplay.add(empruntLabel_1);

        empruntLabel_2 = createEmpruntLabel(70, 81, 181, 39);
        empruntdisplay.add(empruntLabel_2);

        empruntLabel_3 = createEmpruntLabel(261, 81, 181, 39);
        empruntdisplay.add(empruntLabel_3);

        empruntLabel_4 = createEmpruntLabel(70, 131, 181, 39);
        empruntdisplay.add(empruntLabel_4);

        empruntLabel_5 = createEmpruntLabel(261, 131, 181, 39);
        empruntdisplay.add(empruntLabel_5);

        empruntLabel_6 = createEmpruntLabel(70, 181, 181, 39);
        empruntdisplay.add(empruntLabel_6);

        empruntLabel_7 = createEmpruntLabel(261, 181, 181, 39);
        empruntdisplay.add(empruntLabel_7);

        empruntLabel_8 = createEmpruntLabel(70, 231, 181, 39);
        empruntdisplay.add(empruntLabel_8);

        empruntLabel_9 = createEmpruntLabel(261, 231, 181, 39);
        empruntdisplay.add(empruntLabel_9);

        empruntLabel_10 = createEmpruntLabel(70, 281, 181, 39);
        empruntdisplay.add(empruntLabel_10);

        empruntLabel_11 = createEmpruntLabel(261, 281, 181, 39);
        empruntdisplay.add(empruntLabel_11);

        empruntLabel_12 = createEmpruntLabel(70, 331, 181, 39);
        empruntdisplay.add(empruntLabel_12);

        empruntLabel_13 = createEmpruntLabel(261, 331, 181, 39);
        empruntdisplay.add(empruntLabel_13);

        empruntLabel_14 = createEmpruntLabel(70, 381, 181, 39);
        empruntdisplay.add(empruntLabel_14);

        empruntLabel_15 = createEmpruntLabel(261, 381, 181, 39);
        empruntdisplay.add(empruntLabel_15);

        empruntLabel_16 = createEmpruntLabel(70, 431, 181, 39);
        empruntdisplay.add(empruntLabel_16);

        empruntLabel_17 = createEmpruntLabel(261, 431, 181, 39);
        empruntdisplay.add(empruntLabel_17);

        empruntLabel_18 = createEmpruntLabel(70, 481, 181, 39);
        empruntdisplay.add(empruntLabel_18);

        empruntLabel_19 = createEmpruntLabel(261, 481, 181, 39);
        empruntdisplay.add(empruntLabel_19);

        // Create and configure ID labels
        empruntidLabel = createIDLabel(19, 33, 39, 39);
        empruntdisplay.add(empruntidLabel);

        empruntidLabel_1 = createIDLabel(19, 83, 39, 39);
        empruntdisplay.add(empruntidLabel_1);

        empruntidLabel_2 = createIDLabel(19, 133, 39, 39);
        empruntdisplay.add(empruntidLabel_2);

        empruntidLabel_3 = createIDLabel(19, 183, 39, 39);
        empruntdisplay.add(empruntidLabel_3);

        empruntidLabel_4 = createIDLabel(19, 233, 39, 39);
        empruntdisplay.add(empruntidLabel_4);

        empruntidLabel_5 = createIDLabel(19, 283, 39, 39);
        empruntdisplay.add(empruntidLabel_5);

        empruntidLabel_6 = createIDLabel(19, 333, 39, 39);
        empruntdisplay.add(empruntidLabel_6);

        empruntidLabel_7 = createIDLabel(19, 383, 39, 39);
        empruntdisplay.add(empruntidLabel_7);

        empruntidLabel_8 = createIDLabel(19, 433, 39, 39);
        empruntdisplay.add(empruntidLabel_8);

        empruntidLabel_9 = createIDLabel(19, 483, 39, 39);
        empruntdisplay.add(empruntidLabel_9);
        
        
        JLabel lbldisplay1 = new JLabel("Student recently added");
        lbldisplay1.setFont(new Font("Jost", Font.BOLD, 23));
        lbldisplay1.setBounds(228, 130, 277, 36);
        frmDashboard.getContentPane().add(lbldisplay1);
        
        JLabel lblBooksRecentlyAdded = new JLabel("Books recently added");
        lblBooksRecentlyAdded.setFont(new Font("Jost", Font.BOLD, 23));
        lblBooksRecentlyAdded.setBounds(748, 130, 277, 36);
        frmDashboard.getContentPane().add(lblBooksRecentlyAdded);
        
        JLabel lbllatereturn = new JLabel("Late returns");
        lbllatereturn.setFont(new Font("Jost", Font.BOLD, 23));
        lbllatereturn.setBounds(1181, 130, 427, 36);
        frmDashboard.getContentPane().add(lbllatereturn);
        empruntdisplay.revalidate();
        empruntdisplay.repaint();
        loadLateEmprunts();
        loadRecentStudents();
        loadRecentBooks();
        frmDashboard.setVisible(true);
    }
    private JLabel createEmpruntLabel(int x, int y, int width, int height) {
    	JLabel label = new JLabel();
    	width=150;
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Jost", Font.BOLD, 17));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK); // Ensure text is visible
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setHorizontalAlignment(SwingConstants.CENTER); // Align text properly
        
        return label;
    }

    private JLabel createIDLabel(int x, int y, int width, int height) {
    	 JLabel label = new JLabel("", SwingConstants.CENTER);
         label.setBounds(x, y, width, height);
         label.setFont(new Font("Jost", Font.BOLD, 14));
         label.setOpaque(true);
         label.setBackground(new Color(200, 200, 200));
         label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }
    
    private JLabel createStudentLabel(int x, int y, int width, int height) {
        JLabel label = new JLabel();
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Jost", Font.BOLD, 17));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK); // Ensure text is visible
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setHorizontalAlignment(SwingConstants.CENTER); // Align text properly
        return label;
    }
    private JLabel createBookLabel(int x, int y, int width, int height) {
        JLabel label = new JLabel();
        //width=181;
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Jost", Font.BOLD, 17));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK); // Ensure text is visible
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setHorizontalAlignment(SwingConstants.CENTER); // Align text properly
        return label;
    }
    
    private JLabel createNumberLabel(int x, int y, int width, int height) {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Jost", Font.BOLD, 14));
        label.setOpaque(true);
        label.setBackground(new Color(200, 200, 200));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }
    private JLabel createBookNumberLabel(int x, int y, int width, int height) {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Jost", Font.BOLD, 14));
        label.setOpaque(true);
        label.setBackground(new Color(200, 200, 200));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }
}