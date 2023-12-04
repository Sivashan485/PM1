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

    /*
    @BeforeEach
    void setUp() {
        // 1 Setup
        glossaryOne = new GlossaryApp(output);
        textOne = new ArrayList<>();
        textOne.add("This is a test paragraph.\n");
        textOne.add("Another test paragraph.\n");
        textOne.add("Another weird useless test paragraph\n");
        Map<String, Integer> wordFrequency1 = glossaryOne.computeWordFrequency(textOne);
        glossaryOne.insertGlossaryEntries(textOne, wordFrequency1);

        // 2 Setup
        glossaryTwo = new GlossaryApp(output);
        textTwo = new ArrayList<>();
        textTwo.add("This is a test paragraph.\n");
        textTwo.add("Another test paragraph.\n");
        textTwo.add("Another weird useless test paragraph. This is a ParaGrAPh:, and this one para.graph isnt.\n");
        Map<String, Integer> wordFrequency2 = glossaryTwo.computeWordFrequency(textTwo);
        glossaryTwo.insertGlossaryEntries(textTwo, wordFrequency2);

        // 3 Setup
        glossaryThree = new GlossaryApp(output);
        textThree = new ArrayList<>();
        textThree.add("This is a new test paragraph.");
        textThree.add("Another new test paragraph, just for a test.");
        Map<String, Integer> wordFrequency3 = glossaryThree.computeWordFrequency(textThree);
        glossaryThree.insertGlossaryEntries(textThree, wordFrequency3);

    }

    @Test
    void testCheckWordFrequencyOne() {
        Map<String, Integer> wordFrequency = glossaryOne.computeWordFrequency(textOne);
        assertTrue(wordFrequency.containsKey("test"));
        assertEquals(3, wordFrequency.get("test"));

    }

    @Test
    void testCheckWordFrequencyTwo() {
        Map<String, Integer> wordFrequency = glossaryOne.computeWordFrequency(textOne);
        assertFalse(wordFrequency.containsKey("another"));
        assertNull(wordFrequency.get("another"));
    }

    @Test
    void testFindParagraphIndexesOne() {
        List<Integer> testIndexes = glossaryOne.searchWordInParagraphs(textOne, "test");
        assertEquals(Arrays.asList(1, 2, 3), testIndexes);

    }

    @Test
    void testFindParagraphIndexesTwo() {
        List<Integer> nonexistentIndexes = glossaryOne.searchWordInParagraphs(textOne, "nonexistent");
        assertTrue(nonexistentIndexes.isEmpty());
    }

    @Test
    void testComputeWordFrequencyOne() {
        Map<String, Integer> wordFrequency = glossaryTwo.computeWordFrequency(textTwo);
        assertEquals(3, wordFrequency.get("test"));
    }

    @Test
    void testComputeWordFrequencyTwo() {
        Map<String, Integer> wordFrequency = glossaryTwo.computeWordFrequency(textTwo);
        assertEquals(4, wordFrequency.get("paragraph"));
    }

    @Test
    void testComputeWordFrequencyThree() {
        Map<String, Integer> wordFrequency = glossaryTwo.computeWordFrequency(textTwo);
        assertNull(wordFrequency.get("nonexistent"));
    }

    @Test
    void testInsertEntriesToGlossaryOne() {
        TreeMap<String, List<Integer>> glossaryMap = glossaryOne.getGlossary();

        assertTrue(glossaryMap.containsKey("Test"));
        assertEquals(Arrays.asList(1, 2, 3), glossaryMap.get("Test"));

    }

    @Test
    void testInsertEntriesToGlossaryTwo() {
        TreeMap<String, List<Integer>> glossaryMap = glossaryOne.getGlossary();

        assertTrue(glossaryMap.containsKey("Paragraph"));
        assertEquals(Arrays.asList(1, 2, 3), glossaryMap.get("Paragraph"));
    }

    @Test
    void testInsertEntriesToGlossaryThree() {
        TreeMap<String, List<Integer>> glossaryMap = glossaryOne.getGlossary();

        assertFalse(glossaryMap.containsKey("Nonexistent"));
    }

    @Test
    void testRebuildGlossaryOne() {
        glossaryOne.rebuildGlossary(textOne);
        TreeMap<String, List<Integer>> glossaryMap = glossaryOne.getGlossary();

        assertTrue(glossaryMap.containsKey("Test"));
        assertEquals(Arrays.asList(1, 2, 3), glossaryMap.get("Test"));
    }

    @Test
    void testRebuildGlossaryTwo() {
        glossaryTwo.rebuildGlossary(textTwo);
        TreeMap<String, List<Integer>> glossaryMap = glossaryTwo.getGlossary();

        assertFalse(glossaryMap.containsKey("nonexistent"), "Glossary should not contain 'nonexistent'");
    }

    @Test
    void testRebuildGlossaryThree() {
        glossaryThree.rebuildGlossary(textThree);
        TreeMap<String, List<Integer>> glossaryMap = glossaryThree.getGlossary();

        assertFalse(glossaryMap.containsKey("paragraph"), "Glossary should not contain 'paragraph'");
    }

    @Test
    void testFilterParagraphWithAlphabeticCharacters() {
        assertEquals("hello  world ", glossaryOne.filterParagraph("Hello, World!"));
    }

    @Test
    void testFilterParagraphWithNonAlphabeticCharacters() {
        assertEquals("        ", glossaryOne.filterParagraph("1234!@#$"));
    }

    @Test
    void testFilterParagraphEmptyString() {
        assertEquals("", glossaryOne.filterParagraph(""));
    }
}
*/
}