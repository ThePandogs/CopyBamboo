package Model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code FileClassifier} class provides utility methods for classifying
 * files based on their extensions. It maintains a mapping between file
 * extensions and their corresponding categories, such as images, videos, music,
 * documents, etc.
 * <p>
 * This class includes methods for retrieving the file type by its extension and
 * for extracting the file extension from a given {@link Path}.
 * </p>
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public class FileClassifier {

    // A map that holds the association between file extensions and their corresponding file types
    private static final Map<String, String> extensionToTypeMap = new HashMap<>();

    static {
        // Pictures
        extensionToTypeMap.put("jpg", "Images");
        extensionToTypeMap.put("jpeg", "Images");
        extensionToTypeMap.put("png", "Images");
        extensionToTypeMap.put("gif", "Images");
        extensionToTypeMap.put("bmp", "Images");
        extensionToTypeMap.put("tiff", "Images");
        extensionToTypeMap.put("tif", "Images");
        extensionToTypeMap.put("svg", "Images");
        extensionToTypeMap.put("webp", "Images");
        extensionToTypeMap.put("ico", "Images");

        // Videos
        extensionToTypeMap.put("mp4", "Videos");
        extensionToTypeMap.put("avi", "Videos");
        extensionToTypeMap.put("mkv", "Videos");
        extensionToTypeMap.put("mov", "Videos");
        extensionToTypeMap.put("wmv", "Videos");
        extensionToTypeMap.put("flv", "Videos");
        extensionToTypeMap.put("webm", "Videos");
        extensionToTypeMap.put("mpg", "Videos");
        extensionToTypeMap.put("mpeg", "Videos");
        extensionToTypeMap.put("3gp", "Videos");

        // Music
        extensionToTypeMap.put("mp3", "Music");
        extensionToTypeMap.put("wav", "Music");
        extensionToTypeMap.put("flac", "Music");
        extensionToTypeMap.put("aac", "Music");
        extensionToTypeMap.put("ogg", "Music");
        extensionToTypeMap.put("wma", "Music");
        extensionToTypeMap.put("m4a", "Music");
        extensionToTypeMap.put("aiff", "Music");

        // Documents
        extensionToTypeMap.put("pdf", "Documents");
        extensionToTypeMap.put("doc", "Documents");
        extensionToTypeMap.put("docx", "Documents");
        extensionToTypeMap.put("xls", "Documents");
        extensionToTypeMap.put("xlsx", "Documents");
        extensionToTypeMap.put("ppt", "Documents");
        extensionToTypeMap.put("pptx", "Documents");
        extensionToTypeMap.put("txt", "Documents");
        extensionToTypeMap.put("odt", "Documents");
        extensionToTypeMap.put("rtf", "Documents");
        extensionToTypeMap.put("md", "Documents");

        // Compressed
        extensionToTypeMap.put("zip", "Compressed");
        extensionToTypeMap.put("rar", "Compressed");
        extensionToTypeMap.put("7z", "Compressed");
        extensionToTypeMap.put("tar", "Compressed");
        extensionToTypeMap.put("gz", "Compressed");
        extensionToTypeMap.put("bz2", "Compressed");
        extensionToTypeMap.put("xz", "Compressed");

        // Executables
        extensionToTypeMap.put("exe", "Executables");
        extensionToTypeMap.put("msi", "Executables");
        extensionToTypeMap.put("bat", "Executables");
        extensionToTypeMap.put("sh", "Executables");
        extensionToTypeMap.put("bin", "Executables");
        extensionToTypeMap.put("jar", "Executables");

        // Scripts
        extensionToTypeMap.put("java", "Code");
        extensionToTypeMap.put("py", "Code");
        extensionToTypeMap.put("js", "Code");
        extensionToTypeMap.put("html", "Code");
        extensionToTypeMap.put("css", "Code");
        extensionToTypeMap.put("cpp", "Code");
        extensionToTypeMap.put("c", "Code");
        extensionToTypeMap.put("h", "Code");
        extensionToTypeMap.put("php", "Code");
        extensionToTypeMap.put("rb", "Code");
        extensionToTypeMap.put("swift", "Code");
        extensionToTypeMap.put("kt", "Code");
        extensionToTypeMap.put("go", "Code");
        extensionToTypeMap.put("rs", "Code");
        extensionToTypeMap.put("ts", "Code");
        extensionToTypeMap.put("xml", "Code");

        // Imágenes vectoriales y CAD
        extensionToTypeMap.put("dwg", "Design");
        extensionToTypeMap.put("dxf", "Design");
        extensionToTypeMap.put("ai", "Design");
        extensionToTypeMap.put("eps", "Design");

        // Others
        extensionToTypeMap.put("iso", "DiskImage");
        extensionToTypeMap.put("dmg", "DiskImage");
        extensionToTypeMap.put("vmdk", "DiskImage");
        extensionToTypeMap.put("img", "DiskImage");
    }

    /**
     * Retrieves the file type associated with the given file extension.
     * <p>
     * This method uses a predefined mapping of file extensions to file types
     * (e.g., "jpg" -> "Images", "mp3" -> "Music"). If the extension is not
     * found in the mapping, it returns "Others" by default.
     * </p>
     *
     * @param extension The file extension (e.g., "jpg", "mp3").
     * @return The corresponding file type as a {@code String}, or "Others" if
     * the extension is not found in the map.
     */
    public static String getFileTypeByExtension(String extension) {
        return extensionToTypeMap.getOrDefault(extension.toLowerCase(), "Others");
    }

    /**
     * Extracts the file extension from the given {@link Path}.
     * <p>
     * The extension is determined by finding the substring after the last
     * period ('.') in the file name. If no period is found, an empty string is
     * returned.
     * </p>
     *
     * @param file The {@link Path} object representing the file.
     * @return The file extension as a {@code String}, or an empty string if the
     * file has no extension.
     */
    public static String getFileExtension(Path file) {
        String fileName = file.getFileName().toString();
        int lastIndex = fileName.lastIndexOf('.');
        return (lastIndex == -1) ? "" : fileName.substring(lastIndex + 1);
    }
}
