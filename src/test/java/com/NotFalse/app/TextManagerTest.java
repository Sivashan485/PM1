package com.NotFalse.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextManagerTest {

    TextManager textManager;
    InputReceiver input;

    @BeforeEach
    void setUp() {
        textManager = new TextManager();
        input = new InputReceiver();

    }

    //ADD
    /*
    private void addDummyAuto(String index) {

        textManager = new TextManager();
        String item = "dummy " + index + "\n";
        System.setIn(new ByteArrayInputStream(item.getBytes()));
        input = new InputReceiver();
        input.splitInput();
        textManager.setParagraphIndex(input.getIndex());
        textManager.addDummyParagraph(input.getIsIndexNull());
        textManager.printText();
    }

    @Test
    void testAddDummyIndexOne() {
        addDummyAuto("5");
        assertEquals(TextManager.DUMMYTEXT, textManager.getTextList().get(4));
        addDummyAuto("-11");
        for (int i = 0; i < textManager.getTextList().size(); i++) {
            assertNotEquals(TextManager.DUMMYTEXT, textManager.getTextList().get(i));
        }
    }

    @Test
    void testAddDummyIndexTwo() {
        addDummyAuto("5");
        assertEquals(TextManager.DUMMYTEXT, textManager.getTextList().get(4));
    }

    @Test
    void testAddDummyIndexThree() {
        addDummyAuto("-11");
        for (int i = 0; i < textManager.getTextList().size(); i++) {
            assertNotEquals(TextManager.DUMMYTEXT, textManager.getTextList().get(i));
        }
    }
*/
    // ADD TEXT test
    private void addElementAuto(Integer index, boolean isIndexNull, String addingItem) {
        // Initialize textManager and input
        textManager.addNewParagraph(isIndexNull, addingItem, index);
    }


    @Test
    void testAddTextWithIndex() {
        addElementAuto(1, false, "T");
        assertEquals(textManager.getTextList().get(0), "T");
    }

    @Test
    void testAddTextWithoutIndex() {
        addElementAuto(null, true, "T");
        int listSize = textManager.getTextList().size();
        assertEquals(textManager.getTextList().get(listSize - 1), "T");

    }


    void testAddIndexRangeTest(int index) {
        String testedString = "T";
        addElementAuto(index, false, "T");
        for (int i = 0; i < textManager.getTextList().size(); i++) {
            assertNotEquals(textManager.getTextList().get(i), testedString);
        }
    }

    @Test
    void testAddTextIndexRange() {
        testAddIndexRangeTest(20000000);
        testAddIndexRangeTest(-10000);
        testAddIndexRangeTest(0);
    }

    // REPLACE TEXT
    /*public void replaceElementAuto(String sentenceToChange, int sentenceIndex, int replaceIndex, String replacingItem, String replacingWith) {

        addElementAuto(sentenceIndex, false, sentenceToChange);
        textManager.replaceWordInParagraph(replaceIndex, replacingItem, replacingWith);
        textManager.replaceParagraphSection(false, replacingItem, replacingWith);
    }


    @Test
    void testReplaceTextValid() {
        int index = 0;
        replaceElementAuto("Test Te", 1, 1, "Te", "EE");

        String textAfterChange = textManager.getTextList().get(index);
        assertEquals("EEst EE", textAfterChange);

    }



    @Test
    void TestReplaceWithIndex() {
        textManager.printText();
        replaceElementAuto("WA. S.DF", 1, 1, ".", "1");
        assertEquals("WA1 S1DF", textManager.getTextList().get(0));
    }

    @Test
    void TestReplaceIndexAtEnd() {
        replaceElementAuto(" .WA. S.DF. . ", textManager.getTextList().size() + 1, ".", "1");
        String lastElement = textManager.getTextList().get(textManager.getTextList().size() - 1);
        assertEquals(" 1WA1 S1DF1 1 ", lastElement);
    }


    @Test
    void TestReplaceIndexUnder0() {
        replaceElementAuto(" .WA. S.DF. . ", -11, -1111, ".", "1");
        assertFalse(textManager.getTextList().contains(" 1WA1 S1DF1 1 "));
    }


    @Test
    void TestReplaceIndexOverListSize() {
        textManager.printText();
        Integer endingIndex = textManager.getTextList().size() + 20;
        replaceElementAuto(" .WA. S.DF. . ", 1, endingIndex, ".", "1");
        for (int i = 0; i < textManager.getText().size() - 1; i++) {
            assertNotEquals(" 1WA1 S1DF1 1 ", textManager.getTextList().get(i));
        }
    }


    @Test
    void testReplaceTextInvalid() {
        int index = 0;
        addElementAuto(1, false,"Fifth End of text.");
        List<String> testTextList = textManager.getTextList();
        textManager.replaceWordInParagraph(index, "ASDF", "E");
        String textAfterChange = testTextList.get(index);
        System.out.println(testTextList.get(index));
        assertEquals(textAfterChange, "Fifth End of text.");
    }
    */

    // DEL FUN
    private int deleteElementAuto(int addIndex, String addSentenceText, int delIndex, boolean isindexnull) {
        addElementAuto(addIndex, false, addSentenceText);
        int listSizeBeforeDel = textManager.getTextList().size();
        textManager.deleteParagraph(isindexnull,delIndex);
        return listSizeBeforeDel;

    }


    @Test
    void testDeleteNotInRangeOverListSize() {
        Integer delIndex = textManager.getTextList().size() + 100;
        int indexSizeBefore = deleteElementAuto(1, "WAS", delIndex,false);
        assertEquals(textManager.getTextList().size(), indexSizeBefore);
    }


    @Test
    void testDeleteInRangeListSize() {
        int indexSizeBefore = deleteElementAuto(1, "WAS", 1, false);
        assertEquals((indexSizeBefore - 1), textManager.getTextList().size());
    }

    @Test
    void testDeleteNotInRangeN0() {
        int indexSizeBefore = deleteElementAuto(1, "WAS", 0, false);
        assertEquals(indexSizeBefore, textManager.getTextList().size());
    }

    @Test
    void testDeleteNotInRangeUnder0() {
        int indexSizeBefore = deleteElementAuto(1, "WAS", -1000, false);
        assertEquals(indexSizeBefore, textManager.getTextList().size());
    }

    /*

    // Test for Method formatTextFix
    @Test
    void testSingleShortWord() {
        String expected = "test\n";
        textManager.setText(List.of("test\n"));
        textManager.setMaxWidth(4);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testEmptyInput() {
        String expected = "\n";
        textManager.setText(List.of(""));
        textManager.setMaxWidth(4);
        assertEquals(expected, textManager.formatTextFix());
    }

    // Test for Method formatTextFix
    @Test
    void testSingleLongWord() {
        String expected = "0123456\n" +
                "7890123\n" +
                "456789\n";
        textManager.setText(List.of("01234567890123456789"));
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
        textManager.resetParagraphWidth(fixFormatted);
        String expected = "test\n";
        assertEquals(expected, fixFormatted.toString());
    }


    // Test for Method formatTextRaw
    @Test
    void testFormatTextRawWithSingleLine() {
        String expected = "<1>: This is a single line of text.\n";
        textManager.setText(List.of("This is a single line of text."));
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
        textManager.setText(List.of(""));
        assertEquals(expected, textManager.formatTextRaw());
    }*/
}