package APP;

import javax.swing.*;

import java.awt.Color;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Home {
	JLabel bookLabel, availableQtyLabel_1, availableQtyLabel_2, bookLabel_3, availableQtyLabel_4,
    bookLabel_5, availableQtyLabel_6, bookLabel_7, availableQtyLabel_8, bookLabel_9,
    availableQtyLabel_10, bookLabel_11, availableQtyLabel_12, bookLabel_13, availableQtyLabel_14,
    bookLabel_15, availableQtyLabel_16, bookLabel_17, availableQtyLabel_18, bookLabel_19;

	JLabel bookNumberLabel, bookNumberLabel_1, bookNumberLabel_2, bookNumberLabel_3, bookNumberLabel_4,
    bookNumberLabel_5, bookNumberLabel_6, bookNumberLabel_7, bookNumberLabel_8, bookNumberLabel_9;
	private void loadRecentBooks() {
	    JLabel[] bookNameLabels = {
	        
	        
	        bookLabel, bookLabel_3, bookLabel_5, bookLabel_7, bookLabel_9,
	        bookLabel_11, bookLabel_13, bookLabel_15, bookLabel_17, bookLabel_19
	    };

	    JLabel[] availableQtyLabels = {
	    		availableQtyLabel_1, availableQtyLabel_2, availableQtyLabel_4, availableQtyLabel_6, availableQtyLabel_8,
	    		availableQtyLabel_10, availableQtyLabel_12, availableQtyLabel_14, availableQtyLabel_16, availableQtyLabel_18
	        
	    };

	    java.sql.Connection conn = LibraryApp.getConnection();
	    final String query = "SELECT BOOKNAME, AVAILABLE_QTY FROM (SELECT BOOKNAME, AVAILABLE_QTY, ROW_NUMBER() OVER (ORDER BY BOOKID  DESC) AS rn FROM BOOKSLIST) WHERE rn <= 10";

	    try (PreparedStatement stmtn = conn.prepareStatement(query)) {
	        ResultSet rs = stmtn.executeQuery();

	        int bookCount = 0;
	        while (rs.next() && bookCount < bookNameLabels.length) {
	            String bookName = rs.getString("BOOKNAME");
	            String availableQty = "  " + rs.getInt("AVAILABLE_QTY");

	            System.out.println("Book: " + bookName + ", Available Quantity: " + availableQty);

	            bookNameLabels[bookCount].setText(bookName);
	            availableQtyLabels[bookCount].setText(availableQty);

	            bookNameLabels[bookCount].revalidate();
	            bookNameLabels[bookCount].repaint();
	            availableQtyLabels[bookCount].revalidate();
	            availableQtyLabels[bookCount].repaint();

	            bookCount++;
	        }

	        // Clear remaining labels if fewer than 10 books are loaded
	        for (int i = bookCount; i < bookNameLabels.length; i++) {
	            bookNameLabels[i].setText("");
	            availableQtyLabels[i].setText("");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(frmDashboard, 
	            "Error loading recent books: " + e.getMessage(), 
	            "Database Error", JOptionPane.ERROR_MESSAGE);
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
        frmDashboard.setSize(1500, 901);
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
        studentManagementLabel.setBounds(27, 216, 152, 21);
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
        bookManagementButton.setBounds(40, 265, 112, 68);
        bookManagementButton.setContentAreaFilled(false);
        bookManagementButton.setBorderPainted(false);
        bookManagementButton.setFocusPainted(false);
        bookManagementButton.setOpaque(false);
        bookManagementButton.setToolTipText("Opens a window to the book management dashboard");
        panel.add(bookManagementButton);
        
        JLabel addBookLabel = new JLabel("Loan and Return");
        addBookLabel.setVerticalAlignment(SwingConstants.TOP);
        addBookLabel.setBounds(43, 470, 129, 21);
        addBookLabel.setForeground(Color.WHITE);
        addBookLabel.setFont(new Font("Jost", Font.BOLD, 15));
        panel.add(addBookLabel);
        
        JButton removeBookButton = new JButton("");
        removeBookButton.setIcon(new ImageIcon("src\\resrc\\exclamation.png"));
        removeBookButton.setBounds(40, 523, 112, 68);
        removeBookButton.setContentAreaFilled(false);
        removeBookButton.setBorderPainted(false);
        removeBookButton.setFocusPainted(false);
        removeBookButton.setOpaque(false);
        removeBookButton.setToolTipText("Removes a book from the library's database");
        panel.add(removeBookButton);
        
        JLabel removeBookLabel = new JLabel("Report");
        removeBookLabel.setHorizontalAlignment(SwingConstants.CENTER);
        removeBookLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        removeBookLabel.setBounds(40, 604, 102, 21);
        removeBookLabel.setForeground(Color.WHITE);
        removeBookLabel.setFont(new Font("Jost", Font.BOLD, 15));
        panel.add(removeBookLabel);
        
        JButton borrowBookButton = new JButton("");
        borrowBookButton.setIcon(new ImageIcon("src\\resrc\\borrow_book.png"));
        borrowBookButton.setBounds(40, 395, 112, 68);
        borrowBookButton.setContentAreaFilled(false);
        borrowBookButton.setBorderPainted(false);
        borrowBookButton.setFocusPainted(false);
        borrowBookButton.setOpaque(false);
        borrowBookButton.setToolTipText("Logs a borrowed book to the database");
        panel.add(borrowBookButton);
        
        JLabel booksManagementLabel = new JLabel("Books Management");
        booksManagementLabel.setForeground(Color.WHITE);
        booksManagementLabel.setFont(new Font("Jost", Font.BOLD, 15));
        booksManagementLabel.setBounds(40, 344, 132, 21);
        panel.add(booksManagementLabel);
        
        JLabel managementLabel = new JLabel(" \r\nManagement");
        managementLabel.setVerticalAlignment(SwingConstants.TOP);
        managementLabel.setForeground(Color.WHITE);
        managementLabel.setFont(new Font("Jost", Font.BOLD, 15));
        managementLabel.setBounds(53, 491, 92, 21);
        panel.add(managementLabel);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(145, 149, 153));
        panel_1.setBounds(201, 0, 1494, 88);
        frmDashboard.getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        JLabel connectedAsLabel = new JLabel("CONNECTED AS:");
        connectedAsLabel.setFont(new Font("Jost", Font.BOLD, 20));
        connectedAsLabel.setBounds(928, 11, 178, 16);
        panel_1.add(connectedAsLabel);
        
        JLabel accessLevelLabel = new JLabel("ACCESS LEVEL :");
        accessLevelLabel.setFont(new Font("Jost", Font.BOLD, 20));
        accessLevelLabel.setBounds(926, 48, 168, 16);
        panel_1.add(accessLevelLabel);
        
        JLabel usernameLabel = new JLabel(user);
        usernameLabel.setFont(new Font("Jost", Font.BOLD, 20));
        usernameLabel.setBounds(1116, 11, 120, 16);
        panel_1.add(usernameLabel);
        
        JLabel accessLevelLabel1 = new JLabel(level);
        accessLevelLabel1.setFont(new Font("Jost", Font.BOLD, 20));
        accessLevelLabel1.setBounds(1116, 48, 120, 16);
        panel_1.add(accessLevelLabel1);
        
        JButton addUserButton = new JButton("");
        addUserButton.addActionListener(e -> openWindow(new AdminPermissionsWindow(user, level)));
        addUserButton.setIcon(new ImageIcon("src\\resrc\\add_16321386.png"));
        addUserButton.setBounds(818, 11, 70, 66);
        addUserButton.setContentAreaFilled(false);
        addUserButton.setBorderPainted(false);
        addUserButton.setFocusPainted(false);
        addUserButton.setOpaque(false);
        addUserButton.setToolTipText("Create a new user");
        addUserButton.setVisible(level.equals("ADMIN"));
        panel_1.add(addUserButton);
        
        JButton logOutButton = new JButton("");
        logOutButton.setContentAreaFilled(false);
        logOutButton.setToolTipText("Log out from the app");
        logOutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frmDashboard.dispose();
                LibraryApp.main(null);
            }
        });
        logOutButton.setIcon(new ImageIcon("src\\resrc\\log-out_10024482.png"));
        logOutButton.setBounds(1411, 971, 65, 65);
        logOutButton.setContentAreaFilled(false);
        logOutButton.setBorderPainted(false);
        logOutButton.setFocusPainted(false);
        logOutButton.setOpaque(false);
        frmDashboard.getContentPane().add(logOutButton);
        
        JPanel studentsdisplay = new JPanel();
        studentsdisplay.setBackground(new Color(0, 165, 207));
        studentsdisplay.setBounds(212, 192, 479, 566);
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
        booksdisplay.setBounds(701, 192, 325, 566);
        frmDashboard.getContentPane().add(booksdisplay);
        booksdisplay.setBackground(new Color(37, 161, 142));
        booksdisplay.setLayout(null);
        
       

        // Create and configure book name labels
        bookLabel = createBookLabel(70, 31, 181, 39);
        booksdisplay.add(bookLabel);

        availableQtyLabel_1 = createBookLabel(261, 31, 50, 39);
        booksdisplay.add(availableQtyLabel_1);

        availableQtyLabel_2 = createBookLabel(261, 81, 50, 39);
        booksdisplay.add(availableQtyLabel_2);

        bookLabel_3 = createBookLabel(70, 81, 181, 39);
        booksdisplay.add(bookLabel_3);

        availableQtyLabel_4 = createBookLabel(261, 131, 50, 39);
        booksdisplay.add(availableQtyLabel_4);

        bookLabel_5 = createBookLabel(70, 131, 181, 39);
        booksdisplay.add(bookLabel_5);

        availableQtyLabel_6 = createBookLabel(261, 181, 50, 39);
        booksdisplay.add(availableQtyLabel_6);

        bookLabel_7 = createBookLabel(70, 181, 181, 39);
        booksdisplay.add(bookLabel_7);

        availableQtyLabel_8 = createBookLabel(261, 231, 50, 39);
        booksdisplay.add(availableQtyLabel_8);

        bookLabel_9 = createBookLabel(70, 231, 181, 39);
        booksdisplay.add(bookLabel_9);

        availableQtyLabel_10 = createBookLabel(261, 281, 50, 39);
        booksdisplay.add(availableQtyLabel_10);

        bookLabel_11 = createBookLabel(70, 281, 181, 39);
        booksdisplay.add(bookLabel_11);

        availableQtyLabel_12 = createBookLabel(261, 331, 50, 39);
        booksdisplay.add(availableQtyLabel_12);

        bookLabel_13 = createBookLabel(70, 331, 181, 39);
        booksdisplay.add(bookLabel_13);

        availableQtyLabel_14 = createBookLabel(261, 381, 50, 39);
        booksdisplay.add(availableQtyLabel_14);

        bookLabel_15 = createBookLabel(70, 381, 181, 39);
        booksdisplay.add(bookLabel_15);

        availableQtyLabel_16 = createBookLabel(261, 431, 50, 39);
        booksdisplay.add(availableQtyLabel_16);

        bookLabel_17 = createBookLabel(70, 431, 181, 39);
        booksdisplay.add(bookLabel_17);

        availableQtyLabel_18 = createBookLabel(261, 481, 50 ,39);
        booksdisplay.add(availableQtyLabel_18);

        bookLabel_19 = createBookLabel(70, 481, 181, 39);
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
        empruntdisplay.setBounds(1036, 192, 441, 566);
        frmDashboard.getContentPane().add(empruntdisplay);
        empruntdisplay.setLayout(null);
        
        JLabel lbldisplay1 = new JLabel("Student recently added");
        lbldisplay1.setFont(new Font("Jost", Font.BOLD, 23));
        lbldisplay1.setBounds(211, 99, 277, 36);
        frmDashboard.getContentPane().add(lbldisplay1);
        
        JLabel lblBooksRecentlyAdded = new JLabel("Books recently added");
        lblBooksRecentlyAdded.setFont(new Font("Jost", Font.BOLD, 23));
        lblBooksRecentlyAdded.setBounds(700, 99, 277, 36);
        frmDashboard.getContentPane().add(lblBooksRecentlyAdded);
        
        JLabel lblBookRecentlyBorrowed = new JLabel("Book recently borrowed");
        lblBookRecentlyBorrowed.setFont(new Font("Jost", Font.BOLD, 23));
        lblBookRecentlyBorrowed.setBounds(1035, 99, 427, 36);
        frmDashboard.getContentPane().add(lblBookRecentlyBorrowed);
        loadRecentStudents();
        loadRecentBooks();
        frmDashboard.setVisible(true);
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
