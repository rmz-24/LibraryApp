package APP;

import java.awt.Color;

public class ThemeManager {
    public static final Color DARK_BACKGROUND = new Color(60, 63, 65);
    public static final Color DARK_FOREGROUND = Color.WHITE;
    public static final Color LIGHT_BACKGROUND = new Color(238, 238, 238);
    public static final Color LIGHT_FOREGROUND = Color.BLACK;
    
    private static boolean darkMode = false;
    
    public static boolean isDarkMode() {
        return darkMode;
    }
    
    public static void toggleTheme() {
        darkMode = !darkMode;
    }
}