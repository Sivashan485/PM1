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
        int fixedWidth = 4;
        ArrayList<String> input = new ArrayList<>(Arrays.asList("test"));
        String expected = "test";
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testEmptyInput() {
        int fixedWidth = 5;
        ArrayList<String> input = new ArrayList<>();
        String expected = "";
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testSingleLongWord() {
        int fixedWidth = 7;
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList(
                        "01234567890123456789"));
        String expected = "0123456\n" +
                "7890123\n" +
                "456789";
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testMultipleShortWords() {
        int fixedWidth = 7;
        ArrayList<String> input = new ArrayList<>(Arrays.asList("hello", "world"));
        String expected = "hello\n" +
                "world";
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testMultipleLongWords() {
        int fixedWidth = 20;
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("abcdefghij", "jiwer", "klmnopqrstuvwxy", "zabcdefghij"));
        String expected = "abcdefghij jiwer\n" +
                "klmnopqrstuvwxy\n" +
                "zabcdefghij";
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testWordsExactlyMatchingMaxWidth() {
        int fixedWidth = 10;
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("abcdefghij", "klmnopqrst", "uvwxyzabcd", "efghijklmn", "opqrstuvwx"));
        String expected = "abcdefghij\n" +
                "klmnopqrst\n" +
                "uvwxyzabcd\n" +
                "efghijklmn\n" +
                "opqrstuvwx";
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testLineBreakOnWordBoundary() {
        int fixedWidth = 20;
        ArrayList<String> input = new ArrayList<>(Arrays.asList("abcdefghij", "klmnopqrst", "uvwxyzabc", "efghijkl",
                "opqrstu", "yzabcd", "ijklm", "stuv", "cde", "mn", "o"));
        String expected = "abcdefghij\n" +
                "klmnopqrst uvwxyzabc\n" +
                "efghijkl opqrstu\n" +
                "yzabcd ijklm stuv\n" +
                "cde mn o";
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testSpacesNotPreserved() {
        int fixedWidth = 20;
        ArrayList<String> input = new ArrayList<>(Arrays.asList("hello", " ", "world"));
        String expected = "hello world";
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testMaximumWidthBoundary() {
        int fixedWidth = 80;
        String longWord = "a".repeat(80);
        ArrayList<String> input = new ArrayList<>(Arrays.asList(longWord, "b"));
        String expected = longWord + "\nb";
        assertEquals(expected, textManager.formatTextFix( ));
    }

    // Test for Method formatTextRaw
    @Test
    void testFormatTextRawWithSingleLine() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("This is a single line of text."));
        String expected = "<1>: This is a single line of text.\n";
        assertEquals(expected, textManager.formatTextRaw());
    }

    // Test for Method formatTextRaw
    @Test
    void testFormatTextRawWithMultipleLines() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("This is the first line of text.", "This is the second line of text."));
        String expected = "<1>: This is the first line of text.\n" +
                "<2>: This is the second line of text.\n";
        assertEquals(expected, textManager.formatTextRaw());
    }

    // Test for Method formatTextRaw
    @Test
    void testFormatTextRawWithEmptyList() {
        ArrayList<String> input = new ArrayList<>();
        String expected = "";
        assertEquals(expected, textManager.formatTextRaw());
    }

}
