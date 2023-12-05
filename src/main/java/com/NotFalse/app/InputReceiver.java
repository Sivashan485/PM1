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
    private String restPart;
    private boolean isIndexValid;




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
        restPart = "";
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
        userCommand = extractCommand(userInput);
        restPart = userInput.substring(userCommand.length()).trim();
        validateAndSplitCommand(userCommand, restPart);
        System.out.println("command: " +userCommand);
        System.out.println("index: " + restPart);
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
    private void validateAndSplitCommand(String command, String restPart) {
        // Handles commands that require an index
        if (Command.parseCommand(command).getCommandHasIndex() && !restPart.isEmpty()) {
            handleIndexCommand();
        }/*
        // Handle commands that should not have extra text
        else if (!restPart.isEmpty()) {
            return null;
        } else {
            return restPart;
        }
        return null;*/
    }

    private void handleIndexCommand(){
        String replaceUnallowedCharacters = restPart.replaceAll("[^1-9]", "");
        if (restPart.equals(replaceUnallowedCharacters)) {
            setUserIndex();
            isIndexValid = true;
        }else{
            isIndexValid = false;
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

    public boolean getIsIndexValid(){
        return isIndexValid;
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


    public String getRestPart() {
        return restPart;
    }

    public void setUserIndex(){
        try{
            if (restPart != null) {
                userIndex = Integer.parseInt(restPart);
            } else {
                userIndex = null;
            }
        }catch (Exception e){
            OutputManager.createUnallowedCharacterWarning();
        }

    }


}
