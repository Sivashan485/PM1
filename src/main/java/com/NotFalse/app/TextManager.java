package com.NotFalse.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private List<String> text;
    private String formattedText;
    private int maxWidth;
    private boolean isFormatterRaw;
    private boolean isFormatRawSuccessful;
    private boolean isFormatFixSuccessful;
    private static final int MAX_MAXWIDTH = 2147483647;
    private Integer paragraphIndex;

    /**
     * Constructor for the TextManager class. It initializes the input, output,
     * glossary, text, isExitTriggered and isFormatterRaw variables.
     */
    TextManager() {
        input = new InputReceiver();
        output = new OutputManager();
        text = new ArrayList<>();
        text.add("First Hello this is a Test sentence.");
        text.add("Second Another Test sentence.");
        text.add("Third useless Test sentence.");
        text.add("Fourth Hello Hello Hello");
        text.add("Fifth End of text.");
        isFormatterRaw = true;
        output.createWelcomeMessage();

    }

    public boolean getIsFormatRawSuccessful() {
        return isFormatRawSuccessful;
    }

    public boolean getIsFormatFixSuccessful() {
        return isFormatFixSuccessful;
    }

    /**
     * This method is responsible for the communication with the user. It calls
     * the methods for editing the text and formatting the text.
     */

    boolean validateIndex(Integer paragraphIndex) {
        return paragraphIndex >= 0 && paragraphIndex <= text.size();
    }

    /**
     * Adds a dummy paragraph to the specified index. If the index is larger than
     * the size of the text, the dummy paragraph is added to the end of the text.
     */
    void addDummyParagraph() {
        if (paragraphIndex == null) {
            text.add(DUMMYTEXT);
            output.createAddMessage(true);
        } else if (validateIndex(paragraphIndex)) {
            text.add(paragraphIndex-1, DUMMYTEXT);
            output.createAddMessage(true);
        } else {
            output.createIndexWarning();
        }
    }

    void addNewParagraph() {
        String enteredText;

        if (input.getIsIndexNull()) {
            enteredText = receiveEnteredText();
            text.add(enteredText);
            output.createAddMessage(true);
        }
        if (isIndexValid(paragraphIndex, text.size() + 1)) {
            enteredText = receiveEnteredText();
            text.add(paragraphIndex - 1, enteredText);
            output.createAddMessage(true);
        }
    }

    private String receiveEnteredText() {
        System.out.print("Text: ");
        return input.readAndFilterUserInput();
    }

    /**
     * Deletes the paragraph at the specified index.
     */
    void deleteParagraph() {
        if (input.getIsIndexNull()) {
            text.remove(text.size() - 1);
            output.createDeleteMessage(true);
        }
        if (isIndexValid(paragraphIndex, text.size())) {
            text.remove(paragraphIndex - 1);
            output.createDeleteMessage(true);
        }
    }

    private boolean isIndexValid(int paragraphIndex, int textSize) {
        if (!input.getIsIndexNull()) {
            if (paragraphIndex <= 0 || paragraphIndex > textSize) {
                output.createIndexWarning();
                return false;
            }
            return true;
        }
        return false;
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
        try {
            for (int paragraph = 0; paragraph < text.size(); paragraph++) {
                formattedText += "<" + (paragraph + 1) + ">: " + text.get(paragraph) + "\n";
            }
            isFormatterRaw = true;
            isFormatRawSuccessful = true;
            return formattedText;
        } catch (Exception e) {
            output.createFormatMessage(false);
            isFormatRawSuccessful = false;
            return "";
        }
    }

    /**
     * Formats the given text to fit within the specified maximum width.
     *
     * @return The formatted text.
     */
    String formatTextFix() {
        if (!validateMaxWidth(maxWidth)) {
            isFormatFixSuccessful = false;
            return "";
        } else {
            StringBuilder fixFormatted = new StringBuilder();
            int currentParagraphWidth = 0;
            for (String paragraph : text) {
                String[] words = paragraph.split("\\s+");
                for (String word : words) {
                    // If the word itself is longer than maxWidth, break it down.
                    while (word.length() > maxWidth) {
                        // If the current line is not empty, start a new line.
                        resetParagraphWidth(currentParagraphWidth, fixFormatted);
                        // Add the first maxWidth characters of the word to the current line.
                        fixFormatted.append(word, 0, maxWidth);
                        // Remove the first maxWidth characters from the word.
                        word = word.substring(maxWidth);
                        // If the word is not empty, start a new line.
                        if (!word.isEmpty()) {
                            fixFormatted.append("\n");
                            currentParagraphWidth = 0;
                        }
                    }
                    currentParagraphWidth = appendNewLine(word, fixFormatted, currentParagraphWidth);
                    currentParagraphWidth = appendSpace(fixFormatted, currentParagraphWidth);
                    fixFormatted.append(word);
                    currentParagraphWidth += word.length();
                }
            }
            fixFormatted.append("\n");
            formattedText = fixFormatted.toString();
            isFormatterRaw = false;
            isFormatFixSuccessful = true;
            return formattedText;
        }
    }

    /**
     * If the current line is not empty, start a new line.
     *
     * @param currentParagraphWidth
     * @param fixFormatted
     */
    void resetParagraphWidth(int currentParagraphWidth, StringBuilder fixFormatted) {
        if (currentParagraphWidth > 0) {
            fixFormatted.append("\n");
            currentParagraphWidth = 0;
        }
    }

    /**
     * Check if the maxWidth is valid.
     *
     * @param maxWidth
     * @return
     */
    boolean validateMaxWidth(int maxWidth) {
        if (maxWidth <= 0 || maxWidth > MAX_MAXWIDTH) {
            return false;
        }
        return true;
    }

    /**
     * Check if adding the current word exceeds maxWidth, and if it does, add a new
     * line.
     *
     * @param word
     * 
     * @param fixFormatted
     * @param currentParagraphWidth
     * @return
     */
    int appendNewLine(String word, StringBuilder fixFormatted, int currentParagraphWidth) {
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
     * Print the text.
     */
    void printText() {
        if (text.isEmpty()) {
            output.createEmptyTextWarning();
        } else {
            if (isFormatterRaw) {
                formatTextRaw();
            } else {
                formatTextFix();
            }
        }
        System.out.println(formattedText);
    }


    /**
     * Replaces occurrences of a specified word in the text list at the given index.
     *
     * @param index           The index of the text to be modified.
     * @param originalWord    The word to be replaced.
     * @param replacementWord The word to replace the specified word.
     */
    private void replaceWordInParagraph(int index, String originalWord, String replacementWord) {
        // Retrieve the text to be modified from the list
        String paragraph = text.get(index);
        originalWord = originalWord.trim();
        replacementWord = replacementWord.trim();

        paragraph = paragraph.replaceAll(originalWord, replacementWord);
        boolean isReplacementSuccessful = !text.get(index).equalsIgnoreCase(paragraph);
        // If different, update the text at the index
        if (isReplacementSuccessful) {
            text.set(index, paragraph);
        }
        // Create a message indicating the replacement result
        output.createReplaceMessage(isReplacementSuccessful);
    }

    /**
     * Replaces the paragraphs in the specified range with the given text.
     */
    void replaceParagraph() {
        System.out.print("Replacing Word: ");
        String originalWord = input.readAndFilterUserInput();
        System.out.print("Replacing with: ");
        String replacementWord = input.readAndFilterUserInput();

        if (paragraphIndex != null) {
            if (validateIndex(paragraphIndex)) {
                replaceWordInParagraph(paragraphIndex-1, originalWord, replacementWord);
            } else {
                output.createIndexWarning();
            }
        } else {
            replaceWordInParagraph(text.size() - 1, originalWord, replacementWord);
        }
    }

    /**
     * Setter for the maxWidth.
     *
     * @param widthIndex Input from user
     */
    void setMaxWidth(Integer widthIndex) {
        if(widthIndex == null){
            output.createInvalidMaxWidthWarning();
        }else if (widthIndex <= 0){
            output.createIndexWarning();
        }else{
            this.maxWidth = widthIndex;
        }
    }

    /**
     * Setter for the text. It is used for testing.
     *
     * @param text
     */
    void setText(List<String> text) {
        this.text = text;
    }

    /**
     * Getter for the text. It is used for testing.
     *
     * @return the text
     */
    List<String> getText() {
        return Collections.unmodifiableList(text);
    }

}
