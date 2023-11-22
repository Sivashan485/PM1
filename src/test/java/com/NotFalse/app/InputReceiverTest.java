package com.NotFalse.app;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    /*
     * -> this test is not working -> fix bug
     * 
     * @Test
     * void allowedCharactersAll() {
     * System.setIn(new
     * ByteArrayInputStream("Aadf .,:;-!? '()\"%@+*[]{}/&#$ksnkdf23324ä\n".getBytes(
     * )));
     * input = new InputReceiver();
     * String inputText = input.splitInput()[0];
     * assertEquals("Aadf .,:;-!? '()\"%@+*[]{}/&#$ksnkdf23324ä", inputText);
     * }
     */

    /*
     * -> this text is not working -> fix bug
     * 
     * @Test
     * void unallowedCharacters() {
     * System.setIn(new ByteArrayInputStream("£€¢¬§°¦éà^`\n".getBytes()));
     * input = new InputReceiver();
     * String inputText = input.splitInput()[0];
     * assertEquals("", inputText);
     * }
     */

}
