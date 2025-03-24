package application.APP;

import javax.swing.*;

public class Home{
    private JFrame frame;

    public Home() {
        frame = new JFrame("Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel("Welcome to the Home Screen!", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(24f));
        frame.add(label);

        frame.setVisible(true);
    }
}
