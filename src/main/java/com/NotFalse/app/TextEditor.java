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



    private void replace(String originalWord, Integer paragraphIndex, boolean isIndexNull){


            if(textManager.containsWord( originalWord,paragraphIndex)){
                System.out.println("Replacing with: ");
                String replacingWord = input.readAndFilterUserInput();
                textManager.replaceParagraphSection(isIndexNull, paragraphIndex,originalWord, replacingWord);
            }

    }



    /**
     * Processes and executes text editing commands based on user input.
     * Parses the input, updates the text manager, and performs operations according to the command received.
     * Displays corresponding messages or triggers actions such as adding, deleting, replacing, formatting, or printing text.
     */
    public void editText() {
        System.out.println("-----------------------------------------------------------");

        input.splitInput();
        Integer widthIndex = input.getUserIndex();
        textManager.setParagraphIndex(input.getUserIndex());


        switch (Command.parseCommand(input.getUserCommand())) {
            case DUMMY:
                textManager.addDummyParagraph(input.isIndexNull(), widthIndex);
                break;
            case EXIT:
                output.createExitMessage();
                isExitTriggered = true;
                break;
            case ADD:
                String enteredText = input.readAndFilterUserInput();
                textManager.addNewParagraph(input.isIndexNull(),enteredText, widthIndex);
                //addText();
                break;
            case DEL:
                textManager.deleteParagraph(input.isIndexNull());
                break;
            case INDEX:
                glossary.printGlossary(textManager.getText());
                break;
            case PRINT:
                textManager.printText();
                break;
            case REPLACE:
                Integer paragraphIndex = input.getUserIndex();
                System.out.println(paragraphIndex);
                boolean indexIsValid = textManager.isIndexValid(paragraphIndex, textManager.getText().size());
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
                textManager.setIsFormatterRaw(true);
                textManager.formatTextRaw();
                output.createFormatMessage(true);
                break;
            case FORMAT_FIX:
                textManager.setIsFormatterRaw(false);
                textManager.setMaxWidth(widthIndex);
                textManager.formatTextFix();
                break;
            default:
                output.createInvalidCommandMessage();
                break;
        }

    }

}
