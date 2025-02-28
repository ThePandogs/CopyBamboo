package Controller;

import Model.DateFile;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * This class handles extracting various types of date information from a file's
 * metadata. It implements the {@link DateFile} interface and supports
 * extraction of creation, last modified, and EXIF metadata dates, including
 * parsing and handling different date formats.
 */
public class DateFileController implements DateFile {

    private Tika tika;

    /**
     * Constructor that initializes the Tika library to parse file metadata.
     */
    public DateFileController() {
        tika = new Tika();
    }

    // <editor-fold defaultstate="collapsed" desc="getDateFile">
    /**
     * Retrieves the creation date of the given file based on its metadata. The
     * method first checks for metadata tags in the file's metadata, and if none
     * are found, attempts to read the EXIF data if available.
     *
     * @param f the file from which to extract the creation date.
     * @return a {@link LocalDateTime} representing the creation date, or
     * {@code null} if not found.
     */
    @Override
    public LocalDateTime getMetaCreationDate(File f) {
        Metadata metadata = new Metadata();
        String creationDate = null;
        try (FileInputStream inputStream = new FileInputStream(f)) {
            tika.parse(inputStream, metadata);
            String[] possibleTags = {
                "dcterms:created", // XMP Dublin Core
                "photoshop:DateCreated", // Photoshop
                "Exif:DateTimeOriginal", // EXIF metadata
                "xmp:CreateDate", // XMP standard creation date
                "xmp:ModifyDate", // XMP standard modification date
                "dc:created", // Dublin Core
                "Creation-Date", // General metadata field
                "meta:created", // Metadata tag often used in video files
                "date" // Generic date tag
            };

            // Iterate through tags to find creation date
            for (String tag : possibleTags) {
                creationDate = metadata.get(tag);
                if (creationDate != null) {
                    return parseDate(creationDate);
                }
            }
            // If no creation date found, check EXIF metadata
            if (creationDate == null) {
                ExifSubIFDDirectory directory = null;

                com.drew.metadata.Metadata metadataExif = ImageMetadataReader.readMetadata(f);
                directory = metadataExif.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

                if (directory != null) {
                    Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

                }

            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * Parses a date string into a {@link LocalDateTime} object. Attempts to
     * parse using several date formats.
     *
     * @param dateString the string to parse.
     * @return a {@link LocalDateTime} if the date is valid, {@code null}
     * otherwise.
     */
    private LocalDateTime parseDate(String dateString) {
        if ("1904-01-01T00:00:00Z".equals(dateString)) {
            return null;
        }

        // Remove 'Z' (UTC timezone) if present
        if (dateString != null && dateString.endsWith("Z")) {
            dateString = dateString.substring(0, dateString.length() - 1);
        }

        // Try parsing with different formats
        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ISO_DATE_TIME, // Formato estándar ISO 8601
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), // Formato clásico
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss") // Formato con 'T'
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(dateString, formatter);
            } catch (Exception e) {
                // If format fails, continue to the next one
                continue;
            }
        }
        return null; // Return null if parsing fails
    }

    /**
     * Retrieves the last modified date of the given file.
     *
     * @param f the file from which to extract the last modified date.
     * @return a {@link LocalDateTime} representing the last modified date.
     */
    @Override
    public LocalDateTime getLastModifiedDate(File f) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(f.lastModified()), ZoneId.systemDefault());
    }

    /**
     * Retrieves the creation date of the given file from its file system
     * attributes.
     *
     * @param f the file from which to extract the creation date.
     * @return a {@link LocalDateTime} representing the creation date.
     */
    @Override
    public LocalDateTime getCreationDate(File f) {
        try {
            FileTime creationDate = Files.readAttributes(f.toPath(), BasicFileAttributes.class).creationTime();
            return LocalDateTime.ofInstant(creationDate.toInstant(), ZoneId.systemDefault());
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Prints all metadata of the given file for debugging purposes.
     *
     * @param f the file whose metadata will be printed.
     */
    public void printAllMetadata(File f) {
        Metadata metadata = new Metadata();
        try (FileInputStream inputStream = new FileInputStream(f)) {
            tika.parse(inputStream, metadata);

            // Print all metadata fields
            for (String name : metadata.names()) {
                System.out.println(name + " = " + metadata.get(name));
            }

        } catch (IOException e) {
        }
    }

    /**
     * Retrieves the EXIF creation date from the given file's EXIF metadata.
     *
     * @param f the file from which to extract the EXIF creation date.
     * @return the EXIF date as a string, or {@code null} if not found.
     */
    public String getExifDate(File f) {
        Metadata metadata = new Metadata();
        try (FileInputStream inputStream = new FileInputStream(f)) {
            // Use Tika to parse the EXIF metadata

            tika.parse(inputStream, metadata);

            // Look for common EXIF date tags
            String exifDate = metadata.get("exif:DateTimeOriginal");
            if (exifDate != null) {
                return exifDate;
            }

            // Also check for other possible EXIF date tags
            exifDate = metadata.get("exif:CreateDate");
            if (exifDate != null) {
                return exifDate;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    // </editor-fold>
}
