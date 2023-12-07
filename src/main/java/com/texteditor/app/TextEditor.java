package com.texteditor.app;

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
     * Initializes the text manager, glossary, output manager, and input receiver.
     */
    public TextEditor() {
        textManager = new TextManager();
        glossary = new GlossaryApp();
        output = new OutputManager();
        input = new InputReceiver();
        isExitTriggered = false;
    }

    /**
     * This is the main method for the TextEditor application.
     * It creates an instance of TextEditor and starts the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        TextEditor textEditor = new TextEditor();
        textEditor.runTextEditor();
    }

    /**
     * This method starts and runs the TextEditor application.
     * It continues running until an exit condition is triggered.
     */
    private void runTextEditor() {
        boolean isRunning;
        output.createWelcomeMessage();
        do {
            editText();
            isRunning = !isExitTriggered;
        } while (isRunning);
    }

    /**
     * Processes and executes text editing commands based on user input.
     * Parses the input, updates the text manager, and performs operations according
     * to the command received.
     * Displays corresponding messages or triggers actions such as adding, deleting,
     * replacing, formatting, or printing text.
     */
    private void editText() {
        OutputManager.logAndPrintInfoMessage("________________________________________________\n️");
        input.resetValues();
        input.parseInput();
        Integer paragraphIndex = input.getUserIndex();
        Integer maxWidth = input.getUserIndex();
        int textSize = textManager.getText().size();
        boolean isIndexValid = input.getIsIndexValid();
        boolean executionSuccessful = false;
        boolean isIndexNull = input.isIndexNull();

        switch (ApplicationCommand.parseCommand(input.getUserCommand())) {
            case DUMMY:
                dummy(paragraphIndex, textSize, isIndexValid, isIndexNull);
                break;
            case EXIT:
                output.createExitMessage();
                isExitTriggered = true;
                break;
            case ADD:
                if (validateParagraphIndex(paragraphIndex, textSize, isIndexValid, true)) {
                    executionSuccessful = add(paragraphIndex, isIndexNull);
                }
                output.createAddMessage(executionSuccessful);
                break;
            case DEL:
                del(paragraphIndex, textSize, isIndexValid, isIndexNull);
                break;
            case INDEX:
                index();
                break;
            case PRINT:
                print();
                break;
            case REPLACE:
                if (isTextNotEmpty() && (validateParagraphIndex(paragraphIndex, textSize, isIndexValid, false))) {
                    executionSuccessful = replace(paragraphIndex, isIndexNull);
                }
                output.createReplaceMessage(executionSuccessful);
                break;
            case HELP:
                output.createHelpMessage();
                break;
            case FORMAT_RAW:
                textManager.setIsFormatterRaw(true);
                output.createFormatMessage(textManager.formatTextRaw());
                break;
            case FORMAT_FIX:
                fixFormatter(maxWidth, isIndexValid);
                break;
            default:
                output.createInvalidCommandMessage();
                break;
        }
    }

    /**
     * Validates the maximum width of the text editor.
     *
     * @param maxWidth     The maximum width to validate. This should be a positive
     *                     integer and less than or equal to MAX_WIDTH.
     * @param isIndexValid A boolean indicating whether the index is valid or not.
     * @return Returns true if the maximum width and index are valid, false
     *         otherwise.
     */
    boolean validateMaxWidth(Integer maxWidth, boolean isIndexValid) {
        if (maxWidth == null && isIndexValid) {
            output.createInvalidMaxWidthWarning();
            return false;
        }
        if (!isIndexValid) {
            output.createIndexWarning();
            return false;
        }
        if (!input.getIsIndexValid() && (maxWidth <= 0 || maxWidth > MAX_WIDTH)) {
            output.createIndexWarning();
            return false;
        }
        return true;
    }

    /**
     * Validates the paragraph index.
     * Displays a warning message if the index is out of bounds and returns false.
     *
     * @param paragraphIndex The paragraph index to validate. This should be a
     *                       positive integer and less than or equal to the text
     *                       size.
     * @param textSize       The size of the text.
     * @param isIndexValid   A boolean indicating whether the index is valid or not.
     * @param isAddFunction  A boolean indicating whether the add function is called
     *                       or not.
     * @return Returns true if the index is valid, false otherwise.
     */
    boolean validateParagraphIndex(Integer paragraphIndex, int textSize, boolean isIndexValid, boolean isAddFunction) {
        if (paragraphIndex == null && isIndexValid) {
            return true;
        }
        if (!isIndexValid) {
            output.createIndexWarning();
            return false;
        }
        if (isAddFunction && (paragraphIndex <= 0 || paragraphIndex - 1 > textSize)) {
            output.createIndexWarning();
            return false;

        }
        if (!isAddFunction && (paragraphIndex <= 0 || paragraphIndex - 1 > textSize - 1)) {
            output.createIndexWarning();
            return false;
        }
        return true;
    }

    /**
     * Checks if the text is empty.
     * Displays a warning message if the text is empty and returns false.
     *
     * @return Returns true if the text is not empty, false otherwise.
     */
    private boolean isTextNotEmpty() {
        if (textManager.isTextEmpty()) {
            output.createEmptyTextWarning();
            return false;
        }
        return true;
    }

    /**
     * Checks if the glossary is empty.
     * Displays a warning message if the glossary is empty and returns false.
     *
     * @return Returns true if the glossary is not empty, false otherwise.
     */
    private void checkForEmptyGlossary() {
        glossary.rebuildGlossary(textManager.getText());
        if (glossary.isGlossaryEmpty()) {
            output.createEmptyGlossaryWarning();
        } else {
            OutputManager.logAndPrintInfoMessage("Glossary:");
        }
    }

    /**
     * Replaces a word in the text.
     * Displays a warning message if the word is not found and returns false.
     *
     * @param paragraphIndex The paragraph index to replace the word in.
     * @param isIndexNull    A boolean indicating whether the index is null or not.
     * @return Returns true if the word is found and replaced, false otherwise.
     */
    private boolean replace(Integer paragraphIndex, boolean isIndexNull) {
        OutputManager.logAndPrintInfoMessage("Replacing Word: ");
        String originalWord = input.readAndFilterUserInput();
        if (textManager.containsWord(originalWord, paragraphIndex)) {
            OutputManager.logAndPrintInfoMessage("Replacing with: ");
            String replacingWord = input.readAndFilterUserInput();
            return textManager.replaceParagraphSection(isIndexNull, originalWord, replacingWord, paragraphIndex);
        } else {
            output.createInvalidWordWarning();
            return false;
        }
    }

    /**
     * Adds a new paragraph to the text.
     * Displays a warning message if the paragraph index is out of bounds and returns
     * false.
     *
     * @param paragraphIndex The paragraph index to add the new paragraph to.
     * @param isIndexNull    A boolean indicating whether the index is null or not.
     * @return Returns true if the paragraph index is valid and the paragraph is
     *         added, false otherwise.
     */
    private boolean add(Integer paragraphIndex, boolean isIndexNull) {
        OutputManager.logAndPrintInfoMessage("Enter a Text you want to add:");
        String enteredText = input.readAndFilterUserInput();
        return textManager.addNewParagraph(isIndexNull, enteredText, paragraphIndex);
    }

    /**
     * Deletes a paragraph from the text.
     * Displays a warning message if the paragraph index is out of bounds and returns
     * false.
     *
     * @param paragraphIndex The paragraph index to delete.
     * @param textSize       The size of the text.
     * @param isIndexValid   A boolean indicating whether the index is valid or not.
     * @param isIndexNull    A boolean indicating whether the index is null or not.
     */
    private void del(Integer paragraphIndex, int textSize, boolean isIndexValid, boolean isIndexNull) {
        boolean executionSuccessful = false;
        if (isTextNotEmpty() && (validateParagraphIndex(paragraphIndex, textSize, isIndexValid, false))) {
            executionSuccessful = textManager.deleteParagraph(isIndexNull, paragraphIndex);

        }
        output.createDeleteMessage(executionSuccessful);
    }

    /**
     * Adds a dummy paragraph to the text.
     * Displays a warning message if the paragraph index is out of bounds and returns
     * false.
     *
     * @param paragraphIndex The paragraph index to add the dummy paragraph to.
     * @param textSize       The size of the text.
     * @param isIndexValid   A boolean indicating whether the index is valid or not.
     * @param isIndexNull    A boolean indicating whether the index is null or not.
     */
    private void dummy(Integer paragraphIndex, int textSize, boolean isIndexValid, boolean isIndexNull) {
        boolean executionSuccessful = false;
        if (validateParagraphIndex(paragraphIndex, textSize, isIndexValid, true)) {
            executionSuccessful = textManager.addDummyParagraph(isIndexNull, paragraphIndex);
        }
        output.createAddMessage(executionSuccessful);
    }

    /**
     * Prints the text.
     * Displays a warning message if the text is empty.
     */
    private void print() {
        if (isTextNotEmpty()) {
            textManager.printText();
        }
    }

    /**
     * Prints the glossary.
     * Displays a warning message if the glossary is empty.
     */
    private void index() {
        checkForEmptyGlossary();
        glossary.printGlossary(textManager.getText());
    }

    /**
     * Formats the text using the fix formatter.
     * Displays a warning message if the maximum width is invalid.
     *
     * @param maxWidth     The maximum width to format the text to.
     * @param isIndexValid A boolean indicating whether the index is valid or not.
     */
    private void fixFormatter(Integer maxWidth, boolean isIndexValid) {
        textManager.setIsFormatterRaw(false);
        if (validateMaxWidth(maxWidth, isIndexValid)) {
            textManager.setMaxWidth(maxWidth);
            textManager.formatTextFix();
        }
        output.createFormatMessage(textManager.getIsFormatterFixSuccessful());
    }
}
