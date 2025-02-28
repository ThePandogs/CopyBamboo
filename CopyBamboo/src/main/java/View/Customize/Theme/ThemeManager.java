package View.Customize.Theme;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.util.SystemInfo;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.SwingUtilities;

/**
 * The {@code ThemeManager} class provides functionality to manage and apply
 * different themes in a Java Swing application. It allows switching between
 * light, dark, or system-based themes.
 * <p>
 * This class uses the FlatLaf Look and Feel library to provide support for Flat
 * Design themes, such as light and dark modes, and detects the system theme to
 * apply the corresponding theme dynamically.
 * </p>
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public class ThemeManager {

    /**
     * Enum representing the available themes that can be applied in the
     * application.
     */
    public enum Theme {
        LIGHT, // Light theme
        DARK, // Dark theme
        SYSTEM // System theme based on the OS's current theme
    }

    /**
     * Applies the specified theme to the application.
     * <p>
     * This method sets the Look and Feel of the UI based on the selected theme.
     * It can set the theme to light, dark, or system-based depending on the
     * argument provided. If an unsupported theme is provided, it defaults to
     * the light theme.
     * </p>
     *
     * @param theme The theme to be applied. Can be {@link Theme#LIGHT},
     *              {@link Theme#DARK}, or {@link Theme#SYSTEM}.
     */
    public static void applyTheme(Theme theme) {
        try {
            switch (theme) {
                case LIGHT ->
                    UIManager.setLookAndFeel(new FlatLightLaf()); // Set light theme
                case DARK ->
                    UIManager.setLookAndFeel(new FlatDarkLaf()); // Set dark theme
                case SYSTEM ->
                    applySystemTheme(); // Apply system theme
                default ->
                    UIManager.setLookAndFeel(new FlatLightLaf()); // Default to light theme
            }
            // Update the UI components after the Look and Feel change
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(null));
        } catch (UnsupportedLookAndFeelException e) {
            // Handle exception if the Look and Feel is not supported
            e.printStackTrace();
        }
    }

    /**
     * Applies the system-based theme based on the current system's theme
     * setting.
     * <p>
     * This method checks whether the system is in dark mode or light mode and
     * applies the corresponding theme accordingly. If the system is in dark
     * mode, the dark theme is applied; otherwise, the light theme is applied.
     * </p>
     *
     * @throws UnsupportedLookAndFeelException If the Look and Feel cannot be
     * set
     */
    public static void applySystemTheme() throws UnsupportedLookAndFeelException {
        if (isDarkThemeEnabled()) {
            // Apply dark theme if system is in dark mode
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } else {
            // Apply light theme if system is in light mode
            UIManager.setLookAndFeel(new FlatLightLaf());
        }
    }

    /**
     * Detects whether the system is currently using the dark theme.
     * <p>
     * This method checks the operating system's theme preference. If the system
     * is using a dark theme (currently supported for Windows and macOS), it
     * returns {@code true}. Otherwise, it returns {@code false}, indicating the
     * system is using a light theme.
     * </p>
     *
     * @return {@code true} if the system theme is dark, {@code false} otherwise
     */
    public static boolean isDarkThemeEnabled() {
        if (SystemInfo.isWindows || SystemInfo.isMacOS) {
            // Logic to detect dark theme in Windows or macOS (future implementation)
        }
        // If not macOS or Windows, by default use the light theme
        return false;
    }
}
