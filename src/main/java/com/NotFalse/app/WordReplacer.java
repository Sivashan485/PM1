package com.NotFalse.app;

public class WordReplacer {
    private String textParagraph;
    private String[] words;
    private String originalWord, replacementWord;
    private boolean isWordChanged;

    public WordReplacer() {
        words = null;
    }


    public String replaceWordInParagraph(String paragraph, String originalWord, String replacementWord) {
        this.textParagraph = paragraph;
        if (originalWord.split(" ").length > 1) {
            return paragraph.replaceAll(originalWord, replacementWord);
        } else {
            splitTextInWords();
            setOrignalAndReplacementWord(originalWord, replacementWord);
            replaceOriginalWord();
            return combineWordsIntoText();
        }
    }

    private void splitTextInWords() {
        this.words = textParagraph.split(" ");
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
                isWordChanged = true;
            }
        }
    }


    private String combineWordsIntoText() {
        StringBuilder replacedWordText = new StringBuilder();
        if(isWordChanged){
            for (int i = 0; i<words.length; i++) {
                if(i==0){
                    replacedWordText.append(words[i]);
                }else{
                    replacedWordText.append(" ").append(words[i]);
                }
            }
        }else{
            replacedWordText = new StringBuilder(textParagraph);
        }
        return replacedWordText.toString();
    }



}
