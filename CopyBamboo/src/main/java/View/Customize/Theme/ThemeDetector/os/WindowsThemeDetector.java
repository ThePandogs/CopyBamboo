/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package View.Customize.Theme.ThemeDetector.os;

import com.jthemedetecor.util.ConcurrentHashSet;
import com.sun.jna.platform.win32.Advapi32;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.W32Errors;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinReg;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Detects the dark/light theme in Windows systems through the Windows Registry.
 * <p>
 * This class uses JNA (Java Native Access) to read values from the Windows
 * Registry to determine whether the system is using a dark or light theme. It
 * specifically checks the "AppsUseLightTheme" registry value under the path:
 * "Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize".
 * </p>
 *
 * <p>
 * Listeners can be registered to be notified when the theme changes. When the
 * theme changes, listeners are called with the new theme state.
 * </p>
 *
 * <p>
 * A separate thread is used to monitor registry changes and detect theme
 * changes in real-time.
 * </p>
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
class WindowsThemeDetector extends OsThemeDetector {

    private static final Logger logger = LoggerFactory.getLogger(WindowsThemeDetector.class);

    private static final String REGISTRY_PATH = "Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize";
    private static final String REGISTRY_VALUE = "AppsUseLightTheme";

    private final Set<Consumer<Boolean>> listeners = new ConcurrentHashSet<>();
    private volatile DetectorThread detectorThread;

    WindowsThemeDetector() {
    }

    @Override
    public boolean isDark() {
        return Advapi32Util.registryValueExists(WinReg.HKEY_CURRENT_USER, REGISTRY_PATH, REGISTRY_VALUE)
                && Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, REGISTRY_PATH, REGISTRY_VALUE) == 0;
    }

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

    @Override
    public synchronized void removeListener(@Nullable Consumer<Boolean> darkThemeListener) {
        listeners.remove(darkThemeListener);
        if (listeners.isEmpty()) {
            this.detectorThread.interrupt();
            this.detectorThread = null;
        }
    }

    /**
     * Thread implementation for detecting the theme changes
     */
    private static final class DetectorThread extends Thread {

        private final WindowsThemeDetector themeDetector;

        private boolean lastValue;

        DetectorThread(WindowsThemeDetector themeDetector) {
            this.themeDetector = themeDetector;
            this.lastValue = themeDetector.isDark();
            this.setName("Windows 10 Theme Detector Thread");
            this.setDaemon(true);
            this.setPriority(Thread.NORM_PRIORITY - 1);
        }

        @Override
        public void run() {
            WinReg.HKEYByReference hkey = new WinReg.HKEYByReference();
            int err = Advapi32.INSTANCE.RegOpenKeyEx(WinReg.HKEY_CURRENT_USER, REGISTRY_PATH, 0, WinNT.KEY_READ, hkey);
            if (err != W32Errors.ERROR_SUCCESS) {
                throw new Win32Exception(err);
            }

            while (!this.isInterrupted()) {
                err = Advapi32.INSTANCE.RegNotifyChangeKeyValue(hkey.getValue(), false, WinNT.REG_NOTIFY_CHANGE_LAST_SET, null, false);
                if (err != W32Errors.ERROR_SUCCESS) {
                    throw new Win32Exception(err);
                }

                boolean currentDetection = themeDetector.isDark();
                if (currentDetection != this.lastValue) {
                    lastValue = currentDetection;
                    logger.debug("Theme change detected: dark: {}", currentDetection);
                    for (Consumer<Boolean> listener : themeDetector.listeners) {
                        try {
                            listener.accept(currentDetection);
                        } catch (RuntimeException e) {
                            logger.error("Caught exception during listener notifying ", e);
                        }
                    }
                }
            }
            Advapi32Util.registryCloseKey(hkey.getValue());
        }
    }
}
