package com.NotFalse.app;
import java.util.*;

/**
 * This class is responsible for creating the glossary and updating it.
 */
public class GlossaryApp {

    private TreeMap<String, List<Integer>> glossary;


    /**
     * Constructor for GlossaryApp.
     */
    public GlossaryApp() {
        glossary = new TreeMap<>();
    }


    /**
     * Updates the glossary with the new text by creating a new glossary.
     * @param text new text to be mapped
     * @return returns a new GlossaryApp
     */
    public GlossaryApp rebuildGlossary(List<String> text) {
        GlossaryApp newGlossary = new GlossaryApp();
        newGlossary.computeWordFrequency(text);
        newGlossary.insertEntriesToGlossary(text);
        return newGlossary;
    }

    /**
     * Calculates the frequency of each word, puts them into the wordFrequency Map and filters them.
     * @param text text to be mapped
     */
    Map<String,Integer> computeWordFrequency(List<String> text) {
        Map<String,Integer> wordFrequency = new HashMap<>();
        for (String paragraph : text) {
            String cleanedParagraph = filterParagraph(paragraph);
            String[] words = cleanedParagraph.split(" ");
            for (String word : words) {
                if(!wordFrequency.containsKey(word)){
                    wordFrequency.put(word,0);
                }
                int counter = wordFrequency.get(word);
                wordFrequency.put(word,++counter);
            }
        }
        //filters the words that appear less than 3 times
        wordFrequency.entrySet().removeIf(entry -> entry.getValue() < 3);
        return wordFrequency;
    }

    String filterParagraph(String paragraphToFilter ){
        return paragraphToFilter.replaceAll("[^A-Za-z ]", " ").toLowerCase();
    }

    String capitalizeFirstLetter(String word) {
        if (word.equals(null) || word.length() == 0) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }


    /**
     * Inserts the entries to the glossary.
     * @param text text to be mapped
     */
    void insertEntriesToGlossary(List<String> text) {
        for (String word : computeWordFrequency(text).keySet()) {
            String cleanedWord = capitalizeFirstLetter(word).trim();
            glossary.computeIfAbsent(cleanedWord, k -> findParagraphIndexes(text, word));
        }
    }

    /**
     * Finds the indexes of the paragraphs which contain the word and returns them in an ArrayList.
     * @param text text to be mapped
     * @param word word to be searched
     * @return returns a sorted ArrayList of the indexes of the paragraphs which contain the word
     */
    List<Integer> findParagraphIndexes(List<String> text, String word) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < text.size(); i++) {
            String cleanedParagraph = filterParagraph(text.get(i));
            if (cleanedParagraph.contains(word)) {
                indexes.add(i);
            }
        }
        //sorts the indexes
        Collections.sort(indexes);
        return indexes;
    }


    /**
     * Gets the Glossary Map.
     * @return returns the Glossary Map
     */
    TreeMap<String, List<Integer>> getGlossary() {
        return glossary;
    }

}
