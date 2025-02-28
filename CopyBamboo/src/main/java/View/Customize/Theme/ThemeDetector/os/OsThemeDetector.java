package View.Customize.Theme.ThemeDetector.os;

import View.Customize.Theme.ThemeDetector.util.OsInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.annotation.concurrent.ThreadSafe;
import java.util.function.Consumer;

/**
 * Abstract class for detecting the dark/light theme used by the Operating
 * System.
 * <p>
 * This class provides methods for detecting the current theme of the operating
 * system (whether it is dark or light). It also supports registering listeners
 * that will be notified when the theme changes. The appropriate theme detection
 * mechanism is selected based on the operating system version and type.
 * </p>
 *
 * <p>
 * Subclasses of this class (such as
 * {@link WindowsThemeDetector}, {@link GnomeThemeDetector}, and
 * {@link MacOSThemeDetector}) implement the OS-specific theme detection logic.
 * </p>
 *
 * <p>
 * If the operating system is not supported, an {@link EmptyDetector} is used,
 * which always returns {@code false} for the dark theme status.
 * </p>
 *
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public abstract class OsThemeDetector {

    private static final Logger logger = LoggerFactory.getLogger(OsThemeDetector.class);

    // Singleton instance of the theme detector
    private static volatile OsThemeDetector osThemeDetector;

    // Private constructor to prevent direct instantiation
    OsThemeDetector() {
    }

    /**
     * Retrieves the singleton instance of the appropriate OS theme detector
     * based on the current operating system.
     *
     * @return the instance of the {@link OsThemeDetector} for the current
     * operating system.
     */
    @NotNull
    @ThreadSafe
    public static OsThemeDetector getDetector() {
        OsThemeDetector instance = osThemeDetector;

        if (instance == null) {
            synchronized (OsThemeDetector.class) {
                instance = osThemeDetector;

                if (instance == null) {
                    osThemeDetector = instance = createDetector();
                }
            }
        }

        return instance;
    }

    /**
     * Creates the appropriate OS theme detector based on the current operating
     * system. This method detects the OS version and desktop environment and
     * creates an appropriate theme detector class.
     *
     * @return the instance of the appropriate OS theme detector.
     */
    private static OsThemeDetector createDetector() {
        if (OsInfo.isWindows10OrLater()) {
            logDetection("Windows 10", WindowsThemeDetector.class);
            return new WindowsThemeDetector();
        } else if (OsInfo.isGnome()) {
            logDetection("Gnome", GnomeThemeDetector.class);
            return new GnomeThemeDetector();
        } else if (OsInfo.isMacOsMojaveOrLater()) {
            logDetection("MacOS", MacOSThemeDetector.class);
            return new MacOSThemeDetector();
        } else {
            logger.debug("Theme detection is not supported on the system: {} {}", OsInfo.getFAMILY(), OsInfo.getVERSION());
            logger.debug("Creating empty detector...");
            return new EmptyDetector();
        }
    }

    /**
     * Logs the detection of a supported desktop environment.
     *
     * @param desktop The name of the detected desktop environment.
     * @param detectorClass The class of the theme detector being used.
     */
    private static void logDetection(String desktop, Class<? extends OsThemeDetector> detectorClass) {
        logger.debug("Supported Desktop detected: {}", desktop);
        logger.debug("Creating {}...", detectorClass.getName());
    }

    /**
     * Checks if the operating system is using a dark theme.
     *
     * @return {@code true} if the OS is using a dark theme, {@code false}
     * otherwise.
     */
    @ThreadSafe
    public abstract boolean isDark();

    /**
     * Registers a listener that will be notified whenever the system's theme
     * changes. The listener will receive a {@link Boolean} indicating whether
     * the system is using a dark theme.
     *
     * @param darkThemeListener the {@link Consumer} that accepts a
     * {@link Boolean} indicating whether the OS is using a dark theme.
     * @throws NullPointerException if the listener is {@code null}.
     */
    @ThreadSafe
    public abstract void registerListener(@NotNull Consumer<Boolean> darkThemeListener);

    /**
     * Removes a previously registered listener from receiving theme change
     * notifications.
     *
     * @param darkThemeListener the listener to be removed.
     */
    @ThreadSafe
    public abstract void removeListener(@Nullable Consumer<Boolean> darkThemeListener);

    /**
     * Checks if theme detection is supported on the current operating system.
     *
     * @return {@code true} if theme detection is supported on the current
     * operating system, {@code false} otherwise.
     */
    @ThreadSafe
    public static boolean isSupported() {
        return OsInfo.isWindows10OrLater() || OsInfo.isMacOsMojaveOrLater() || OsInfo.isGnome();
    }

    /**
     * A dummy implementation of the {@link OsThemeDetector} class used when
     * theme detection is not supported. This class always returns {@code false}
     * for the dark theme and does not support registering or removing
     * listeners.
     */
    private static final class EmptyDetector extends OsThemeDetector {

        @Override
        public boolean isDark() {
            return false;
        }

        @Override
        public void registerListener(@NotNull Consumer<Boolean> darkThemeListener) {
        }

        @Override
        public void removeListener(@Nullable Consumer<Boolean> darkThemeListener) {
        }
    }
}
