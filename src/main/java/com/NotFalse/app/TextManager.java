package com.NotFalse.app;

import java.util.ArrayList;
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
    private int fixedWidth;

    /**
     * Constructor for the TextManager class. It initializes the input, output,
     * glossary, text, isExitTriggered and isFormatterRaw variables.
     */
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

    /**
     * This method is responsible for the communication with the user. It calls
     * the methods for editing the text and formatting the text.
     */
    public void editText() {
        String[] userInput = input.splitInput();

        switch (Commands.lookupCommand(userInput[0])) {
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
                showGlossary();
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
                isFormatterRaw = true;
                formatTextRaw();
                break;
            case FORMAT_FIX:
                isFormatterRaw = false;
                formatTextFix();
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
        String enteredText = input.receiveUnsplittedParagraph();
        addIndexCheck(inputText, enteredText);
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
    String formatTextFix() {

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

    /**
     * If the word itself is longer than maxWidth, break it down. If the word itself
     * is longer than maxWidth, break it down.
     *
     * @param word
     * @param maxWidth
     * @param fixFormatted
     * @param currentWidth
     * @return
     */
    String breakDownLongWord(String word, int maxWidth, StringBuilder fixFormatted, int currentWidth) {
        // If the word itself is longer than maxWidth, break it down.
        while (word.length() > maxWidth) {
            if (currentWidth > 0) {
                fixFormatted.append("\n");
                currentWidth = 0;
            }
            fixFormatted.append(word, 0, maxWidth).append("\n");
            word = word.substring(fixedWidth);
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
     * @param currentWidth
     * @return
     */
    private int appendNewLine(String word, int maxWidth, StringBuilder fixFormatted, int currentWidth) {
        if (currentWidth + (currentWidth > 0 ? 1 : 0) + word.length() > fixedWidth) {
            fixFormatted.append("\n");
            currentWidth = 0;
        }
        return currentWidth;
    }

    /**
     * Add a space if it's not the first word on the paragraph.
     *
     * @param fixFormatted
     * @param currentWidth
     * @return
     */
    private int appendSpace(StringBuilder fixFormatted, int currentWidth) {
        // Add a space if it's not the first word on the paragraph
        if (currentWidth > 0) {
            fixFormatted.append(" ");
            currentWidth++;
        }
        return currentWidth;
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

    /**
     * Print the text.
     */
    private void printText() {
        StringBuilder sb = new StringBuilder();
        for (String paragraph : text) {
            sb.append(paragraph);
        }
        System.out.println(sb);

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
     * Separates a text paragraph based on a specified replacing word.
     * If the replacing word is found in the text paragraph, the method returns the portion
     * of the text that comes after the first occurrence of the replacing word.
     * If the replacing word is not present, an empty string is returned.
     *
     * @param textParagraph   The input text paragraph to be split.
     * @param replacingWord   The word used as a delimiter for splitting the text.
     * @return                The portion of the text after the first occurrence of the replacing word,
     *                        or an empty string if the replacing word is not found.
     */
    private String separateWordSyntax(String textParagraph, String replacingWord){
        // Split the text paragraph based on the replacing word
        String[] splitTextParagraph = textParagraph.split("(?i)" + replacingWord);
        // Check and return the second part of the split if it exists, otherwise an empty string
        return (splitTextParagraph.length > 1) ? splitTextParagraph[1] : "";
    }


    /**
     * Validates and performs word replacement in a text paragraph at the specified index.
     *
     * @param index The index in the 'text' list where the replacement should occur.
     * @param textParagraph The new text paragraph to replace the existing one.
     */
    private void validateWordReplacement(int index, String textParagraph) {
        // Check if the new text is different from the existing text at the specified index (case-insensitive)
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
     * @param index         The index of the text to be modified.
     * @param originalWord The word to be replaced.
     * @param replacementWord   The word to replace the specified word.
     */
    private void replaceWord(int index, String originalWord, String replacementWord){
        // Retrieve the text to be modified from the list
        String textParagraph =text.get(index);
        originalWord = originalWord.trim();
        replacementWord = replacementWord.trim();
        String wordEndSyntax = separateWordSyntax(textParagraph, originalWord);

        // Check and replace at the beginning of the text
        if(textParagraph.startsWith(originalWord)){
            textParagraph = textParagraph.replace(originalWord+" ",replacementWord+" " );
        }
        // Check and replace in the middle of the text
        if(textParagraph.contains(" "+originalWord+" ")){
            textParagraph = textParagraph.replaceAll(" "+originalWord+" "," "+replacementWord+" ");
        }
        // Remove the original text and insert the modified text back into the list
        if(textParagraph.endsWith(originalWord+wordEndSyntax)){
            // Replace the word with the replacement word
            textParagraph = textParagraph.replace(originalWord+wordEndSyntax, " "+replacementWord+wordEndSyntax);
        }
        //Adding replacement and validation for the text
        validateWordReplacement(index,textParagraph);
    }

    /**
     * Replaces the paragraphs in the specified range with the given text.
     */
    void replaceParagraphSection(String[] userInput) {
        System.out.print("Replacing Word: ");
        String originalWord = input.receiveUnsplittedParagraph();
        System.out.print("Replacing with: ");
        String replacementWord = input.receiveUnsplittedParagraph();

        if (userInput.length>1) {
            int index = Integer.parseInt(userInput[1])-1;
            if (index < text.size() && index>=0) {
                replaceWord(index,originalWord,replacementWord );
            } else{
                replaceWord(text.size()-1,originalWord,replacementWord );
            }
        }else{
            replaceWord(text.size()-1,originalWord,replacementWord );
        }
    }

    /**
     * Adds a dummy paragraph to the specified index. If the index is larger than
     * the size of the text, the dummy paragraph is added to the end of the text.
     */
    private void addDummyParagraph(String[] inputText) {
        addIndexCheck(inputText, DUMMYTEXT);
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
     * Getter for the fixed width.
     *
     * @return the fixed width
     */
    public int getFixedWidth() {
        return fixedWidth;
    }

    /**
     * Setter for the fixed width.
     *
     * @param fixedWidth
     */
    public void setFixedWidth(int fixedWidth) {
        this.fixedWidth = fixedWidth;
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
