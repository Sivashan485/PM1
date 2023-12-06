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
    private final OutputManager output;
    private List<String> text;
    private Integer maxWidth;
    private String formattedText;
    boolean isFormatterFixSuccessful;
    boolean isFormatterRaw;

    private boolean validationFailed;
    public void setMaxWidth(Integer maxWidth){
        this.maxWidth = maxWidth;
    }

    public void setValidationFailed(boolean validationFailed)
    {
        this.validationFailed = validationFailed;
    }
    public boolean getValidationFailed(){
        return validationFailed;
    }

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
    void addDummyParagraph(boolean isIndexNull ,Integer paragraphIndex) {
        addNewParagraph(isIndexNull, TextManager.DUMMYTEXT, paragraphIndex);
    }


    /**
     * Adds a new paragraph to the text based on user input.
     * If the index is not provided or is invalid, the new paragraph is added at the end.
     * If the index is valid, the new paragraph is inserted at the specified position.
     */
    void addNewParagraph(boolean isIndexNull, String enteredText, Integer paragraphIndex) {
        boolean isSuccessful;
        if (isIndexNull) {
            text.add(enteredText);
            isSuccessful = true;
        } else if (paragraphIndex <= 0 || paragraphIndex -1 > text.size()) {
            isSuccessful = false;
            output.createIndexWarning();
        }else{
            text.add(paragraphIndex-1, enteredText);
            isSuccessful = true;
        }
        output.createAddMessage(isSuccessful);
    }



    /**
     * Deletes the paragraph at the specified index.
     */
    void deleteParagraph(boolean isIndexNull, Integer paragraphIndex) {
        boolean isSuccessful;
        if (isIndexNull) {
            text.remove(text.size() - 1);
            isSuccessful = true;
        } else if (paragraphIndex <= 0 || paragraphIndex > text.size()) {
            isSuccessful = false;
            output.createIndexWarning();

        }else{
            text.remove(paragraphIndex - 1);
            isSuccessful = true;
        }
        output.createDeleteMessage(isSuccessful);
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
    void formatTextRaw() {
        String formattedText = "";
        for (int paragraph = 0; paragraph < text.size(); paragraph++) {
            formattedText += "<" + (paragraph + 1) + ">: " + text.get(paragraph) + "\n";
        }
        setFormattedText(formattedText);
    }


    /**
     * Formats the given text to fit within the specified maximum width.
     *
     * @return The formatted text.
     */
    void formatTextFix() {
        if(maxWidth ==null){
            isFormatterFixSuccessful = false;
        }else{
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
                    currentParagraphWidth = appendNewLine(word, fixFormatted, currentParagraphWidth, maxWidth);
                    currentParagraphWidth = appendSpace(fixFormatted, currentParagraphWidth);
                    fixFormatted.append(word);
                    currentParagraphWidth += word.length();
                }
            }
            fixFormatted.append("\n");
            setFormattedText(fixFormatted.toString());
            isFormatterFixSuccessful = true;
        }

    }
    boolean getIsFormatterFixSuccessful(){
        return isFormatterFixSuccessful;
    }

    void setIsFormatterRaw(boolean isFormatterRaw){
        this.isFormatterRaw = isFormatterRaw;
    }



    int resetParagraphWidth(StringBuilder fixFormatted){
        fixFormatted.append("\n");
        return 0;
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
    int appendNewLine(String word, StringBuilder fixFormatted, int currentParagraphWidth, int maxWidth) {
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

    public void setFormattedText(String formattedText){
        this.formattedText = formattedText;
    }

    /**
     * Print the text.
     */
    void printText() {
        if(isFormatterRaw){
            formatTextRaw();
        }else{
            formatTextFix();
        }
        System.out.print(formattedText);
    }


    /**
     * Replaces occurrences of a specified word in the text list at the given index.
     *
     * @param index           The index of the text to be modified.
     */

    /**
     * Replaces occurrences of a specified word in the text list at the given index.
     *
     * @param index           The index of the text to be modified.
     */
    void replaceWordInParagraph(Integer index, String originalWord, String replacementWord) {
        String paragraph = text.get(index);
        paragraph = paragraph.replace(originalWord, replacementWord);
        boolean isReplacementSuccessful = !text.get(index).equalsIgnoreCase(paragraph);

        if (isReplacementSuccessful) {

            text.set(index, paragraph);
        }
        output.createReplaceMessage(isReplacementSuccessful);
    }

    boolean containsWord(String originalWord, Integer paragraphIndex){
        boolean isWordEmpty = originalWord.equals("");
        if(paragraphIndex == null){
            paragraphIndex = text.size();
        }
        if(!isWordEmpty&&text.get(paragraphIndex-1).contains(originalWord)){
            return true;
        }else{
            output.createInvalidWordWarning();
            return false;
        }
    }


    /**
     * Replaces the paragraphs in the specified range with the given text.
     */
    void replaceParagraphSection(boolean isIndexNull , String originalWord, String replacingWord, Integer paragraphIndex) {
        if (isIndexNull) {
            replaceWordInParagraph(text.size() - 1, originalWord, replacingWord);

        } else if( isIndexValid(paragraphIndex, text.size()+ 1, isIndexNull)){
            replaceWordInParagraph(paragraphIndex - 1, originalWord, replacingWord);
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
