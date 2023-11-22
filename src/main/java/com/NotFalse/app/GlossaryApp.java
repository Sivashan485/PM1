package com.NotFalse.app;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is responsible for creating the glossary and updating it.
 * The glossary is a TreeMap, which contains the words as keys and the indexes
 * of the paragraphs
 * which contain the word as values.
 */
public class GlossaryApp {

    private final TreeMap<String, List<Integer>> glossary;
    private OutputManager output;

    /**
     * Constructor for GlossaryApp.
     */
    public GlossaryApp(OutputManager output) {
        glossary = new TreeMap<>();
        this.output = output;

    }

    /**
     * Prints the glossary.
     */
    public void printGlossary(List<String> text) {
        rebuildGlossary(text);
        if (glossary.isEmpty()) {
            output.createEmptyGlossaryWarning();
        } else {
            System.out.println("Glossary:");
            for (String word : glossary.keySet()) {
                List<Integer> indexes = glossary.get(word);
                String indexStream = indexes.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "));
                System.out.printf("%-10s %s%n", word, indexStream);
            }
        }
    }

    /**
     * Rebuilds the glossary with the new text.
     *
     * @param text new text to be mapped
     */
    void rebuildGlossary(List<String> text) {
        glossary.clear();
        Map<String, Integer> wordFrequency = computeWordFrequency(text);
        insertGlossaryEntries(text, wordFrequency);
    }

    /**
     * Inserts the entries to the glossary based on the word frequency.
     *
     * @param text          text to be mapped
     * @param wordFrequency a map with the frequency of each word
     */
    void insertGlossaryEntries(List<String> text, Map<String, Integer> wordFrequency) {
        for (String word : wordFrequency.keySet()) {
            glossary.put(word.trim(), searchWordInParagraphs(text, word));
        }
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
                if (startWithUpperCase(word.trim())){
                    if (!wordFrequency.containsKey(word)) {
                        wordFrequency.put(word, 0);
                    }
                    int counter = wordFrequency.get(word);
                    wordFrequency.put(word, ++counter);
                    }
            }
        }
        wordFrequency.entrySet().removeIf(entry -> entry.getValue() < 3);
        return wordFrequency;
    }

    private boolean startWithUpperCase(String word){
        return Character.isUpperCase(word.charAt(0));
    }

    String filterParagraph(String paragraph) {
        return paragraph.replaceAll("[^A-Za-z ]", " ");
    }


    /**
     * Finds the indexes of the paragraphs which contain the word and returns them
     * in an ArrayList.
     *
     * @param text text to be mapped
     * @param word word to be searched
     * @return returns a sorted ArrayList of the indexes of the paragraphs which
     *         contain the word
     */
    List<Integer> searchWordInParagraphs(List<String> text, String word) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < text.size(); i++) {
            if (text.get(i).contains(word)) {
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
