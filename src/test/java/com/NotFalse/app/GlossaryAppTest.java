package com.NotFalse.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GlossaryAppTest {

    private GlossaryApp glossaryOne;
    private GlossaryApp glossaryTwo;
    private GlossaryApp glossaryThree;
    private ArrayList<String> textOne;
    private ArrayList<String> textTwo;
    private ArrayList<String> textThree;

    @BeforeEach
    void setUp() {
        // 1 Setup
        glossaryOne = new GlossaryApp();
        textOne = new ArrayList<>();
        textOne.add("This is a test paragraph.\n");
        textOne.add("Another test paragraph.\n");
        textOne.add("Another weird useless test paragraph\n");
        Map<String, Integer> wordFrequency1 = glossaryOne.computeWordFrequency(textOne);
        glossaryOne.insertGlossaryEntries(textOne, wordFrequency1);

        // 2 Setup
        glossaryTwo = new GlossaryApp();
        textTwo = new ArrayList<>();
        textTwo.add("This is a test paragraph.\n");
        textTwo.add("Another test paragraph.\n");
        textTwo.add("Another weird useless test paragraph. This is a ParaGrAPh:, and this one para.graph isnt.\n");
        Map<String, Integer> wordFrequency2 = glossaryTwo.computeWordFrequency(textTwo);
        glossaryTwo.insertGlossaryEntries(textTwo,wordFrequency2);

        // 3 Setup
        glossaryThree = new GlossaryApp();
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
        assertEquals(Arrays.asList(1, 2,3), glossaryMap.get("Test"));
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
    public void testRebuildGlossaryEmpty() {
        List<String> text = Arrays.asList("Hello world", "Hello again", "World says hello");
        glossaryOne.rebuildGlossary(text);
        assertFalse(glossaryOne.isEmpty(), "Glossary should not be empty");
        // Add more assertions to check if the glossary contains the correct data
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

    // Tests for capitalizeFirstLetter method
    @Test
    void testCapitalizeFirstLetterNormalWord() {
        assertEquals("Hello", glossaryOne.capitalizeFirstLetter("hello"));
    }

    @Test
    void testCapitalizeFirstLetterEmptyString() {
        assertEquals("", glossaryOne.capitalizeFirstLetter(""));
    }

    @Test
    void testCapitalizeFirstLetterSingleCharacter() {
        assertEquals("H", glossaryOne.capitalizeFirstLetter("h"));
    }

    @Test
    void testCapitalizeFirstLetterNullInput() {
        assertNull(glossaryOne.capitalizeFirstLetter(null));
    }
}