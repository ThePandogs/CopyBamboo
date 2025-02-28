package Model;

/**
 * The {@code CopyParameters} enum defines different parameters or flags that
 * can influence how files are copied during a file transfer operation. These
 * parameters are used to configure the behavior of the copying process.
 *
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public enum CopyParameters {

    /**
     * Rename the file using its creation date as the new file name.
     */
    RENAME_NAME_DATE_CREATION,
    /**
     * Flag indicating that files which are not classified should be placed in a
     * specific folder, typically for pending or unclassified files.
     */
    FOLDER_FILE_NOT_CLASSIFIED,
    /**
     * Flag that allows overwriting existing files during the copy process.
     */
    OVERWRITE_FILE;

}
