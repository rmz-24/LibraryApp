package LibraryApp;

import javax.swing.*;

public class LibraryApp extends JFrame {
    private JPanel mainPanel;
    private JPanel greenfield;
    // private JButton PRESSMEButton;

    public LibraryApp() {
        setTitle("Library App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 600);
        setContentPane(mainPanel); // ✅ Load designed UI



        setVisible(true);
    }
    public static void main(String[] args) {
        // Run the GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryApp();
            }
        });
    }
}
