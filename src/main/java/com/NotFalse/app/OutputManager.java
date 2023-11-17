package com.NotFalse.app;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;


public class OutputManager {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(OutputManager.class.getName());

    Handler consoleHandler;

    public OutputManager() {
        LOGGER.setUseParentHandlers(false);
        consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.OFF);
        consoleHandler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.OFF);
    }

    public void createUserInfoMessage(String logText) {
        System.out.println(logText);
        LOGGER.log(Level.INFO, logText);
    }

    public void createUserErrorMessage(String logText) {
        System.err.println(logText);
        LOGGER.log(Level.WARNING, logText);
    }

    public void createWelcomeMessage() {
        System.out.println("Welcome to the TextEditor! Created by NotFalse.");
    }

    public void createMenuOptions() {
        System.out.println("Here are the commands you can use:");
        System.out.println(Commands.getAllCommands());
    }



    public void createExitMessage() {
        System.out.println("Exiting TextEditor...\n" +
                "Thank you for using TextEditor! Created by NotFalse.");
    }

    public void createAddMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Text has been added");
        } else {
            createUserErrorMessage("Text has not been added");
        }
    }

    public void createDeleteMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Text deleted successfully!");
        } else {
            createUserErrorMessage("Text has not been deleted");
        }
    }

    public void createDummyMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Dummy text generated successfully!");
        } else {
            createUserErrorMessage("Dummy text has not been generated");
        }
    }

    public void createIndexMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Index generated successfully!");
        } else {
            createUserErrorMessage("Index has not been generated");
        }
    }

    public void createPrintMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Printing text...");
        } else {
            createUserErrorMessage("Text has not been printed");
        }
    }

    public void createReplaceMessage(boolean success) {
        if (success) {
            createUserInfoMessage("Text replaced successfully!");
        } else {
            createUserErrorMessage("Text has not been replaced");
        }
    }

    public void createInvalidCommandMessage() {
        System.out.println("Invalid command! Please try again.");
    }
}




