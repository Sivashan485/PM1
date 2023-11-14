package com.NotFalse.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Glossary {

    private TreeMap<String, ArrayList<Integer>> glossary;

    public Glossary(ArrayList<String> text) {
        glossary = new TreeMap<String, ArrayList<Integer>>();
    }

    public Glossary updateGlossary(ArrayList<String> text) {
        // updateGlossary implementation
        return new Glossary(text);
    }

    public HashMap<String, Integer> checkWordFrequency() {
        // checkWordFrequency implementation
        return new HashMap<String, Integer>();
    }

    public void insertEntriesToGlossary() {
        // insertEntriesToGlossary implementation
    }

    public String findeParagraphIndex(String word) {
        // findeParagraphIndex implementation
        return "";
    }

    public boolean isIndexValid() {
        // isIndexValid implementation
        return false;
    }

    public TreeMap<String, ArrayList<Integer>> getGlossary() {
        return glossary;
    }

    public void setGlossary(TreeMap<String, ArrayList<Integer>> glossary) {
        this.glossary = glossary;
    }

}
