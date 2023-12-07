package com.texteditor.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TextManagerTest {

    private TextManager textManager;

    @BeforeEach
    void setUp() {
        textManager = new TextManager();
    }

    @Test
    void testAddNewParagraph() {
        assertTrue(textManager.addNewParagraph(false, "Test paragraph", 1));
        assertEquals("Test paragraph", textManager.getText().get(0));
    }

    @Test
    void testDeleteParagraph() {
        textManager = new TextManager();
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertTrue(textManager.deleteParagraph(false, 1));
        assertTrue(textManager.isTextEmpty());
    }

    @Test
    void testFormatTextRaw() {
        textManager.addNewParagraph(false, "Test paragraph1", 1);
        textManager.addNewParagraph(false, "Test paragraph2", 2);
        textManager.addNewParagraph(false, "Test paragraph3", 3);
        textManager.addNewParagraph(false, "Test paragraph4", 4);
        textManager.addNewParagraph(false, "Test paragraph5", 5);
        textManager.formatTextRaw();
        String expected = ("1: Test paragraph1\n" +
                "2: Test paragraph2\n" +
                "3: Test paragraph3\n" +
                "4: Test paragraph4\n" +
                "5: Test paragraph5");
        assertEquals(expected, textManager.getFormattedText());
    }

    @Test
    void testFormatTextFix() {
        textManager.addNewParagraph(false, "Test paragraph", 1);
        textManager.setMaxWidth(5);
        textManager.formatTextFix();
        assertEquals("Test\nparag\nraph", textManager.getFormattedText());
    }

    @Test
    void testReplaceWordInParagraph() {
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertTrue(textManager.replaceWordInParagraph(0, "Test", "Best"));
        assertEquals("Best paragraph", textManager.getText().get(0));
    }

    @Test
    void testReplaceParagraphSection() {
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertTrue(textManager.replaceParagraphSection(false, "Test", "Best", 1));
        assertEquals("Best paragraph", textManager.getText().get(0));
    }

    @Test
    void testContainsWord() {
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertTrue(textManager.containsWord("Test", 1));
        assertFalse(textManager.containsWord("Best", 1));
    }

    @Test
    void testIsTextEmpty() {
        assertTrue(textManager.isTextEmpty());
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertFalse(textManager.isTextEmpty());
    }

    @Test
    void testFormatTextFixWithSingleWordExceedingMaxWidth() {
        textManager.addNewParagraph(false, "Supercalifragilisticexpialidocious", 1);
        textManager.setMaxWidth(5);
        textManager.formatTextFix();
        assertEquals("Super\ncalif\nragil\nistic\nexpia\nlidoc\nious", textManager.getFormattedText());
    }

    @Test
    void testFormatTextFixWithMultipleWordsExceedingMaxWidth() {
        textManager.addNewParagraph(false, "Supercalifragilisticexpialidocious Pneumonoultramicroscopicsilicovolcanoconiosis", 1);
        textManager.setMaxWidth(5);
        textManager.formatTextFix();
        assertEquals("Super\ncalif\nragil\nistic\nexpia\nlidoc\nious\nPneum\nonoul\ntrami\ncrosc\nopics\nilico\nvolca\nnocon\niosis", textManager.getFormattedText());
    }

    @Test
    void testFormatTextFixWithMultipleParagraphs() {
        textManager.addNewParagraph(false, "Hello world", 1);
        textManager.addNewParagraph(false, "This is a test", 2);
        textManager.setMaxWidth(5);
        textManager.formatTextFix();
        assertEquals("Hello\nworld\n\nThis\nis a\ntest", textManager.getFormattedText());
    }

    @Test
    void testReplaceWordInParagraphWithMultipleOccurrences() {
        textManager.addNewParagraph(false, "Test Test Test", 1);
        assertTrue(textManager.replaceWordInParagraph(0, "Test", "Best"));
        assertEquals("Best Best Best", textManager.getText().get(0));
    }

    @Test
    void testReplaceWordInParagraphWithNoOccurrences() {
        textManager.addNewParagraph(false, "Test Test Test", 1);
        assertFalse(textManager.replaceWordInParagraph(0, "Best", "Test"));
        assertEquals("Test Test Test", textManager.getText().get(0));
    }

    @Test
    void testReplaceParagraphSectionWithMultipleOccurrences() {
        textManager.addNewParagraph(false, "Test Test Test", 1);
        assertTrue(textManager.replaceParagraphSection(false, "Test", "Best", 1));
        assertEquals("Best Best Best", textManager.getText().get(0));
    }

    @Test
    void testReplaceParagraphSectionWithNoOccurrences() {
        textManager.addNewParagraph(false, "Test Test Test", 1);
        assertFalse(textManager.replaceParagraphSection(false, "Best", "Test", 1));
        assertEquals("Test Test Test", textManager.getText().get(0));
    }

    @Test
    void testReplaceParagraphSectionWithNullIndex() {
        textManager.addNewParagraph(false, "Test Test Test", 1);
        assertTrue(textManager.replaceParagraphSection(true, "Test", "Best", null));
        assertEquals("Best Best Best", textManager.getText().get(0));
    }
    @Test
    void testDeleteParagraphWithValidIndex() {
        textManager.addNewParagraph(false, "Test paragraph1", 1);
        textManager.addNewParagraph(false, "Test paragraph2", 2);
        assertTrue(textManager.deleteParagraph(false, 1));
        assertEquals("Test paragraph2", textManager.getText().get(0));
    }

    @Test
    void testDeleteParagraphWithNullIndex() {
        textManager.addNewParagraph(false, "Test paragraph1", 1);
        textManager.addNewParagraph(false, "Test paragraph2", 2);
        assertTrue(textManager.deleteParagraph(true, null));
        assertEquals("Test paragraph1", textManager.getText().get(0));
    }
    
    @Test
    void testAddNewParagraphAtSpecificIndex() {
        textManager.addNewParagraph(false, "Test paragraph1", 1);
        textManager.addNewParagraph(false, "Test paragraph2", 2);
        assertTrue(textManager.addNewParagraph(false, "Test paragraph3", 2));
        assertEquals("Test paragraph3", textManager.getText().get(1));
        assertEquals("Test paragraph2", textManager.getText().get(2));
    }

    @Test
    void testAddNewParagraphAtEndWhenIndexIsNull() {
        textManager.addNewParagraph(false, "Test paragraph1", 1);
        textManager.addNewParagraph(true, "Test paragraph2", null);
        assertEquals("Test paragraph2", textManager.getText().get(1));
    }

    @Test
    void testAddNewParagraphAtEndWhenIndexIsInvalid() {
        textManager.addNewParagraph(false, "Test paragraph1", 1);
        textManager.addNewParagraph(false, "Test paragraph2", 2);
        assertEquals("Test paragraph1", textManager.getText().get(0));
    }

    @Test
    void testAddNewParagraphWhenTextIsEmpty() {
        assertTrue(textManager.addNewParagraph(false, "Test paragraph1", 1));
        assertEquals("Test paragraph1", textManager.getText().get(0));
    }

    @Test
    void testSetIsFormatterRaw() {
        textManager.setIsFormatterRaw(true);
        assertTrue(textManager.getIsFormatterRaw());
    }

    @Test
    void testReplaceWordInParagraphWithMultipleWords() {
        textManager.addNewParagraph(false, "Hello Hello Hello", 1);
        assertTrue(textManager.replaceWordInParagraph(0, "Hello", "Hi"));
        assertEquals("Hi Hi Hi", textManager.getText().get(0));
    }

    @Test
    void testReplaceWordInParagraphWithNoMatch() {
        textManager.addNewParagraph(false, "Hello Hello Hello", 1);
        assertFalse(textManager.replaceWordInParagraph(0, "Hi", "Hello"));
        assertEquals("Hello Hello Hello", textManager.getText().get(0));
    }
    @Test
    void testFormatTextRawWithMultipleParagraphs() {
        TextManager textManager = new TextManager();
        textManager.addNewParagraph(false, "Hello world", 1);
        textManager.addNewParagraph(false, "This is a test", 2);
        textManager.formatTextRaw();
        String expected = "1: Hello world\n2: This is a test";
        assertEquals(expected, textManager.getFormattedText());
    }

    @Test
    void testFormatTextRawWithEmptyText() {
        TextManager textManager = new TextManager();
        textManager.formatTextRaw();
        String expected = "";
        assertEquals(expected, textManager.getFormattedText());
    }

    @Test
    void testFormatTextFixWithEmptyText() {
        TextManager textManager = new TextManager();
        textManager.setMaxWidth(5);
        textManager.formatTextFix();
        String expected = "";
        assertEquals(expected, textManager.getFormattedText());
    }


    @Test
    void testReplaceWordInParagraphNoMatch() {
        TextManager textManager = new TextManager();
        textManager.addNewParagraph(false, "Hello world", 1);
        assertFalse(textManager.replaceWordInParagraph(0, "Goodbye", "Hi"));
        assertEquals("Hello world", textManager.getText().get(0));
    }


    @Test
    void testReplaceParagraphSectionNoMatch() {
        TextManager textManager = new TextManager();
        textManager.addNewParagraph(false, "Hello world", 1);
        assertFalse(textManager.replaceParagraphSection(false, "Goodbye world", "Hi world", 1));
        assertEquals("Hello world", textManager.getText().get(0));
    }


}

