package com.NotFalse.app;

import java.util.Random;

public class DummyGenerator {

    //Number for amount of Word that needs to be added to a string
    private final int amountOfWords = 100;
    private final Random random;

    /**
     * Returns the amountOfWords as Int
     * @return
     */
    int getaMountOfWords(){
        return amountOfWords;
    }

    //Array for storing random Words to generate a Paragraph
    private final static String[] RANDOM_STRINGS = { "Das","Panzer", "Traktor" ,"Maschine", "isch","de", "mah", "Gugus", "Vereis" , "dreamer","Bruno","gsi", "sheesh","Richi",
            "guet" ,"geblieben", "Bananen" ,"di","bin", "So", "gmacht", "und","gschroe","Kamera","suiii","Down","you",  "Jo", "Getrocknete", "Broke", "heb", "ich", "Beamer"};

    /**
     * Constructor: initializing random
     */
    public DummyGenerator() {
        // initialization
        random = new Random();
    }

    /**
     * Generates a random dummy text by concatenating a specified number of randomly selected strings.
     * @return A string containing the generated dummy text.
     */
    public String createDummyText() {
        String randomizedText = "";
        for(int i = 1; i<amountOfWords; i++){
            randomizedText = randomizedText+" "+selectRandomString().trim();
        }
        return randomizedText;

    }

    /**
     * This method selects a random string from random_strings and returns it
     * @return
     */
    private String selectRandomString(){
     return RANDOM_STRINGS[random.nextInt(RANDOM_STRINGS.length)];
    }
}
