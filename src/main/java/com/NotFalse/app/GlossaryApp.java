package com.NotFalse.app;

import java.util.*;

/**
 * This class is responsible for creating the glossary and updating it.
 * The glossary is a TreeMap, which contains the words as keys and the indexes of the paragraphs
 * which contain the word as values.
 */
public class GlossaryApp {

    private final TreeMap<String, List<Integer>> glossary;

    /**
     * Constructor for GlossaryApp.
     */
    public GlossaryApp() {
        glossary = new TreeMap<>();
    }

    /**
     * Updates the glossary with the new text by creating a new glossary.
     *
     * @param text new text to be mapped
     * @return returns a new GlossaryApp
     */
    public GlossaryApp rebuildGlossary(List<String> text) {
        GlossaryApp newGlossary = new GlossaryApp();
        newGlossary.computeWordFrequency(text);
        newGlossary.insertGlossaryEntries(text);
        return newGlossary;
    }

    /**
     * Calculates the frequency of each word, puts them into the wordFrequency Map
     * and filters them.
     *
     * @param text text to be mapped
     */
    Map<String, Integer> computeWordFrequency(List<String> text) {
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (String paragraph : text) {
            String cleanedParagraph = filterParagraph(paragraph);
            String[] words = cleanedParagraph.split(" ");
            for (String word : words) {
                if (!wordFrequency.containsKey(word)) {
                    wordFrequency.put(word, 0);
                }
                int counter = wordFrequency.get(word);
                wordFrequency.put(word, ++counter);
            }
        }
        // filters the words that appear less than 3 times
        wordFrequency.entrySet().removeIf(entry -> entry.getValue() < 3);
        return wordFrequency;
    }

    /**
     * Filters the paragraph by removing all the non-alphabetic characters and
     * converting all the characters to lowercase.
     *
     * @param paragraph to be filtered
     * @return returns the filtered paragraph
     */
    String filterParagraph(String paragraph) {
        return paragraph.replaceAll("[^A-Za-z ]", " ").toLowerCase();
    }

    /**
     * Capitalizes the first letter of the word.
     *
     * @param word word to be capitalized
     * @return returns the capitalized word
     */
    String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty())
            return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    /**
     * Inserts the entries to the glossary.
     *
     * @param text text to be mapped
     */
    void insertGlossaryEntries(List<String> text) {
        for (String word : computeWordFrequency(text).keySet()) {
            String cleanedWord = capitalizeFirstLetter(word).trim();
            glossary.computeIfAbsent(cleanedWord, k -> searchWordInParagraphs(text, word));
        }
        if(glossary.isEmpty()){
            System.out.println("Your Glossary is empty...");
        }
        else{
            System.out.println("Glossary: ");
        }
    }


    /**
     * Finds the indexes of the paragraphs which contain the word and returns them
     * in an ArrayList.
     *
     * @param text text to be mapped
     * @param word word to be searched
     * @return returns a sorted ArrayList of the indexes of the paragraphs which
     * contain the word
     */
    List<Integer> searchWordInParagraphs(List<String> text, String word) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < text.size(); i++) {
            String cleanedParagraph = filterParagraph(text.get(i));
            if (cleanedParagraph.contains(word)) {
                indexes.add(i + 1);
            }

        }
        // sorts the indexes
        Collections.sort(indexes);
        return indexes;
    }

    /**
     * Gets the Glossary Map.
     *
     * @return returns the Glossary Map
     */
    TreeMap<String, List<Integer>> getGlossary() {
        return glossary;
    }

}
