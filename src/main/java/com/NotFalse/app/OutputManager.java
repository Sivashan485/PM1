package com.NotFalse.app;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 * This class is responsible for creating all the messages that are displayed to the user
 * and the log entries.
 */
public class OutputManager {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(OutputManager.class.getName());
    Handler consoleHandler = new ConsoleHandler();

    /**
     * Initializes the consoleHandler and the LOGGER.
     * Sets the logging level for the handler and creates a formatter for the handler.
     */
    public OutputManager() {
        consoleHandler.setLevel(Level.ALL);
        SimpleFormatter formatter = new SimpleFormatter();
        consoleHandler.setFormatter(formatter);
        LOGGER.addHandler(consoleHandler);
    }

    /**
     * Creates a log entry of level INFO with the given logText.
     *
     * @param logText logText to be displayed of level INFO
     */
    public void createUserInfoMessage(String logText) {
        System.out.println(logText);
        LOGGER.log(Level.INFO, logText);
    }

    /**
     * Creates a log entry of level WARNING with the given logText.
     *
     * @param logText logText to be displayed of level WARNING
     */
    public void createUserErrorMessage(String logText) {
        System.err.println(logText);
        LOGGER.log(Level.WARNING, logText);
    }

    /**
     * Creates a welcome message for the user.
     */
    public void createWelcomeMessage() {
        System.out.println("Welcome to the TextEditor! Created by NotFalse.");
    }

    /**
     * Creates a menu option message with the commands that can be used.
     */
    public void createMenuOptions() {
        System.out.println("Here are the commands you can use:" + Commands.getAllCommands());
    }

    /**
     * Creates an error message that the entered text is too long.
     */
    public void createMaxStringWarning() {
        System.err.println("The text you have entered is too long! Please try again or " +
                "fix the text length.");
    }

    /**
     * Creates an error message that the entered index is too large.
     */
    public void createMaxIntWarning() {
        System.err.println("The index you have entered is too large! Please try again.");
    }

    /**
     * Creates a goodbye message.
     */
    public void createExitMessage() {
        System.out.println("Exiting TextEditor...\n" +
                "Thank you for using TextEditor! Created by NotFalse.");
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
     * Creates a log message for the status of creating a dummy text.
     *
     * @param success status of creating a dummy text
     */
    public void createDummyMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Dummy text generated successfully!");
        } else {
            createUserErrorMessage("Dummy text has not been generated");
        }
    }

    /**
     * Creates a log message for the status of creating an index.
     *
     * @param success status of creating an index
     */
    public void createIndexMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Index generated successfully!");
        } else {
            createUserErrorMessage("Index has not been generated");
        }
    }

    /**
     * Creates a log message for the status of printing a text.
     *
     * @param success status of printing a text
     */
    public void createPrintMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Printing text...");
        } else {
            createUserErrorMessage("Text has not been printed");
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
     * Creates an error message for an invalid command.
     */
    public void createInvalidCommandMessage() {
        System.err.println("Invalid command! Please try again.");
    }
}




