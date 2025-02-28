/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.ClassifyTypes;
import Log.LogExcepcion;
import Log.LogHandler;

/**
 *
 * @author ThePandogs
 */
/**
 * The LectorController class is responsible for managing the copying process of
 * directories. It serves as a bridge between the user interface and the core
 * logic responsible for copying files by utilizing the CopyController class.
 *
 * This class provides an interface to copy a directory from an origin to a
 * destination, optionally renaming files, handling pending files, and
 * overwriting existing files. It also logs any exceptions that occur during the
 * process.
 *
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public class LectorController {

    private final LogExcepcion logFile = new LogExcepcion();// Instance for logging exceptions
    private final LogHandler logWindow; // Interface for logging actions in the UI

    /**
     * Constructs a new LectorController with the provided LogHandler. This is
     * used to handle logging for the user interface.
     *
     * @param jtexLog the LogHandler to log messages in the UI.
     */
    public LectorController(LogHandler jtexLog) {
        this.logWindow = jtexLog;

    }

    /**
     * Initiates the process of copying a directory from the origin path to the
     * destination path. It delegates the task to the CopyController class,
     * which handles the actual copying and logging.
     *
     * @param origin the path to the source directory.
     * @param destination the path to the destination directory.
     * @param classifyTypes the classification strategy to use for categorizing
     * files.
     * @param rename whether to rename the files during the copying process.
     * @param pendients whether to handle pending files during the process.
     * @param overwrite whether to overwrite existing files in the destination.
     * @return {@code true} if the directory was successfully copied;
     * {@code false} if an error occurred.
     */
    public boolean copyDirectory(String origin, String destination, ClassifyTypes classifyTypes, boolean rename, boolean pendients, boolean overwrite) {
        try {
            // Create an instance of CopyController and start the copying process

            CopyController copyController = new CopyController(classifyTypes, origin, destination, rename, pendients, overwrite, logWindow);
            copyController.StartCopy();
        } catch (Exception e) {
            logFile.anadirExcetionCustom("Error in copy action " + e.getMessage());
            return false;
        }

        return true;
    }

}
