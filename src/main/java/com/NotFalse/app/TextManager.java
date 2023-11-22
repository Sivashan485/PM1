package com.NotFalse.app;

import java.util.ArrayList;
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
    private final GlossaryApp glossary;
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
        glossary = new GlossaryApp(output);
        text = new ArrayList<>();
        text.add("12345678911234567892 12345678931234567894");
        isExitTriggered = false;
        formatTextRaw();
        output.createWelcomeMessage();
    }

    /**
     * This method is responsible for the communication with the user. It calls
     * the methods for editing the text and formatting the text.
     */
    public void editText() {
        String[] userInput = input.splitInput();

        switch (Commands.parseCommand(userInput[0])) {
            case DUMMY:
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
                glossary.printGlossary(text);
                break;
            case PRINT:
                printText();
                break;
            case REPLACE:
                replaceParagraphSection(userInput);
                break;
            case HELP:
                output.createMenuOptionsMessage();
                break;
            case FORMAT_RAW:
                formatTextRaw();
                break;
            case FORMAT_FIX:
                setMaxWidth(userInput);
                formatTextFix();
                break;
            default:
                output.createInvalidCommandMessage();
                break;
        }
    }

    /**
     * Adds a new paragraph to the text.
     */
    private void addNewParagraph(String[] userInput) {
        System.out.println("Text: ");
        String enteredText = input.filterUserInput();
        addIndexCheck(userInput, enteredText);
    }

    /**
     * Adds a dummy paragraph to the specified index. If the index is larger than
     * the size of the text, the dummy paragraph is added to the end of the text.
     */
    private void addDummyParagraph(String[] userInput) {
        addIndexCheck(userInput, DUMMYTEXT);
    }

    private void addIndexCheck(String[] userInput, String enteredText) {
        try {
            if (userInput.length > 1) {
                // the index is the second element of the array
                int index = Integer.parseInt(userInput[1]) - 1;
                if (index >= 0 && index <= text.size()) {
                    text.add(index, enteredText + "\n");
                }
            } else {
                text.add(enteredText + "\n");
            }
            output.createAddMessage(true);
        } catch (Exception e) {
            output.createAddMessage(false);
        }

    }

    /**
     * Deletes the paragraph at the specified index.
     */
    private void deleteParagraph(String[] userInput) {
        if (text.isEmpty()) {
            output.createEmptyTextWarning();
        }
        try {
            if (userInput.length > 1) {
                int index = Integer.parseInt(userInput[1]) - 1;
                if (index >= 0 && index < text.size()) {
                    text.remove(index);
                } else {
                    output.createIndexWarning();
                }
            } else {
                text.remove(text.size() - 1);
            }
            output.createDeleteMessage(true);
        } catch (Exception e) {
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
        try {
            for (int paragraph = 0; paragraph < text.size(); paragraph++) {
                formattedText += "<" + (paragraph + 1) + ">: " + text.get(paragraph) + "\n";
            }
            output.createFormatMessage(true);
            return formattedText;
        } catch (Exception e) {
            output.createFormatMessage(false);
            return "";
        }
    }

    /**
     * Formats the given text to fit within the specified maximum width.
     *
     * @return The formatted text.
     */
    String formatTextFix() {
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
                    if (!word.isEmpty()) {
                        fixFormatted.append("\n");
                        currentParagraphWidth = 0;
                    }
                }

                // Check if adding the current word exceeds maxWidth
                currentParagraphWidth = appendNewLine(word, fixFormatted, currentParagraphWidth);

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
    private void printText() {
        if (text.isEmpty()) {
            output.createEmptyTextWarning();
        } else {
            System.out.println(formattedText);
        }

    }

    String getFormattedText() {
        return formattedText;
    }

    /**
     * Separates a text paragraph based on a specified replacing word.
     * If the replacing word is found in the text paragraph, the method returns the
     * portion
     * of the text that comes after the first occurrence of the replacing word.
     * If the replacing word is not present, an empty string is returned.
     *
     * @param textParagraph The input text paragraph to be split.
     * @param replacingWord The word used as a delimiter for splitting the text.
     * @return The portion of the text after the first occurrence of the replacing
     *         word,
     *         or an empty string if the replacing word is not found.
     */
    private String separateWordSyntax(String textParagraph, String replacingWord) {
        // Split the text paragraph based on the replacing word
        String[] splitTextParagraph = textParagraph.split("(?i)" + replacingWord);
        // Check and return the second part of the split if it exists, otherwise an
        // empty string
        return (splitTextParagraph.length > 1) ? splitTextParagraph[1] : "";
    }

    /**
     * Validates and performs word replacement in a text paragraph at the specified
     * index.
     *
     * @param index         The index in the 'text' list where the replacement
     *                      should occur.
     * @param textParagraph The new text paragraph to replace the existing one.
     */
    private void validateWordReplacement(int index, String textParagraph) {
        // Check if the new text is different from the existing text at the specified
        // index (case-insensitive)
        boolean isReplacementSuccessful = !text.get(index).equalsIgnoreCase(textParagraph);
        // If different, update the text at the index
        if (isReplacementSuccessful) {
            text.set(index, textParagraph);
        }
        // Create a message indicating the replacement result
        output.createReplaceMessage(isReplacementSuccessful);
    }

    /**
     * Replaces occurrences of a specified word in the text list at the given index.
     *
     * @param index           The index of the text to be modified.
     * @param originalWord    The word to be replaced.
     * @param replacementWord The word to replace the specified word.
     */
    private void replaceWord(int index, String originalWord, String replacementWord) {
        // Retrieve the text to be modified from the list
        String textParagraph = text.get(index);
        originalWord = originalWord.trim();
        replacementWord = replacementWord.trim();
        String wordEndSyntax = separateWordSyntax(textParagraph, originalWord);

        // Check and replace at the beginning of the text
        if (textParagraph.startsWith(originalWord)) {
            textParagraph = textParagraph.replace(originalWord + " ", replacementWord + " ");
        }
        // Check and replace in the middle of the text
        if (textParagraph.contains(" " + originalWord + " ")) {
            textParagraph = textParagraph.replaceAll(" " + originalWord + " ", " " + replacementWord + " ");
        }
        // Remove the original text and insert the modified text back into the list
        if (textParagraph.endsWith(originalWord + wordEndSyntax)) {
            // Replace the word with the replacement word
            textParagraph = textParagraph.replace(originalWord + wordEndSyntax, " " + replacementWord + wordEndSyntax);
        }
        // Adding replacement and validation for the text
        validateWordReplacement(index, textParagraph);
    }

    /**
     * Replaces the paragraphs in the specified range with the given text.
     */
    void replaceParagraphSection(String[] userInput) {
        System.out.print("Replacing Word: ");
        String originalWord = input.filterUserInput();
        System.out.print("Replacing with: ");
        String replacementWord = input.filterUserInput();

        if (userInput.length > 1) {
            int index = Integer.parseInt(userInput[1]) - 1;
            if (index < text.size() && index >= 0) {
                replaceWord(index, originalWord, replacementWord);
            } else {
                replaceWord(text.size() - 1, originalWord, replacementWord);
            }
        } else {
            replaceWord(text.size() - 1, originalWord, replacementWord);
        }
    }

    /**
     * Setter for the maxWidth.
     *
     * @param userInput Input from user
     */
    void setMaxWidth(String[] userInput) {
        if (userInput.length > 1) {
            try {
                this.maxWidth = Integer.parseInt(userInput[1]);
            } catch (NumberFormatException e) {
                output.createInvalidArgumentWarning();
            }
        } else {
            output.createMissingArgumentWarning();

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
     * Setter for the text. It is used for testing.
     *
     * @param text
     */
    // for being able to test the methods
    public void setText(List<String> text) {
        this.text = text;
    }

}
