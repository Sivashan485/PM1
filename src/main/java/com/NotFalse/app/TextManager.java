package com.NotFalse.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for managing the text. It contains the text, the
 * glossary and the methods for editing the text. It also contains the methods
 * for formatting the text. It is also responsible for the communication with
 * the user.
 */
public class TextManager {
    final static String DUMMYTEXT = "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
            "when an unknown printer took a galley of type and scrambled it to make a type specimen book." +
            " It has survived not only five centuries, but also the leap into electronic typesetting, " +
            "remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset" +
            " sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like " +
            "Aldus PageMaker including versions of Lorem Ipsum.";
    private final InputReceiver input;

    private final OutputManager output;
    private GlossaryApp glossary;
    private boolean isFormatterRaw;
    private boolean isExitTriggered;
    private List<String> text;
    private String formattedText;
    private int maxWidth;

    /**
     * Constructor for the TextManager class. It initializes the input, output,
     * glossary, text, isExitTriggered and isFormatterRaw variables.
     */
    TextManager() {
        input = new InputReceiver();
        output = new OutputManager();
        glossary = new GlossaryApp();
        text = new ArrayList<>();
        text.add("12345678911234567892 12345678931234567894");
        isExitTriggered = false;
        isFormatterRaw = false;
        maxWidth = 10;
        formatTextRaw();
        output.createWelcomeMessage();
        printText();

    }

    /**
     * This method is responsible for the communication with the user. It calls
     * the methods for editing the text and formatting the text.
     */
    public void editText() {
        String[] userInput = input.splitInput();

        switch (Commands.lookupCommand(userInput[0])) {
            case DUMMY:
                System.out.println(Arrays.toString(text.toArray()));
                addDummyParagraph(userInput);
                break;
            case EXIT:
                output.createExitMessage();
                isExitTriggered = true;
                break;
            case ADD:
                addNewParagraph(userInput);
                break;
            case DEL:
                deleteParagraph(userInput);
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
                output.createMenuOptionsMessage();
                break;
            case FORMAT_RAW:
                isFormatterRaw = true;
                formatTextRaw();
                break;
            case FORMAT_FIX:
                isFormatterRaw = false;
                setMaxWidth(userInput);
                formatTextFix(maxWidth);
                break;
            default:
                System.err.println("UNKOWN ERROR");
                break;
        }
    }

    private void addIndexCheck(String[] inputText, String entredText) {
        try {
            if (inputText.length > 1) {
                int convertToInteger = Integer.parseInt(inputText[1]);
                if (convertToInteger - 1 <= text.size() && convertToInteger - 1 >= 0) {
                    text.add(convertToInteger - 1, entredText);
                } else {
                    text.add(entredText);
                }
            } else {
                text.add(entredText);
            }
            output.createAddMessage(true);
        } catch (Exception e) {
            output.createAddMessage(false);
        }

    }

    /**
     * Adds a new paragraph to the end of the text.
     */
    private void addNewParagraph(String[] inputText) {

        System.out.println("Text: ");
        String entredText = input.unsplittedText();
        /*
         * if (inputText.length>1) {
         * int convertToInteger = Integer.parseInt(inputText[1]);
         * if (convertToInteger-1 <= text.size() && convertToInteger-1>=0) {
         * text.add(convertToInteger-1, entredText);
         * } else{
         * text.add(entredText);
         * }
         * }else{
         * text.add(entredText);
         * }
         */
        addIndexCheck(inputText, entredText);
    }

    /**
     * Deletes the paragraph at the specified index.
     */
    private void deleteParagraph(String[] userInput) {
        if (userInput.length == 2) {
            try {
                int index = Integer.parseInt(userInput[1]) - 1;
                if (index >= 0 && index < text.size()) {
                    text.remove(index);
                    output.createDeleteMessage(true);
                } else {
                    output.createDeleteMessage(false);
                }
            } catch (NumberFormatException e) {
                output.createDeleteMessage(false);
            }
        } else {
            output.createDeleteMessage(false);
        }
    }

    /**
     * Formats the given ArrayList of Strings into a single String with each element
     * of the ArrayList
     * preceded by its index in the ArrayList enclosed in angle brackets.
     *
     * @return the formatted String
     */
    String formatTextRaw() {
        formattedText = "";
        for (int paragraph = 0; paragraph < text.size(); paragraph++) {
            formattedText += "<" + (paragraph + 1) + ">: " + text.get(paragraph) + "\n";
        }
        return formattedText;
    }

    /**
     * Formats the given text to fit within the specified maximum width.
     *
     * @return The formatted text.
     */
    String formatTextFix(int maxWidth) {
        StringBuilder fixFormatted = new StringBuilder();
        int currentParagraphWidth = 0;

        for (String paragraph : text) {
            String[] words = paragraph.split("\\s+");
            for (String word : words) {
                // If the word itself is longer than maxWidth, break it down.
                while (word.length() > maxWidth) {
                    // If the current line is not empty, start a new line.
                    if (currentParagraphWidth > 0) {
                        fixFormatted.append("\n");
                        currentParagraphWidth = 0;
                    }
                    // Add the first maxWidth characters of the word to the current line.
                    fixFormatted.append(word, 0, maxWidth);
                    // Remove the first maxWidth characters from the word.
                    word = word.substring(maxWidth);
                    // If the word is not empty, start a new line.
                    if (word.length() > 0) {
                        fixFormatted.append("\n");
                        currentParagraphWidth = 0;
                    }
                }

                // Check if adding the current word exceeds maxWidth
                currentParagraphWidth = appendNewLine(word, maxWidth, fixFormatted, currentParagraphWidth);

                // Add a space if it's not the first word on the paragraph
                currentParagraphWidth = appendSpace(fixFormatted, currentParagraphWidth);

                fixFormatted.append(word);
                currentParagraphWidth += word.length(); 
            }
        }
        formattedText = fixFormatted.toString();
        return formattedText;
    }

