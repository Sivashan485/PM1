package com.NotFalse.app;

import java.util.Arrays;
import java.util.Scanner;

public class InputReceiver {

    private Scanner input;
    private final String allowedRegex ="([^A-z äöüÄÖÜ 0-9 .,:;\\-!?'()\\\"%@+*\\\\[\\\\]{}\\\\\\\\&#$])";


    public InputReceiver() {
        input = new Scanner(System.in);  // initialize new Scanner object
    }


    public String filterInput(String textToFilter) {
        // implementation
        return  textToFilter.replaceAll(allowedRegex,"");
    }

    public static void main(String[] args) {
        InputReceiver test = new InputReceiver();
        String [] a = test.splitInput();
        System.out.println(Arrays.toString(a));
    }
    public String []splitInput() {
        String []splitedtext = new String[1]; //local String Array
        String inputText = input.nextLine();
        inputText = filterInput(inputText.trim()); //Filtering the text using regex
        splitedtext[0]=inputText; //initalizing default value as Input
        for (Commands command : Commands.values()) {
            if (inputText.contains(command.name()+" ")) {
                //if a command contains then split it in two parts
                splitedtext= inputText.split(command.name());
                splitedtext[0] = command.name();
            }
        }
        // implementation
        return splitedtext;
    }


}
