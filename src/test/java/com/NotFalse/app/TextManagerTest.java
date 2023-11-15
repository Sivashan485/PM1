package com.NotFalse.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TextManagerTest {

    TextManager textManager;

    @BeforeEach
    void setUp() {
        textManager = new TextManager();
    }

    // Test for Method formatTextFix
    @Test
    void testSingleShortWord() {
        String expected = "test";
        textManager.setText(Arrays.asList("test"));
        assertEquals(expected, textManager.formatTextFix(4));
    }

    // Test for Method formatTextFix
    @Test
    void testEmptyInput() {
        String expected = "";
        textManager.setText(Arrays.asList(""));
        assertEquals(expected, textManager.formatTextFix(5));
    }

    // Test for Method formatTextFix
    @Test
    void testSingleLongWord() {
        String expected = "0123456\n" +
                "7890123\n" +
                "456789";
        textManager.setText(Arrays.asList("01234567890123456789"));
        assertEquals(expected, textManager.formatTextFix(7));
    }

    // Test for Method formatTextFix
    @Test
    void testMultipleShortWords() {
        String expected = "hello\n" +
                "world";
        textManager.setText(Arrays.asList("hello", "world"));
        assertEquals(expected, textManager.formatTextFix(7));
    }

    // Test for Method formatTextFix
    @Test
    void testMultipleLongWords() {
        String expected = "abcdefghij jiwer\n" +
                "klmnopqrstuvwxy\n" +
                "zabcdefghij";
        textManager.setText(Arrays.asList("abcdefghij", "jiwer", "klmnopqrstuvwxy", "zabcdefghij"));
        assertEquals(expected, textManager.formatTextFix(20));
    }

    // Test for Method formatTextFix
    @Test
    void testWordsExactlyMatchingMaxWidth() {
        String expected = "abcdefghij\n" +
                "klmnopqrst\n" +
                "uvwxyzabcd\n" +
                "efghijklmn\n" +
                "opqrstuvwx";
        textManager.setText(Arrays.asList("abcdefghij", "klmnopqrst", "uvwxyzabcd", "efghijklmn", "opqrstuvwx"));
        assertEquals(expected, textManager.formatTextFix(10));
    }

    // Test for Method formatTextFix
    @Test
    void testLineBreakOnWordBoundary() {
        String expected = "abcdefghij\n" +
                "klmnopqrst uvwxyzabc\n" +
                "efghijkl opqrstu\n" +
                "yzabcd ijklm stuv\n" +
                "cde mn o";
        textManager.setText(Arrays.asList("abcdefghij", "klmnopqrst", "uvwxyzabc", "efghijkl",
                "opqrstu", "yzabcd", "ijklm", "stuv", "cde", "mn", "o"));
        assertEquals(expected, textManager.formatTextFix(20));
    }

    // Test for Method formatTextFix
    @Test
    void testSpacesNotPreserved() {
        String expected = "hello world";
        textManager.setText(Arrays.asList("hello", " ", "world"));
        assertEquals(expected, textManager.formatTextFix(20));
    }

    // Test for Method formatTextFix
    @Test
    void testMaximumWidthBoundary() {
        String longWord = "a".repeat(80);
        String expected = longWord + "\nb";
        textManager.setText(Arrays.asList(longWord, "b"));
        assertEquals(expected, textManager.formatTextFix(80));
    }

    // Test for Method formatTextRaw
    @Test
    void testFormatTextRawWithSingleLine() {
        String expected = "<1>: This is a single line of text.\n";
        textManager.setText(Arrays.asList("This is a single line of text."));
        assertEquals(expected, textManager.formatTextRaw());
    }

    // Test for Method formatTextRaw
    @Test
    void testFormatTextRawWithMultipleLines() {
        String expected = "<1>: This is the first line of text.\n" +
                "<2>: This is the second line of text.\n";
        textManager.setText(Arrays.asList("This is the first line of text.", "This is the second line of text."));
        assertEquals(expected, textManager.formatTextRaw());
    }

    // Test for Method formatTextRaw
    @Test
    void testFormatTextRawWithEmptyList() {
        String expected = "<1>: \n";
        textManager.setText(Arrays.asList(""));
        assertEquals(expected, textManager.formatTextRaw());
    }


}