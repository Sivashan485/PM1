package com.NotFalse.app;

import java.util.ArrayList;

public class TextEditor {

    private TextManager textManager;

    public TextEditor() {
        textManager = new TextManager();
        // Constructor implementation
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        TextEditor a = new TextEditor();
        a.runTextEditor();
        a.runTextEditor();

    }

    private void startTextEditor() {
        // startTextEditor implementation
    }

    private void runTextEditor() {
        textManager.editText();;
        // runTextEditor implementation
    }

    private void showHelpMessage() {
        // showHelpMessage implementation
    }

    

}
