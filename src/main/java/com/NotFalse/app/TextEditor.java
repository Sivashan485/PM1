package com.NotFalse.app;

import java.util.ArrayList;

public class TextEditor {

    private ArrayList<String> texts;
    private DummyGenerator generator;
    private Formatter formatter;
    private InputReceiver input;
    private OutputManager output;
    private GlossaryApp glossary;
    private boolean isFormatterRaw;
    private boolean isExitTriggered;

    public TextEditor() {

        this.formatter = new Formatter();
        // Constructor implementation
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    private void startTextEditor() {
        // startTextEditor implementation
    }

    private void runTextEditor() {
        // runTextEditor implementation
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
                    return formatter.formatTextFix(text, maxWidth);
                case FORMAT_RAW:
                    isFormatterRaw = true;
                    return formatter.formatTextRaw(text);
                default:
                    throw new IllegalArgumentException("Invalid command: " + commands.toString());
        }
    }    

    private void printText() {  
        // printText implementation
    }

    private void showIndex() {
        // showIndex implementation
    }

    private void replaceText() {
        // replaceText implementation
    }

    private void addDummyText() {
        // addDummyText implementation
    }

    private boolean checkForExit() {
        // checkForExit implementation
        return false;
    }

    private void showHelpMessage() {
        // showHelpMessage implementation
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
