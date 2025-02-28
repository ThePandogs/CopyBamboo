package Controller;

import Controller.Classification.ClassificationContext;
import Controller.Classification.types.DateClassificationStrategy;
import Controller.Classification.types.FileExtensionClassificationStrategy;
import Controller.Classification.types.FileTypeClassificationStrategy;
import Log.LogExcepcion;
import Log.LogHandler;
import Model.ClassifyTypes;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class FileController {

    protected boolean rename, pendients, overwrite;
    protected Path originPath, destinationPath;
    protected int countCopy = 0, countRepeat = 0, countErr = 0;
    protected int desiredBlockSize = 2500;
    protected ClassifyTypes classifyTypes;
    protected DateFileController dateFileController = new DateFileController();
    protected LogExcepcion logFile = new LogExcepcion();
    protected LogHandler logWindow;
    protected FileRenamer fileRenamer = new FileRenamer();
    protected Set<Path> createdDirectories = new HashSet<>();
    protected ClassificationContext classificationContext = new ClassificationContext();
    protected ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public LocalDateTime getDateFile(File f) {
        return switch (classifyTypes) {
            case CREATION_DATE ->
                dateFileController.getCreationDate(f);
            case CREATION_DATE_META ->
                dateFileController.getMetaCreationDate(f);
            case CREATION_DATE_MODIFY ->
                dateFileController.getLastModifiedDate(f);
            default ->
                null;
        };
    }

    protected boolean existsDirectory(Path pathDestination) {
        return Files.exists(pathDestination) && Files.isDirectory(pathDestination);
    }

    protected boolean createDirectory(Path pathDestination) {
        try {
            Files.createDirectories(pathDestination);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    protected boolean checkIsReatableIsWritable(Path origin, Path destination) {
        if (!Files.isReadable(origin)) {
            logWindow.appendLog(" Error: Can't read from origin directory!");
            return false;
        } else if (!Files.isWritable(destination)) {
            logWindow.appendLog("Error: Can't write in destination directory!");
            return false;
        }
        return true;
    }

    protected void applyAttributes(Path originPath, Path destinationPath, LocalDateTime creationDate) {
        try {
            FileTime creationFileTime;
            BasicFileAttributes attrs = Files.readAttributes(originPath, BasicFileAttributes.class);
            FileTime lastAccessTime = attrs.lastAccessTime();
            if (creationDate != null) {
                creationFileTime = FileTime.from(creationDate.atZone(ZoneId.systemDefault()).toInstant());
            } else {
                creationFileTime = FileTime.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            }
            Files.getFileAttributeView(destinationPath, BasicFileAttributeView.class)
                    .setTimes(lastAccessTime, attrs.lastModifiedTime(), creationFileTime);
        } catch (IOException ex) {
            logFile.anadirExcepcionLog(ex);
        }
    }

    protected int calculateBatchSize(int totalFiles) {
        int batchSize = desiredBlockSize;
        if (totalFiles <= desiredBlockSize) {
            batchSize = totalFiles;
        } else {
            int remainingFilesThreshold = desiredBlockSize / 2;
            int remainingFiles = totalFiles % desiredBlockSize;
            if (remainingFiles > 0 && remainingFiles <= remainingFilesThreshold) {
                batchSize = totalFiles - remainingFiles;
            }
        }
        return batchSize;
    }

    protected boolean isSameFileContent(Path file1, Path file2) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash1 = digest.digest(Files.readAllBytes(file1));
            byte[] hash2 = digest.digest(Files.readAllBytes(file2));
            return Arrays.equals(hash1, hash2);
        } catch (Exception e) {
            logFile.anadirExcepcionLog(e);
            return false;
        }
    }

    protected void setClassificationStrategy() {
        switch (classifyTypes) {
            case FILE_TYPE:
                classificationContext.setStrategy(new FileTypeClassificationStrategy());
                break;
            case FILE_EXTENSION:
                classificationContext.setStrategy(new FileExtensionClassificationStrategy());
                break;
            case CREATION_DATE:
            case CREATION_DATE_META:
            case CREATION_DATE_MODIFY:
                classificationContext.setStrategy(new DateClassificationStrategy());
                break;
        }
    }

    protected Path getDestinationPathFile(Path originPath, Path destinationPath, LocalDateTime fileDate) {
        switch (classifyTypes) {
            case FILE_TYPE ->
                classificationContext.setStrategy(new FileTypeClassificationStrategy());
            case FILE_EXTENSION ->
                classificationContext.setStrategy(new FileExtensionClassificationStrategy());
            case CREATION_DATE, CREATION_DATE_META, CREATION_DATE_MODIFY ->
                classificationContext.setStrategy(new DateClassificationStrategy());
            default ->
                throw new IllegalArgumentException("Classification type not supported.");
        }
        Path classifiedPath = classificationContext.classify(originPath, destinationPath, fileDate, pendients);

        if (classifiedPath == null) {
            return null;
        }

        classifiedPath = Path.of(classifiedPath.toString() + "/" + originPath.getFileName());

        if (Files.isRegularFile(originPath) && rename) {
            classifiedPath = fileRenamer.renameFile(classifiedPath, fileDate);
        }
        return classifiedPath;
    }
}
