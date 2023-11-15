package com.NotFalse.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
    private List<String> text;
    private int fixedWidth;

    TextManager() {
        input = new InputReceiver();
        output = new OutputManager();
        glossary = new GlossaryApp();
        text = new ArrayList<>();
        text.add("This three thrEE Threeis a new test paragraph.\n");
        text.add("Another New test paragraph.\n");
        text.add("Another weird useless nEw test paragraph");
        isExitTriggered = false;
        isFormatterRaw = true;
        fixedWidth = 80;
        formatTextRaw();
        output.createWelcomeMessage();

    }

    public void editText() {
        String userInput[] = input.splitInput();
        switch (Commands.getCommandsEnum(userInput[0])) {
            case DUMMY:
                addDummyParagraph();
                break;
            case EXIT:
                output.createExitMessage();
                isExitTriggered = true;
                break;
            case ADD:
                addNewParagraph(userInput[1]);
                break;
            case DEL:
                deleteParagraph();
                break;
            case INDEX:
                showGlossary();
                break;
            case PRINT:
                printText();
                break;
            case REPLACE:
                replaceParagraphSection();
                break;
            case HELP:
                output.createMenuOptions();
                break;
            case FORMAT_RAW:
                isFormatterRaw = true;
                formatTextRaw();
                break;
            case FORMAT_FIX:
                isFormatterRaw = false;
                //setFixedWidth(Integer.parseInt(userInput[1]));
                formatTextFix(fixedWidth);
                break;
            default:
                System.err.println("UNKOWN ERROR");
                break;
        }
    }

    private void addNewParagraph(String inputText) {
        try {
            text.add(inputText);
            output.createAddMessage(true);
        } catch (Exception e) {
            output.createAddMessage(false);
        }

        // addText implementation
    }

    private void deleteParagraph() {
        // deleteText implementation
    }

    /**
     * Formats the given ArrayList of Strings into a single String with each element
     * of the ArrayList
     * preceded by its index in the ArrayList enclosed in angle brackets.
     * 
     * @return the formatted String
     */
    String formatTextRaw() {

        String newText = "";
        for (int paragraph = 0; paragraph < text.size(); paragraph++) {
            newText += "<" + (paragraph + 1) + ">: " + text.get(paragraph) + "\n";
        }
        return newText;
    }

    /**
     * Formats the given text to fit within the specified maximum width.
     * 
     * @return The formatted text.
     */
    String formatTextFix(int fixedWidth) {

        StringBuilder fixFormatted = new StringBuilder();
        int currentWidth = 0;

        for (String paragraph : text) {
            String[] words = paragraph.split("\\s+");
            for (String word : words) {
                // If the word itself is longer than maxWidth, break it down.
                word = breakDownLongWord(word, fixedWidth, fixFormatted, currentWidth);

                // Check if adding the current word exceeds maxWidth
                currentWidth = appendNewLine(word, fixedWidth, fixFormatted, currentWidth);

                // Add a space if it's not the first word on the paragraph
                currentWidth = appendSpace(fixFormatted, currentWidth);

                fixFormatted.append(word);
                currentWidth += word.length();
            }
        }
        return fixFormatted.toString();
    }

    String breakDownLongWord(String word, int maxWidth, StringBuilder fixFormatted, int currentWidth) {
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

    private int appendNewLine(String word, int maxWidth, StringBuilder fixFormatted, int currentWidth) {
        if (currentWidth + (currentWidth > 0 ? 1 : 0) + word.length() > maxWidth) {
            fixFormatted.append("\n");
            currentWidth = 0;
        }
        return currentWidth;
    }

    private int appendSpace(StringBuilder fixFormatted, int currentWidth) {
        // Add a space if it's not the first word on the paragraph
        if (currentWidth > 0) {
            fixFormatted.append(" ");
            currentWidth++;
        }
        return currentWidth;
    }

    // for being able to test the methods
    public void setText(List<String> text) {
        this.text = text;
    }

    private void printText() {
        StringBuilder sb = new StringBuilder();
        for(String paragraph : text) {
            sb.append(paragraph);
        }
        System.out.println(sb);

    }

    void showGlossary() {
        System.out.println("Glossary:");
        glossary = glossary.rebuildGlossary(text);
        for (String word : glossary.getGlossary().keySet()) {
            List<Integer> indexes = glossary.getGlossary().get(word);
            String indexesStr = indexes.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            System.out.printf("%-10s %s%n", word, indexesStr);
        }
    }

    void replaceParagraphSection() {
        // replaceText implementation
    }

    private void addDummyParagraph() {
        text.add(DUMMYTEXT);
    }

    public boolean getIsFormatterRaw() {
        return isFormatterRaw;
    }

    public void setIsFormatterRaw(boolean isFormatterRaw) {
        this.isFormatterRaw = isFormatterRaw;
    }

    public int getFixedWidth() {
        return fixedWidth;
    }

    public void setFixedWidth(int fixedWidth) {
        this.fixedWidth = fixedWidth;
    }

    public boolean getIsExitTriggered() {
        return isExitTriggered;
    }

    public void setIsExitTriggered(boolean isExitTriggered) {
        this.isExitTriggered = isExitTriggered;
    }
}
