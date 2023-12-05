package com.NotFalse.app;

import java.util.Scanner;

/**
 * Class for receiving user input and filtering the input with a regex.
 */
public class InputReceiver {

    private final static String ALLOWED_REGEX = "[^A-Za-zäöüÄÖÜ 0-9 / .,:;\\-!?'\\\\()\\\"%@+*{}\\\\\\\\&#$\\[\\]]";
    private final Scanner userInput;
    private String userCommand;
    private Integer userIndex;




    /**
     * Constructor for InputReceiver.
     */
    public InputReceiver() {
        userInput = new Scanner(System.in);
        resetValues();
    }

    /**
     * Resets the values of the instance variables associated with this class.
     * This method sets IsIndexValid to true, index to null, command to an empty string,
     * and indexIsNull to false.
     */
    private void resetValues() {
        userIndex = null;
        userCommand = "";
    }

    /**
     * Receives the input from the user and filters it.
     *
     * @return returns the filtered input text
     */
    String readAndFilterUserInput() {
        String inputText = userInput.nextLine();
        return inputText.replaceAll(ALLOWED_REGEX, "");
    }

    /**
     * Splits the user input into a command and its arguments.
     */
    public void parseUserInput() {
        String userInput = readAndFilterUserInput();
        //resetValues();
        setUserCommand(userInput);
        String restPart = userInput.substring(userCommand.length()).trim();
        setUserIndex(validateIndex(userCommand, restPart));
    }



    /**
     * Extracts the command from the input
     *
     * @param userInput
     * @return returns the command
     */
    String extractCommand(String userInput) {
        String[] userInputPartition = userInput.toLowerCase().split(" ");
        Command command = Command.parseCommand(userInputPartition[0]);
        if (command == Command.UNKNOWN && userInputPartition.length > 1) {
            command = Command.parseCommand(userInputPartition[0] + " " + userInputPartition[1]);
        }
        if (command != Command.UNKNOWN) {
            return command.getCommand();
        }
        return "";
    }

    /**
     * Validates the command and splits the input accordingly
     */
    private String validateIndex(String command, String restPart) {
        // Handles commands that require an restPart
        if (Command.parseCommand(command).getCommandHasIndex() && !restPart.isEmpty()){
            return handelIndexCommand(restPart);
        }else if(!restPart.isEmpty()){
            return null;

        }else{
            return restPart;
        }

    }

    private String handelIndexCommand(String restPart){
        System.out.println("command with index");
        String replaceUnallowedCharacters = restPart.replaceAll("[^1-9]", "");
        if (restPart.equals(replaceUnallowedCharacters)) {
            return restPart;
        }else {
            OutputManager.createUnallowedCharacterWarning();
            return null;

        }
    }


    /**
     * Checks if the 'restPart' is empty and sets 'indexIsNull' accordingly.
     * Returns the value of 'indexIsNull' after the check.
     *
     * @return {@code true} if 'restPart' is empty; otherwise, {@code false}.
     */
    public boolean isIndexNull() {
        return userIndex == null;
    }


    /**
     * Retrieves the current command.
     *
     * @return The current command as a string.
     */
    public String getUserCommand () {
        return userCommand;
    }

    /**
     * Retrieves the current index.
     *
     * @return The current index as an integer.
     */
    public Integer getUserIndex () {
        return userIndex;
    }

    public void setUserCommand(String userInput){
        userCommand = extractCommand(userInput);
    }

    public void setUserIndex(String restPart){
        userIndex = Integer.parseInt(restPart);
    }


}
