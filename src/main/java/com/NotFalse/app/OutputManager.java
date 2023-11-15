package com.NotFalse.app;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class OutputManager {

    private static final Logger LOGGER = Logger.getLogger(OutputManager.class.getName());
    Handler consoleHandler = new ConsoleHandler();

    public OutputManager() {
        // Initialization code here
        // Set the logging level for the handler
        consoleHandler.setLevel(Level.ALL);

        // Create a formatter for the handler
        SimpleFormatter formatter = new SimpleFormatter();
        consoleHandler.setFormatter(formatter);

        // Add the handler to the logger
        LOGGER.addHandler(consoleHandler);
    }

    public void createUserInfoMessage(String logText) {
        System.out.println(logText);
        LOGGER.log(Level.INFO, logText);
    }

    public void createUserErrorMessage(String logText) {
        System.err.println(logText);
        LOGGER.log(Level.WARNING, logText);
    }

    // Methods
    public void createHelpMessage() {
        Commands.getAllCommands();
    }

    public void createMaxStringWarning() {
        // Method implementation here
    }

    public void createMaxIntWaring() {
        // Method implementation here
    }

    public void createExitMessage() {
        // Method implementation here
    }

    public void createAddMessage(boolean success) {
        // Method implementation here
        if (success)
            createUserInfoMessage("Text has been added");
        else
            createUserErrorMessage("Text has not been added");

    }

    public void createDeleteMessage() {
        // Method implementation here
    }

    public void createDummyMessage() {
        // Method implementation here
    }

    public void createIndexMessage() {
        // Method implementation here
    }

    public void createPrintMessage() {
        // Method implementation here
    }

    public void createReplaceMessage() {
        // Method implementation here
    }

    public void createInvalidCommandMessage() {
        // Method implementation here
    }
}
