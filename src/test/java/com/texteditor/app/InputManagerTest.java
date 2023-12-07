package com.texteditor.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

public class InputManagerTest {

    InputManager input;

    @Test
    void allowedCharactersUmlauts() {
        System.setIn(new ByteArrayInputStream("äöüÄÖÜ\n".getBytes()));
        input = new InputManager();
        String inputText = input.readAndFilterUserInput();
        String expected = "äöüÄÖÜ";
        assertEquals(expected, inputText);
    }

    @Test
    void allowedCharactersAtoZ() {
        System.setIn(new ByteArrayInputStream("AkniecnienTernnvEsflksjSS\n".getBytes()));
        input = new InputManager();
        String inputText = input.readAndFilterUserInput();
        String expected = "AkniecnienTernnvEsflksjSS";
        assertEquals(expected, inputText);
    }

    @Test
    void testAllowedCharactersAll() {
        System.setIn(new ByteArrayInputStream("Aadf .,:;-!? '()\"%@+*[]{}&#$ksnkdf23324ä\n".getBytes()));
        input = new InputManager();
        String inputText = input.readAndFilterUserInput();
        String expected = "Aadf .,:;-!? '()\"%@+*[]{}&#$ksnkdf23324ä";
        assertEquals(expected, inputText);
    }

    @Test
    void unallowedCharacters() {
        System.setIn(new ByteArrayInputStream("£€¢¬§°¦éà\n".getBytes()));
        input = new InputManager();
        String inputText = input.readAndFilterUserInput();
        String expected = "";
        assertEquals(expected, inputText);
    }

    @Test
    void testReadAndFilterUserInput() {
        System.setIn(new ByteArrayInputStream("This is a test input with some disallowed characters: @#%".getBytes()));
        input = new InputManager();
        String filteredInput = input.readAndFilterUserInput();
        String expected = "This is a test input with some disallowed characters: @#%";
        assertEquals(expected, filteredInput);
    }

    @Test
    void testFilterInputShouldFilterUnwantedCharacters() {
        System.setIn(new ByteArrayInputStream("Hello123²§~".getBytes()));
        input = new InputManager();
        String filteredInput = input.readAndFilterUserInput();
        String expected = "Hello123";
        assertEquals(expected, filteredInput);
    }

    @Test
    void testSplitInputShouldSplitAndIdentifyCommandAdd() {
        System.setIn(new ByteArrayInputStream("add Hey how are you, i'm the test".getBytes()));
        input = new InputManager();
        input.parseInput();
        assertEquals("add", input.getUserCommand());
        assertNull(input.getUserIndex());
        assertEquals("Hey how are you, i'm the test", input.getRestPart());
    }

    @Test
    void testSplitInputShouldSplitAndIdentifyCommandReplace() {
        System.setIn(new ByteArrayInputStream(("replace diam horem").getBytes()));
        input = new InputManager();
        input.parseInput();
        assertEquals("replace", input.getUserCommand());
        assertNull(input.getUserIndex());
        assertEquals("diam horem", input.getRestPart());
    }

    @Test
    void testSplitInputShouldSplitAndIdentifyCommandExit() {
        System.setIn(new ByteArrayInputStream(("exit 1212").getBytes()));
        input = new InputManager();
        input.parseInput();
        assertEquals(CommandApp.UNKNOWN.getCommand(), input.getUserCommand());
        assertNull(input.getUserIndex());
        assertEquals("1212", input.getRestPart());
    }

    @Test
    void testSplitInputShouldSplitButNotIdentifyCommandExit() {
        System.setIn(new ByteArrayInputStream(("add exit is the exit For the Exit").getBytes()));
        input = new InputManager();
        input.parseInput();
        assertEquals("add", input.getUserCommand());
        assertNull(input.getUserIndex());
        assertEquals("exit is the exit For the Exit", input.getRestPart());
    }

    @Test
    void testSplitInputShouldSplitAndIdentifyCommandDel() {
        System.setIn(new ByteArrayInputStream(("Del is to del some text In The Text Editor.").getBytes()));
        input = new InputManager();
        input.parseInput();
        assertEquals("del", input.getUserCommand());
        assertNull(input.getUserIndex());
        assertEquals("is to del some text In The Text Editor.", input.getRestPart());
    }

