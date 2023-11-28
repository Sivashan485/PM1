package com.NotFalse.app;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 * Class for managing the output of the TextEditor application.
 */
public class OutputManager {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger
            .getLogger(OutputManager.class.getName());

    Handler consoleHandler;

    /**
     * Initializes the consoleHandler and the LOGGER.
     * Sets the logging level for the handler and creates a formatter for the
     * handler.
     */
    public OutputManager() {
        LOGGER.setUseParentHandlers(false);
        consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.OFF);
        consoleHandler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.OFF);
    }

    /**
     * Creates a log entry of level INFO with the given logText.
     *
     * @param logText logText to be displayed of level INFO
     */
    public void createUserInfoMessage(String logText) {
        System.out.println(logText+"\n");
        LOGGER.log(Level.INFO, logText);
    }

    /**
     * Creates a log entry of level WARNING with the given logText.
     *
     * @param logText logText to be displayed of level WARNING
     */
    public void createUserErrorMessage(String logText) {
        System.err.println(logText+"\n");
        LOGGER.log(Level.WARNING, logText);
    }

    /**
     * Creates a welcome message for the user.
     */
    public void createWelcomeMessage() {
        createUserInfoMessage("Welcome to the TextEditor! Created by NotFalse.");
    }

    /**
     * Creates a goodbye message.
     */
    public void createExitMessage() {
        createUserInfoMessage("Exiting TextEditor...\n" +
                "Thank you for using TextEditor! Created by NotFalse.");
    }

    /**
     * Creates a menu option message with the commands that can be used.
     */
    public void createHelpMessage() {
        createUserInfoMessage("Here are the commands you can use: " + Command.getAllCommands());
    }

    /**
     * Creates a log message for the status of adding a text.
     *
     * @param success status of adding a text
     */
    public void createAddMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Text has been added");
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
        createUserErrorMessage("Invalid command! Please try again.");
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

    public void createMissingArgumentWarning() {
        createUserErrorMessage("Missing argument for MaxWidth");
    }

    public void createInvalidArgumentWarning() {
        createUserErrorMessage("MaxWidth argument must be an integer");
    }

    public void createEmptyGlossaryWarning() {
        createUserErrorMessage("Your glossary is empty...");
    }

}
