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
        String inputText = input.nextLine();
        return inputText.replaceAll(ALLOWED_REGEX, "");
    }

    /**
     *
     */
    public void splitInput() {
        String userInput = readAndFilterUserInput();
        command = extractCommand(userInput);
        String restPart = userInput.substring(command.length()).trim();
        command += validateAndSplitCommand(command, restPart);
    }

    // Extracts the command from the input
    private String extractCommand(String userInput) {
        for (Command command : Command.values()) {
            if (userInput.toLowerCase().startsWith(command.getCommand())) {
                return command.getCommand();
            }
        }
        return "";
    }

    // Validates the command and splits the input accordingly
    private String validateAndSplitCommand(String command, String restPart) {
        // Handles commands that require an index
        if (Command.parseCommand(command).getCommandHasIndex() && !restPart.isEmpty()) {
            handleIndexCommand(restPart);
        } else if (!restPart.isEmpty()) {
            // Handle commands that should not have extra text
            return  null;
        } else {
            return restPart;
        }
        return "";
    }

    // Handles commands that require an index
    private void handleIndexCommand(String restPart) {
        try {
           index = Integer.parseInt(restPart);
        } catch (NumberFormatException e) {
            System.err.println("Please enter a valid index.");
        }
    }

    public String getCommand() {
       
        return command;
    }

    public Integer convertToListIndex() {
        try {
            return  index-1;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
        
    }

    public Integer getIndex() {
        return index;
    }

}
