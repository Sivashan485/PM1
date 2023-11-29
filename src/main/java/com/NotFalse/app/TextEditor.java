package com.NotFalse.app;

/**
 * Main class for the TextEditor application.
 */
public class TextEditor {

    private final InputReceiver input;
    private final TextManager textManager;
    private boolean isExitTriggered;
    private final OutputManager output;
    private final GlossaryApp glossary;

    /**
     * Constructor for the TextEditor class.
     */
    public TextEditor() {
        input = new InputReceiver();
        textManager = new TextManager();
        output = new OutputManager();
        isExitTriggered = false;
        glossary = new GlossaryApp(output);
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
            editText();
            isRunning = !isExitTriggered;
        } while (isRunning);
    }

    public void editText() {
        input.splitInput();
        Integer widthIndex = input.getIndex();

        switch (Command.parseCommand(input.getCommand())) {
            case DUMMY:
                textManager.addDummyParagraph();
                break;
            case EXIT:
                output.createExitMessage();
                isExitTriggered = true;
                break;
            case ADD:
                textManager.addNewParagraph();
                break;
            case DEL:
                textManager.deleteParagraph();
                break;
            case INDEX:
                glossary.printGlossary(textManager.getText());
                break;
            case PRINT:
                textManager.printText();
                break;
            case REPLACE:
                textManager.replaceParagraph();
                break;
            case HELP:
                output.createHelpMessage();
                break;
            case FORMAT_RAW:
                textManager.formatTextRaw();
                if (textManager.getIsFormatRawSuccessful()) {
                    output.createFormatMessage(true);
                } else {
                    output.createFormatMessage(false);
                }
                break;
            case FORMAT_FIX:
                textManager.setMaxWidth(widthIndex);
                textManager.formatTextFix();
                if (textManager.getIsFormatFixSuccessful()) {
                    output.createFormatMessage(true);
                } else {
                    output.createFormatMessage(false);
                }
                break;
            default:
                output.createInvalidCommandMessage();
                break;
        }
    }

}
