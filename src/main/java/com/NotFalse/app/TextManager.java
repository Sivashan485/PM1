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
    private static final int MAX_MAXWIDTH = 2147483647;
    private final OutputManager output;
    private List<String> text;
    private int maxWidth;
    private boolean isFormatterRaw;
    private Integer paragraphIndex;

    /**
     * Constructor for the TextManager class. It initializes the input, output,
     * glossary, text, isExitTriggered and isFormatterRaw variables.
     */
    TextManager() {
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

    List<String> getTextList() {
        return text;
    }

    /**
     * Sets the paragraph index for the text manager.
     *
     * @param paragraphIndex The index to be set.
     */
    public void setParagraphIndex(Integer paragraphIndex) {
        this.paragraphIndex = paragraphIndex;
    }





    /**
     * Adds a dummy paragraph to the specified index. If the index is larger than
     * the size of the text, the dummy paragraph is added to the end of the text.
     */
    void addDummyParagraph(boolean isIndexNull) {
        addNewParagraph(isIndexNull, TextManager.DUMMYTEXT);
    }


    /**
     * Adds a new paragraph to the text based on user input.
     * If the index is not provided or is invalid, the new paragraph is added at the end.
     * If the index is valid, the new paragraph is inserted at the specified position.
     */
    void addNewParagraph(boolean isIndexNull, String enteredText) {
        boolean isSuccessful;
        if (isIndexNull) {
            text.add(enteredText);
            isSuccessful = true;
        } else if (isIndexValid(paragraphIndex, text.size() + 1 , isIndexNull)) {
            text.add(paragraphIndex - 1, enteredText);
            isSuccessful = true;
        }else{
            isSuccessful = false;
        }
        output.createAddMessage(isSuccessful);
    }



    /**
     * Deletes the paragraph at the specified index.
     */
    void deleteParagraph(boolean isIndexNull) {
        if(text.isEmpty()){
            output.createEmptyTextWarning();
        }
        boolean isSuccessful;
        if (isIndexNull && !text.isEmpty()) {
            text.remove(text.size() - 1);
            isSuccessful = true;
        } else if (isIndexValid(paragraphIndex, text.size(), isIndexNull)) {
            text.remove(paragraphIndex - 1);
            isSuccessful = true;
        }else{
            isSuccessful = false;
        }
        output.createDeleteMessage(isSuccessful);
    }

    public int getTextSize(){
        return text.size();
    }

    /**
     * Checks if the provided paragraph index is valid for the current text size.
     * Displays a warning message if the index is out of bounds and returns false.
     *
     * @param paragraphIndex The index to be validated.
     * @param textSize       The size of the current text.
     * @return {@code true} if the index is valid; otherwise, {@code false}.
     */
    public boolean isIndexValid(Integer paragraphIndex, int textSize, boolean isIndexNull) {
        if (!isIndexNull && paragraphIndex != null) {
            if (paragraphIndex <= 0 || paragraphIndex > textSize) {
                output.createIndexWarning();
                return false;
            }
            return true;
        } else {
            output.createIndexWarning();
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
        String formattedText = "";
        for (int paragraph = 0; paragraph < text.size(); paragraph++) {
            formattedText += "<" + (paragraph + 1) + ">: " + text.get(paragraph) + "\n";
        }
        return formattedText;
    }

    public void setIsFormatterRaw(boolean status){
        this.isFormatterRaw = status;
    }

    /**
     * Formats the given text to fit within the specified maximum width.
     *
     * @return The formatted text.
     */
    String formatTextFix() {
        if(!isMaxWidthValid(maxWidth)){
            return "";
        }
        StringBuilder fixFormatted = new StringBuilder();
        int currentParagraphWidth = 0;
        for (String paragraph : text) {
            String[] words = paragraph.split("\\s+");
            for (String word : words) {
                while (word.length() > maxWidth) {
                    // If the current line is not empty, start a new line.
                    if(currentParagraphWidth > 0){
                        currentParagraphWidth = resetParagraphWidth(fixFormatted);
                    }
                    // Add the first maxWidth characters of the word to the current line.
                    fixFormatted.append(word, 0, maxWidth);
                    // Remove the first maxWidth characters from the word.
                    word = word.substring(maxWidth);
                    // If the word is not empty, start a new line.
                    if (!word.isEmpty()) {
                        currentParagraphWidth = resetParagraphWidth(fixFormatted);
                    }
                }
                currentParagraphWidth = appendNewLine(word, fixFormatted, currentParagraphWidth);
                currentParagraphWidth = appendSpace(fixFormatted, currentParagraphWidth);
                fixFormatted.append(word);
                currentParagraphWidth += word.length();
            }
        }
        fixFormatted.append("\n");
        return fixFormatted.toString();
    }


    int resetParagraphWidth(StringBuilder fixFormatted){
        fixFormatted.append("\n");
        return 0;
    }


    /**
     * Check if the maxWidth is valid.
     *
     * @param maxWidth The maximum width to be validated.
     * @return {@code true} if the maxWidth is valid; otherwise, {@code false}.
     */
    boolean isMaxWidthValid(int maxWidth) {
        return maxWidth <= MAX_MAXWIDTH && maxWidth > 0;
    }

    /**
     * Check if adding the current word exceeds maxWidth, and if it does, add a new
     * line.
     *
     * @param word                  The word to be appended.
     * @param fixFormatted          The StringBuilder representing the formatted text.
     * @param currentParagraphWidth The current width of the paragraph.
     * @return The updated current paragraph width.
     */
    int appendNewLine(String word, StringBuilder fixFormatted, int currentParagraphWidth) {
        if (currentParagraphWidth + (currentParagraphWidth > 0 ? 1 : 0) + word.length() > maxWidth) {
            currentParagraphWidth = resetParagraphWidth(fixFormatted);
        } return currentParagraphWidth;
    }

    /**
     * Add a space if it's not the first word on the paragraph.
     *
     * @param fixFormatted          The StringBuilder representing the formatted text.
     * @param currentParagraphWidth The current width of the paragraph.
     * @return The updated current paragraph width.
     */
    int appendSpace(StringBuilder fixFormatted, int currentParagraphWidth) {
        if (currentParagraphWidth > 0) {
            fixFormatted.append(" ");
            currentParagraphWidth++;
        } return currentParagraphWidth;
    }

    /**
     * Print the text.
     */
    void printText() {
        if (text.isEmpty()) {
            output.createEmptyTextWarning();
        } else {
            if (isFormatterRaw) {
                System.out.print(formatTextRaw());
            } else {
                System.out.print(formatTextFix());
            }
        }
    }


    /**
     * Replaces occurrences of a specified word in the text list at the given index.
     *
     * @param index           The index of the text to be modified.
     */
    void replaceWordInParagraph(int index, String originalWord, String replacementWord) {
        String paragraph = text.get(index);
        paragraph = paragraph.replace(originalWord, replacementWord);
        boolean isReplacementSuccessful = !text.get(index).equalsIgnoreCase(paragraph);

        if (isReplacementSuccessful) {
            text.set(index, paragraph);
        }
        output.createReplaceMessage(isReplacementSuccessful);
    }

    boolean containsWord(String originalWord, int index){
        if(text.get(index).contains(originalWord)){
            return true;
        }else{
            output.createInvalidWordWarning();
            return false;
        }
    }


    /**
     * Replaces the paragraphs in the specified range with the given text.
     */
    void replaceParagraphSection(boolean isIndexNull , String originalWord, String replacingWord) {
        if (isIndexNull) {
            replaceWordInParagraph(text.size() - 1, originalWord, replacingWord);

        } else if( isIndexValid(paragraphIndex, text.size()+ 1, isIndexNull)){
            replaceWordInParagraph(paragraphIndex - 1, originalWord, replacingWord);
        }
    }


    /**
     * Setter for the maxWidth.
     *
     * @param widthIndex Input from user
     */
    void setMaxWidth(Integer widthIndex) {
        if (widthIndex == null) {
            output.createInvalidMaxWidthWarning();
        } else if (widthIndex <= 0) {
            output.createIndexWarning();
        } else {
            this.maxWidth = widthIndex;
        }
    }

    /**
     * Getter for the text. It is used for testing.
     *
     * @return the text
     */
    List<String> getText() {
        return Collections.unmodifiableList(text);
    }

    /**
     * Setter for the text. It is used for testing.
     *
     * @param text The list of strings representing the text content.
     */
    void setText(List<String> text) {
        this.text = text;
    }

}
