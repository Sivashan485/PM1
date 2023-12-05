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
    private static final int MAX_WIDTH = 2147483647;


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
        output.createWelcomeMessage();
        do {
            editText();
            isRunning = !isExitTriggered;
        } while (isRunning);
    }


    private void validateMaxWidth(Integer maxWidth){
        try{
            if(maxWidth!=null)
                if(!input.getIsIndexValid() && (maxWidth <= 0 || maxWidth > MAX_WIDTH)){
                    output.createIndexWarning();
                }
        }catch (NullPointerException e){
            output.createInvalidMaxWidthWarning();
        }

    }

    private void validateParagraphIndex(boolean printError){
        if(printError){
            output.createIndexWarning();
        }
    }

    /**
     * Checks if the provided paragraph index is valid for the current text size.
     * Displays a warning message if the index is out of bounds and returns false.
     *

     * @return {@code true} if the index is valid; otherwise, {@code false}.
     */


    public void isTextEmpty(){
        if(textManager.getText().isEmpty()){
            output.createEmptyTextWarning();
        }

    }

    public void replace(Integer paragraphIndex, boolean isIndexNull ){
        System.out.print("Replacing Word: ");
        String originalWord = input.readAndFilterUserInput();
        if(textManager.containsWord( originalWord,paragraphIndex)){
            System.out.print("Replacing with: ");
            String replacingWord = input.readAndFilterUserInput();
            textManager.replaceParagraphSection(isIndexNull,originalWord, replacingWord,paragraphIndex);
        }else{
            validateParagraphIndex(input.getIsIndexValid());
        }
    }

    public void add(Integer paragraphIndex, boolean isIndexNull){
        if(input.getIsIndexValid()){
            System.out.print("Enter a Text you want to add:");
            String enteredText = input.readAndFilterUserInput();
            textManager.addNewParagraph(isIndexNull,enteredText, paragraphIndex);
        }else{
            validateParagraphIndex(input.getIsIndexValid());
        }
    }




    /**
     * Processes and executes text editing commands based on user input.
     * Parses the input, updates the text manager, and performs operations according to the command received.
     * Displays corresponding messages or triggers actions such as adding, deleting, replacing, formatting, or printing text.
     */
    public void editText() {
        System.out.println("-----------------------------------------------------------");
        textManager.setValidationFailed(false);
        input.resetValues();
        input.parseUserInput();
        Integer paragraphIndex = input.getUserIndex();
        Integer maxWidth = input.getUserIndex();



        boolean isIndexNull = input.isIndexNull();
        switch (Command.parseCommand(input.getUserCommand())) {
            case DUMMY:
                validateParagraphIndex(!input.getIsIndexValid());
                textManager.addDummyParagraph(isIndexNull, paragraphIndex);
                break;
            case EXIT:
                output.createExitMessage();
                isExitTriggered = true;
                break;
            case ADD:
                validateParagraphIndex(!input.getIsIndexValid());
                add(paragraphIndex, isIndexNull);
                break;
            case DEL:
                isTextEmpty();
                validateParagraphIndex(!input.getIsIndexValid());
                textManager.deleteParagraph(isIndexNull, paragraphIndex);
                break;
            case INDEX:
                glossary.printGlossary(textManager.getText());
                break;
            case PRINT:
                isTextEmpty();
                textManager.printText();
                break;
            case REPLACE:
                isTextEmpty();
                validateParagraphIndex(!input.getIsIndexValid());
                replace(paragraphIndex, isIndexNull);
                break;
            case HELP:
                output.createHelpMessage();
                break;
            case FORMAT_RAW:
                textManager.setIsFormatterRaw(true);
                textManager.formatTextRaw();
                output.createFormatMessage(true);
                break;
            case FORMAT_FIX:
                textManager.setIsFormatterRaw(false);
                validateMaxWidth(maxWidth);
                textManager.setMaxWidth(maxWidth);
                textManager.formatTextFix();
                output.createFormatMessage(textManager.getIsFormatterFixSuccessful());
                break;
            default:
                output.createInvalidCommandMessage();
                break;
        }



    }

}