    /**
     * If the word itself is longer than maxWidth, break it down. If the word itself
     * is longer than maxWidth, break it down.
     *
     * @param word
     * @param maxWidth
     * @param fixFormatted
     * @param currentParagraphWidth
     * @return
     */
    String breakDownLongWord(String word, int maxWidth, StringBuilder fixFormatted, int currentParagraphWidth) {
        // If the word itself is longer than maxWidth, break it down.
        while (word.length() > maxWidth) {
            // If the current line is not empty, start a new line.
            if (currentParagraphWidth > 0) {
                fixFormatted.append("\n");
                currentParagraphWidth = 0;
            }
            // Add the first maxWidth characters of the word to the current line.
            fixFormatted.append(word, 0, maxWidth);
            // Remove the first maxWidth characters from the word.
            word = word.substring(maxWidth);
            // If the word is not empty, start a new line.
            if (word.length() > 0) {
                fixFormatted.append("\n");
                currentParagraphWidth = 0;
            }
        }
        return word;
    }

    /**
     * Check if adding the current word exceeds maxWidth, and if it does, add a new
     * line.
     *
     * @param word
     * @param maxWidth
     * @param fixFormatted
     * @param currentParagraphWidth
     * @return
     */
    int appendNewLine(String word, int maxWidth, StringBuilder fixFormatted, int currentParagraphWidth) {
        // if the word doesn't fit on the current line
        if (currentParagraphWidth + (currentParagraphWidth > 0 ? 1 : 0) + word.length() > maxWidth) {
            // add a new line
            fixFormatted.append("\n");
            // reset the current width to 0
            currentParagraphWidth = 0;
        }
        return currentParagraphWidth;
    }

    /**
     * Add a space if it's not the first word on the paragraph.
     *
     * @param fixFormatted
     * @param currentParagraphWidth
     * @return
     */
    int appendSpace(StringBuilder fixFormatted, int currentParagraphWidth) {
        // Add a space if it's not the first word on the paragraph
        if (currentParagraphWidth > 0) {
            fixFormatted.append(" ");
            currentParagraphWidth++;
        }
        return currentParagraphWidth;
    }

    /**
     * Setter for the text. It is used for testing.
     *
     * @param text
     */
    // for being able to test the methods
    void setText(List<String> text) {
        this.text = text;
    }

    /**
     * Print the text.
     */
    private void printText() {
        if (isFormatterRaw) {
            formatTextRaw();
        } else {
            formatTextFix(maxWidth);
        }
        System.out.println(getFormattedText());

    }

    String getFormattedText() {
        return formattedText;
    }

    /**
     * Prints the glossary.
     */
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

    /**
     * Replaces the paragraphs in the specified range with the given text.
     */
    void replaceParagraphSection() {
        String[] userInput = input.splitInput();
        if (userInput.length == 3) {
            try {
                int start = Integer.parseInt(userInput[1]) - 1;
                int end = Integer.parseInt(userInput[2]) - 1;

                if (start >= 0 && start < text.size() && end >= 0 && end < text.size() && start <= end) {
                    String replacementText = input.filterInput(input.input.nextLine().trim());

                    List<String> replacementParagraphs = Arrays.asList(replacementText.split("\n"));
                    text.subList(start, end + 1).clear();
                    text.addAll(start, replacementParagraphs);
                    output.createReplaceMessage(true);
                } else {
                    output.createReplaceMessage(false);
                }
            } catch (NumberFormatException e) {
                output.createReplaceMessage(false);
            }
        }
    }

    /**
     * Adds a dummy paragraph to the specified index. If the index is larger than
     * the size of the text, the dummy paragraph is added to the end of the text.
     */
    private void addDummyParagraph(String[] inputText) {
        addIndexCheck(inputText, DUMMYTEXT);
        /*
         * if (inputText.length>1) {
         * 
         * int convertToInteger = Integer.parseInt(inputText[1]);
         * if (convertToInteger-1 <= text.size() && convertToInteger-1>=0) {
         * text.add(convertToInteger-1, DUMMYTEXT);
         * } else{
         * text.add(DUMMYTEXT);
         * }
         * }else{
         * text.add(DUMMYTEXT);
         * }
         */
    }

    /**
     * Getter for the FormatterRaw text.
     *
     * @return the formatted text
     */
    public boolean getIsFormatterRaw() {
        return isFormatterRaw;
    }

    /**
     * Setter for the FormatterRaw text.
     *
     * @param isFormatterRaw
     */
    public void setIsFormatterRaw(boolean isFormatterRaw) {
        this.isFormatterRaw = isFormatterRaw;
    }

    /**
     * Getter for the maxWidth.
     *
     * @return the maxWidth
     */
    public int getMaxWidth() {
        return maxWidth;
    }

    /**
     * Setter for the maxWidth.
     *
     * @param maxWidth
     */
    private void setMaxWidth(String[] inputText) {
        if (inputText.length > 1) {
            try {
                this.maxWidth = Integer.parseInt(inputText[1]);
            } catch (NumberFormatException e) {
                System.err.println("MaxWidth argument must be an integer");
            }
        } else {
            System.err.println("Missing argument for MaxWidth");
        }
    }

    /**
     * Getter for the isExitTriggered boolean.
     *
     * @return the isExitTriggered boolean
     */
    public boolean getIsExitTriggered() {
        return isExitTriggered;
    }

    /**
     * Setter for the isExitTriggered boolean.
     *
     * @param isExitTriggered
     */
    public void setIsExitTriggered(boolean isExitTriggered) {
        this.isExitTriggered = isExitTriggered;
    }
}
