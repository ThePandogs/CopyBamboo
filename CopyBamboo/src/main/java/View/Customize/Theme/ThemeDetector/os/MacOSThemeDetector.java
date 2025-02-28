package View.Customize.Theme.ThemeDetector.os;

import com.jthemedetecor.util.ConcurrentHashSet;
import com.sun.jna.Callback;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * Detects whether the system theme on a macOS device is dark or light using the
 * Apple Foundation framework.
 * <p>
 * This class interfaces with macOS APIs to observe the system's theme changes
 * and check whether the current theme is dark or light. It utilizes the
 * `AppleInterfaceStyle` key from `NSUserDefaults` and listens for system
 * notifications about theme changes.
 * </p>
 *
 * <p>
 * When the theme is changed, this class notifies registered listeners with the
 * updated theme status (dark or light).
 * </p>
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
class MacOSThemeDetector extends OsThemeDetector {

    private static final Logger logger = LoggerFactory.getLogger(MacOSThemeDetector.class);

    // Set of listeners that will be notified when the theme changes
    private final Set<Consumer<Boolean>> listeners = new ConcurrentHashSet<>();
    // Pattern for detecting dark theme in the system's theme name
    private final Pattern themeNamePattern = Pattern.compile(".*dark.*", Pattern.CASE_INSENSITIVE);
    // Executor for handling callback asynchronously
    private final ExecutorService callbackExecutor = Executors.newSingleThreadExecutor(DetectorThread::new);

    // Callback for when the theme changes
    private final Callback themeChangedCallback = new Callback() {
        @SuppressWarnings("unused")
        public void callback() {
            callbackExecutor.execute(() -> notifyListeners(isDark()));
        }
    };

    /**
     * Initializes the theme observer to listen for system theme changes.
     * Registers a listener to monitor the theme status on macOS.
     */
    MacOSThemeDetector() {
        initObserver();
    }

    /**
     * Sets up the observer for macOS theme changes using Apple's Foundation
     * framework. It listens for the notification
     * {@code AppleInterfaceThemeChangedNotification} to detect theme changes.
     */
    private void initObserver() {
        final Foundation.NSAutoreleasePool pool = new Foundation.NSAutoreleasePool();
        try {
            final ID delegateClass = Foundation.allocateObjcClassPair(Foundation.getObjcClass("NSObject"), "NSColorChangesObserver");
            if (!ID.NIL.equals(delegateClass)) {
                if (!Foundation.addMethod(delegateClass, Foundation.createSelector("handleAppleThemeChanged:"), themeChangedCallback, "v@")) {
                    logger.error("Observer method cannot be added");
                }
                Foundation.registerObjcClassPair(delegateClass);
            }

            final ID delegate = Foundation.invoke("NSColorChangesObserver", "new");
            Foundation.invoke(
                    Foundation.invoke("NSDistributedNotificationCenter", "defaultCenter"),
                    "addObserver:selector:name:object:",
                    delegate,
                    Foundation.createSelector("handleAppleThemeChanged:"),
                    Foundation.nsString("AppleInterfaceThemeChangedNotification"),
                    ID.NIL);
        } finally {
            pool.drain();
        }
    }

    /**
     * Checks if the current theme on macOS is dark by querying the system's
     * user defaults.
     *
     * @return {@code true} if the system theme is dark, {@code false} if it is
     * light.
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public boolean isDark() {
        final Foundation.NSAutoreleasePool pool = new Foundation.NSAutoreleasePool();
        try {
            final ID userDefaults = Foundation.invoke("NSUserDefaults", "standardUserDefaults");
            final String appleInterfaceStyle = Foundation.toStringViaUTF8(Foundation.invoke(userDefaults, "objectForKey:", Foundation.nsString("AppleInterfaceStyle")));
            return isDarkTheme(appleInterfaceStyle);
        } catch (RuntimeException e) {
            logger.error("Couldn't execute theme name query with the Os", e);
        } finally {
            pool.drain();
        }
        return false;
    }

    /**
     * Determines if the provided theme name corresponds to a dark theme.
     *
     * @param themeName The name of the theme.
     * @return {@code true} if the theme is dark, {@code false} otherwise.
     */
    private boolean isDarkTheme(String themeName) {
        return themeName != null && themeNamePattern.matcher(themeName).matches();
    }

    /**
     * Registers a listener to be notified whenever the theme changes on macOS.
     *
     * @param darkThemeListener The listener to be notified of theme changes.
     * @throws NullPointerException if the listener is {@code null}.
     */
    @Override
    public void registerListener(@NotNull Consumer<Boolean> darkThemeListener) {
        listeners.add(darkThemeListener);
    }

    /**
     * Removes a previously registered listener from receiving theme change
     * notifications.
     *
     * @param darkThemeListener The listener to be removed.
     */
    @Override
    public void removeListener(@Nullable Consumer<Boolean> darkThemeListener) {
        listeners.remove(darkThemeListener);
    }

    /**
     * Notifies all registered listeners with the current theme status (dark or
     * light).
     *
     * @param isDark {@code true} if the current theme is dark, {@code false}
     * otherwise.
     */
    private void notifyListeners(boolean isDark) {
        listeners.forEach(listener -> listener.accept(isDark));
    }

    /**
     * A background thread used to handle theme change notifications
     * asynchronously.
     * <p>
     * This thread runs in the background and ensures theme change events are
     * processed in a non-blocking manner.
     * </p>
     */
    private static final class DetectorThread extends Thread {

        DetectorThread(@NotNull Runnable runnable) {
            super(runnable);
            setName("MacOS Theme Detector Thread");
            setDaemon(true);
        }
    }
}
