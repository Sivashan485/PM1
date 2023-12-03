package com.NotFalse.app;

import com.NotFalse.app.GlossaryApp;
import com.NotFalse.app.OutputManager;
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
        glossaryApp = new GlossaryApp(new OutputManager()); // Replace OutputManager with the actual class, if available
    }

    @Test
    void testRebuildGlossaryOne() {
        List<String> inputText = Arrays.asList("This is a sample text.", "Sample text for testing.", "Text testing glossary.");
        glossaryApp.rebuildGlossary(inputText);
        TreeMap<String, List<Integer>> glossary = glossaryApp.getGlossary();

        assertTrue(glossary.containsKey("Text"));
        assertEquals(Arrays.asList(1, 2, 3), glossary.get("Text"));
    }

    @Test
    void testComputeWordFrequencyOne() {
        List<String> inputText = Arrays.asList("This is a sample text.", "Sample text for testing.", "Text testing glossary.");
        TreeMap<String, List<Integer>> glossary = glossaryApp.getGlossary();

        assertEquals(3, glossaryApp.computeWordFrequency(inputText).get("Text"));
    }

    @Test
    void testRebuildGlossary() {
        List<String> text = Arrays.asList("This is a test.", "Test for the glossary app.");

        // Initial glossary is empty
        assertTrue(glossaryApp.getGlossary().isEmpty());

        // Rebuild glossary with the given text
        glossaryApp.rebuildGlossary(text);

        // Check if the glossary is not empty after rebuilding
        assertFalse(glossaryApp.getGlossary().isEmpty());

        // Check if the glossary contains the expected entries
        TreeMap<String, List<Integer>> expectedGlossary = new TreeMap<>();
        expectedGlossary.put("Test", Arrays.asList(1, 2));
        expectedGlossary.put("This", Arrays.asList(1));
        expectedGlossary.put("a", Arrays.asList(1));
        expectedGlossary.put("app", Arrays.asList(2));
        expectedGlossary.put("for", Arrays.asList(2));
        expectedGlossary.put("glossary", Arrays.asList(2));
        expectedGlossary.put("is", Arrays.asList(1));
        expectedGlossary.put("the", Arrays.asList(2));
        assertEquals(expectedGlossary, glossaryApp.getGlossary());
    }

    @Test
    void testComputeWordFrequency() {
        List<String> text = Arrays.asList("This is a test.", "Test for the glossary app.");

        // Compute the word frequency
        Map<String, Integer> wordFrequency = glossaryApp.computeWordFrequency(text);

        // Check if the word frequency is as expected
        TreeMap<String, Integer> expectedWordFrequency = new TreeMap<>();
        expectedWordFrequency.put("Test", 2);
        expectedWordFrequency.put("This", 1);
        expectedWordFrequency.put("a", 1);
        expectedWordFrequency.put("app", 1);
        expectedWordFrequency.put("for", 1);
        expectedWordFrequency.put("glossary", 1);
        expectedWordFrequency.put("is", 1);
        expectedWordFrequency.put("test", 1);
        expectedWordFrequency.put("the", 1);
        assertEquals(expectedWordFrequency, wordFrequency);
    }

    @Test
    void testFilterParagraph() {
        String paragraph = "This is a test paragraph!123";

        // Filter the paragraph
        String filteredParagraph = glossaryApp.filterParagraph(paragraph);

        // Check if the filtered paragraph contains only letters and spaces
        assertEquals("This is a test paragraph", filteredParagraph);
    }

    @Test
    void testSearchWordInParagraphs() {
        List<String> text = Arrays.asList("This is a test", "Test for the glossary app.");

        // Search for the word "Test" in paragraphs
        List<Integer> indexes = glossaryApp.searchWordInParagraphs(text, "Test");

        // Check if the indexes are as expected
        assertEquals(Arrays.asList(1, 2), indexes);

        // Search for a non-existent word
        List<Integer> nonExistentIndexes = glossaryApp.searchWordInParagraphs(text, "nonexistentword");

    }
}