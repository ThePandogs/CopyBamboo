package Controller.Classification.types;

import Controller.Classification.ClassificationStrategy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * The DateClassificationStrategy class implements the ClassificationStrategy
 * interface and provides a specific strategy for classifying files or
 * directories based on the date provided.
 *
 * If a date is given, this strategy classifies the files by placing them in
 * folders named after the year and month of the provided date. If the
 * `pendients` flag is true and no date is given, it will place the file in a
 * "0_Pending" directory under the original parent folder.
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public class DateClassificationStrategy implements ClassificationStrategy {

    /**
     * Classifies the given file or directory based on the provided date. If a
     * date is provided, the file or directory is classified into a folder named
     * by the year and month of the date. If no date is provided and the
     * `pendients` flag is true, the file is classified under a "0_Pending"
     * folder.
     *
     * @param originPath the path of the file or directory to be classified.
     * @param destinationPath the base destination path where the classified
     * file or directory will be moved.
     * @param date the date that influences the classification; used for
     * determining the folder (year/month).
     * @param pendients flag indicating if pending files should be placed under
     * a "0_Pending" folder when no date is given.
     * @return the path of the classified file or directory, depending on the
     * classification logic.
     */
    @Override
    public Path classify(Path originPath, Path destinationPath, LocalDateTime date, boolean pendients) {
        if (date != null) {
            // Classify based on the year and month from the provided date
            return Paths.get(destinationPath.toString(), String.valueOf(date.getYear()), String.valueOf(date.getMonthValue()));
        }
        if (pendients) {
            // If no date and 'pendients' flag is true, classify under "0_Pending" folder
            return Paths.get(destinationPath.toString(), "0_Pending", originPath.getParent().getFileName().toString());
        }
        // Return null if no classification is possible
        return null;
    }
}
