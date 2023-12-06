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
    private List<String> text;
    private Integer maxWidth;
    private String formattedText;
    boolean isFormatterFixSuccessful;
    boolean isFormatterRaw;


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
        }else{
            text.add(paragraphIndex-1, enteredText);
        }
        return true;
    }

    /**
     * Deletes the paragraph at the specified index.
     */
    boolean deleteParagraph(boolean isIndexNull, Integer paragraphIndex) {
        if (isIndexNull) {
            text.remove(text.size() - 1);
        }else{
            text.remove(paragraphIndex - 1);
        }
        return true;
    }

    /**
     * Formats the given ArrayList of Strings into a single String with each element
     * of the ArrayList
     * preceded by its index in the ArrayList enclosed in angle brackets.
     */
    boolean formatTextRaw() {
        StringBuilder rawText = new StringBuilder();
        for (int paragraph = 0; paragraph < text.size(); paragraph++) {
            rawText.append((paragraph + 1)).append(": ").append(text.get(paragraph)).append("\n");
        }
        setFormattedText(rawText.toString());
        return true;
    }

    /**
     * Formats the given text to fit within the specified maximum width.
     *
     */
    void formatTextFix() {
        if (!isMaxWidthValid()) {
            isFormatterFixSuccessful = false;
        } else {
            StringBuilder fixText = new StringBuilder();
            int currentParagraphWidth = 0;
            for (String paragraph : text) {
                String[] words = paragraph.split("\\s+");
                for (String word : words) {
                    currentParagraphWidth = handleWordWrapping(currentParagraphWidth, word, fixText);
                }
                fixText.append("\n\n");
                currentParagraphWidth = 0;
            }
            setFormattedText(fixText.toString());
            isFormatterFixSuccessful = true;
        }
    }

    private int handleWordWrapping(int currentParagraphWidth, String word, StringBuilder fixText) {
        while (word.length() > maxWidth) {
            if (currentParagraphWidth > 0) {
                currentParagraphWidth = resetParagraphWidth(fixText);
            }
            fixText.append(word, 0, maxWidth);
            word = word.substring(maxWidth);
            if (!word.isEmpty()) {
                currentParagraphWidth = resetParagraphWidth(fixText);
            }
        }
        currentParagraphWidth = appendNewLine(word, fixText, currentParagraphWidth);
        currentParagraphWidth = appendSpace(fixText, currentParagraphWidth);
        fixText.append(word);
        return currentParagraphWidth + word.length();
    }

    private int appendNewLine(String word, StringBuilder fixText, int currentParagraphWidth) {
        if (currentParagraphWidth + (currentParagraphWidth > 0 ? 1 : 0) + word.length() > maxWidth) {
            currentParagraphWidth = resetParagraphWidth(fixText);
        }
        return currentParagraphWidth;
    }

    private int appendSpace(StringBuilder fixText, int currentParagraphWidth) {
        if (currentParagraphWidth > 0) {
            fixText.append(" ");
            currentParagraphWidth++;
        }
        return currentParagraphWidth;
    }

    private boolean isMaxWidthValid() {
        return maxWidth != null;
    }

    private int resetParagraphWidth(StringBuilder fixFormatted) {
        fixFormatted.append("\n");
        return 0;
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
        return !isWordEmpty && text.get(paragraphIndex - 1).contains(originalWord);
    }

    /**
     * Replaces the paragraphs in the specified range with the given text.
     */
    boolean replaceParagraphSection(boolean isIndexNull, String originalWord, String replacingWord,
            Integer paragraphIndex) {
        if (isIndexNull) {
            return replaceWordInParagraph(text.size() - 1, originalWord, replacingWord);
        }else{
            return replaceWordInParagraph(paragraphIndex - 1, originalWord, replacingWord);
        }
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

    void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    boolean getIsFormatterFixSuccessful() {
        return isFormatterFixSuccessful;
    }

    void setIsFormatterRaw(boolean isFormatterRaw) {
        this.isFormatterRaw = isFormatterRaw;
    }
    private void setFormattedText(String formattedText) {
        this.formattedText = formattedText;
    }




}
