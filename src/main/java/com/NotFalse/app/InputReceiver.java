package com.NotFalse.app;

import java.util.Scanner;

/**
 * Class for receiving user input and filtering the input with a regex.
 */
public class InputReceiver {

    private final static String ALLOWED_REGEX = "[^A-Za-zäöüÄÖÜ 0-9 / .,:;\\-!?'\\\\()\\\"%@+*{}\\\\\\\\&#$\\[\\]]";
    private final Scanner input;
    private String command;
    private Integer index;
    private String restPart;
    private boolean indexIsNull;


    /**
     * Constructor for InputReceiver.
     */
    public InputReceiver() {
        input = new Scanner(System.in);
        command = "";
        index = null;
    }

    /**
     * Resets the values of the instance variables associated with this class.
     * This method sets IsIndexValid to true, index to null, command to an empty string,
     * and indexIsNull to false.
     */
    private void resetValues() {
        index = null;
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

    /**
     * Checks if the combination of the first two elements in 'userInputPartition'
     * matches the command string obtained from the provided 'Command' object.
     *
     * @param userInputPartition User input partitioned into segments.
     * @param command            The 'Command' object to compare against.
     * @return {@code true} if the concatenation of the first two elements matches the command; otherwise, {@code false}.
     */
    boolean isCommandMatchingInputPart(String[] userInputPartition, Command command) {
        return userInputPartition.length > 1 && (userInputPartition[0] + " " + userInputPartition[1]).equals(command.getCommand());
    }

    /**
     * Validates the command and splits the input accordingly
     */
    private String validateAndSplitCommand(String command, String restPart) {
        // Handles commands that require an index
        if (Command.parseCommand(command).getCommandHasIndex() && !restPart.isEmpty()) {
            handleIndexCommand();
        }
        // Handle commands that should not have extra text
        else if (!restPart.isEmpty()) {

            return null;
        } else {
            return restPart;
        }
        return "";
    }


    /**
     * Checks if the 'restPart' is empty and sets 'indexIsNull' accordingly.
     * Returns the value of 'indexIsNull' after the check.
     *
     * @return {@code true} if 'restPart' is empty; otherwise, {@code false}.
     */
    public boolean getIsIndexNull() {
        indexIsNull = restPart.isEmpty();
        return indexIsNull;
    }

    private void handleIndexCommand() {
        try{
            index = Integer.parseInt(restPart);
        }catch (NumberFormatException e){
            OutputManager.createUnallowedCharacterWarning();
        }

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
