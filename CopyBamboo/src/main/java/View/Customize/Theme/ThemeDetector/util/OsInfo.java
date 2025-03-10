package View.Customize.Theme.ThemeDetector.util;

import View.Customize.Theme.ThemeDetector.os.OsThemeDetector;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.software.os.OperatingSystem;
import io.github.g00fy2.versioncompare.Version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OsInfo {

    private static final Logger logger = LoggerFactory.getLogger(OsThemeDetector.class);

    private static final PlatformEnum PLATFORM_TYPE;
    private static final String FAMILY;
    private static final String VERSION;

    static {
        final SystemInfo systemInfo = new SystemInfo();
        final OperatingSystem osInfo = systemInfo.getOperatingSystem();
        final OperatingSystem.OSVersionInfo osVersionInfo = osInfo.getVersionInfo();

        PLATFORM_TYPE = SystemInfo.getCurrentPlatform();
        FAMILY = osInfo.getFamily();
        VERSION = osVersionInfo.getVersion();
    }

    public static boolean isWindows10OrLater() {
        return hasTypeAndVersionOrHigher(PlatformEnum.WINDOWS, "10");
    }

    public static boolean isLinux() {
        return hasType(PlatformEnum.LINUX);
    }

    public static boolean isMacOsMojaveOrLater() {
        return hasTypeAndVersionOrHigher(PlatformEnum.MACOS, "10.14");
    }

    public static boolean isGnome() {
        return isLinux() && (queryResultContains("echo $XDG_CURRENT_DESKTOP", "gnome")
                || queryResultContains("echo $XDG_DATA_DIRS | grep -Eo 'gnome'", "gnome")
                || queryResultContains("ps -e | grep -E -i \"gnome\"", "gnome"));
    }

    public static boolean hasType(PlatformEnum platformType) {
        return OsInfo.PLATFORM_TYPE.equals(platformType);
    }

    public static boolean isVersionAtLeast(String version) {
        return new Version(OsInfo.VERSION).isAtLeast(version);
    }

    public static boolean hasTypeAndVersionOrHigher(PlatformEnum platformType, String version) {
        return hasType(platformType) && isVersionAtLeast(version);
    }

    public static String getVERSION() {
        return VERSION;
    }

    public static String getFAMILY() {
        return FAMILY;
    }

    private static boolean queryResultContains(@NotNull String cmd, @NotNull String subResult) {
        return query(cmd).toLowerCase().contains(subResult);
    }

    @NotNull
    private static String query(@NotNull String cmd) {
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String actualReadLine;
                while ((actualReadLine = reader.readLine()) != null) {
                    if (stringBuilder.length() != 0) {
                        stringBuilder.append('\n');
                    }
                    stringBuilder.append(actualReadLine);
                }
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            logger.error("Exception caught while querying the OS", e);
            return "";
        }
    }

    private OsInfo() {
    }
}
