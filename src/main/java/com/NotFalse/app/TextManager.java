package com.NotFalse.app;

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

    TextManager() {
        input = new InputReceiver();
        output = new OutputManager();
        glossary = new GlossaryApp();
        texts = new ArrayList<String>();
    }

    public void editText() {

        String userInput[] = input.splitInput();
        switch (Commands.getCommandsEnum(userInput[0])) {
            case DUMMY:
                addDummyText();
                break;
            case EXIT:
                isExitTriggered = true;
                break;
            case ADD:
                addText(userInput[1]);
                break;
            case DEL:
                deleteText();
                break;
            case INDEX:
                showIndex();
                break;
            case PRINT:
                printText();
                break;
            case REPLACE:
                replaceText();
                break;
            case HELP:
                Commands.getCommandsAsString();
                break;
            case FORMAT_RAW:
                formatTextRaw(texts);
                break;
            case FORMAT_FIX:
                formatTextFix(texts, fixedWidth);
                break;
            default:
                System.out.println("UNKOWN ERROR");
                break;
        }
    }

    private void addText(String inputText) {
        try {
            texts.add(inputText);
            output.createAddMessage(true);
        } catch (Exception e) {
            output.createAddMessage(false);
        }

        // addText implementation
    }

    private void moveText() {
        // moveText implementation
    }

    private void deleteText() {
        // deleteText implementation
    }

    /**
     * Formats the given ArrayList of Strings into a single String with each element
     * of the ArrayList
     * preceded by its index in the ArrayList enclosed in angle brackets.
     *
     * @param text the ArrayList of Strings to be formatted
     * @return the formatted String
     */
    String formatTextRaw(){

        String newText = "";
        for (int paragraph = 0; paragraph < texts.size(); paragraph++) {
            newText += "<" + (paragraph + 1) + ">: " + texts.get(paragraph) + "\n";
        }
        return newText;
    }

    /**
     * Formats the given text to fit within the specified maximum width.
     *
     * @param text     The text to format.
     * @param maxWidth The maximum width of each line.
     * @return The formatted text.
     */
    String formatTextFix() {

        StringBuilder fixFormatted = new StringBuilder();
        int currentWidth = 0;

        for (String paragraph : text) {
            String[] words = paragraph.split("\\s+");
            for (String word : words) {
                // If the word itself is longer than maxWidth, break it down.
                word = breakDownLongWord(word, maxWidth, fixFormatted, currentWidth);

                // Check if adding the current word exceeds maxWidth
                currentWidth = appendNewLineIfNecessary(word, maxWidth, fixFormatted, currentWidth);

                // Add a space if it's not the first word on the paragraph
                currentWidth = appendSpaceIfNotFirstWord(fixFormatted, currentWidth);

                fixFormatted.append(word);
                currentWidth += word.length();
            }
        }
        return fixFormatted.toString();
    }

    private String breakDownLongWord(String word, int maxWidth, StringBuilder fixFormatted, int currentWidth) {
        // If the word itself is longer than maxWidth, break it down.
        while (word.length() > maxWidth) {
            if (currentWidth > 0) {
                fixFormatted.append("\n");
                currentWidth = 0;
            }
            fixFormatted.append(word, 0, maxWidth).append("\n");
            word = word.substring(maxWidth);
        }
        return word;
    }

    private int appendNewLineIfNecessary(String word, int maxWidth, StringBuilder fixFormatted, int currentWidth) {
        if (currentWidth + (currentWidth > 0 ? 1 : 0) + word.length() > maxWidth) {
            fixFormatted.append("\n");
            currentWidth = 0;
        }
        return currentWidth;
    }

    private int appendSpaceIfNotFirstWord(StringBuilder fixFormatted, int currentWidth) {
        // Add a space if it's not the first word on the paragraph
        if (currentWidth > 0) {
            fixFormatted.append(" ");
            currentWidth++;
        }
        return currentWidth;
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
