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
    private static final String DUMMY_TEXT = "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
            "when an unknown printer took a galley of type and scrambled it to make a type specimen book." +
            " It has survived not only five centuries, but also the leap into electronic typesetting, " +
            "remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset" +
            " sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like " +
            "Aldus PageMaker including versions of Lorem Ipsum.";
    //private final OutputManager output;
    private List<String> text;
    private Integer maxWidth;
    private String formattedText;
    boolean isFormatterFixSuccessful;
    boolean isFormatterRaw;

    private boolean validationFailed;

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setValidationFailed(boolean validationFailed) {
        this.validationFailed = validationFailed;
    }

    public boolean getValidationFailed() {
        return validationFailed;
    }

    /**
     * Constructor for the TextManager class. It initializes the input, output,
     * glossary, text, isExitTriggered and isFormatterRaw variables.
     */
    TextManager() {
        text = new ArrayList<>();
        text.add("First Hello this is a Test sentence.");
        text.add("Second Another Test sentence.");
        text.add("Third useless Test sentence.");
        text.add("Fourth Hello Hello Hello");
        text.add("Fifth End of text.");
        formattedText = "";
        formatTextRaw();
        isFormatterRaw = true;
    }

    List<String> getTextList() {
        return text;
    }

    /**
     * Adds a dummy paragraph to the specified index. If the index is larger than
     * the size of the text, the dummy paragraph is added to the end of the text.
     */
    boolean addDummyParagraph(boolean isIndexNull, Integer paragraphIndex) {
        return addNewParagraph(isIndexNull, TextManager.DUMMY_TEXT, paragraphIndex);
    }


    /**
     * Adds a new paragraph to the text based on user input.
     * If the index is not provided or is invalid, the new paragraph is added at the
     * end.
     * If the index is valid, the new paragraph is inserted at the specified
     * position.
     */
    boolean addNewParagraph(boolean isIndexNull, String enteredText, Integer paragraphIndex) {
        if (isIndexNull) {
            text.add(enteredText);
            return  true;
        }else{
            text.add(paragraphIndex-1, enteredText);
            return  true;
        }
    }

    /**
     * Deletes the paragraph at the specified index.
     */
    boolean deleteParagraph(boolean isIndexNull, Integer paragraphIndex) {
        if (isIndexNull) {
            text.remove(text.size() - 1);
            return true;
        }else{
            text.remove(paragraphIndex - 1);
            return true;
        }
    }

    /**
     * Formats the given ArrayList of Strings into a single String with each element
     * of the ArrayList
     * preceded by its index in the ArrayList enclosed in angle brackets.
     */
    void formatTextRaw() {
        StringBuilder rawText = new StringBuilder();
        for (int paragraph = 0; paragraph < text.size(); paragraph++) {
            rawText.append((paragraph + 1)).append(": ").append(text.get(paragraph)).append("\n");
        }
        setFormattedText(rawText.toString());
    }

    /**
     * Formats the given text to fit within the specified maximum width.
     *
     */
    void formatTextFix() {
        if (maxWidth == null) {
            isFormatterFixSuccessful = false;
        }else{
            StringBuilder fixText = new StringBuilder();
            int currentParagraphWidth = 0;
            for (String paragraph : text) {
                String[] words = paragraph.split("\s+");
                for (String word : words) {
                    while (word.length() > maxWidth) {
                        // If the current line is not empty, start a new line.
                        if(currentParagraphWidth > 0){
                            currentParagraphWidth = resetParagraphWidth(fixText);
                        }
                        // Add the first maxWidth characters of the word to the current line.
                        fixText.append(word, 0, maxWidth);
                        // Remove the first maxWidth characters from the word.
                        word = word.substring(maxWidth);
                        // If the word is not empty, start a new line.
                        if (!word.isEmpty()) {
                            currentParagraphWidth = resetParagraphWidth(fixText);
                        }
                    }
                    currentParagraphWidth = appendNewLine(word, fixText, currentParagraphWidth, maxWidth);
                    currentParagraphWidth = appendSpace(fixText, currentParagraphWidth);
                    fixText.append(word);
                    currentParagraphWidth += word.length();
                }
                fixText.append("\n\n");
                currentParagraphWidth = 0;
            }
            setFormattedText(fixText.toString());
            isFormatterFixSuccessful = true;
        }
    }

    // Getter for isFormatterFixSuccessful.
    boolean getIsFormatterFixSuccessful() {
        return isFormatterFixSuccessful;
    }

    // Setter for isFormatterRaw.
    void setIsFormatterRaw(boolean isFormatterRaw) {
        this.isFormatterRaw = isFormatterRaw;
    }

    // Getter for isFormatterRaw.
    int resetParagraphWidth(StringBuilder fixFormatted) {
        fixFormatted.append("\n");
        return 0;
    }


    /**
     * Start a new line if the current line is full.
     * @param word
     * @param fixFormatted
     * @param currentParagraphWidth
     * @param maxWidth
     * @return
     */
    int appendNewLine(String word, StringBuilder fixFormatted, int currentParagraphWidth, int maxWidth) {
        if (currentParagraphWidth + (currentParagraphWidth > 0 ? 1 : 0) + word.length() > maxWidth) {
            currentParagraphWidth = resetParagraphWidth(fixFormatted);
        }
        return currentParagraphWidth;
    }

    /**
     * Add a space if it's not the first word on the paragraph.
     *
     * @param fixFormatted          The StringBuilder representing the formatted
     *                              text.
     * @param currentParagraphWidth The current width of the paragraph.
     * @return The updated current paragraph width.
     */
    int appendSpace(StringBuilder fixFormatted, int currentParagraphWidth) {
        if (currentParagraphWidth > 0) {
            fixFormatted.append(" ");
            currentParagraphWidth++;
        }
        return currentParagraphWidth;
    }

    // Setter for formattedText.
    private void setFormattedText(String formattedText) {
        this.formattedText = formattedText;
    }

    /**
     * Print the text.
     */
    void printText() {
        if (isFormatterRaw) {
            formatTextRaw();
        } else {
            formatTextFix();
        }
        OutputManager.logAndPrintInfoMessage(formattedText);
    }

    /**
     * Checks if the provided paragraph index is valid for the current text size.
     * Displays a warning message if the index is out of bounds and returns false.
     *
     * @param paragraphIndex The index to be validated.
     * @param textSize       The size of the current text.
     * @return {@code true} if the index is valid; otherwise, {@code false}.
     */
    /*public boolean isIndexValid(Integer paragraphIndex) {
        return (paragraphIndex <= 0 || paragraphIndex > text.size());
    }*/


    /**
     * Replaces occurrences of a specified word in the text list at the given index.
     *
     * @param index The index of the text to be modified.
     */

    /**
     * Replaces occurrences of a specified word in the text list at the given index.
     *
     * @param index The index of the text to be modified.
     */
    boolean replaceWordInParagraph(Integer index, String originalWord, String replacementWord) {
        String paragraph = text.get(index);
        paragraph = paragraph.replace(originalWord, replacementWord);
        boolean isReplacementSuccessful = !text.get(index).equalsIgnoreCase(paragraph);
        if (isReplacementSuccessful) {

            text.set(index, paragraph);
        }
        return isReplacementSuccessful;
    }

    boolean containsWord(String originalWord, Integer paragraphIndex){
        boolean isWordEmpty = "".equals(originalWord);
        if(paragraphIndex == null){
            paragraphIndex = text.size();
        }
        if(!isWordEmpty && text.get(paragraphIndex-1).contains(originalWord)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Replaces the paragraphs in the specified range with the given text.
     */
    boolean replaceParagraphSection(boolean isIndexNull, String originalWord, String replacingWord,
            Integer paragraphIndex) {
        //boolean isSuccessful = false;
        if (isIndexNull) {
            return replaceWordInParagraph(text.size() - 1, originalWord, replacingWord);

        }else{
            return replaceWordInParagraph(paragraphIndex - 1, originalWord, replacingWord);
        }
        //return false;
        //output.createReplaceMessage(isSuccessful);
    }

    boolean isTextEmpty(){
        return text.isEmpty();
    }




    /**
     * Getter for the text. It is used for testing.
     *
     * @return the text
     */
    public List<String> getText() {
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

    String getFormattedText(){
        return this.formattedText;
    }

    static String getDummyText(){
        return DUMMY_TEXT;
    }




}
