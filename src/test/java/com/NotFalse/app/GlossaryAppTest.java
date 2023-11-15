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
    void testCheckWordFrequency() {
        Map<String, Integer> wordFrequency = glossary.computeWordFrequency(text);

        assertTrue(wordFrequency.containsKey("test"));
        assertFalse(wordFrequency.containsKey("Another"));
        assertEquals(3, wordFrequency.get("test"));
        assertNull(wordFrequency.get("Another"));
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

    @Test
    void testComputeWordFrequency() {
        GlossaryApp glossary = new GlossaryApp();
        List<String> text = new ArrayList<>();
        text.add("This is a test paragraph.");
        text.add("Another test paragraph.");
        text.add("Another weird useless test paragraph. This is a ParaGrAPh:, and this one para.graph isnt.");

        Map<String, Integer> wordFrequency = glossary.computeWordFrequency(text);

        assertEquals(3, wordFrequency.get("test"));
        assertEquals(4, wordFrequency.get("paragraph"));
        assertNull(wordFrequency.get("nonexistent"));
    }

    @Test
    void testInsertEntriesToGlossary() {
        // Create a new GlossaryApp instance
        GlossaryApp glossary = new GlossaryApp();

        // Create a list of test paragraphs
        List<String> text = new ArrayList<>();
        text.add("This is a test paragraph.");
        text.add("Another test paragraph.");
        text.add("Another weird useless test paragraph");

        // Insert entries to the glossary
        glossary.insertEntriesToGlossary(text);

        // Get the glossary map
        TreeMap<String, List<Integer>> glossaryMap = glossary.getGlossary();

        // Test that the glossary contains the correct entries
        assertTrue(glossaryMap.containsKey("test"));
        assertTrue(glossaryMap.containsKey("paragraph"));
        assertFalse(glossaryMap.containsKey("nonexistent"));

        // Test that the glossary entries have the correct paragraph indexes
        assertEquals(Arrays.asList(0, 1, 2), glossaryMap.get("test"));
        assertEquals(Arrays.asList(0, 1, 2), glossaryMap.get("paragraph"));
    }
    @Test
    void testRebuildGlossary() {
        // Create a new GlossaryApp instance
        GlossaryApp glossary = new GlossaryApp();

        // Create a list of test paragraphs
        List<String> text = new ArrayList<>();
        text.add("This is a test paragraph.");
        text.add("Another test paragraph.");
        text.add("Another weird useless test paragraph");

        // Insert entries to the glossary
        glossary.insertEntriesToGlossary(text);

        // Rebuild the glossary with new text
        List<String> newText = new ArrayList<>();
        newText.add("This is a new test paragraph.");
        newText.add("Another new test paragraph, just for a test.");
        GlossaryApp newGlossary = glossary.rebuildGlossary(newText);

        // Get the glossary map
        TreeMap<String, List<Integer>> glossaryMap = newGlossary.getGlossary();

        // Test that the new glossary contains the correct entries
        assertFalse(glossaryMap.containsKey("new"));
        assertTrue(glossaryMap.containsKey("test"));
        assertFalse(glossaryMap.containsKey("paragraph"));
        assertFalse(glossaryMap.containsKey("nonexistent"));

        // Test that the new glossary entries have the correct paragraph indexes
        assertEquals(Arrays.asList(0, 1), glossaryMap.get("test"));
    }
}