package com.NotFalse.app;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GlossaryAppTest {

    private GlossaryApp glossary;
    private ArrayList<String> text;

    @BeforeEach
    void setUp() {
        text = new ArrayList<>();
        text.add("This is a test paragraph.");
        text.add("Another test paragraph.");
        text.add("Another weird useless test paragraph");
        glossary = new GlossaryApp();
        glossary.computeWordFrequency(text);
    }

    @Test
    void testUpdateGlossary() {
        ArrayList<String> newText = new ArrayList<>();
        newText.add("custom test paragraph.");
        newText.add("Another custom test paragraph.");
        newText.add("Yet another custom test paragraph.");
        newText.add("A totally new sentence with so many custom characters");

        GlossaryApp newGlossaryApp = glossary.rebuildGlossary(newText);
        Map<String, Integer> wordFrequency = newGlossaryApp.getWordFrequency();

        assertEquals(4, wordFrequency.get("custom"));
        assertEquals(3, wordFrequency.get("paragraph."));
        assertNull(wordFrequency.get("many"));
        assertTrue(wordFrequency.containsKey("custom"));
    }

    @Test
    void testCheckWordFrequency() {
        Map<String, Integer> wordFrequency = glossary.getWordFrequency();

        assertTrue(wordFrequency.containsKey("test"));
        assertFalse(wordFrequency.containsKey("Another"));
        assertEquals(3, wordFrequency.get("test"));
        assertNull(wordFrequency.get("Another"));
    }

    @Test
    void testInsertEntriesToGlossary() {
        glossary.insertEntriesToGlossary(text);
        TreeMap<String,List<Integer>> glossaryMap = glossary.getGlossary();

        assertTrue(glossaryMap.containsKey("test"));
        assertFalse(glossaryMap.containsKey("Another"));
        assertEquals(Arrays.asList(0, 1, 2), glossaryMap.get("test"));
    }

    @Test
    void testFindParagraphIndexes() {
        glossary.insertEntriesToGlossary(text);

        // Test for a word that exists in the text
        List<Integer> testIndexes = glossary.findParagraphIndexes(text, "test");
        assertEquals(Arrays.asList(0, 1, 2), testIndexes);

        // Test for a word that doesn't exist in the text
        List<Integer> nonexistentIndexes = glossary.findParagraphIndexes(text, "nonexistent");
        assertTrue(nonexistentIndexes.isEmpty());
    }
}
