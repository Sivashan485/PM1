package com.NotFalse.app;

public class WordReplacer {

    private String[] words;
    private String originalWord, replacementWord;

    public WordReplacer() {
        words = null;
    }


    public String replaceWordInParagraph(String paragraph, String originalWord, String replacementWord) {
        if (originalWord.split(" ").length > 1) {
            return paragraph.replaceAll(originalWord, replacementWord);
        } else {
            splitTextInWords(paragraph);
            setOrignalAndReplacementWord(originalWord, replacementWord);
            replaceOriginalWord();
            return combineWordsIntoText();
        }
    }

    private void splitTextInWords(String paragraphText) {
        this.words = paragraphText.split(" ");
    }

    private void setOrignalAndReplacementWord(String originalWord, String replacementWord) {
        if (!originalWord.equals(" ")) {
            this.originalWord = originalWord;
            this.replacementWord = replacementWord;
        }
    }

    private void replaceOriginalWord() {
        for (int i = 0; i < words.length; i++) {
            if (originalWord.equals(words[i])) {
                words[i] = words[i].replaceAll(originalWord, replacementWord);
            }
        }
    }


    private String combineWordsIntoText() {
        String replacedWordText = "";
        for (String word : words) {
            replacedWordText = (replacedWordText+" "+ word);
        }
        return replacedWordText;
    }


    /*private String separatePunctuation(String paragraph, String replacingWord) {
        // Split the text paragraph based on the replacing word
        String[] splitTextParagraph = paragraph.split("(?i)" + replacingWord);
        // Check and return the second part of the split if it exists, otherwise an
        // empty string
        return (splitTextParagraph.length > 1) ? splitTextParagraph[1] : "";
    }

    private String separatePunctuation(String paragraph, String replacingWord) {
        // Split the text paragraph based on the replacing word
        String[] splitTextParagraph = paragraph.split("(?i)" + replacingWord);
        // Check and return the second part of the split if it exists, otherwise an
        // empty string
        return (splitTextParagraph.length > 1) ? splitTextParagraph[1] : "";
    }


    private void replaceWordInParagraph(int index, String originalWord, String replacementWord) {
        // Retrieve the text to be modified from the list
        String paragraph = text.get(index);
        originalWord = originalWord.trim();
        replacementWord = replacementWord.trim();
        String wordEndSyntax = separatePunctuation(paragraph, originalWord);

        // Check and replace at the beginning of the text
        if (paragraph.startsWith(originalWord)) {
            paragraph = paragraph.replace(originalWord + " ", replacementWord + " ");
        }
        // Check and replace in the middle of the text
        if (paragraph.contains(" " + originalWord + " ")) {
            paragraph = paragraph.replaceAll(" " + originalWord + " ", " " + replacementWord + " ");
        }
        // Remove the original text and insert the modified text back into the list
        if (paragraph.endsWith(originalWord + wordEndSyntax)) {
            // Replace the word with the replacement word
            paragraph = paragraph.replace(originalWord + wordEndSyntax, replacementWord + wordEndSyntax);
        }
        // Adding replacement and validation for the text
        boolean isReplacementSuccessful = !text.get(index).equalsIgnoreCase(paragraph);
        // If different, update the text at the index
        if (isReplacementSuccessful) {
            text.set(index, paragraph);
        }
        // Create a message indicating the replacement result
        output.createReplaceMessage(isReplacementSuccessful);
    }


    void replaceParagraph(Integer paragraphIndex) {
        System.out.print("Replacing Word: ");
        String originalWord = input.readAndFilterUserInput();
        System.out.print("Replacing with: ");
        String replacementWord = input.readAndFilterUserInput();

        if (paragraphIndex != null) {
            if (validateIndex(paragraphIndex)) {
                replaceWordInParagraph(paragraphIndex, originalWord, replacementWord);
            } else {
                output.createIndexWarning();
            }
        } else {
            replaceWordInParagraph(text.size() - 1, originalWord, replacementWord);
        }
    }*/


}
