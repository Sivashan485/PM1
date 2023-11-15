package com.NotFalse.app;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class DummyGeneratorTest {
    @Test
    void amountOfWordsGenerated(){
        DummyGenerator  test = new DummyGenerator();
        String[] dummyTestText = test.createDummyText().split(" ");
        assertEquals(test.getaMountOfWords(),dummyTestText.length);

    }
}
