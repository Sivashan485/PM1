package com.NotFalse.app;

import java.util.ArrayList;

public class Formatter {

    private String formattedText;

    public static void main(String[] args) {
        Formatter formatter = new Formatter();

        ArrayList<String> paragraphs = new ArrayList<>();
        paragraphs.add(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris.");
        paragraphs.add("Maecenas congue ligula ac quam viverra nec consectetur ante hendrerit. Donec et mollis dolor.");
        paragraphs.add("Praesent et diam eget libero egestas mattis sit amet vitae augue.");
        paragraphs.add("Nam tincidunt congue enim, ut porta lorem lacinia consectetur.");

        String rawText = formatter.formatTextRaw(paragraphs);
        String fixedText = formatter.formatTextFix(paragraphs, 30);

        System.out.println("This is the Text in fixesFormat\n\n" + fixedText);
        System.out.println("\n\n\n");
        System.out.println("This is the Text in rawFormat\n\n" + rawText);

    }

    public String formatTextFix(ArrayList<String> text, int maxWidth) {
        if (text == null) {
            return ""; // Handle null input by returning an empty string
        }

        StringBuilder fixFormatted = new StringBuilder();
        String[] words = String.join(" ", text).split("\\s+"); // Splits the text into words
        int currentWidth = 0;

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

        return fixFormatted.toString();
    }

    public String formatTextRaw(ArrayList<String> textList) {
        // Check if the input is null and return an empty string if it is
        if (textList == null) {
            return "";
        }

        StringBuilder rawFormatted = new StringBuilder();
        int paragraphNumber = 1;
        for (String text : textList) {
            rawFormatted.append("<").append(paragraphNumber).append(">: ").append(text).append("\n");
            paragraphNumber++;
        }
        this.formattedText = rawFormatted.toString();
        return formattedText;
    }
}
