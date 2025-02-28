package Log;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * The LogHandler class is responsible for managing and appending log messages
 * to a JTextArea. It provides a method to add messages to the log area and
 * ensures the latest log message is always visible. This class is typically
 * used in GUI applications to display log or exception information to the user
 * in real-time.
 * <p>
 * The LogHandler interacts with a JTextArea component to display log messages
 * in a user-friendly format.
 * </p>
 * <p>
 * <b>Author:</b> ThePandogs</p>
 */
public class LogHandler {

    private final JTextArea logTextArea;

    /**
     * Constructs a LogHandler instance with the specified JTextArea.
     *
     * @param logTextArea The JTextArea where log messages will be appended.
     */
    public LogHandler(JTextArea logTextArea) {
        this.logTextArea = logTextArea;
    }

    /**
     * Appends a log message to the JTextArea and ensures that the latest log
     * entry is visible by scrolling down. This method appends the provided
     * message to the log area and automatically adjusts the caret position to
     * show the most recent log entry.
     *
     * @param message The message to be appended to the log area.
     */
    public void appendLog(final String message) {
        logTextArea.append(message + "\n\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());

        // Ensures the log area updates on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Nothing needed inside here as SwingUtilities.invokeLater ensures thread safety
        });
    }
}
