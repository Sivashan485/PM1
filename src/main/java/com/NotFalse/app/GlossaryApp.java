package com.NotFalse.app;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class GlossaryApp {

    private TreeMap<String, ArrayList<Integer>> glossary;
    private Map<String,Integer> wordFrequency;

    public GlossaryApp() {
        glossary = new TreeMap<>();
        wordFrequency = new HashMap<>();
    }

    public GlossaryApp updateGlossary(ArrayList<String> text) {
        GlossaryApp newGlossary = new GlossaryApp();
        newGlossary.calculateWordFrequency(text);
        newGlossary.insertEntriesToGlossary(text);
        return newGlossary;
    }

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
     * Removes all entries with a value less than 3.
     */
    void filterWordFrequency(){
        wordFrequency.entrySet().removeIf(entry -> entry.getValue() < 3);
    }


    void insertEntriesToGlossary(ArrayList<String> text) {
        for (String word : wordFrequency.keySet()) {
            glossary.computeIfAbsent(word, k -> findParagraphIndexes(text, word));
        }
    }

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
     * Returns the word frequency map.
     * @return
     */
    public Map<String, Integer> getWordFrequency() {
        return wordFrequency;
    }

    TreeMap<String, ArrayList<Integer>> getGlossary() {
        return glossary;
    }

}
