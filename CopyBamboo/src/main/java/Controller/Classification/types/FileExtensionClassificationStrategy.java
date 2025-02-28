package Controller.Classification.types;

import Controller.Classification.ClassificationStrategy;
import Model.FileClassifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * The FileExtensionClassificationStrategy class implements the
 * ClassificationStrategy interface and provides a specific strategy for
 * classifying files or directories based on their file extension.
 * <p>
 * This strategy classifies the files by creating a folder named after the
 * file's extension (e.g., "jpg", "pdf") and moves the file into that folder
 * under the specified destination path.
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public class FileExtensionClassificationStrategy implements ClassificationStrategy {

    /**
     * Classifies the given file or directory by its file extension. The file
     * will be placed into a folder named after its extension (e.g., "jpg",
     * "pdf").
     *
     * @param originPath the path of the file or directory to be classified.
     * @param destinationPath the base destination path where the classified
     * file or directory will be moved.
     * @param date the date is not used in this classification strategy.
     * @param pendients flag is not used in this classification strategy.
     * @return the path where the file will be classified, which is the
     * destination folder named after the file's extension.
     */
    @Override
    public Path classify(Path originPath, Path destinationPath, LocalDateTime date, boolean pendients) {
        // Get the file extension using the FileClassifier utility
        String extension = FileClassifier.getFileExtension(originPath);
        // Create a new path by combining the destination path with the file extension
        return Paths.get(destinationPath.toString(), extension);
    }
}
