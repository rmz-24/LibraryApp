package APP;
import javax.swing.*;
import java.awt.*;
public class ThemeToggleButton extends JToggleButton {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Color LIGHT_BG = new Color(240, 240, 240);
    private final Color DARK_BG = new Color(60, 63, 65);
    private final Color LIGHT_TEXT = Color.BLACK;
    private final Color DARK_TEXT = Color.WHITE;
    
    // iOS-style colors
    private final Color TOGGLE_ON = new Color(76, 217, 100);  // iOS green
    private final Color TOGGLE_OFF = new Color(142, 142, 147); // iOS gray
    private final Color THUMB_COLOR = Color.WHITE;
    
    private JPanel backgroundPanel;
    private JLabel[] themeLabels;

    public ThemeToggleButton(JPanel bgPanel, JLabel... labels) {
        this.backgroundPanel = bgPanel;
        this.themeLabels = labels;
        
        // iOS toggle styling
        setPreferredSize(new Dimension(51, 31));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        
        addActionListener(e -> toggleTheme());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw track background
        g2.setColor(isSelected() ? TOGGLE_ON : TOGGLE_OFF);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        
        // Draw thumb
        int thumbSize = getHeight() - 6;
        int thumbX = isSelected() ? getWidth() - thumbSize - 3 : 3;
        g2.setColor(THUMB_COLOR);
        g2.fillOval(thumbX, 3, thumbSize, thumbSize);
    }

    private void toggleTheme() {
        if (isSelected()) {
            backgroundPanel.setBackground(DARK_BG);
            for (JLabel label : themeLabels) {
                label.setForeground(DARK_TEXT);
            }
        } else {
            backgroundPanel.setBackground(LIGHT_BG);
            for (JLabel label : themeLabels) {
                label.setForeground(LIGHT_TEXT);
            }
        }
        repaint();
    }
}