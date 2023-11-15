package com.NotFalse.app;

public class OutputManager {
    private Logger logger;

    // Constructor
    public OutputManager() {
    }

    public void createWelcomeMessage() {
        System.out.println("Welcome to the TextEditor! Created by NotFalse.");
    }

    public void createMenuOptions() {
        System.out.println("Here are the commands you can use:");
        System.out.println("1. ADD\n2. DEL\n3. DUMMY\n4. FORMAT RAW\n5. FORMAT FIX\n" +
                "6. PRINT\n7. INDEX\n8. REPLACE\n9. EXIT\n10. HELP\n11.");
    }
    // Methods
    public void createHelpMessage() {
        System.out.println("Here are the commands you can use:");
        System.out.println("1. ADD\n2. DEL\n3. DUMMY\n4. FORMAT RAW\n5. FORMAT FIX\n" +
                "6. PRINT\n7. INDEX\n8. REPLACE\n9. EXIT\n10.");
    }

    public void createMaxStringWarning() {
        System.out.println("The text you have entered is too long! Please try again or " +
                "fix the text length.");
    }

    public void createMaxIntWaring() {
        System.out.println("The index you have entered is too large! Please try again.");
    }

    public void createExitMessage() {
        System.out.println("Exiting TextEditor...");
        System.out.println("Thank you for using TextEditor! Created by NotFalse.");
    }

    public void createAddMessage() {
        System.out.println("Add your text here:");
    }

    public void createDeleteMessage() {
        System.out.println("Text deleted successfully!");
    }

    public void createDummyMessage() {
        System.out.println("Dummy text generated successfully!");
    }

    public void createIndexMessage() {
        System.out.println("Text moved successfully!");
    }

    public void createPrintMessage() {
        System.out.println("Printing text...");
    }

    public void createReplaceMessage() {
        System.out.println("Text replaced successfully!");
    }

    public void createInvalidCommandMessage() {
        System.out.println("Invalid command! Please try again.");
    }
}
