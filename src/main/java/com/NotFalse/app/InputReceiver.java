package com.NotFalse.app;

import java.util.Scanner;

/**
 * Class for receiving user input and filtering the input with a regex.
 */
public class InputReceiver {

    private final Scanner input;
    private final static String ALLOWED_REGEX = "[^A-Za-zäöüÄÖÜ 0-9 / .,:;\\-!?'\\\\()\\\"%@+*{}\\\\\\\\&#$\\[\\]]";
    private String command;
    private Integer index;
    private String restPart;
    private boolean IsIndexValid;
    private boolean indexIsNull;

    /**
     * Constructor for InputReceiver.
     */
    public InputReceiver() {
        input = new Scanner(System.in);
        command = "";
        index = 0;
    }

    private void resetValues() {
        IsIndexValid = true;
        index = 0;
        command = "";
        indexIsNull = false;
    }

    /**
     * Receives the input from the user and filters it.
     *
     * @return returns the filtered input text
     */
    String readAndFilterUserInput() {
        resetValues();
        String inputText = input.nextLine();
        return inputText.replaceAll(ALLOWED_REGEX, "");
    }

    /**
     * Splits the user input into a command and its arguments.
     */
    public void splitInput() {
        System.out.print(">> ");
        String userInput = readAndFilterUserInput();
        command = extractCommand(userInput);
        restPart = userInput.substring(command.length()).trim();
        command += validateAndSplitCommand(command, restPart);
    }

    /**
     * Extracts the command from the input
     *
     * @param userInput
     * @return returns the command
     */
    String extractCommand(String userInput) {
        String[] userInputPartition = userInput.toLowerCase().split(" ");
        for (Command command : Command.values()) {
            if (userInput.toLowerCase().startsWith(command.getCommand())) {
                if (userInputPartition[0].equals(command.getCommand())) {
                    return command.getCommand();
                } else if (isCommandMatchingInputPart(userInputPartition, command)) {
                    return command.getCommand();
                } else {
                    return "";
                }
            }
        }
        return "";
    }

    boolean isCommandMatchingInputPart(String[] userInputPartition, Command command) {
        return userInputPartition.length > 1
                && (userInputPartition[0] + " " + userInputPartition[1]).equals(command.getCommand());
    }

    /**
     * Validates the command and splits the input accordingly
     */
    private String validateAndSplitCommand(String command, String restPart) {
        // Handles commands that require an index
        if (Command.parseCommand(command).getCommandHasIndex() && !restPart.isEmpty()) {
            handleIndexCommand();
        } else if (!restPart.isEmpty()) {
            // Handle commands that should not have extra text
            return null;
        } else {
            return restPart;
        }
        return "";
    }

    /**
     * Handles commands that require an index
     * 
     * @param restPart
     */
    private void handleIndexCommand() {
        try {
            index = Integer.parseInt(restPart);
            if (index < 1) {
                IsIndexValid = false;
            }
        } catch (NumberFormatException e) {
            IsIndexValid = false;
        }
    }

    public boolean getIsIndexNull() {
        if (restPart.isEmpty()) {
            indexIsNull = true;
        } else {
            indexIsNull = false;
        }
        return indexIsNull;
    }



    /**
     * Retrieves the current command.
     * 
     * @return The current command as a string.
     */
    public String getCommand() {

        return command;
    }


    /**
     * Determines whether the index is invalid.
     * 
     * @return `true` if the index is invalid, `false` otherwise.
     */
    public boolean getIsIndexValid() {
        return IsIndexValid;
    }

    /**
     * Retrieves the current index.
     * 
     * @return The current index as an integer.
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Retrieves the remaining part of the user input after extracting the command.
     * 
     * @return The remaining part of the user input as a string.
     */
    String getRestPart() {
        return restPart;
    }
}
