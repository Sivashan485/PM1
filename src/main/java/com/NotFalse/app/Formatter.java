package com.NotFalse.app;

import java.util.ArrayList;

public class Formatter {


    public Formatter(){
  
    }

    public String formatTextFix(ArrayList<String> text, int maxWidth) {

        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        StringBuilder fixFormatted = new StringBuilder();
        int currentWidth = 0;

        for (String paragraph : text) {
            String[] words = paragraph.split("\\s+");
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

                // Add a space if it's not the first word on the paragraph
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

    private void breakLongWords(String word){}



    public String formatTextRaw(ArrayList<String> text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        StringBuilder rawFormatted = new StringBuilder();
        int paragraphNumber = 1;
        for (String paragraph : text) {
            rawFormatted.append("<").append(paragraphNumber).append(">: ").append(paragraph).append("\n");
            paragraphNumber++;
        }
        return rawFormatted.toString();
    }


}
