package Controller;

import Log.LogHandler;
import Model.ClassifyTypes;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class handles the copying of files and directories with classification
 * and logging functionality. It extends the {@link FileController} class.
 */
public class CopyController extends FileController {

    /**
     * Constructor for initializing the CopyController with specific settings.
     *
     * @param classifyTypes the classification strategy to be used.
     * @param originPath the path of the directory to copy from.
     * @param destinationPath the path of the directory to copy to.
     * @param rename whether to rename files during the copy process.
     * @param pendients whether to handle pending files.
     * @param overwrite whether to overwrite existing files.
     * @param logWindow the log handler to display log messages.
     */
    public CopyController(ClassifyTypes classifyTypes, String originPath, String destinationPath, boolean rename, boolean pendients, boolean overwrite, LogHandler logWindow) {
        this.classifyTypes = classifyTypes;
        this.originPath = Paths.get(originPath);
        this.destinationPath = Paths.get(destinationPath);
        this.rename = rename;
        this.pendients = pendients;
        this.overwrite = overwrite;
        this.logWindow = logWindow;

    }

    /**
     * Starts the copy process by creating a fixed thread pool and copying the
     * directories.
     */
    public void StartCopy() {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        setClassificationStrategy();  // Establecemos la estrategia de clasificación
        copyDirectories(this.originPath, this.destinationPath);
        shutdownExecutor();  // Asegúrate de que todas las tareas se completen
    }

    /**
     * Copies a directory from the origin path to the destination path using the
     * provided settings.
     *
     * @param origin the path of the directory to copy from.
     * @param destination the path of the directory to copy to.
     * @param classifyTypes the classification strategy to be used.
     * @param rename whether to rename files during the copy process.
     * @param pendients whether to handle pending files.
     * @param overwrite whether to overwrite existing files.
     * @return true if the copy was successful, false otherwise.
     */
    public boolean copyDirectory(String origin, String destination, ClassifyTypes classifyTypes, boolean rename, boolean pendients, boolean overwrite) {
        this.rename = rename;
        this.pendients = pendients;
        this.overwrite = overwrite;
        this.classifyTypes = classifyTypes;
        this.originPath = Paths.get(origin);
        this.destinationPath = Paths.get(destination);
        try {
            copyDirectories(originPath, destinationPath);
            return true;
        } catch (Exception e) {
            logWindow.appendLog("Error during the copy: " + e.getMessage());
            return false;
        } finally {
            shutdownExecutor();  // Asegúrate de que todas las tareas se completen
        }
    }

    /**
     * Recursively copies files and directories from the origin path to the
     * destination path. It also handles file classification and logs the
     * process.
     *
     * @param originPath the path of the directory to copy from.
     * @param destinationPath the path of the directory to copy to.
     */
    private void copyDirectories(Path originPath, Path destinationPath) {
        File[] fileList = originPath.toFile().listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                Path relativePath = originPath.relativize(file.toPath());
                Path destinationFile = destinationPath.resolve(relativePath);

                logWindow.appendLog("Processing: " + file.toPath() + " -> " + destinationFile);

                if (file.isFile()) {
                    LocalDateTime date = getDateFile(file);
                    destinationFile = getDestinationPathFile(file.toPath(), destinationPath, date);
                    if (destinationFile == null) {
                        logWindow.appendLog("Error: The file could not be classified: " + file.toPath());
                        continue;
                    }
                    copyFile(file.toPath(), destinationFile, date);
                } else if (file.isDirectory()) {
                    copyDirectories(file.toPath(), destinationPath);
                }
            }
        }
    }

    /**
     * Copies a single file from the origin path to the destination path,
     * applying attributes and logging the process.
     *
     * @param originPath the path of the file to copy.
     * @param destinationPath the path of the file to copy to.
     * @param creationDate the creation date of the file.
     */
    private void copyFile(Path originPath, Path destinationPath, LocalDateTime creationDate) {
        try {
            if (!Files.exists(destinationPath.getParent())) {
                Files.createDirectories(destinationPath.getParent());
            }
            if (Files.exists(destinationPath) && !overwrite) {
                if (isSameFileContent(originPath, destinationPath)) {
                    logWindow.appendLog(originPath.getFileName() + " already exists and is identical, not overwritten.");
                    return;
                }
            }
            Files.copy(originPath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            applyAttributes(originPath, destinationPath, creationDate);
            logWindow.appendLog("File copied from: " + originPath + " to " + destinationPath);
        } catch (IOException e) {
            logFile.anadirExcepcionLog(e);
            logWindow.appendLog("Error copying file: " + originPath + " - " + e.getMessage());
        }
    }

    private void shutdownExecutor() {
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            logFile.anadirExcepcionLog(ex);
            logWindow.appendLog("Error waiting for task termination: " + ex.getMessage());
        }
    }
}
