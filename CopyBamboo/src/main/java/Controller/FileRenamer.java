package Controller;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The FileRenamer class provides functionality to rename files based on their
 * creation or modification date. The new file name will be generated in the
 * format "yyyy-MM-dd_HH-mm-ss" followed by the original file extension.
 * <p>
 * The renaming process keeps the directory structure intact while renaming the
 * file itself.
 * </p>
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public class FileRenamer {

    // Formatter to format the date into "yyyy-MM-dd_HH-mm-ss"
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    /**
     * Renames the provided file based on its creation or modification date. If
     * the provided date is null, the original file path is returned without any
     * changes. The new file name is generated using the date in the format
     * "yyyy-MM-dd_HH-mm-ss" followed by the file's original extension.
     *
     * @param originalFilePath the original path of the file to be renamed.
     * @param fileDate the date to use for renaming the file (could be creation
     * or modification date).
     * @return a new Path object with the renamed file while maintaining the
     * original directory structure.
     */
    public Path renameFile(Path originalFilePath, LocalDateTime fileDate) {
        if (fileDate == null) {
            return originalFilePath; // If no date is provided, return the original file path
        }

        String originalName = originalFilePath.getFileName().toString();
        String fileExtension = "";

        int extensionIndex = originalName.lastIndexOf(".");
        if (extensionIndex > 0) {
            fileExtension = originalName.substring(extensionIndex);
        }

        String newFileName = dateFormatter.format(fileDate) + "." + fileExtension;

        return originalFilePath.resolveSibling(newFileName);
    }
}
