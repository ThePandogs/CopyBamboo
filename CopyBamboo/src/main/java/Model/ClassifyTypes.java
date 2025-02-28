package Model;

/**
 * The {@code ClassifyTypes} enum defines the types of classification strategies
 * that can be applied to files during their processing.
 * <p>
 * The enum provides different classification strategies based on either the
 * file's metadata or its extension. These strategies can be used to categorize
 * files into specific folders or groups based on creation date, modification
 * date, or file type.
 * </p>
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public enum ClassifyTypes {

    // <editor-fold defaultstate="collapsed" desc="DATE">
    /**
     * Classify based on the creation date of the file.
     */
    CREATION_DATE,
    /**
     * Classify based on the creation date found in the metadata of the file.
     */
    CREATION_DATE_META,
    /**
     * Classify based on the last modification date of the file.
     */
    CREATION_DATE_MODIFY,
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="EXTENSION">
    /**
     * Classify based on the file's extension.
     */
    FILE_EXTENSION,
    /**
     * Classify based on the file's type, determined by its extension.
     */
    FILE_TYPE;
    // </editor-fold>
}
