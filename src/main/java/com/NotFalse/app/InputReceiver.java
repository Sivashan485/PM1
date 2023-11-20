package com.NotFalse.app;

import java.util.Scanner;

public class InputReceiver {

    final Scanner input;
    private final String allowedRegex = "([^A-z äöüÄÖÜ 0-9 .,:;\\-!?'()\\\"%@+*\\\\[\\\\]{}\\\\\\\\&#$])";

    public InputReceiver() {
        input = new Scanner(System.in);
    }

    public String filterInput(String textToFilter) {
        // implementation
        return textToFilter.replaceAll(allowedRegex, "");
    }

    public String getFilteredInputLine() {
        String inputText = input.nextLine();
        inputText = filterInput(inputText);
        if(inputText==null){
            inputText =" ";
        }
        return inputText;
    }

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
        return new String[] { inputText };
    }

}
