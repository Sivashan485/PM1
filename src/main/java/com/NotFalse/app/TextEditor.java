package com.NotFalse.app;

import java.util.ArrayList;

public class TextEditor {

    private ArrayList<String> text;
    private DummyGenerator generator;
    private Formatter formatter;
    private InputReceiver input;
    private OutputManager output;
    private Glossary glossary;
    private boolean isFormatterRaw;
    private boolean isExitTriggered;

    public TextEditor() {
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

    private void formatText() {
        // formatText implementation
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
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
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
