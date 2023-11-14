package com.NotFalse.app;

import java.util.Random;

public class DummyGenerator {

    private Random random;
    private final static String[] RANDOM_STRINGS = { "random", "text", "beep", "boop" };

    public DummyGenerator() {
        // initialization
        random = new Random();
    }

    public String createDummyText() {
        // implementation
        return RANDOM_STRINGS[random.nextInt(RANDOM_STRINGS.length)];
    }
}
