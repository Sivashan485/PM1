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
     * Filters the unwanted characters from the input and only accepts the characters within the allowedRegex.
     *
     * @param textToFilter input text to be filtered
     * @return returns the filtered input text
     */
    public String filterInput(String textToFilter) {
        // implementation
        return textToFilter.replaceAll(allowedRegex, "");
    }

    /**
     * Receives the input from the user and filters it.
     *
     * @return returns the filtered input text
     */
    public String getFilteredInputLine() {
        String inputText = input.nextLine();
        inputText = filterInput(inputText);
        if (inputText == null) {
            inputText = " ";
        }
        return inputText;
    }

    /**
     * Splits the input text at the first space and returns the split array.
     * At the first position the Command is stored and at the second if given the index is stored.
     *
     * @return returns the split array with Command and index
     */
    public String[] splitInput() {
        String inputText = getFilteredInputLine();
        // Split the input text at the first space
        String[] splitText = inputText.split("\\s+", 2);

        for (Commands command : Commands.values()) {
            if (splitText[0].equalsIgnoreCase(command.getCommand())) {
                // If the first part of splitText matches a command, return the split array
                System.out.println(splitText[0]);
                return splitText;
            }
        }
        // If no command is found, return the original input as the only element in an array
        return new String[]{inputText};
    }

}
