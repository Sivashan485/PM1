package com.NotFalse.app;

import java.util.ArrayList;

public class Formatter {
    private FormatType formatType;
    private String formattedText;

    public static void main (String[] args) {
        Formatter formatter = new Formatter();

        ArrayList<String> testText = new ArrayList<>();
        testText.add("01234567890123456789 01 234 567 89 0123456789");
        testText.add("012345678901234567890123456789");
        testText.add("01234567890123456789012345678901234567890123456789012345678901234567890123456789");

        String result = formatter.formatTextRaw(testText, 80);
        System.out.println(result);
    }
    public Formatter() {

    }

    public String formatTextRaw(ArrayList<String> text, int maxWidth) {
        if (text == null) {
            return ""; // Handle null input by returning an empty string
        }
    
        StringBuilder formatted = new StringBuilder();
        String[] words = String.join(" ", text).split("\\s+"); // Splits the text into words
        int currentWidth = 0;
    
        for (String word : words) {
            // If the word itself is longer than maxWidth, break it down.
            while (word.length() > maxWidth) {
                if (currentWidth > 0) {
                    formatted.append("\n");
                    currentWidth = 0;
                }
                formatted.append(word, 0, maxWidth).append("\n");
                word = word.substring(maxWidth);
            }
    
            // Check if adding the current word exceeds maxWidth
            if (currentWidth + (currentWidth > 0 ? 1 : 0) + word.length() > maxWidth) {
                formatted.append("\n");
                currentWidth = 0;
            }
    
            // Add a space if it's not the first word on the line
            if (currentWidth > 0) {
                formatted.append(" ");
                currentWidth++;
            }
    
            formatted.append(word);
            currentWidth += word.length();
        }
    
        return formatted.toString();
    }
    

    public String formatTextFixed(ArrayList<String> text) {
        // implementation
        return formattedText;
    }

    public String getFormattedText() {
        return formattedText;
    }

    public FormatType getFormatType() {
        return formatType;
    }

    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }

}
