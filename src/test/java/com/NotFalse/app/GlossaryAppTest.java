package com.NotFalse.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class GlossaryAppTest {

    private GlossaryApp glossaryApp;


    @BeforeEach
    void setUp() {
        glossaryApp = new GlossaryApp();
    }


    @Test
    void testRebuildGlossary() {
        List<String> text = Arrays.asList("Another Another Text Text Text.", "Another Text Text.", "More Text here.");

        // Initial glossary is empty
        assertTrue(glossaryApp.getGlossary().isEmpty());

        // Rebuild glossary with the given text
        glossaryApp.rebuildGlossary(text);

        // Check if the glossary is not empty after rebuilding
        assertFalse(glossaryApp.getGlossary().isEmpty());

        // Check if the glossary contains the expected entries
        TreeMap<String, List<Integer>> expectedGlossary = new TreeMap<>();
        expectedGlossary.put("Text", Arrays.asList(1, 2, 3));
        expectedGlossary.put("Another", Arrays.asList(1, 2));
        assertEquals(expectedGlossary, glossaryApp.getGlossary());
    }


    @Test
    void testComputeWordFrequency() {
        List<String> text = Arrays.asList("This is a test Test.", "Test Test for the glossary App App App.", "Text Text Text Text Text Text.");

        // Compute the word frequency
        Map<String, Integer> wordFrequency = glossaryApp.computeWordFrequency(text);

        // Check if the word frequency is as expected
        TreeMap<String, Integer> expectedWordFrequency = new TreeMap<>();
        expectedWordFrequency.put("Test", 3);
        expectedWordFrequency.put("App", 3);
        expectedWordFrequency.put("Text", 6);

        assertEquals(expectedWordFrequency, wordFrequency);
    }

    @Test
    void testSearchWordInParagraphs() {
        List<String> text = Arrays.asList("This is a Test", "Test for the glossary app.");
        List<Integer> indexes = glossaryApp.findParagraphIndexes(text, "Test");

        assertEquals(Arrays.asList(1, 2), indexes);
    }

    @Test
    void testFindParagraphIndexesOne() {
        List<String> text = Arrays.asList("This is a Test", "Test for the glossary app.");
        List<Integer> nonexistentIndexes = glossaryApp.findParagraphIndexes(text, "nonexistent");
        assertTrue(nonexistentIndexes.isEmpty());
    }

    @Test
    void testFindParagraphIndexesTwo() {
        List<String> text = Arrays.asList("This is a Test", "Test Test for the glossary app.");
        List<Integer> testIndexes = glossaryApp.findParagraphIndexes(text, "Test");
        assertEquals(Arrays.asList(1, 2), testIndexes);

    }

    @Test
    void testFilterParagraph() {
        String paragraph = "This is a test paragraph!123";
        String filteredParagraph = glossaryApp.filterParagraph(paragraph);

        assertEquals("This is a test paragraph", filteredParagraph);
    }

    @Test
    void testFilterParagraphWithAlphabeticCharacters() {
        assertEquals("Hello World", glossaryApp.filterParagraph("Hello, World!"));
    }

    @Test
    void testFilterParagraphWithNonAlphabeticCharacters() {
        assertEquals("", glossaryApp.filterParagraph("1234!@#$"));
    }

    @Test
    void testFilterParagraphEmptyString() {
        assertEquals("", glossaryApp.filterParagraph(""));
    }


}