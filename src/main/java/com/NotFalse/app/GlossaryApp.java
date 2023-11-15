package com.NotFalse.app;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is responsible for creating the glossary and updating it.
 */
public class GlossaryApp {

    private TreeMap<String, ArrayList<Integer>> glossary;
    private Map<String,Integer> wordFrequency;

    /**
     * Constructor for GlossaryApp.
     */
    public GlossaryApp() {
        glossary = new TreeMap<>();
        wordFrequency = new HashMap<>();
    }

    /**
     * Updates the glossary with the new text by creating a new glossary.
     * @param text new text to be mapped
     * @return returns a new GlossaryApp
     */
    public GlossaryApp updateGlossary(ArrayList<String> text) {
        GlossaryApp newGlossary = new GlossaryApp();
        newGlossary.calculateWordFrequency(text);
        newGlossary.insertEntriesToGlossary(text);
        return newGlossary;
    }

    /**
     * Calculates the frequency of each word, puts them into the wordFrequency Map and filters them.
     * @param text text to be mapped
     */
    void calculateWordFrequency(ArrayList<String> text) {
        for (String paragraph : text) {
            String[] words = paragraph.split(" ");
            for (String word : words) {
                if(!wordFrequency.containsKey(word)){
                    wordFrequency.put(word,0);
                }
                int counter = wordFrequency.get(word);
                wordFrequency.put(word,++counter);
            }
        }
        filterWordFrequency();
    }

    /**
     * Filters all entries out of the wordFrequency with a value less than 3.
     */
    void filterWordFrequency(){
        wordFrequency.entrySet().removeIf(entry -> entry.getValue() < 3);
    }

    /**
     * Inserts the entries to the glossary.
     * @param text text to be mapped
     */
    void insertEntriesToGlossary(ArrayList<String> text) {
        for (String word : wordFrequency.keySet()) {
            glossary.computeIfAbsent(word, k -> findParagraphIndexes(text, word));
        }
    }

    /**
     * Finds the indexes of the paragraphs which contain the word and returns them in an ArrayList.
     * @param text text to be mapped
     * @param word word to be searched
     * @return returns a sorted ArrayList of the indexes of the paragraphs which contain the word
     */
    ArrayList<Integer> findParagraphIndexes(ArrayList<String> text, String word) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < text.size(); i++) {
            if (text.get(i).contains(word)) {
                indexes.add(i);
            }
        }
        //sorts the indexes
        Collections.sort(indexes);
        return indexes;
    }
  
    /*boolean isIndexValid(int index) {
        return index >= 0 && index < text.size();
    }*/

    /**
     * Gets the WordFrequency Map.
     * @return returns the WordFrequency Map
     */
    public Map<String, Integer> getWordFrequency() {
        return wordFrequency;
    }

    /**
     * Gets the Glossary Map.
     * @return returns the Glossary Map
     */
    TreeMap<String, ArrayList<Integer>> getGlossary() {
        return glossary;
    }

}
