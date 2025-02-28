package Controller.Classification;

import java.nio.file.Path;
import java.time.LocalDateTime;

/**
 * The ClassificationStrategy interface defines the contract for classification
 * strategies. Any class that implements this interface must provide an
 * implementation for the classify method, which will be used to classify files
 * or directories based on the given criteria.
 *
 * This allows for multiple classification strategies to be used interchangeably
 * in the system, providing flexibility in how files are organized or classified
 * based on different rules or logic.
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public interface ClassificationStrategy {

    /**
     * Classifies the given file or directory based on the provided parameters.
     * The actual classification logic is defined by the specific implementation
     * of this interface.
     *
     * @param originPath the path of the file or directory to be classified.
     * @param destinationPath the path where the classified file or directory
     * should be moved or stored.
     * @param date the date that may be used to influence the classification,
     * such as creation or modification date.
     * @param pendients flag indicating if pending files should be handled
     * during the classification.
     * @return the path resulting from the classification, which may represent a
     * new location or name.
     */
    Path classify(Path originPath, Path destinationPath, LocalDateTime date, boolean pendients);
}
