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
     * @param args sdijsdf
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


    private boolean validateMaxWidth(Integer maxWidth,boolean isIndexValid){
        if(maxWidth == null && isIndexValid){
            output.createInvalidMaxWidthWarning();
            return false;
        }
        if(!isIndexValid){
            output.createIndexWarning();
            return false;
        }
        if(!input.getIsIndexValid() && (maxWidth <= 0 || maxWidth > MAX_WIDTH)){
            output.createIndexWarning();
            return false;
        }
        return true;
    }

    public boolean validateParagraphIndex(Integer paragraphIndex, int textSize,boolean isIndexValid, boolean isAddFunction) {
        if(paragraphIndex == null && isIndexValid){
            return true;
        }
        if(!isIndexValid){
            output.createIndexWarning();
            return false;
        }
        if(isAddFunction && (paragraphIndex <= 0 || paragraphIndex -1 > textSize)) {
                output.createIndexWarning();
                return false;

        }
        if(!isAddFunction && (paragraphIndex <= 0 || paragraphIndex -1 > textSize-1)) {
                output.createIndexWarning();
                return false;
        }
        return true;
    }


    /**
     * Checks if the provided paragraph index is valid for the current text size.
     * Displays a warning message if the index is out of bounds and returns false.
     *

     * @return {@code true} if the index is valid; otherwise, {@code false}.
     */

    public boolean isTextNotEmpty(){
        if(textManager.isTextEmpty()){
            output.createEmptyTextWarning();
            return false;
        }
        return true;
    }

    public void checkForEmptyGlossary(){
        glossary.rebuildGlossary(textManager.getText());
        if(glossary.isGlossaryEmpty()){
            output.createEmptyGlossaryWarning();
        }else{
            OutputManager.logAndPrintInfoMessage("Glossary:");

        }
    }

    public void replace(Integer paragraphIndex, boolean isIndexNull){
        OutputManager.logAndPrintInfoMessage("Replacing Word: ");
        String originalWord = input.readAndFilterUserInput();
        if(textManager.containsWord( originalWord,paragraphIndex)){
            OutputManager.logAndPrintInfoMessage("Replacing with: ");
            String replacingWord = input.readAndFilterUserInput();
            textManager.replaceParagraphSection(isIndexNull,originalWord, replacingWord,paragraphIndex);
        }
    }

    public void add(Integer paragraphIndex, boolean isIndexNull){
        output.logAndPrintInfoMessage("Enter a Text you want to add:");
        String enteredText = input.readAndFilterUserInput();
        textManager.addNewParagraph(isIndexNull,enteredText, paragraphIndex);

    }




    /**
     * Processes and executes text editing commands based on user input.
     * Parses the input, updates the text manager, and performs operations according to the command received.
     * Displays corresponding messages or triggers actions such as adding, deleting, replacing, formatting, or printing text.
     */
    public void editText() {
        OutputManager.logAndPrintInfoMessage("-----------------------------------------------------------");
        textManager.setValidationFailed(false);
        input.resetValues();
        input.parseInput();
        Integer paragraphIndex = input.getUserIndex();
        Integer maxWidth = input.getUserIndex();
        int textSize = textManager.getText().size();
        boolean isIndexValid = input.getIsIndexValid();
        boolean isIndexNull = input.isIndexNull();

        switch (ApplicationCommand.parseCommand(input.getUserCommand())) {
            case DUMMY:
                if( validateParagraphIndex(paragraphIndex,textSize,isIndexValid,true)){
                    textManager.addDummyParagraph(isIndexNull, paragraphIndex);
                } break;
            case EXIT:
                output.createExitMessage();
                isExitTriggered = true;
                break;
            case ADD:
                if( validateParagraphIndex(paragraphIndex,textSize,isIndexValid, true)){
                    add(paragraphIndex, isIndexNull);
                }
                break;
            case DEL:
                if(isTextNotEmpty()){
                    if (validateParagraphIndex(paragraphIndex,textSize, isIndexValid, false)){
                        textManager.deleteParagraph(isIndexNull, paragraphIndex);
                    }
                }
                break;
            case INDEX:
                checkForEmptyGlossary();
                glossary.printGlossary(textManager.getText());
                break;
            case PRINT:
                if(isTextNotEmpty()){
                    textManager.printText();
                }
                break;
            case REPLACE:
                if(isTextNotEmpty()){
                    if (validateParagraphIndex(paragraphIndex,textSize, isIndexValid, false)){
                        replace(paragraphIndex, isIndexNull);
                    }
                }
                break;
            case HELP:
                output.createHelpMessage();
                break;
            case FORMAT_RAW:
                textManager.setIsFormatterRaw(true);
                textManager.formatTextRaw();
                //output.createFormatMessage(true);
                break;
            case FORMAT_FIX:
                textManager.setIsFormatterRaw(false);
                if(validateMaxWidth(maxWidth,isIndexValid)){
                    textManager.setMaxWidth(maxWidth);
                    textManager.formatTextFix();
                }
                output.createFormatMessage(textManager.getIsFormatterFixSuccessful());
                break;
            default:
                output.createInvalidCommandMessage();
                break;
        }
    }
}
