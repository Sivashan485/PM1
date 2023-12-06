package com.NotFalse.app;

import java.util.Scanner;

/**
 * Class for receiving user input and filtering the input with a regex.
 */
public class InputReceiver {

    private static final String ALLOWED_REGEX = "[^A-Za-zäöüÄÖÜ 0-9 / .,:;\\-!?'\\\\()\\\"%@+*{}\\\\\\\\&#$\\[\\]]";
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
    void resetValues() {
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
    public void parseInput() {
        String input = readAndFilterUserInput();
        userCommand = extractCommand(input);
        restPart = input.substring(userCommand.length()).trim();
        if(!validateAndSetIndex(userCommand, restPart)){
            userCommand = "unknown";
        }
    }



    /**
     * Extracts the command from the input
     *
     * @param userInput adfisjoij
     * @return returns the command
     */
    String extractCommand(String userInput) {
        String[] userInputPartition = userInput.toLowerCase().split(" ");
        ApplicationCommand command = ApplicationCommand.parseCommand(userInputPartition[0]);
        if (command == ApplicationCommand.UNKNOWN && userInputPartition.length > 1) {
            command = ApplicationCommand.parseCommand(userInputPartition[0] + " " + userInputPartition[1]);
        }
        if (command != ApplicationCommand.UNKNOWN) {
            return command.getCommand();
        }
        return "";
    }

    private boolean validateAndSetIndex(String command, String restPart) {
        if(ApplicationCommand.parseCommand(command).getCommandHasIndex()){
            if(restPart != null && !restPart.isEmpty()){
                handleIndexCommand();
            }else{
                setUserIndex();
            }
        } else{
            return "".equals(restPart);
        }
        return true;
    }

    private void handleIndexCommand(){
        if (restPart.matches("^[0-9]+$")) {
            setUserIndex();
            isIndexValid = true;
        }else{
            isIndexValid = false;
        }
    }

    private void setUserIndex(){
        isIndexValid = true;
        userIndex = null;
        try{
            if (restPart != null) {
                userIndex = Integer.parseInt(restPart);
            }
        }catch (NumberFormatException e){
            //left empty
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


    String getRestPart() {
        return restPart;
    }




}
