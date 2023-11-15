package com.NotFalse.app;

import java.util.ArrayList;

public class Formatter {

    /**
     * Formats the given ArrayList of Strings into a single String with each element
     * of the ArrayList
     * preceded by its index in the ArrayList enclosed in angle brackets.
     *
     * @param text the ArrayList of Strings to be formatted
     * @return the formatted String
     */
    public String formatTextRaw(ArrayList<String> text) {

        String newText = "";
        for (int paragraph = 0; paragraph < text.size(); paragraph++) {
            newText += "<" + (paragraph + 1) + ">: " + text.get(paragraph) + "\n";
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
    public String formatTextFix(ArrayList<String> text, int maxWidth) {

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

}
