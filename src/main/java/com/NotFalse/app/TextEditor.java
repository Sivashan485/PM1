package com.NotFalse.app;

/**
 * Main class for the TextEditor application.
 */
public class TextEditor {

    private final InputReceiver input;
    private final TextManager textManager;
    private final OutputManager output;
    private final GlossaryApp glossary;
    private boolean isExitTriggered;

    /**
     * Constructor for the TextEditor class.
     */
    public TextEditor() {
        input = new InputReceiver();
        textManager = new TextManager();
        output = new OutputManager();
        isExitTriggered = false;
        glossary = new GlossaryApp();
    }

    /**
     * Main method for the TextEditor application.
     *
     * @param args
     */
    public static void main(String[] args) {
        TextEditor textEditor = new TextEditor();
        textEditor.runTextEditor();
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

    /**
     * Creates and displays a formatted message based on the success of the 'formatRaw' operation.
     * If the 'formatRaw' operation is successful, a success message is created; otherwise, an error message is created.
     * The decision is based on the result obtained from 'textManager.getIsFormatRawSuccessful()'.
     */
    private void createFormatRawOutputMessage() {
        output.createFormatMessage(textManager.getIsFormatRawSuccessful());
    }

    /**
     * Creates and displays a formatted message based on the success of the 'formatFix' operation.
     * If the 'formatFix' operation is successful, a success message is created; otherwise, an error message is created.
     * The decision is based on the result obtained from 'textManager.getIsFormatFixSuccessful()'.
     */
    private void createFormatFixOutputMessage() {
        output.createFormatMessage(textManager.getIsFormatFixSuccessful());
    }

    private void replace(){
        System.out.print("Replacing Word: ");
        String originalWord = input.readAndFilterUserInput();
        if(textManager.retrieveReplacementWord( originalWord, input.getIndex())){
            System.out.println("Replacing with: ");
            String replacingWord = input.readAndFilterUserInput();
            textManager.replaceParagraph(input.getIsIndexNull(), originalWord, replacingWord);
        }
    }

    private void addText(){
        String enteredText;
        if (input.getIsIndexNull()) {
            System.out.print("Text: ");
            enteredText = input.readAndFilterUserInput();
            textManager.addNewParagraph(input.getIsIndexNull(), enteredText);
        } else{
            System.out.print("Text: ");
            enteredText = input.readAndFilterUserInput();
            textManager.addNewParagraph(input.getIsIndexNull(), enteredText);
        }
    }

    /**
     * Processes and executes text editing commands based on user input.
     * Parses the input, updates the text manager, and performs operations according to the command received.
     * Displays corresponding messages or triggers actions such as adding, deleting, replacing, formatting, or printing text.
     */
    public void editText() {
        input.splitInput();
        Integer widthIndex = input.getIndex();
        textManager.setParagraphIndex(input.getIndex());

        switch (Command.parseCommand(input.getCommand())) {
            case DUMMY:
                textManager.addDummyParagraph(input.getIsIndexNull());
                break;
            case EXIT:
                output.createExitMessage();
                isExitTriggered = true;
                break;
            case ADD:
                addText();
                break;
            case DEL:
                textManager.deleteParagraph(input.getIsIndexNull());
                break;
            case INDEX:
                glossary.printGlossary(textManager.getText());
                break;
            case PRINT:
                textManager.printText();
                break;
            case REPLACE:
                replace();
                break;
            case HELP:
                output.createHelpMessage();
                break;
            case FORMAT_RAW:
                textManager.formatTextRaw();
                createFormatRawOutputMessage();
                break;
            case FORMAT_FIX:
                textManager.setMaxWidth(widthIndex);
                textManager.formatTextFix();
                createFormatFixOutputMessage();
                break;
            default:
                output.createInvalidCommandMessage();
                break;
        }
    }

}
