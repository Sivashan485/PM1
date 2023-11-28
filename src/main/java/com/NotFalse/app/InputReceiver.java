package com.NotFalse.app;

import java.util.Scanner;

/**
 * Class for receiving user input and filtering the input with a regex.
 */
public class InputReceiver {

    private final Scanner input;
    private final static String ALLOWED_REGEX = "([^A-z äöüÄÖÜ 0-9 .,:;\\-!?'()\\\"%@+*\\\\[\\\\]{}\\\\\\\\&#$])";
    private String command;
    private Integer index;
    private String restPart;
    private boolean isIndexInvalid;

    /**
     * Constructor for InputReceiver.
     */
    public InputReceiver() {
        input = new Scanner(System.in);
        command = "";
        index = null;
    }

    /**
     * Receives the input from the user and filters it.
     *
     * @return returns the filtered input text
     */
    public String readAndFilterUserInput() {
        isIndexInvalid = false;
        String inputText = input.nextLine();
        return inputText.replaceAll(ALLOWED_REGEX, "");
    }

    /**
     * Splits the user input into a command and its arguments.
     */
    public void splitInput() {
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
    private String extractCommand(String userInput) {
        for (Command command : Command.values()) {
            if (userInput.toLowerCase().startsWith(command.getCommand())) {
                return command.getCommand();
            }
        }
        return "";
    }

    /**
     * Validates the command and splits the input accordingly
     */
    private String validateAndSplitCommand(String command, String restPart) {
        // Handles commands that require an index
        if (Command.parseCommand(command).getCommandHasIndex() && !restPart.isEmpty()) {
            handleIndexCommand(restPart);
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
     * @param restPart
     */
    private void handleIndexCommand(String restPart) {
        try {
            index = Integer.parseInt(restPart);
        } catch (NumberFormatException e) {
            isIndexInvalid = true;
            System.err.println("Please enter a valid index.");
        }
    }

    /**
     * Retrieves the current command.
     * @return The current command as a string.
     */
    public String getCommand() {

        return command;
    }

    /**
     * Converts the index for list usage
     *
     * @return The valid list index or `null` if the conversion fails.
     */
    public Integer convertToListIndex() {
        try {
            return index - 1;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }

    }


    /**
     * Determines whether the index is invalid.
     * @return `true` if the index is invalid, `false` otherwise.
     */
    public boolean getIsIndexInvalid() {
        return isIndexInvalid;
    }

    /**
     * Retrieves the current index.
     * @return The current index as an integer.
     */
    public Integer getIndex() {
        return index;
    }


    /**
     * Retrieves the remaining part of the user input after extracting the command.
     * @return The remaining part of the user input as a string.
     */
    public String getRestPart() {
        return restPart;
    }
}
