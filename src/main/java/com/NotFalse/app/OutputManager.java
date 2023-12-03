package com.NotFalse.app;

import java.io.IOException;
import java.util.logging.*;

/**
 * Class for managing the output of the TextEditor application.
 */
public class OutputManager {

    private static final Logger LOGGER = Logger.getLogger(OutputManager.class.getName());
    private final FileHandler fileHandler;
    private final Handler consoleHandler;

    /**
     * Initializes the consoleHandler and the LOGGER.
     * Sets the logging level for the handler and creates a formatter for the
     * handler.
     */
    public OutputManager() {
        try {
            fileHandler = new FileHandler("./logs/OutputManager.log");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.setUseParentHandlers(false);
        consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.OFF);
        consoleHandler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fileHandler);
        LOGGER.setLevel(Level.ALL);
    }

    /**
     * Creates a log entry of level INFO with the given logText.
     *
     * @param logText logText to be displayed of level INFO
     */
    public void createUserInfoMessage(String logText) {
        System.out.println(logText + "\n");
        LOGGER.log(Level.INFO, logText);
    }

    /**
     * Creates a log entry of level WARNING with the given logText.
     *
     * @param logText logText to be displayed of level WARNING
     */
    public void createUserErrorMessage(String logText) {
        System.err.println(logText + "\n");
        LOGGER.log(Level.WARNING, logText);
    }

    /**
     * Creates a welcome message for the user.
     */
    public void createWelcomeMessage() {
        System.out.println("----------------------------------------------------------\n");
        createUserInfoMessage("Welcome to the TextEditor! Created by NotFalse.\n"
                + "Following operations are possible:\n" +
                "add, del, index, replace, print, format raw, format fix, help");
        System.out.println("----------------------------------------------------------");
    }

    /**
     * Creates a goodbye message.
     */
    public void createExitMessage() {
        System.out.println("----------------------------------------------------------\n");
        createUserInfoMessage("Exiting TextEditor...\n" +
                "Thank you for using TextEditor! Created by NotFalse.");
        System.out.println("----------------------------------------------------------");
    }

    /**
     * Creates a menu option message with the commands that can be used.
     */
    public void createHelpMessage() {
        createUserInfoMessage("Here are the commands you can use:\n" + Command.getAllCommands());
    }

    /**
     * Creates a log message for the status of adding a text.
     *
     * @param success status of adding a text
     */
    public void createAddMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Text added successfully!");
        } else {
            createUserErrorMessage("Text has not been added");
        }
    }

    /**
     * Creates a log message for the status of deleting a text.
     *
     * @param success status of deleting a text
     */
    public void createDeleteMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Text deleted successfully!");
        } else {
            createUserErrorMessage("Text has not been deleted");
        }
    }

    /**
     * Creates a log message for the status of replacing a text.
     *
     * @param success status of replacing a text
     */
    public void createReplaceMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Text replaced successfully!");
        } else {
            createUserErrorMessage("Text has not been replaced");
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
            createUserInfoMessage("Text formatted successfully!");
        } else {
            createUserErrorMessage("Text has not been formatted");
        }
    }

    /**
     * Creates a log message for an invalid command.
     */
    public void createInvalidCommandMessage() {
        createUserErrorMessage("Invalid command! If you don't know which commands you can us, call the help function..");
    }

    /**
     * Creates a log message for an invalid index.
     */
    public void createIndexWarning() {
        createUserErrorMessage("This index is not valid. Please try again.");

    }

    /**
     * Creates a log message for the empty text if you try to delete a text.
     */
    public void createEmptyTextWarning() {
        createUserErrorMessage("Your TextEditor is empty...\n"
                + "You can add new text, by calling the add function.");
    }

    /**
     * Creates and displays an error message for an invalid maximum width scenario.
     * The error message informs the user that the text width index is missing and prompts them to try again.
     */
    public void createInvalidMaxWidthWarning() {
        createUserErrorMessage("The text width index is missing. Please try again");
    }

    /**
     * Creates and displays an error message for an empty glossary scenario.
     * The error message notifies the user that their glossary is empty.
     */
    public void createEmptyGlossaryWarning() {
        createUserErrorMessage("Your glossary is empty...");
    }

}
