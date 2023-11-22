package com.NotFalse.app;

import java.util.Scanner;

/**
 * Class for receiving user input and filtering the input with a regex.
 */
public class InputReceiver {

    final Scanner input;
    private final String allowedRegex = "([^A-z äöüÄÖÜ 0-9 .,:;\\-!?'()\\\"%@+*\\\\[\\\\]{}\\\\\\\\&#$])";

    /**
     * Constructor for InputReceiver.
     */
    public InputReceiver() {
        input = new Scanner(System.in);
    }

    /**
     * Receives the input from the user and filters it.
     *
     * @return returns the filtered input text
     */
    public String filterUserInput() {
        String inputText = input.nextLine();
        return inputText.replaceAll(allowedRegex, "");
    }

    /**
     * Spit
     */
    public String[] splitInput() {
        String inputText = filterUserInput();
        String command = extractCommand(inputText);

        if (command.isEmpty()) {
            return new String[]{inputText};
        }

        String restPart = inputText.substring(command.length()).trim();
        return validateAndSplitCommand(command, restPart);
    }


    // Extracts the command from the input
    private String extractCommand(String inputText) {
        for (Commands command : Commands.values()) {
            if (inputText.toLowerCase().startsWith(command.getCommand())) {
                return command.getCommand();
            }
        }
        return "";
    }

    // Validates the command and splits the input accordingly
    private String[] validateAndSplitCommand(String command, String restPart) {
        // Handles commands that require an index
        if (Commands.parseCommand(command).getIndex() != null && !restPart.isEmpty()) {
            return handleIndexCommand(command, restPart);
        } else if (!restPart.isEmpty()) {
            // Handle commands that should not have extra text
            return new String[]{"INVALID"};
        } else {
            return new String[]{command};
        }
    }

    // Handles commands that require an index
    private String[] handleIndexCommand(String command, String restPart) {
        try {
            Integer.parseInt(restPart); // Validate if it's a number
            return new String[]{command, restPart};
        } catch (NumberFormatException e) {
            return new String[]{"INVALID"};
        }
    }
}
