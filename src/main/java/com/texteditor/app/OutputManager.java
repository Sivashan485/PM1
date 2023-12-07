package com.texteditor.app;

import java.io.IOException;
import java.util.logging.*;

/**
 * Class for managing the output of the TextEditor application.
 */
public class OutputManager {

    private static final Logger LOGGER = Logger.getLogger(OutputManager.class.getName());

    /**
     * Initializes the consoleHandler and the LOGGER.
     * Sets the logging level for the handler and creates a formatter for the
     * handler.
     */
    public OutputManager() {
        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler("./logs/OutputManager.log");
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        LOGGER.setUseParentHandlers(false);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.OFF);
        consoleHandler.setFormatter(new SimpleFormatter());
        LOGGER.setLevel(Level.ALL);
    }

    /**
     * Creates a log entry of level INFO with the given logText.
     *
     * @param logText logText to be displayed of level INFO
     */
    public static void logAndPrintInfoMessage(String logText) {
        System.out.println(logText);
        LOGGER.log(Level.INFO, logText);
    }

    /**
     * Creates a log entry of level WARNING with the given logText.
     *
     * @param logText logText to be displayed of level WARNING
     */
    public static void logAndPrintWarningMessage(String logText) {
        System.err.println(logText);
        LOGGER.log(Level.WARNING, logText);
    }

    /**
     * Creates a welcome message for the user.
     */
    void createWelcomeMessage() {
        logAndPrintInfoMessage("\n" +
                " █   █ ▄▀▀ █   ▄▀▀ ▄▀▀▄ █▄ ▄█ ▄▀▀\n" +
                " █   █ █   █   █   █  █ █▀▄▀█ █\n" +
                " █   █ █▀▀ █   █   █  █ █ ▀ █ █▀▀\n" +
                " █▄█▄█ █   █   █   █  █ █   █ █\n" +
                "  ▀ ▀   ▀▀  ▀▀  ▀▀  ▀▀  ▀   ▀  ▀▀\n" +
                " ...to the TextEditor\n" +
                " created by NotFalse...\n" +
                " 🙈🙈🙈🙈🙈🙈🙈🙈🙈🙈\n\n" +

                " Available Functions              \n" +
                "——————————————————————————————————————————————————————————————————————————————————————\n\n" +
                " ☒ add[i]         ☒ dummy[i]          ☒ del[i]     ☒ replace[i]    ☒ index    \n" +
                " ☒ format raw     ☒ format fix<b>     ☒ print      ☒ help          ☒ exit                \n");
    }

    /**
     * Creates a goodbye message.
     */
    void createExitMessage() {
        logAndPrintInfoMessage("                       88          \n" +
                "                       \"\"   ,d     \n" +
                "                            88     \n" +
                " ,adPPYba, 8b,     ,d8 88 MM88MMM  \n" +
                "a8P_____88  `Y8, ,8P'  88   88     \n" +
                "8PP\"\"\"\"\"\"\"    )888(    88   88     \n" +
                "\"8b,   ,aa  ,d8\" \"8b,  88   88,    \n" +
                " `\"Ybbd8\"' 8P'     `Y8 88   \"Y888 \n\n\n" +
                "Thank you for using TextEditor created by NotFalse.\uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D\n" +
                "_____________________________________________________________________________________\n");
    }

    /**
     * Creates a menu option message with the commands that can be used.
     */
    void createHelpMessage() {
        logAndPrintInfoMessage("Here are the commands you can use:\n" + CommandApp.getAllCommands());
    }

    /**
     * Creates a log message for the status of adding a text.
     *
     * @param success status of adding a text
     */
    void createAddMessage(boolean success) {
        if (success) {
            logAndPrintInfoMessage("➤ STATUS: Text added successfully");
        } else {
            logAndPrintWarningMessage("➤ STATUS: Text has not been added");
        }
    }

    /**
     * Creates a log message for the status of deleting a text.
     *
     * @param success status of deleting a text
     */
    void createDeleteMessage(boolean success) {
        if (success) {
            logAndPrintInfoMessage("➤ STATUS: Text deleted successfully");
        } else {
            logAndPrintWarningMessage("➤ STATUS: Text has not been deleted");
        }
    }

    /**
     * Creates a log message for the status of replacing a text.
     *
     * @param success status of replacing a text
     */
    void createReplaceMessage(boolean success) {
        if (success) {
            logAndPrintInfoMessage("➤ STATUS: Text replaced successfully");
        } else {
            logAndPrintWarningMessage("➤ STATUS: Text has not been replaced");
        }
    }

    /**
     * Creates and displays a formatted message based on the success status.
     * If 'success' is true, a success message is created; otherwise, an error
     * message is created.
     *
     * @param success Indicates the success status of the operation.
     */
    void createFormatMessage(boolean success) {
        if (success) {
            logAndPrintInfoMessage("➤ STATUS: Text formatted successfully");
        } else {
            logAndPrintWarningMessage("➤ STATUS: Text has not been formatted");
        }
    }

    /**
     * Creates a log message for an invalid command.
     */
    void createInvalidCommandMessage() {
        logAndPrintWarningMessage(
                "Invalid command! If you don't know which commands you can us, call the help function.");
    }

    /**
     * Creates a log message for an invalid index.
     */
    void createIndexWarning() {
        logAndPrintWarningMessage("Invalid index! Please try again.");
    }

    /**
     * Creates a log message for the empty text if you try to delete a text.
     */
    void createEmptyTextWarning() {
        logAndPrintWarningMessage("Your TextEditor is empty.\n"
                + "You can add a new paragraph, by calling the add function.");
    }

    /**
     * Creates and displays an error message for an invalid maximum width scenario.
     * The error message informs the user that the text width index is missing and
     * prompts them to try again.
     */
    void createInvalidMaxWidthWarning() {
        logAndPrintWarningMessage("The text width index is missing. Please try again.");
    }

    /**
     * Creates and displays an error message for an invalid word scenario.
     * The error message notifies the user that the word they are trying to
     * replace does not exist in the paragraph.
     */
    void createInvalidSectionWarning() {
        logAndPrintWarningMessage("This word doesn't exist in this paragraph. Please try again.");
    }

    /**
     * Creates and displays an error message for an empty glossary scenario.
     * The error message notifies the user that their glossary is empty.
     */
    void createEmptyGlossaryWarning() {
        logAndPrintWarningMessage("Your glossary is empty.");
    }

    /**
     * Creates and displays an error message for an invalid glossary scenario.
     * The error message notifies the user that the word they are trying to.
     */
    void createDisallowedCharacterWarning() {
        logAndPrintInfoMessage("Disallowed characters have been removed.");
    }
}
