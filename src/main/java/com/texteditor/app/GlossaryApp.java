package com.texteditor.app;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The GlossaryApp class manages a glossary of words, mapping them to their
 * occurrences in a given text. The class provides methods to build and
 * retrieve a glossary from a list of words, and to perform related operations.
 */
public class GlossaryApp {

    private final TreeMap<String, List<Integer>> glossary;

    /**
     * Initializes a new instance of the GlossaryApp class.
     */
    public GlossaryApp() {
        glossary = new TreeMap<>();
    }

    /**
     * Rebuilds the glossary with the new text and prints it.
     *
     * @param text The list of words to be included in the glossary.
     */
    void printGlossary(List<String> text) {
        rebuildGlossary(text);
        glossary.keySet().forEach(word -> {
            List<Integer> indexes = glossary.get(word);

            String indexStream = indexes.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            OutputManager.logAndPrintInfoMessage(String.format("%-10s %s", word, indexStream));
        });
    }

    /**
     * Rebuilds the glossary with the new text.
     *
     * @param text The new text to be mapped in the glossary.
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
    private void insertGlossaryEntries(List<String> text, Map<String, Integer> wordFrequency) {
        for (String word : wordFrequency.keySet()) {
            glossary.put(word.trim(), findParagraphIndexes(text, word));
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
            String[] words = cleanedParagraph.split("\\s+");
            for (String word : words) {
                if (startWithUpperCase(word.trim())) {
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }
        }
        wordFrequency.entrySet().removeIf(entry -> entry.getValue() < 3);
        return wordFrequency;
    }

    /**
     * Checks if the given word starts with an uppercase letter.
     *
     * @param word The word to be checked.
     * @return true if the word starts with an uppercase letter, false otherwise.
     */
    boolean startWithUpperCase(String word) {
        word = word.trim();
        if (word.isEmpty()) {
            return false;
        }
        return Character.isUpperCase(word.charAt(0));
    }

    /**
     * Filters the given paragraph by removing all characters that are not letters
     * (A-Z or a-z) or spaces.
     *
     * @param paragraph The input paragraph to be filtered.
     * @return A new string containing only letters and spaces from the original
     * paragraph.
     */
    String filterParagraph(String paragraph) {
        return paragraph.replaceAll("[^A-Za-z ]", "");
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
    List<Integer> findParagraphIndexes(List<String> text, String word) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < text.size(); i++) {
            if (text.get(i).contains(word)) {
                indexes.add(i + 1);
            }
        }
        Collections.sort(indexes);
        return indexes;
    }

    /**
     * Gets the Glossary Map.
     *
     * @return returns the Glossary Map
     */
    SortedMap<String, List<Integer>> getGlossary() {
        return glossary;
    }

    /**
     * Check if the glossary is empty.
     *
     * @return true if the glossary is empty, false otherwise.
     */
    boolean isGlossaryEmpty() {
        return glossary.isEmpty();
    }
}