    @Test
    void testSplitInputShouldHandleEmptyInput() {
        System.setIn(new ByteArrayInputStream("\n".getBytes()));
        input = new InputManager();
        input.parseInput();
        assertEquals("", input.getUserCommand());
        assertNull(input.getUserIndex());
        assertEquals("", input.getRestPart());
    }

    @Test
    void testIsIndexNull() {
        System.setIn(new ByteArrayInputStream("add Hello World\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertTrue(input.isIndexNull());
    }

    @Test
    void testGetIsIndexValid() {
        System.setIn(new ByteArrayInputStream("add 1 Hello World\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertFalse(input.getIsIndexValid());
    }

    @Test
    void testGetIsIndexValidWithInvalidIndex() {
        System.setIn(new ByteArrayInputStream("add abc Hello World\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertFalse(input.getIsIndexValid());
    }

    @Test
    void testGetIsCharacterValid() {
        System.setIn(new ByteArrayInputStream("add Hello World\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertTrue(input.getIsCharacterValid());
    }

    @Test
    void testGetIsCharacterValidWithInvalidCharacters() {
        System.setIn(new ByteArrayInputStream("add Hello World£€¢¬§°¦éà\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertFalse(input.getIsCharacterValid());
    }

    @Test
    void testResetValues() {
        InputManager input = new InputManager();
        input.resetValues();
        assertNull(input.getUserIndex());
        assertEquals("", input.getUserCommand());
        assertEquals("", input.getRestPart());
        assertTrue(input.getIsCharacterValid());
    }

    @Test
    void testExtractCommand() {
        System.setIn(new ByteArrayInputStream("add Hello World\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertEquals("add", input.getUserCommand());
    }

    @Test
    void testValidateAndSetIndex() {
        System.setIn(new ByteArrayInputStream("add 1 Hello World\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertNull(input.getUserIndex());
        assertFalse(input.getIsIndexValid());
    }

    @Test
    void testHandleIndexCommandValid() {
        System.setIn(new ByteArrayInputStream("add 2147483647".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertEquals(2147483647, input.getUserIndex());
        assertTrue(input.getIsIndexValid());
    }

    @Test
    void testHandleIndexCommandValidTwo() {
        System.setIn(new ByteArrayInputStream("a_dd 0\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertEquals(0, input.getUserIndex());
        assertTrue(input.getIsIndexValid());
    }

    @Test
    void testHandleIndexCommandInvalid() {
        System.setIn(new ByteArrayInputStream("add 2147483648".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertNull(input.getUserIndex());
        assertFalse(input.getIsIndexValid());
    }

    @Test
    void testSetUserIndex() {
        System.setIn(new ByteArrayInputStream("add 0\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertEquals(0, input.getUserIndex());
        assertTrue(input.getIsIndexValid());
    }

    @Test
    void testExtractCommandWithUnknownCommand() {
        System.setIn(new ByteArrayInputStream("unknown\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertEquals("unknown", input.getUserCommand());
    }

    @Test
    void testValidateAndSetIndexWithNoIndex() {
        System.setIn(new ByteArrayInputStream("add _£\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertNull(input.getUserIndex());
        assertTrue(input.getIsIndexValid());
        assertEquals("add", input.getUserCommand());
    }

    @Test
    void testHandleIndexCommandWithInvalidIndex() {
        System.setIn(new ByteArrayInputStream("add abc\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertNull(input.getUserIndex());
        assertFalse(input.getIsIndexValid());
    }

    @Test
    void testSetUserIndexWithInvalidIndex() {
        System.setIn(new ByteArrayInputStream("add abc\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertNull(input.getUserIndex());
        assertFalse(input.getIsIndexValid());
    }

    @Test
    void testGetRestPart() {
        System.setIn(new ByteArrayInputStream("addHello World\n".getBytes()));
        InputManager input = new InputManager();
        input.parseInput();
        assertFalse(input.getIsIndexValid());
    }
}
