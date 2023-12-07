package com.texteditor.app;

import java.util.Scanner;

/**
 * The InputReceiver class is responsible for handling and processing user inputs.
 * It filters, parses, and extracts relevant parts of the user input such as
 * commands, indexes, and other arguments. The class ensures the input is valid
 * and conforms to the expected format for further processing.
 */
public class InputReceiver {

    private static final String DISALLOWED_CHARACTERS = "[^A-Za-zäöüÄÖÜ 0-9 / .,:;\\-!?'\\\\()\\\"%@+*{}\\\\\\\\&#$\\[\\]]";
    private static final String MAX_INT_VALUE = "^(214748364[0-7]|21474836[0-3][0-9]|2147483[0-5][0-9]{2}|214748[0-2][0-9]{3}|21474[0-7][0-9]{4}|2147[0-3][0-9]{5}|214[0-6][0-9]{6}|21[0-3][0-9]{7}|20[0-9]{8}|1[0-9]{9}|[1-9][0-9]{0,8}|0)$";
    private final Scanner userInput;
    private String userCommand;
    private Integer userIndex;
    private String restPart;
    private boolean isIndexValid;
    private boolean isCharacterValid;

    /**
     * Constructor for InputReceiver.
     */
    public InputReceiver() {
        userInput = new Scanner(System.in);
        resetValues();
    }

    /**
     * Resets the values of the instance variables associated with this class.
     * This method sets userIndex to null, userCommand to an empty string and
     * restPart to an empty string.
     */
    void resetValues() {
        userIndex = null;
        userCommand = "";
        restPart = "";
        isCharacterValid = true;
    }

    /**
     * Reads the user input, removes all characters that are not allowed and
     * returns the filtered input.
     *
     * @return The filtered input text.
     */
    String readAndFilterUserInput() {
        resetValues();
        String input = this.userInput.nextLine();
        String[] characters = input.split("");
        for (String character : characters) {
            if (character.matches(DISALLOWED_CHARACTERS)) {
                isCharacterValid = false;
                break;
            }
        }
        return input.replaceAll(DISALLOWED_CHARACTERS, "");
    }

    /**
     * Splits the user input into a command and its arguments. If the command
     * requires an index and it's not provided, it sets the command to "unknown".
     */
    void parseInput() {
        String input = readAndFilterUserInput();
        userCommand = extractCommand(input);
        restPart = input.substring(userCommand.length()).trim();
        if (!validateAndSetIndex(userCommand, restPart)) {
            userCommand = "unknown";
        }
    }

    /**
     * Extracts the command from the input. If the command is not recognized, it
     * tries to combine the first two words and parse them as a command.
     *
     * @param userInput The user's input.
     * @return The recognized command, or an empty string if no command is
     *         recognized.
     */
    private String extractCommand(String userInput) {
        String[] userInputPartition = userInput.toLowerCase().split(" ");
        CommandApp command = CommandApp.parseCommand(userInputPartition[0]);
        if (command == CommandApp.UNKNOWN && userInputPartition.length > 1) {
            command = CommandApp.parseCommand(userInputPartition[0] + " " + userInputPartition[1]);
        }
        if (command != CommandApp.UNKNOWN) {
            return command.getCommand();
        }
        return "";
    }

    /**
     * Validates the command and sets the index if required. If the command does not
     * require an index, it checks if the rest part of the input is empty.
     *
     * @param command  The command to validate.
     * @param restPart The rest part of the user input.
     * @return True if the command does not require an index and the rest part is
     *         empty, false otherwise.
     */
    private boolean validateAndSetIndex(String command, String restPart) {
        if (CommandApp.parseCommand(command).getCommandHasIndex()) {
            if (restPart != null && !restPart.isEmpty()) {
                handleIndexCommand();
            } else {
                setUserIndex();
            }
        } else {
            return "".equals(restPart);
        }
        return true;
    }

    /**
     * Handles the index command.
     * If the rest part of the command matches a numeric pattern, it sets the user
     * index and validates it.
     * If the rest part does not match the numeric pattern, it invalidates the
     * index.
     */
    private void handleIndexCommand() {
        isIndexValid = true;
        try {
            if (restPart.matches(MAX_INT_VALUE)) {
                setUserIndex();
            } else {
                isIndexValid = false;
            }
        } catch (NullPointerException e) {
            // left empty
        }
    }

    /**
     * Sets the user index from the rest part of the command.
     * If the rest part is not null and can be parsed as an integer, it sets the
     * user index to that integer.
     * If the rest part is not an integer, it catches the NumberFormatException and
     * leaves the user index as null.
     */
    private void setUserIndex() {
        isIndexValid = true;
        userIndex = null;
        try {
            if (restPart != null) {
                userIndex = Integer.parseInt(restPart);
            }
        } catch (NumberFormatException e) {
            // left empty
        }
    }

    /**
     * Checks if the userIndex is empty and sets 'indexIsNull' accordingly.
     * Returns the value of 'indexIsNull' after the check.
     *
     * @return true if userIndex is empty; otherwise false.
     */
    boolean isIndexNull() {
        return userIndex == null;
    }

    /**
     * Returns the validity of the index.
     *
     * @return true if the index is valid, false otherwise.
     */
    boolean getIsIndexValid() {
        return isIndexValid;
    }

    /**
     * Retrieves the current command.
     *
     * @return The current command as a string.
     */
    String getUserCommand() {
        return userCommand;
    }

    /**
     * Retrieves the current index.
     *
     * @return The current index as an integer.
     */
    Integer getUserIndex() {
        return userIndex;
    }

    /**
     * Retrieves the rest part of the command.
     *
     * @return The restpart of the command as a string.
     */
    String getRestPart() {
        return restPart;
    }

    /**
     * Retrieves the validity of the character.
     * 
     * @return true if the character is valid, false otherwise.
     */
    boolean getIsCharacterValid() {
        return isCharacterValid;
    }
}
