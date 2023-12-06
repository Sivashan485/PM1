package com.NotFalse.app;

/**
 * Main class for the TextEditor application.
 */
public class TextEditor {

    private static final int MAX_WIDTH = 2147483647;
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
        output.createWelcomeMessage();
        do {
            editText();
            isRunning = !isExitTriggered;
        } while (isRunning);
    }


    private void validateMaxWidth(Integer maxWidth,boolean isIndexValid){
        if(!isIndexValid){
            output.createIndexWarning();
        }
        try{
            if(maxWidth!=null)
                if(!input.getIsIndexValid() && (maxWidth <= 0 || maxWidth > MAX_WIDTH)){
                    output.createIndexWarning();
                }else{
                    output.createInvalidMaxWidthWarning();
                }
        }catch (NullPointerException e){

        }

    }

    private void displayIndexWarningIfInvalid(boolean printError){
        if(printError){
            output.createIndexWarning();
        }
    }
    public boolean validateParagraphIndex(Integer paragraphIndex, int textSize,boolean isIndexValid, boolean isAddFunction) {
        if(!isIndexValid){
            output.createIndexWarning();
            System.out.println("wrong index");
            return false;
        }else {
            System.out.println(textSize);
            if (isAddFunction){

                if (paragraphIndex <= 0 || paragraphIndex -1 > textSize) {
                    System.out.println("paragraphindex: " + paragraphIndex);
                    System.out.println("add warning");
                    output.createIndexWarning();
                    return false;
                }
            }else{
                if (paragraphIndex <= 0 || paragraphIndex -1 > textSize-1) {
                    System.out.println("del warning");
                    output.createIndexWarning();
                    return false;
                }
            }


        }
        return true;
    }


    /**
     * Checks if the provided paragraph index is valid for the current text size.
     * Displays a warning message if the index is out of bounds and returns false.
     *

     * @return {@code true} if the index is valid; otherwise, {@code false}.
     */

    public void checkForEmptyText(){
        if(textManager.isTextEmpty()){
            output.createEmptyTextWarning();
        }
    }

    public void checkForEmptyGlossary(){
        glossary.rebuildGlossary(textManager.getText());
        if(glossary.isGlossaryEmpty()){
            output.createEmptyGlossaryWarning();
        }else{
            System.out.println("Glossary:");
        }
    }

    public void replace(Integer paragraphIndex, boolean isIndexNull){
        System.out.print("Replacing Word: ");
        String originalWord = input.readAndFilterUserInput();
        if(textManager.containsWord( originalWord,paragraphIndex)){
            System.out.print("Replacing with: ");
            String replacingWord = input.readAndFilterUserInput();
            textManager.replaceParagraphSection(isIndexNull,originalWord, replacingWord,paragraphIndex);
        }
    }

    public void add(Integer paragraphIndex, boolean isIndexNull){
        System.out.print("Enter a Text you want to add:");
        String enteredText = input.readAndFilterUserInput();
        textManager.addNewParagraph(isIndexNull,enteredText, paragraphIndex);

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
        input.splitInputIntoCommandAndRestPart();
        Integer paragraphIndex = input.getUserIndex();
        Integer maxWidth = input.getUserIndex();
        int textSize = textManager.getText().size();
        boolean isIndexValid = input.getIsIndexValid();



        boolean isIndexNull = input.isIndexNull();
        switch (Command.parseCommand(input.getUserCommand())) {
            case DUMMY:
                //displayIndexWarningIfInvalid(!isIndexValid);
                if( validateParagraphIndex(paragraphIndex,textSize,isIndexValid,true)){
                    textManager.addDummyParagraph(isIndexNull, paragraphIndex);
                }
                break;
            case EXIT:
                output.createExitMessage();
                isExitTriggered = true;
                break;
            case ADD:
                //displayIndexWarningIfInvalid(!input.getIsIndexValid());
                if( validateParagraphIndex(paragraphIndex,textSize,isIndexValid, true)){
                    add(paragraphIndex, isIndexNull);
                }
                break;
            case DEL:
                checkForEmptyText();
                //displayIndexWarningIfInvalid(!input.getIsIndexValid());
                if (validateParagraphIndex(paragraphIndex,textSize, isIndexValid, false)){
                    textManager.deleteParagraph(isIndexNull, paragraphIndex);
                }
                break;
            case INDEX:
                checkForEmptyGlossary();
                glossary.printGlossary(textManager.getText());
                break;
            case PRINT:
                checkForEmptyText();
                textManager.printText();
                break;
            case REPLACE:
                checkForEmptyText();
                if (validateParagraphIndex(paragraphIndex,textSize, isIndexValid, false)){
                    replace(paragraphIndex, isIndexNull);
                }
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
                validateMaxWidth(maxWidth,isIndexValid);
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
