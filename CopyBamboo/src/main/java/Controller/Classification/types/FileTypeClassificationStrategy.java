package Controller.Classification.types;

import Controller.Classification.ClassificationStrategy;
import Model.FileClassifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * The FileTypeClassificationStrategy class implements the
 * ClassificationStrategy interface and provides a specific strategy for
 * classifying files or directories based on their file type.
 * <p>
 * This strategy classifies files by determining the type of the file based on
 * its extension. It then creates a folder named after the file type (e.g.,
 * "image", "document") under the specified destination path and moves the file
 * into that folder.
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public class FileTypeClassificationStrategy implements ClassificationStrategy {

    /**
     * Classifies the given file or directory by its file type, which is
     * determined by the file's extension. The file will be placed into a folder
     * named after its file type (e.g., "image", "document").
     *
     * @param originPath the path of the file or directory to be classified.
     * @param destinationPath the base destination path where the classified
     * file or directory will be moved.
     * @param date the date is not used in this classification strategy.
     * @param pendients flag is not used in this classification strategy.
     * @return the path where the file will be classified, which is the
     * destination folder named after the file's type.
     */
    @Override
    public Path classify(Path originPath, Path destinationPath, LocalDateTime date, boolean pendients) {
        // Get the file extension using the FileClassifier utility
        String extension = FileClassifier.getFileExtension(originPath);
        // Determine the file type based on the extension
        String fileType = FileClassifier.getFileTypeByExtension(extension);
        // Create a new path by combining the destination path with the file type
        return Paths.get(destinationPath.toString(), fileType);
    }
}
