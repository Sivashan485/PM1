package com.NotFalse.app;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class GlossaryTest {

    private Glossary glossary;

    @BeforeEach
    void setUp() {
        List<String> text = new ArrayList<>();
        text.add("This is a test paragraph.");
        text.add("Another test paragraph.");
        text.add("Another weird useless test paragraph");
        glossary = new Glossary(text);
        // You should calculate word frequency here
        glossary.calculateWordFrequency();
    }

    @Test
    void testUpdateGlossary() {
        List<String> newText = new ArrayList<>();
        newText.add("custom test paragraph.");
        newText.add("Another custom test paragraph.");
        newText.add("Yet another custom test paragraph.");
        newText.add("A totally new sentence with so many custom characters");
        Glossary newGlossaryApp = new Glossary(newText);
        newGlossaryApp.calculateWordFrequency();

        Map<String, Integer> wordFrequency = newGlossaryApp.updateGlossary().getWordFrequency();

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
        glossary.insertEntriesToGlossary();
        TreeMap<String, ArrayList<Integer>> glossaryMap = glossary.getGlossary();
        
        assertTrue(glossaryMap.containsKey("test"));
        assertFalse(glossaryMap.containsKey("Another"));
        assertEquals(Arrays.asList(0, 1, 2), glossaryMap.get("test"));
       
    }

    @Test
    void testFindParagraphIndexes() {
        // Test for a word that exists in the text
        List<Integer> testIndexes = glossary.findParagraphIndexes("test");
        assertEquals(Arrays.asList(0, 1, 2), testIndexes);

        // Test for a word that doesn't exist in the text
        List<Integer> nonexistentIndexes = glossary.findParagraphIndexes("nonexistent");
        assertTrue(nonexistentIndexes.isEmpty());

    }

    @Test
    void testIsIndexValid() {
        assertTrue(glossary.isIndexValid(0));
        assertTrue(glossary.isIndexValid(1));
        assertTrue(glossary.isIndexValid(2));
        assertFalse(glossary.isIndexValid(-1));
        assertFalse(glossary.isIndexValid(3));
    }

    @Test
    void testNullText() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Glossary(null);
        });
    }
}
