package com.NotFalse.app;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
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

    //ADD DUMMY
    private void addDummyAuto(String index, String addingItem) {
        // Initialize textManager and input
        textManager = new TextManager();
        String item = "dummy " + index + "\n" + addingItem + "\n";
        System.setIn(new ByteArrayInputStream(item.getBytes()));
        input = new InputReceiver();
        input.splitInput();
        textManager.updateInputReceiver(input);
        textManager.setParagraphIndex(input.getIndex());
        textManager.addDummyParagraph();
        textManager.printText();
    }


    @Test
    void testAddDummyText() {
        List<String> testTextList = textManager.getTextList();
        int listSizeBefore = testTextList.size();
        textManager.validateIndex(2);
        textManager.addDummyParagraph();
        assertEquals(listSizeBefore + 1, testTextList.size());
    }

    // ADD TEXT test
    private void addElementAuto(String index, String addingItem) {
        // Initialize textManager and input
        textManager = new TextManager();
        String item = "add " + index + "\n" + addingItem + "\n";
        System.setIn(new ByteArrayInputStream(item.getBytes()));
        input = new InputReceiver();
        input.splitInput();
        textManager.updateInputReceiver(input);
        textManager.setParagraphIndex(input.getIndex());
        textManager.addNewParagraph();
        textManager.printText();
    }



    @Test
    void testAddTextWithIndex() {
        addElementAuto("1", "TEST 1");
        assertEquals(textManager.getTextList().get(0), "TEST 1");
    }

    @Test
    void testAddTextWithoutIndex() {
        addElementAuto("", "TEST 1");
        int listSize = textManager.getTextList().size();
        assertEquals(textManager.getTextList().get(listSize - 1), "TEST 1");

    }


    void testAddIndexRangeTest(String index) {
        String testedString = "TEST ---- --- ... ,,,.  1";
        addElementAuto(index, testedString);
        int listSize = textManager.getTextList().size();
        for (int i = 0; i < textManager.getTextList().size(); i++) {
            Assertions.assertFalse(textManager.getTextList().get(i).equals(testedString));
        }
    }

    @Test
    void testAddTextIndexRange() {
        testAddIndexRangeTest("20000000");
        testAddIndexRangeTest("-10000");
        testAddIndexRangeTest("0");
        testAddIndexRangeTest("WAS2");
    }

    // REPLACE TEXT
    public void replaceElementAuto(String sentenceToChange, String sentenceIndex,String index, String replacingItem, String replacingWith) {
        // Initialize textManager and input
        textManager = new TextManager();
        addElementAuto(sentenceIndex, sentenceToChange);

        String item = "replace " + index + "\n" + replacingItem + "\n"+replacingWith+"\n";
        System.out.println(item);
        System.setIn(new ByteArrayInputStream(item.getBytes()));
        input = new InputReceiver();
        input.splitInput();
        textManager.updateInputReceiver(input);
        textManager.setParagraphIndex(input.getIndex());
        textManager.replaceParagraph();
        textManager.printText();
    }
    @Test
    void testReplaceTextValid() {
        int index = 0;
        addElementAuto("1", "Fifth End of text.");
        List<String> testTextList = textManager.getTextList();
        textManager.replaceWordInParagraph(index, "End", "-");
        String textAfterChange = testTextList.get(index);
        assertEquals(textAfterChange, "Fifth - of text.");

    }

    @Test
    void TestReplaceWithIndex(){
        textManager.printText();
        replaceElementAuto("WA. S.DF","1","1", ".", "1");
        assertEquals("WA1 S1DF", textManager.getTextList().get(0));
    }

    @Test
    void TestReplaceIndexAtEnd(){
        textManager.printText();
        replaceElementAuto(" .WA. S.DF. . ","","", ".", "1");
        int indexEnd = textManager.getTextList().size()-1;
        assertEquals(" 1WA1 S1DF1 1 ", textManager.getTextList().get(indexEnd));
    }

    @Test
    void TestReplaceIndexUnder0(){
        textManager.printText();
        replaceElementAuto(" .WA. S.DF. . ","1","-11", ".", "1");
        int indexEnd = textManager.getTextList().size()-1;
        for(int i = 0 ; i<textManager.getText().size(); i++){
            Assertions.assertFalse(textManager.getTextList().get(i).equals(" 1WA1 S1DF1 1 "));
        }
    }
    @Test
    void TestReplaceIndexOverListSize(){
        textManager.printText();
        Integer endingIndex = textManager.getTextList().size()+20;
        replaceElementAuto(" .WA. S.DF. . ","1", endingIndex.toString(), ".", "1");
        int indexEnd = textManager.getTextList().size()-1;
        for(int i = 0 ; i<textManager.getText().size(); i++){
            Assertions.assertFalse(textManager.getTextList().get(i).equals(" 1WA1 S1DF1 1 "));
        }
    }


    @Test
    void testReplaceTextUnvalid() {
        int index = 0;
        addElementAuto("1", "Fifth End of text.");
        List<String> testTextList = textManager.getTextList();
        textManager.replaceWordInParagraph(index, "@#@@#@@@#@#@#@#@#@#@@", "-");
        String textAfterChange = testTextList.get(index);
        System.out.println(testTextList.get(index));
        assertEquals(textAfterChange, "Fifth End of text.");
    }


    // DEL FUN

    // Test for Method formatTextFix
    @Test
    void testSingleShortWord() {
        String expected = "test\n";
        textManager.setText(Arrays.asList("test\n"));
        textManager.setMaxWidth(4);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testEmptyInput() {
        String expected = "\n";
        textManager.setText(Arrays.asList(""));
        textManager.setMaxWidth(4);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testSingleLongWord() {
        String expected = "0123456\n" +
                "7890123\n" +
                "456789\n";
        textManager.setText(Arrays.asList("01234567890123456789"));
        textManager.setMaxWidth(7);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testMultipleShortWords() {
        String expected = "hello\n" +
                "world\n";
        input = new InputReceiver();
        textManager.setText(Arrays.asList("hello", "world"));
        textManager.setMaxWidth(7);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testMultipleLongWords() {
        String expected = "abcdefghij jiwer\n" +
                "klmnopqrstuvwxy\n" +
                "zabcdefghij\n";
        textManager.setText(Arrays.asList("abcdefghij", "jiwer", "klmnopqrstuvwxy", "zabcdefghij"));
        textManager.setMaxWidth(20);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testMultipleWordsBiggerThanMaxWidth() {
        String expected = "abcdefghij\n" +
                "klmnopqrst\n" +
                "uvwxyzabcd\n" +
                "efghijklmn\n" +
                "opqrstuvwx\n";
        textManager.setText(Arrays.asList("abcdefghijklmnopqrst", "uvwxyzabcdefghijklmn", "opqrstuvwx"));
        textManager.setMaxWidth(10);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testWordsExactlyMatchingMaxWidth() {
        String expected = "abcdefghij\n" +
                "klmnopqrst\n" +
                "uvwxyzabcd\n" +
                "efghijklmn\n" +
                "opqrstuvwx\n";
        textManager.setText(Arrays.asList("abcdefghij", "klmnopqrst", "uvwxyzabcd", "efghijklmn", "opqrstuvwx"));
        textManager.setMaxWidth(10);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testLineBreakOnWordBoundary() {
        String expected = "abcdefghij\n" +
                "klmnopqrst uvwxyzabc\n" +
                "efghijkl opqrstu\n" +
                "yzabcd ijklm stuv\n" +
                "cde mn o\n";
        textManager.setText(Arrays.asList("abcdefghij", "klmnopqrst", "uvwxyzabc", "efghijkl",
                "opqrstu", "yzabcd", "ijklm", "stuv", "cde", "mn", "o"));
        textManager.setMaxWidth(20);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testSpacesNotPreserved() {
        String expected = "hello world\n";
        textManager.setText(Arrays.asList("hello", " ", "world"));
        textManager.setMaxWidth(20);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testMaximumWidthBoundary() {
        String longWord = "a".repeat(80);
        String expected = longWord + "\nb\n";
        textManager.setText(Arrays.asList(longWord, "b"));
        textManager.setMaxWidth(80);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method resetParagraphWidth
    @Test
    void testResetParagraphWidthGreaterThanZero() {
        StringBuilder fixFormatted = new StringBuilder("test");
        int currentParagraphWidth = 5;
        textManager.resetParagraphWidth(currentParagraphWidth, fixFormatted);
        String expected = "test\n";
        assertEquals(expected, fixFormatted.toString());
    }

    // Test for Method resetParagraphWidth
    @Test
    void testResetParagraphWidthZero() {
        StringBuilder fixFormatted = new StringBuilder("test");
        int currentParagraphWidth = 0;
        textManager.resetParagraphWidth(currentParagraphWidth, fixFormatted);
        String expected = "test";
        assertEquals(expected, fixFormatted.toString());
    }

    // Test for Method resetParagraphWidth
    @Test
    void testResetParagraphWidthNegative() {
        StringBuilder fixFormatted = new StringBuilder("test");
        int currentParagraphWidth = -5;
        textManager.resetParagraphWidth(currentParagraphWidth, fixFormatted);
        String expected = "test";
        assertEquals(expected, fixFormatted.toString());
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