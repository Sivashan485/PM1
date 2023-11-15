package com.NotFalse.app;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TextManager {
    final static String DUMMYTEXT = "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
            "when an unknown printer took a galley of type and scrambled it to make a type specimen book." +
            " It has survived not only five centuries, but also the leap into electronic typesetting, " +
            "remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset" +
            " sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like " +
            "Aldus PageMaker including versions of Lorem Ipsum.";
    private InputReceiver input;

    private OutputManager output;
    private GlossaryApp glossary;
    private boolean isFormatterRaw;
    private boolean isExitTriggered;
    private ArrayList texts;
    private int fixedWidth;



    TextManager(){
         input = new InputReceiver();
         output = new OutputManager();
         glossary = new GlossaryApp();
         texts = new ArrayList<String>();
    }


    public void editText(){

        String userInput[] = input.splitInput();
        switch(Commands.getCommandsEnum(userInput[0])){
            case DUMMY: addDummyText();
                break;
            case EXIT: isExitTriggered = true;
                break;
            case ADD:  addText();
                break;
            case DEL: deleteText();
                break;
            case INDEX: showIndex();
                break;
            case PRINT:printText();
                break;
            case REPLACE:replaceText();
            break;
            case HELP:Commands.getCommandsAsString();
            break;
            case FORMAT_RAW:
            break;
            case FORMAT_FIX:break;
            default:
                System.out.println("UNKOWN ERROR");
                break;
        }
    }

    private void addText() {
        // addText implementation
    }

    private void moveText() {
        // moveText implementation
    }

    private void deleteText() {
        // deleteText implementation
    }
    /*
     * public String formatTexts(ArrayList<String> texts, int maxWidth, Commands
     * command, OutputManager output) {
     * return formatter.formatText(texts, maxWidth, command, output);
     * }
     */



    private String formatText(ArrayList<String> text, int maxWidth, Commands commands) {
        switch (commands) {
            case FORMAT_FIX:
                isFormatterRaw = false;
               // return formatTextFix(text, maxWidth);
            case FORMAT_RAW:
                isFormatterRaw = true;
                //return formatTextRaw(text);
            default:
                throw new IllegalArgumentException("Invalid command: " + commands.toString());
        }
    }

    private void printText() {
        System.out.println(Arrays.toString(texts.toArray()));
        // printText implementation
    }

    private void showIndex() {
        // showIndex implementation
    }

    private void replaceText() {
        // replaceText implementation
    }

    private void addDummyText() {
        texts.add(DUMMYTEXT);
    }

    private boolean checkForExit() {
        // checkForExit implementation
        return false;
    }



    public ArrayList<String> getText() {
        return texts;
    }

    public void setText(ArrayList<String> texts) {
        this.texts = texts;
    }

    public boolean getIsFormatterRaw() {
        return isFormatterRaw;
    }

    public void setIsFormatterRaw(boolean isFormatterRaw) {
        this.isFormatterRaw = isFormatterRaw;
    }

    public boolean getIsExitTriggered() {
        return isExitTriggered;
    }

    public void setIsExitTriggered(boolean isExitTriggered) {
        this.isExitTriggered = isExitTriggered;
    }




}
