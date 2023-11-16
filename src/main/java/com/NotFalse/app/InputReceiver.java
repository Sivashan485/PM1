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

    public String[] splitInput() {
        String[] splitedtext = new String[1];
        String inputText = input.nextLine();
        inputText = filterInput(inputText.trim());
        splitedtext[0] = inputText;
        for (Commands command : Commands.values()) {
            if (inputText.contains(command.name() + " ")) {
                // if a command contains then split it in two parts
                splitedtext = inputText.split(command.name());
                splitedtext[0] = command.name();
            }
        }

        return splitedtext;
    }

}
