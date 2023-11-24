package com.NotFalse.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class GlossaryAppTest {

        GlossaryApp glossaryApp;
        OutputManager output;

        @BeforeEach
        void setUp() {
            output = new OutputManager();
            glossaryApp = new GlossaryApp(output);
        }

        @Test
        void testRebuildGlossaryOne() {
            List<String> text = Arrays.asList("Hello", "World", "Hello", "World");
            glossaryApp.rebuildGlossary(text);
            TreeMap<String, List<Integer>> expected = new TreeMap<>();
            expected.put("Hello", Arrays.asList(1, 3));
            expected.put("World", Arrays.asList(2, 4));
            assertEquals(expected, glossaryApp.getGlossary());
        }

        @Test
        void testRebuildGlossaryEmpty() {
            List<String> text = Arrays.asList("");
            glossaryApp.rebuildGlossary(text);
            TreeMap<String, List<Integer>> expected = new TreeMap<>();
            assertEquals(expected, glossaryApp.getGlossary());
        }

        @Test
        void testRebuildGlossaryNull() {
            List<String> text = null;
            glossaryApp.rebuildGlossary(text);
            TreeMap<String, List<Integer>> expected = new TreeMap<>();
            assertEquals(expected, glossaryApp.getGlossary());
        }

        @Test
        void testRebuildGlossaryMultipleParagraphs() {
            List<String> text = Arrays.asList("Hello", "World", "", "Hello", "World");
            glossaryApp.rebuildGlossary(text);
            TreeMap<String, List<Integer>> expected = new TreeMap<>();
            expected.put("Hello", Arrays.asList(0, 3));
            expected.put("World", Arrays.asList(1, 4));
            assertEquals(expected, glossaryApp.getGlossary());
        }

        @Test
        void testRebuildGlossaryMultipleParagraphsEmpty() {
            List<String> text = Arrays.asList("", "", "");
            glossaryApp.rebuildGlossary(text);
            TreeMap<String, List<Integer>> expected = new TreeMap<>();
            assertEquals(expected, glossaryApp.getGlossary());
        }

        @Test
        void testRebuildGlossaryMultipleParagraphsNull() {
            List<String> text = Arrays.asList(null, null, null);
            glossaryApp.rebuildGlossary(text);
            TreeMap<String, List<Integer>> expected = new TreeMap<>();
            assertEquals(expected, glossaryApp.getGlossary());
        }


    @Test
    void testRebuildGlossary() {
        // Test the rebuildGlossary method with a simple case
        GlossaryApp glossaryApp = new GlossaryApp(new OutputManager());
        List<String> text = Arrays.asList("This is a test.", "Another test.");
        glossaryApp.rebuildGlossary(text);
        TreeMap<String, List<Integer>> glossary = glossaryApp.getGlossary();
        assertTrue(glossary.containsKey("This"));
        assertTrue(glossary.containsKey("is"));
        assertTrue(glossary.containsKey("test"));
        assertEquals(1, glossary.get("This").get(0));
        assertEquals(1, glossary.get("is").get(0));
        assertEquals(1, glossary.get("test").get(0));
        assertEquals(2, glossary.get("test").get(1));
    }

    @Test
    void testComputeWordFrequency() {
        // Test the computeWordFrequency method
        GlossaryApp glossaryApp = new GlossaryApp(new OutputManager());
        List<String> text = Arrays.asList("This is a test.", "Another test.");
        Map<String, Integer> wordFrequency = glossaryApp.computeWordFrequency(text);
        assertEquals(2, wordFrequency.size());
        assertTrue(wordFrequency.containsKey("This"));
        assertTrue(wordFrequency.containsKey("test"));
        assertEquals(1, wordFrequency.get("This"));
        assertEquals(2, wordFrequency.get("test"));
    }

    @Test
    void testInsertGlossaryEntries() {
        // Test the insertGlossaryEntries method
        GlossaryApp glossaryApp = new GlossaryApp(new OutputManager());
        List<String> text = Arrays.asList("This is a test.", "Another test.");
        Map<String, Integer> wordFrequency = glossaryApp.computeWordFrequency(text);
        glossaryApp.insertGlossaryEntries(text, wordFrequency);
        TreeMap<String, List<Integer>> glossary = glossaryApp.getGlossary();
        assertTrue(glossary.containsKey("This"));
        assertTrue(glossary.containsKey("test"));
        assertEquals(1, glossary.get("This").get(0));
        assertEquals(1, glossary.get("is").get(0));
        assertEquals(1, glossary.get("test").get(0));
        assertEquals(2, glossary.get("test").get(1));
    }

    @Test
    void testSearchWordInParagraphs() {
        // Test the searchWordInParagraphs method
        GlossaryApp glossaryApp = new GlossaryApp(new OutputManager());
        List<String> text = Arrays.asList("This is a test.", "Another test.");
        List<Integer> indexes = glossaryApp.searchWordInParagraphs(text, "test");
        assertEquals(2, indexes.size());
        assertEquals(1, indexes.get(0));
        assertEquals(2, indexes.get(1));
    }


}