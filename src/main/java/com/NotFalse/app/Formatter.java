package com.NotFalse.app;

import java.util.ArrayList;

public class Formatter {

    public String formatTextFix(ArrayList<String> text, int maxWidth) {

        String nullInputResult = handleNullInput(text);
        if (nullInputResult != null) {
            return nullInputResult;
        }

        StringBuilder fixFormatted = new StringBuilder();
        int currentWidth = 0;

        for (String line : text) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                // If the word itself is longer than maxWidth, break it down.
                while (word.length() > maxWidth) {
                    if (currentWidth > 0) {
                        fixFormatted.append("\n");
                        currentWidth = 0;
                    }
                    fixFormatted.append(word, 0, maxWidth).append("\n");
                    word = word.substring(maxWidth);
                }

                // Check if adding the current word exceeds maxWidth
                if (currentWidth + (currentWidth > 0 ? 1 : 0) + word.length() > maxWidth) {
                    fixFormatted.append("\n");
                    currentWidth = 0;
                }

                // Add a space if it's not the first word on the line
                if (currentWidth > 0) {
                    fixFormatted.append(" ");
                    currentWidth++;
                }

                fixFormatted.append(word);
                currentWidth += word.length();
            }
        }
        return fixFormatted.toString();
    }

    public String formatTextRaw(ArrayList<String> textList) {

        String nullInputResult = handleNullInput(textList);
        if (nullInputResult != null) {
            return nullInputResult;
        }

        StringBuilder rawFormatted = new StringBuilder();
        int paragraphNumber = 1;
        for (String text : textList) {
            rawFormatted.append("<").append(paragraphNumber).append(">: ").append(text).append("\n");
            paragraphNumber++;
        }
        return rawFormatted.toString();
    }

    private String handleNullInput(ArrayList<String> textList) {
        if (textList == null) {
            return "";
        }
        return null;
    }
}
