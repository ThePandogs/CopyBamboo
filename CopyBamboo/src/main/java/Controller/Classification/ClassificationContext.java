package Controller.Classification;

import java.nio.file.Path;
import java.time.LocalDateTime;

/**
 * The ClassificationContext class serves as the context for applying different
 * classification strategies. It allows for dynamically changing the strategy
 * used for classifying files during the copy or management process.
 *
 * The class provides methods to set the classification strategy and execute the
 * classification using the set strategy.
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public class ClassificationContext {

    private ClassificationStrategy strategy;

    /**
     * Sets the classification strategy that will be used for classifying files.
     *
     * @param strategy the strategy to be used for file classification.
     * @throws IllegalArgumentException if the strategy is null.
     */
    public void setStrategy(ClassificationStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Executes the classification strategy on the provided paths and date.
     *
     * This method delegates the classification to the currently set strategy
     * and returns the result based on the strategy's implementation. If no
     * strategy has been set, an exception will be thrown.
     *
     * @param originPath the path of the original file or directory to be
     * classified.
     * @param destinationPath the path where the classified file or directory
     * should be placed.
     * @param date the date to be used for the classification process.
     * @param pendients flag indicating if pending files should be handled.
     * @return the resulting path after classification.
     * @throws IllegalStateException if no classification strategy has been set.
     */
    public Path classify(Path originPath, Path destinationPath, LocalDateTime date, boolean pendients) {
        if (strategy == null) {
            throw new IllegalStateException("No se ha establecido una estrategia de clasificación.");
        }
        return strategy.classify(originPath, destinationPath, date, pendients);
    }
}
