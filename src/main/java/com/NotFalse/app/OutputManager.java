package com.NotFalse.app;

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
     * Creates a log entry of level WARNING with th
     * e given logText.
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
    public void createWelcomeMessage() {

        logAndPrintInfoMessage("\n" +
                "█   █ ▄▀▀ █   ▄▀▀ ▄▀▀▄ █▄ ▄█ ▄▀▀\n" +
                "█   █ █   █   █   █  █ █▀▄▀█ █\n" +
                "█   █ █▀▀ █   █   █  █ █ ▀ █ █▀▀\n" +
                "█▄█▄█ █   █   █   █  █ █   █ █\n" +
                " ▀ ▀   ▀▀  ▀▀  ▀▀  ▀▀  ▀   ▀  ▀▀\n" +
                " ...to the TextEditor\n" +
                " created by NotFalse...\n"+
                " 🙈🙈🙈🙈🙈🙈🙈🙈🙈🙈\n" +
                "+---------------------+\n" +
                "| Available Functions |\n" +
                "+---------------------+\n" +
                "| add [i]             |\n" +
                "| dummy [i]           |\n" +
                "| del [i]             |\n" +
                "| replace [i]         |\n" +
                "| format fix <b>      |\n" +
                "| format raw          |\n" +
                "| index               |\n" +
                "| print               |\n" +
                "| help                |\n" +
                "| exit                |\n" +
                "+---------------------+\n");
    }

    /**
     * Creates a goodbye message.
     */
    public void createExitMessage() {
        logAndPrintInfoMessage("                       88          \n" +
                "                       \"\"   ,d     \n" +
                "                            88     \n" +
                " ,adPPYba, 8b,     ,d8 88 MM88MMM  \n" +
                "a8P_____88  `Y8, ,8P'  88   88     \n" +
                "8PP\"\"\"\"\"\"\"    )888(    88   88     \n" +
                "\"8b,   ,aa  ,d8\" \"8b,  88   88,    \n" +
                " `\"Ybbd8\"' 8P'     `Y8 88   \"Y888 \n\n\n" +
                "Thank you for using TextEditor created by NotFalse.\uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D\n");

    }

    /**
     * Creates a menu option message with the commands that can be used.
     */
    public void createHelpMessage() {
        logAndPrintInfoMessage("Here are the commands you can use:\n" + ApplicationCommand.getAllCommands());
    }

    /**
     * Creates a log message for the status of adding a text.
     *
     * @param success status of adding a text
     */
    public void createAddMessage(boolean success) {
        if (success) {
            logAndPrintInfoMessage("Status: Text added successfully!");
        } else {
            logAndPrintWarningMessage("Status: Text has not been added");
        }
    }

    /**
     * Creates a log message for the status of deleting a text.
     *
     * @param success status of deleting a text
     */
    public void createDeleteMessage(boolean success) {
        if (success) {
            logAndPrintInfoMessage("Status: Text deleted successfully!");
        } else {
            logAndPrintWarningMessage("Status: Text has not been deleted");
        }
    }

    /**
     * Creates a log message for the status of replacing a text.
     *
     * @param success status of replacing a text
     */
    public void createReplaceMessage(boolean success) {
        if (success) {
            logAndPrintInfoMessage("Status: Text replaced successfully!");
        } else {
            logAndPrintWarningMessage("Status: Text has not been replaced");
        }
    }

    /**
     * Creates and displays a formatted message based on the success status.
     * If 'success' is true, a success message is created; otherwise, an error message is created.
     *
     * @param success Indicates the success status of the operation.
     */
    public void createFormatMessage(boolean success) {
        if (success) {
            logAndPrintInfoMessage("Status: Text formatted successfully!");
        } else {
            logAndPrintWarningMessage("Status: Text has not been formatted");
        }
    }

    /**
     * Creates a log message for an invalid command.
     */
    public void createInvalidCommandMessage() {
        logAndPrintWarningMessage("Invalid command! If you don't know which commands you can us, call the help function..");
    }

    /**
     * Creates a log message for an invalid index.
     */
    public void createIndexWarning() {
        logAndPrintWarningMessage("Invalid index. Please try again.");

    }

    /**
     * Creates a log message for the empty text if you try to delete a text.
     */
    public void createEmptyTextWarning() {
        logAndPrintWarningMessage("Your TextEditor is empty...\n"
                + "You can add new text, by calling the add function.");
    }

    /**
     * Creates and displays an error message for an invalid maximum width scenario.
     * The error message informs the user that the text width index is missing and prompts them to try again.
     */
    public void createInvalidMaxWidthWarning() {
        logAndPrintWarningMessage("The text width index is missing. Please try again");
    }

    public void createInvalidWordWarning(){
        logAndPrintWarningMessage("This word doesn't exist in this paragraph. Please try again.");
    }

    /**
     * Creates and displays an error message for an empty glossary scenario.
     * The error message notifies the user that their glossary is empty.
     */
    public void createEmptyGlossaryWarning() {
        logAndPrintWarningMessage("Your glossary is empty...");
    }

}
