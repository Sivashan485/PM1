package com.NotFalse.app;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class InputReceiverTest {

    InputReceiver input;

    @Test
    void allowedCharactersUmlauts() {
        System.setIn(new ByteArrayInputStream("äöüÄÖÜ\n".getBytes()));
        input = new InputReceiver();
        String inputText = input.splitInput()[0];
        assertEquals(inputText, "äöüÄÖÜ");
    }

    @Test
    void allowedCharactersAtoZ() {
        System.setIn(new ByteArrayInputStream("AkniecnienTernnvEsflksjSS\n".getBytes()));
        input = new InputReceiver();
        String inputText = input.splitInput()[0];
        assertEquals(inputText, "AkniecnienTernnvEsflksjSS");
    }


    @Test
    void testAllowedCharactersAll() {
        System.setIn(new
                ByteArrayInputStream("Aadf .,:;-!? '()\"%@+*[]{}&#$ksnkdf23324ä\n".getBytes(
        )));
        input = new InputReceiver();
        String inputText = input.splitInput()[0];
        assertEquals("Aadf .,:;-!? '()\"%@+*[]{}&#$ksnkdf23324ä", inputText);
    }


    @Test
    void unallowedCharacters() {
        System.setIn(new ByteArrayInputStream("£€¢¬§°¦éà\n".getBytes()));
        input = new InputReceiver();
        String inputText = input.splitInput()[0];
        assertEquals("", inputText);
    }

    @Test
    void testFilterInputShouldFilterUnwantedCharacters() {
        InputReceiver inputReceiver = new InputReceiver();
        String filteredInput = inputReceiver.filterInput("Hello123²§~");
        assertEquals("Hello123", filteredInput);
    }

    @Test
    void testGetFilteredInputLineShouldReceiveAndFilterInput() {
        System.setIn(new ByteArrayInputStream("Hello123²§~".getBytes()));
        input = new InputReceiver();
        String filteredInput = input.getFilteredInputLine();
        assertEquals("Hello123", filteredInput);
    }

    @Test
    void testSplitInputShouldSplitAndIdentifyCommandAdd() {
        System.setIn(new ByteArrayInputStream("add Hey how are you, i'm the test".getBytes()));
        input = new InputReceiver();
        String[] splitInput = input.splitInput();
        assertEquals("add", splitInput[0]);
        assertEquals("Hey how are you, i'm the test", splitInput[1]);
    }

    @Test
    void testSplitInputShouldSplitAndIdentifyCommandReplace() {
        System.setIn(new ByteArrayInputStream(("replace diam horem").getBytes()));
        input = new InputReceiver();
        String[] splitInput = input.splitInput();
        assertEquals("replace", splitInput[0]);
        assertEquals("diam horem", splitInput[1]);
    }

    @Test
    void testSplitInputShouldSplitAndIdentifyCommandExit() {
        System.setIn(new ByteArrayInputStream(("exit 1212").getBytes()));
        input = new InputReceiver();
        String[] splitInput = input.splitInput();
        assertEquals("exit", splitInput[0]);
        assertEquals("1212", splitInput[1]);
    }

    @Test
    void testSplitInputShouldSplitButNotIdentifyCommandExit() {
        System.setIn(new ByteArrayInputStream(("add exit is the exit For the Exit").getBytes()));
        input = new InputReceiver();
        String[] splitInput = input.splitInput();
        assertEquals("add", splitInput[0]);
        assertFalse(Boolean.parseBoolean("exit"), splitInput[1]);
        assertEquals("exit is the exit For the Exit", splitInput[1]);
    }

    @Test
    void testSplitInputShouldSplitAndIdentifyCommandDel() {
        System.setIn(new ByteArrayInputStream(("Del is to del some text In The Text Editor.").getBytes()));
        input = new InputReceiver();
        String[] splitInput = input.splitInput();
        assertEquals("Del", splitInput[0]);
        assertEquals("is to del some text In The Text Editor.", splitInput[1]);
    }


    @Test
    void testSplitInputShouldHandleEmptyInput() {
        System.setIn(new ByteArrayInputStream("\n".getBytes()));
        input = new InputReceiver();
        String[] splitInput = input.splitInput();
        assertEquals("", splitInput[0]);
    }

}
