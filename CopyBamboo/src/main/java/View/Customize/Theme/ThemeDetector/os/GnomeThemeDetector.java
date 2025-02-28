package View.Customize.Theme.ThemeDetector.os;

import com.jthemedetecor.util.ConcurrentHashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * Detects the current dark theme status on Linux systems using GNOME/GTK.
 * <p>
 * This class is specifically designed to work with the GNOME desktop
 * environment on Linux (such as Ubuntu), and it monitors the theme settings
 * using `gsettings`.
 * </p>
 * <p>
 * The detection is done by reading the system's theme settings and by
 * monitoring any changes in the GTK theme. When the theme changes, registered
 * listeners are notified of the current theme's status (dark or light).
 * </p>
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
class GnomeThemeDetector extends OsThemeDetector {

    private static final Logger logger = LoggerFactory.getLogger(GnomeThemeDetector.class);

    // Command to monitor GNOME theme changes
    private static final String MONITORING_CMD = "gsettings monitor org.gnome.desktop.interface";

    // Commands to get the current theme and color scheme settings
    private static final String[] GET_CMD = new String[]{
        "gsettings get org.gnome.desktop.interface gtk-theme",
        "gsettings get org.gnome.desktop.interface color-scheme"
    };

    private final Set<Consumer<Boolean>> listeners = new ConcurrentHashSet<>();
    private final Pattern darkThemeNamePattern = Pattern.compile(".*dark.*", Pattern.CASE_INSENSITIVE);

    private volatile DetectorThread detectorThread;

    /**
     * Determines if the current theme is dark or not by executing system
     * commands.
     *
     * @return {@code true} if the system's GTK theme indicates a dark theme,
     * {@code false} otherwise.
     */
    @Override
    public boolean isDark() {
        try {
            Runtime runtime = Runtime.getRuntime();
            for (String cmd : GET_CMD) {
                Process process = runtime.exec(cmd);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String readLine = reader.readLine();
                    if (readLine != null && isDarkTheme(readLine)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Couldn't detect Linux OS theme", e);
        }
        return false;
    }

    /**
     * Checks if the provided GTK theme name matches the pattern for a dark
     * theme.
     *
     * @param gtkTheme The GTK theme name.
     * @return {@code true} if the theme is dark, {@code false} otherwise.
     */
    private boolean isDarkTheme(String gtkTheme) {
        return darkThemeNamePattern.matcher(gtkTheme).matches();
    }

    /**
     * Registers a listener that will be notified whenever the theme changes.
     * <p>
     * The listener will receive a boolean indicating whether the current theme
     * is dark (true) or light (false).
     * </p>
     *
     * @param darkThemeListener The listener to be notified of theme changes.
     * @throws NullPointerException if the listener is {@code null}.
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public synchronized void registerListener(@NotNull Consumer<Boolean> darkThemeListener) {
        Objects.requireNonNull(darkThemeListener);
        final boolean listenerAdded = listeners.add(darkThemeListener);
        final boolean singleListener = listenerAdded && listeners.size() == 1;
        final DetectorThread currentDetectorThread = detectorThread;
        final boolean threadInterrupted = currentDetectorThread != null && currentDetectorThread.isInterrupted();

        if (singleListener || threadInterrupted) {
            final DetectorThread newDetectorThread = new DetectorThread(this);
            this.detectorThread = newDetectorThread;
            newDetectorThread.start();
        }
    }

    /**
     * Removes a listener that was previously registered for theme change
     * notifications.
     *
     * @param darkThemeListener The listener to be removed.
     */
    @Override
    public synchronized void removeListener(@Nullable Consumer<Boolean> darkThemeListener) {
        listeners.remove(darkThemeListener);
        if (listeners.isEmpty()) {
            this.detectorThread.interrupt();
            this.detectorThread = null;
        }
    }

    /**
     * A background thread that monitors changes to the system's GTK theme and
     * notifies listeners when a theme change occurs.
     * <p>
     * This thread continuously monitors for changes in the theme and calls the
     * listeners whenever the theme switches between dark and light.
     * </p>
     */
    private static final class DetectorThread extends Thread {

        private final GnomeThemeDetector detector;
        private final Pattern outputPattern = Pattern.compile("(gtk-theme|color-scheme).*", Pattern.CASE_INSENSITIVE);
        private boolean lastValue;

        DetectorThread(@NotNull GnomeThemeDetector detector) {
            this.detector = detector;
            this.lastValue = detector.isDark();
            this.setName("GTK Theme Detector Thread");
            this.setDaemon(true);
            this.setPriority(Thread.NORM_PRIORITY - 1);
        }

        @Override
        public void run() {
            try {
                Runtime runtime = Runtime.getRuntime();
                Process monitoringProcess = runtime.exec(MONITORING_CMD);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(monitoringProcess.getInputStream()))) {
                    while (!this.isInterrupted()) {
                        // Expected input = gtk-theme: '$GtkThemeName'
                        String readLine = reader.readLine();

                        // reader.readLine sometimes returns null on application shutdown.
                        if (readLine == null) {
                            continue;
                        }

                        if (!outputPattern.matcher(readLine).matches()) {
                            continue;
                        }
                        String[] keyValue = readLine.split("\\s");
                        String value = keyValue[1];
                        boolean currentDetection = detector.isDarkTheme(value);
                        logger.debug("Theme changed detection, dark: {}", currentDetection);
                        if (currentDetection != lastValue) {
                            lastValue = currentDetection;
                            for (Consumer<Boolean> listener : detector.listeners) {
                                try {
                                    listener.accept(currentDetection);
                                } catch (RuntimeException e) {
                                    logger.error("Caught exception during listener notifying ", e);
                                }
                            }
                        }
                    }
                    logger.debug("ThemeDetectorThread has been interrupted!");
                    if (monitoringProcess.isAlive()) {
                        monitoringProcess.destroy();
                        logger.debug("Monitoring process has been destroyed!");
                    }
                }
            } catch (IOException e) {
                logger.error("Couldn't start monitoring process ", e);
            } catch (ArrayIndexOutOfBoundsException e) {
                logger.error("Couldn't parse command line output", e);
            }
        }
    }
}
