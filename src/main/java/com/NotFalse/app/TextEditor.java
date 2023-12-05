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
    private boolean isFormatterRaw;

    /**
     * Constructor for the TextEditor class.
     */
    public TextEditor() {
        input = new InputReceiver();
        textManager = new TextManager();
        output = new OutputManager();
        isExitTriggered = false;
        isFormatterRaw = true;
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



    private void replace(String originalWord, Integer paragraphIndex, boolean isIndexNull){


        if(textManager.containsWord( originalWord,paragraphIndex)){
            System.out.println("Replacing with: ");
            String replacingWord = input.readAndFilterUserInput();
            textManager.replaceParagraphSection(isIndexNull,originalWord, replacingWord,paragraphIndex);
        }

    }

    private boolean validateMaxWidth(Integer maxWidth){
        if (maxWidth == null) {
            output.createInvalidMaxWidthWarning();
            return false;
        }
        if (maxWidth <= 0 || maxWidth > MAX_WIDTH) {
            output.createIndexWarning();
            return false;
        } else{
            return true;
        }
    }

    public void setIsFormatterRaw(boolean status){
        this.isFormatterRaw = status;
    }

    /**
     * Checks if the provided paragraph index is valid for the current text size.
     * Displays a warning message if the index is out of bounds and returns false.
     *
     * @param paragraphIndex The index to be validated.
     * @param textSize       The size of the current text.
     * @return {@code true} if the index is valid; otherwise, {@code false}.
     */
    public boolean isIndexValid(Integer paragraphIndex, int textSize) {

        if(paragraphIndex!=null)
            if (paragraphIndex <= 0 || paragraphIndex > textSize) {
                output.createIndexWarning();
                return false;
            }else{
                return true;
            }
        return false;

    }

    public void isTextEmpty(){
        if(textManager.getText().isEmpty()){
            output.createEmptyTextWarning();
        }
    }



    /**
     * Processes and executes text editing commands based on user input.
     * Parses the input, updates the text manager, and performs operations according to the command received.
     * Displays corresponding messages or triggers actions such as adding, deleting, replacing, formatting, or printing text.
     */
    public void editText() {
        System.out.println("-----------------------------------------------------------");

        input.parseUserInput();
        Integer paragraphIndex;
        try{
             paragraphIndex = input.getUserIndex();
        }catch (NullPointerException e){
            System.out.println("hello");
        }


        isTextEmpty();
        switch (Command.parseCommand(input.getUserCommand())) {
            case DUMMY:
                textManager.addDummyParagraph(input.isIndexNull(), paragraphIndex);
                break;
            case EXIT:
                output.createExitMessage();
                isExitTriggered = true;
                break;
            case ADD:
                if(isIndexValid(paragraphIndex, textManager.getText().size())){
                    String enteredText = input.readAndFilterUserInput();
                    textManager.addNewParagraph(input.isIndexNull(),enteredText, paragraphIndex);
                }
                break;
            case DEL:
                if(isIndexValid(paragraphIndex, textManager.getText().size())){
                    textManager.deleteParagraph(input.isIndexNull(), paragraphIndex);
                }
                break;
            case INDEX:
                glossary.printGlossary(textManager.getText());
                break;
            case PRINT:
                textManager.printText(isFormatterRaw, paragraphIndex);
                break;
            case REPLACE:
                System.out.println(paragraphIndex);
                boolean indexIsValid = isIndexValid(paragraphIndex, textManager.getText().size());
                boolean isIndexNull = input.isIndexNull();
                if(indexIsValid){
                    System.out.print("Replacing Word: ");
                    String originalWord = input.readAndFilterUserInput();
                    replace(originalWord, paragraphIndex, isIndexNull);
                }
                break;
            case HELP:
                output.createHelpMessage();
                break;
            case FORMAT_RAW:
                setIsFormatterRaw(true);
                textManager.formatTextRaw();
                output.createFormatMessage(true);
                break;
            case FORMAT_FIX:
                setIsFormatterRaw(false);
                if (validateMaxWidth(paragraphIndex)){
                    textManager.formatTextFix(paragraphIndex);
                }
                break;
            default:
                output.createInvalidCommandMessage();
                break;
        }

    }

}
