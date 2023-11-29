package com.NotFalse.app;

public class WordReplacer {
    private String paragraph;
    private String[] words;
    private String originalWord, replacementWord;
    private boolean isWordChanged;

    public WordReplacer() {
        words = null;
    }

    public void setParagraph(String paragraph){
        this.paragraph = paragraph;
    }

    public void setOriginalWord(String originalWord){
        this.originalWord = originalWord;
    }

    public void setReplacementWord(String replacementWord){
        this.replacementWord = replacementWord;
    }


    public String replaceWordInParagraph(String paragraph, String originalWord, String replacementWord) {
        setParagraph(paragraph);
        if (originalWord.split(" ").length > 1) {
            return paragraph.replaceAll(originalWord, replacementWord);
        } else {
            this.words = this.paragraph.split(" ");
            setOriginalAndReplacementWord(originalWord, replacementWord);
            replaceOriginalWord();
            return combineWordsIntoText();
        }
    }



    private void setOriginalAndReplacementWord(String originalWord, String replacementWord) {
        if (!originalWord.equals(" ")) {
            setOriginalWord(originalWord);
            setReplacementWord(replacementWord);
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
        StringBuilder replacedPragraph = new StringBuilder();
        if(isWordChanged){
            for (int i = 0; i<words.length; i++) {
                if(i==0){
                    replacedPragraph.append(words[i]);
                }else{
                    replacedPragraph.append(" ").append(words[i]);
                }
            }
        }else{
            replacedPragraph = new StringBuilder(paragraph);
        }
        return replacedPragraph.toString();
    }



}
