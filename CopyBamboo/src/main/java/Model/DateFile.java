package Model;

import java.io.File;
import java.time.LocalDateTime;

/**
 * The {@code DateFile} interface defines methods for obtaining different types
 * of date information related to a file, such as its creation date, last
 * modified date, and metadata-based creation date.
 * <p>
 * Implementing classes should provide specific mechanisms to retrieve this
 * information, potentially based on file system attributes or metadata
 * extracted from the file (such as EXIF for images).
 * </p>
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public interface DateFile {

    /**
     * Retrieves the creation date of a file based on its metadata.
     * <p>
     * This method may not work for all file types, as the availability of
     * metadata depends on the file format (e.g., images may have metadata
     * containing creation dates, while other types of files may not).
     * </p>
     *
     * @param f The file whose metadata-based creation date is to be retrieved.
     * @return The {@link LocalDateTime} representing the creation date, or
     * {@code null} if the creation date cannot be determined.
     */
    public LocalDateTime getMetaCreationDate(File f); // Review if this works only with images

    /**
     * Retrieves the last modified date of the specified file.
     *
     * @param f The file whose last modified date is to be retrieved.
     * @return The {@link LocalDateTime} representing the last modified date, or
     * {@code null} if the last modified date cannot be determined.
     */
    public LocalDateTime getLastModifiedDate(File f);

    /**
     * Retrieves the creation date of the specified file from the file system
     * attributes.
     *
     * @param f The file whose creation date is to be retrieved.
     * @return The {@link LocalDateTime} representing the creation date, or
     * {@code null} if the creation date cannot be determined.
     */
    public LocalDateTime getCreationDate(File f);
}
