package com.texteditor.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

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

        assertTrue(glossaryApp.getGlossary().isEmpty());
        glossaryApp.rebuildGlossary(text);
        assertFalse(glossaryApp.getGlossary().isEmpty());
        TreeMap<String, List<Integer>> expectedGlossary = new TreeMap<>();
        expectedGlossary.put("Text", Arrays.asList(1, 2, 3));
        expectedGlossary.put("Another", Arrays.asList(1, 2));
        assertEquals(expectedGlossary, glossaryApp.getGlossary());
    }

    @Test
    void testComputeWordFrequency() {
        List<String> text = Arrays.asList("This is a test Test.", "Test Test for the glossary App App App.",
                "Text Text Text Text Text Text.");
        Map<String, Integer> wordFrequency = glossaryApp.computeWordFrequency(text);
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

    @Test
    void testStartWithUpperCase() {
        assertTrue(glossaryApp.startWithUpperCase("Hello"));
        assertFalse(glossaryApp.startWithUpperCase("hello"));
        assertFalse(glossaryApp.startWithUpperCase("1Hello"));
        assertFalse(glossaryApp.startWithUpperCase(""));
    }

    @Test
    void testIsGlossaryEmpty() {
        assertTrue(glossaryApp.isGlossaryEmpty());

        List<String> text = Arrays.asList("Hello World", "Hello Again");
        glossaryApp.rebuildGlossary(text);

        assertTrue(glossaryApp.isGlossaryEmpty());
    }

    @Test
    void testPrintGlossary() {
        List<String> text = Arrays.asList("Hello World", "Hello Again","Hello Hello Hello");
        glossaryApp.printGlossary(text);

        assertFalse(glossaryApp.isGlossaryEmpty());
    }

    @Test
    void testComputeWordFrequencyWithMultipleOccurrences() {
        GlossaryApp glossaryApp = new GlossaryApp();
        List<String> text = Arrays.asList("Hello Hello Hello", "Hello Again", "Hello Hello Hello");
        Map<String, Integer> wordFrequency = glossaryApp.computeWordFrequency(text);
        assertEquals(7, wordFrequency.get("Hello"));
    }

    @Test
    void testComputeWordFrequencyWithSingleOccurrence() {
        GlossaryApp glossaryApp = new GlossaryApp();
        List<String> text = Arrays.asList("Hello World", "Hello Again");
        Map<String, Integer> wordFrequency = glossaryApp.computeWordFrequency(text);
        assertNull(wordFrequency.get("World"));
    }

    @Test
    void testComputeWordFrequencyWithNoOccurrences() {
        GlossaryApp glossaryApp = new GlossaryApp();
        List<String> text = Arrays.asList("Hello World", "Hello Again");
        Map<String, Integer> wordFrequency = glossaryApp.computeWordFrequency(text);
        assertNull(wordFrequency.get("Test"));
    }

    @Test
    void testComputeWordFrequencyWithEmptyList() {
        GlossaryApp glossaryApp = new GlossaryApp();
        List<String> text = new ArrayList<>();
        Map<String, Integer> wordFrequency = glossaryApp.computeWordFrequency(text);
        assertTrue(wordFrequency.isEmpty());
    }


    @Test
    void testFilterParagraphWithSpecialCharacters() {
        GlossaryApp glossaryApp = new GlossaryApp();
        String paragraph = "Hello, World!";
        String filteredParagraph = glossaryApp.filterParagraph(paragraph);
        assertEquals("Hello World", filteredParagraph);
    }

    @Test
    void testFilterParagraphWithNumbers() {
        GlossaryApp glossaryApp = new GlossaryApp();
        String paragraph = "Hello123 World456";
        String filteredParagraph = glossaryApp.filterParagraph(paragraph);
        assertEquals("Hello World", filteredParagraph);
    }

    @Test
    void testFilterParagraphWithEmptyString() {
        GlossaryApp glossaryApp = new GlossaryApp();
        String paragraph = "";
        String filteredParagraph = glossaryApp.filterParagraph(paragraph);
        assertEquals("", filteredParagraph);
    }

    @Test
    void testFilterParagraphWithSpacesOnly() {
        GlossaryApp glossaryApp = new GlossaryApp();
        String paragraph = "     ";
        String filteredParagraph = glossaryApp.filterParagraph(paragraph);
        assertEquals("     ", filteredParagraph);
    }


}
