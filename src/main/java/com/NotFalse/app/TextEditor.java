package com.NotFalse.app;

/**
 * Main class for the TextEditor application.
 */
public class TextEditor {

    private TextManager textManager;

    /**
     * Constructor for the TextEditor class.
     */
    public TextEditor() {
        textManager = new TextManager();
    }

    /**
     * Main method for the TextEditor application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        TextEditor a = new TextEditor();
        a.runTextEditor();
    }

    /**
     * Runs the TextEditor application.
     */
    private void runTextEditor() {
        boolean isRunning;
        do {
            textManager.editText();
            isRunning = !textManager.getIsExitTriggered();
        } while (isRunning);
    }

}
