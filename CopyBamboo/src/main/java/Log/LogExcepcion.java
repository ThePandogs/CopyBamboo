package Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * The LogExcepcion class is used to log exception details into a file. This
 * class provides methods for adding exception information (such as exception
 * type, date, time, and message) into a log file named "Exception.txt" located
 * in the "src/Log" directory. It also provides the ability to log custom
 * messages.
 * <p>
 * The class automatically records the date and time when an exception occurs,
 * making it easier to track and debug issues in the application.
 * </p>
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public class LogExcepcion {

    private LocalDateTime data;
    private LocalTime time;
    private File archivoExcepciones;

    /**
     * Constructs a LogExcepcion instance and initializes the exception log
     * file. The log file is named "Exception.txt" and is located in the
     * "src/Log" directory.
     */
    public LogExcepcion() {
        archivoExcepciones = new File("src/Log/Exception.txt");
    }

    /**
     * Logs an exception into the log file, including the exception type, date,
     * time, and message. The exception is recorded with the following format:
     * <ul>
     * <li>Excepción: The exception class type</li>
     * <li>Fecha: The current date in ISO format</li>
     * <li>Hora: The current time truncated to seconds</li>
     * <li>Motivo: The localized message of the exception</li>
     * </ul>
     *
     * @param e The exception to be logged.
     */
    public void anadirExcepcionLog(Exception e) {
        data = LocalDateTime.now();
        time = LocalTime.now();
        try (PrintWriter saida = new PrintWriter((new FileWriter(archivoExcepciones, true)))) {

            saida.println("Exception: " + e.getClass());
            saida.println("Date:  " + data.format(DateTimeFormatter.ISO_DATE));
            saida.println("Time:   " + time.truncatedTo(ChronoUnit.SECONDS));
            saida.println("Reason: " + e.getLocalizedMessage());
            saida.println();

        } catch (IOException ex) {
            Logger.getLogger(LogExcepcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Logs a custom message into the exception log file. This method allows the
     * user to add custom messages to the log file, which can be useful for
     * tracking other important events or statuses.
     *
     * @param text The custom message to be logged.
     */
    public void anadirExcetionCustom(String text) {
        try (PrintWriter saida = new PrintWriter((new FileWriter(archivoExcepciones, true)))) {

            saida.println(text);

        } catch (IOException ex) {
            Logger.getLogger(LogExcepcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
