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
        ArrayList<String> input = new ArrayList<>(Arrays.asList("test"));
        String expected = "test";
        textManager.setText(input);
        assertEquals(expected, textManager.formatTextFix(4));
    }

    // Test for Method formatTextFix
    @Test
    void testEmptyInput() {
        ArrayList<String> input = new ArrayList<>();
        String expected = "";
        textManager.setText(input);
        assertEquals(expected, textManager.formatTextFix(5));
    }

    // Test for Method formatTextFix
    @Test
    void testSingleLongWord() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList(
                        "01234567890123456789"));
        String expected = "0123456\n" +
                "7890123\n" +
                "456789";
        textManager.setText(input);
        assertEquals(expected, textManager.formatTextFix(7));
    }

    // Test for Method formatTextFix
    @Test
    void testMultipleShortWords() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("hello", "world"));
        String expected = "hello\n" +
                "world";
        textManager.setText(input);
        assertEquals(expected, textManager.formatTextFix(7));
    }

    // Test for Method formatTextFix
    @Test
    void testMultipleLongWords() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("abcdefghij", "jiwer", "klmnopqrstuvwxy", "zabcdefghij"));
        String expected = "abcdefghij jiwer\n" +
                "klmnopqrstuvwxy\n" +
                "zabcdefghij";
        textManager.setText(input);
        assertEquals(expected, textManager.formatTextFix(20));
    }

    // Test for Method formatTextFix
    @Test
    void testWordsExactlyMatchingMaxWidth() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("abcdefghij", "klmnopqrst", "uvwxyzabcd", "efghijklmn", "opqrstuvwx"));
        String expected = "abcdefghij\n" +
                "klmnopqrst\n" +
                "uvwxyzabcd\n" +
                "efghijklmn\n" +
                "opqrstuvwx";
        textManager.setText(input);
        assertEquals(expected, textManager.formatTextFix(10));
    }

    // Test for Method formatTextFix
    @Test
    void testLineBreakOnWordBoundary() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("abcdefghij", "klmnopqrst", "uvwxyzabc", "efghijkl",
                "opqrstu", "yzabcd", "ijklm", "stuv", "cde", "mn", "o"));
        String expected = "abcdefghij\n" +
                "klmnopqrst uvwxyzabc\n" +
                "efghijkl opqrstu\n" +
                "yzabcd ijklm stuv\n" +
                "cde mn o";
        textManager.setText(input);
        assertEquals(expected, textManager.formatTextFix(20));
    }

    // Test for Method formatTextFix
    @Test
    void testSpacesNotPreserved() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("hello", " ", "world"));
        String expected = "hello world";
        textManager.setText(input);
        assertEquals(expected, textManager.formatTextFix(20));
    }

    // Test for Method formatTextFix
    @Test
    void testMaximumWidthBoundary() {
        String longWord = "a".repeat(80);
        ArrayList<String> input = new ArrayList<>(Arrays.asList(longWord, "b"));
        String expected = longWord + "\nb";
        textManager.setText(input);
        assertEquals(expected, textManager.formatTextFix(80));
    }

    // Test for Method formatTextRaw
    @Test
    void testFormatTextRawWithSingleLine() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("This is a single line of text."));
        String expected = "<1>: This is a single line of text.\n";
        textManager.setText(input);
        assertEquals(expected, textManager.formatTextRaw());
    }

    // Test for Method formatTextRaw
    @Test
    void testFormatTextRawWithMultipleLines() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("This is the first line of text.", "This is the second line of text."));
        String expected = "<1>: This is the first line of text.\n" +
                "<2>: This is the second line of text.\n";
        textManager.setText(input);
        assertEquals(expected, textManager.formatTextRaw());
    }

    // Test for Method formatTextRaw
    @Test
    void testFormatTextRawWithEmptyList() {
        ArrayList<String> input = new ArrayList<>();
        String expected = "";
        textManager.setText(input);
        assertEquals(expected, textManager.formatTextRaw());
    }
}