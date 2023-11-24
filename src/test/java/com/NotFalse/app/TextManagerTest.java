package com.NotFalse.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TextManagerTest {

    TextManager textManager;
    InputReceiver input;

    @BeforeEach
    void setUp() {
        textManager = new TextManager();
        input = new InputReceiver();

    }

    // Test for Method formatTextFix
    @Test
    void testSingleShortWord() {
        String expected = "test";
        textManager.setText(Arrays.asList("test"));
        Integer userInput = 4;
        textManager.setMaxWidth(userInput);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testEmptyInput() {
        String expected = "";
        textManager.setText(Arrays.asList(""));
        Integer userInput = 4;
        textManager.setMaxWidth(userInput);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testSingleLongWord() {
        String expected = "0123456\n" +
                "7890123\n" +
                "456789";
        textManager.setText(Arrays.asList("01234567890123456789"));
        Integer userInput = 6; //0-6 = 7 characters
        textManager.setMaxWidth(userInput);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testMultipleShortWords() {
        String expected = "hello\n" +
                "world";
        input = new InputReceiver();
        textManager.setText(Arrays.asList("hello", "world"));
        Integer userInput = 7;
        textManager.setMaxWidth(userInput);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testMultipleLongWords() {
        String expected = "abcdefghij jiwer\n" +
                "klmnopqrstuvwxy\n" +
                "zabcdefghij";
        textManager.setText(Arrays.asList("abcdefghij", "jiwer", "klmnopqrstuvwxy", "zabcdefghij"));
        Integer userInput = 20;
        textManager.setMaxWidth(userInput);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testMultipleWordsBiggerThanMaxWidth() {
        String expected = "abcdefghij\n" +
                "klmnopqrst\n" +
                "uvwxyzabcd\n" +
                "efghijklmn\n" +
                "opqrstuvwx";
        textManager.setText(Arrays.asList("abcdefghijklmnopqrst", "uvwxyzabcdefghijklmn", "opqrstuvwx"));
        Integer userInput = 9;  //0-9 = 10 characters
        textManager.setMaxWidth(userInput);
        assertEquals(expected, textManager.formatTextFix());
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
        Integer userInput = 10;
        textManager.setMaxWidth(userInput);
        assertEquals(expected, textManager.formatTextFix());
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
        Integer userInput = 19;  //0-19 = 20 characters
        textManager.setMaxWidth(userInput);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testSpacesNotPreserved() {
        String expected = "hello world";
        textManager.setText(Arrays.asList("hello", " ", "world"));
        Integer userInput = 20;
        textManager.setMaxWidth(userInput);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testMaximumWidthBoundary() {
        String longWord = "a".repeat(80);
        String expected = longWord + "\nb";
        textManager.setText(Arrays.asList(longWord, "b"));
        Integer userInput = 80;
        textManager.setMaxWidth(userInput);
        assertEquals(expected, textManager.formatTextFix());
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