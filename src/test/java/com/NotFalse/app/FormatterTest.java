package com.NotFalse.app;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatterTest {

    // Test for Method formatTextFix
    @Test
    public void testSingleShortWord() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("test"));
        String expected = "tes\n" +
                "t";
        assertEquals(expected, new Formatter().formatTextFix(input, 3));
    }

    // Test for Method formatTextFix
    @Test
    public void testEmptyInput() {
        ArrayList<String> input = new ArrayList<>();
        String expected = "";
        assertEquals(expected, new Formatter().formatTextFix(input, 5));
    }

    // Test for Method formatTextFix
    @Test
    public void testSingleLongWord() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList(
                        "01234567890123456789"));
        String expected = "0123456\n" +
                "7890123\n" +
                "456789";
        assertEquals(expected, new Formatter().formatTextFix(input, 7));
    }

    // Test for Method formatTextFix
    @Test
    public void testMultipleShortWords() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("hello", "world"));
        String expected = "hello\n" +
                "world";
        assertEquals(expected, new Formatter().formatTextFix(input, 7));
    }

    // Test for Method formatTextFix
    @Test
    public void testMultipleLongWords() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("abcdefghij", "jiwer", "klmnopqrstuvwxy", "zabcdefghij"));
        String expected = "abcdefghij jiwer\n" +
                "klmnopqrstuvwxy\n" +
                "zabcdefghij";
        assertEquals(expected, new Formatter().formatTextFix(input, 20));
    }

    // Test for Method formatTextFix
    @Test
    public void testWordsExactlyMatchingMaxWidth() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("abcdefghij", "klmnopqrst", "uvwxyzabcd", "efghijklmn", "opqrstuvwx"));
        String expected = "abcdefghij\n" +
                "klmnopqrst\n" +
                "uvwxyzabcd\n" +
                "efghijklmn\n" +
                "opqrstuvwx";
        assertEquals(expected, new Formatter().formatTextFix(input, 10));
    }

    // Test for Method formatTextFix
    @Test
    public void testLineBreakOnWordBoundary() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("abcdefghij", "klmnopqrst", "uvwxyzabc", "efghijkl",
                "opqrstu", "yzabcd", "ijklm", "stuv", "cde", "mn", "o"));
        String expected = "abcdefghij\n" +
                "klmnopqrst uvwxyzabc\n" +
                "efghijkl opqrstu\n" +
                "yzabcd ijklm stuv\n" +
                "cde mn o";
        assertEquals(expected, new Formatter().formatTextFix(input, 20));
    }

    // Test for Method formatTextFix
    @Test
    public void testSpacesNotPreserved() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("hello", " ", "world"));
        String expected = "hello world";
        assertEquals(expected, new Formatter().formatTextFix(input, 20));
    }

    // Test for Method formatTextFix
    @Test
    public void testNullInput() {
        ArrayList<String> input = null;
        String expected = "";
        assertEquals(expected, new Formatter().formatTextFix(input, 20));
    }

    // Test for Method formatTextFix
    @Test
    public void testMaximumWidthBoundary() {
        String longWord = "a".repeat(80);
        ArrayList<String> input = new ArrayList<>(Arrays.asList(longWord, "b"));
        String expected = longWord + "\nb";
        assertEquals(expected, new Formatter().formatTextFix(input, 80));
    }

    // Test for Method formatTextRaw
    @Test
    public void testFormatTextRawWithSingleLine() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("This is a single line of text."));
        String expected = "<1>: This is a single line of text.\n";
        assertEquals(expected, new Formatter().formatTextRaw(input));
    }

    // Test for Method formatTextRaw
    @Test
    public void testFormatTextRawWithMultipleLines() {
        ArrayList<String> input = new ArrayList<>(
                Arrays.asList("This is the first line of text.", "This is the second line of text."));
        String expected = "<1>: This is the first line of text.\n" +
                "<2>: This is the second line of text.\n";
        assertEquals(expected, new Formatter().formatTextRaw(input));
    }

    // Test for Method formatTextRaw
    @Test
    public void testFormatTextRawWithEmptyList() {
        ArrayList<String> input = new ArrayList<>();
        String expected = "";
        assertEquals(expected, new Formatter().formatTextRaw(input));
    }

    // Test for Method formatTextRaw
    @Test
    public void testFormatTextRawWithNullList() {
        ArrayList<String> input = null;
        String expected = "";
        assertEquals(expected, new Formatter().formatTextRaw(input));
    }
}