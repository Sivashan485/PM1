package com.NotFalse.app;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test.*;

import java.io.ByteArrayInputStream;

public class InputReceiverTest {

    InputReceiver input;
    @BeforeEach
    void setupInputReceiverTest(){
        input = new InputReceiver();

    }

    @Test
    void allowedCharactersUmlauts(){
        System.setIn(new ByteArrayInputStream("äöüÄÖÜ\n".getBytes()));
        input = new InputReceiver();
        String inputText = input.splitInput()[0];
        assertEquals(inputText, "äöüÄÖÜ");
    }

    @Test
    void allowedCharactersAtoZ(){
        System.setIn(new ByteArrayInputStream("AkniecnienTernnvEsflksjSS\n".getBytes()));
        input = new InputReceiver();
        String inputText = input.splitInput()[0];
        assertEquals(inputText, "AkniecnienTernnvEsflksjSS");
    }

    @Test
    void allowedCharactersAll(){
        System.setIn(new ByteArrayInputStream("Aadf .,:;-!? '()\"%@+*[]{}/&#$ksnkdf23324ä\n".getBytes()));
        input = new InputReceiver();
        String inputText = input.splitInput()[0];
        assertEquals( "Aadf .,:;-!? '()\"%@+*[]{}/&#$ksnkdf23324ä", inputText);
    }

    @Test
    void unallowedCharacters(){
        System.setIn(new ByteArrayInputStream("£€¢¬§°¦éà^`\n".getBytes()));
        input = new InputReceiver();
        String inputText = input.splitInput()[0];
        assertEquals( "", inputText);
    }
}
