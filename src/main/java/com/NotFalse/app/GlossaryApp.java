package com.NotFalse.app;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;


public class GlossaryApp {

    private TreeMap<String, ArrayList<Integer>> glossary;
    private List<String> text;
    private Map<String,Integer> wordFrequency;

    public GlossaryApp(List<String> text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        glossary = new TreeMap<>();
        wordFrequency = new HashMap<>();
        this.text = text;
        
    }

    public GlossaryApp updateGlossary() {
        GlossaryApp newGlossary = new GlossaryApp(text);

        newGlossary.calculateWordFrequency();
        newGlossary.insertEntriesToGlossary();

        return newGlossary;
    }

    public void calculateWordFrequency() {
        for (String paragraph : text) {
            String[] words = paragraph.split(" ");
            for (String word : words) {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }
        }
        //removes all entries with a value less then 3
        wordFrequency.entrySet().removeIf(entry -> entry.getValue() < 3);
    }


    public Map<String, Integer> getWordFrequency() {
        return wordFrequency;
    }


    void insertEntriesToGlossary() {
        for (String word : wordFrequency.keySet()) {
            if (!glossary.containsKey(word)) {
                ArrayList<Integer> indexes =  findParagraphIndexes(word);
                glossary.put(word, indexes);
            }
        }
    }

    ArrayList<Integer> findParagraphIndexes(String word) {
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
  
    boolean isIndexValid(int index) {
        return index >= 0 && index < text.size();
    }

    TreeMap<String, ArrayList<Integer>> getGlossary() {
        return glossary;
    }

}
