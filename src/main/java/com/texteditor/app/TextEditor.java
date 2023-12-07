package com.texteditor.app;

/**
 * The TextEditor class encapsulates the main functionality of a text editing
 * application. It manages user input, text processing, and output display.
 * The class facilitates adding, deleting, replacing, formatting and printing
 * text, as well as managing a glossary of terms within the text.
 */
public class TextEditor {

    private final InputManager input;
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
        input = new InputManager();
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
        System.out.println("——————————————————————————————————————————————————————————————————————————————————————");
        input.resetValues();
        input.parseInput();
        Integer paragraphIndex = input.getUserIndex();
        Integer maxWidth = input.getUserIndex();
        boolean isIndexNull = input.isIndexNull();
        boolean isExecutionSuccessful = false;

        switch (CommandApp.parseCommand(input.getUserCommand())) {
            case ADD:
                executeAddFunction(paragraphIndex, isIndexNull, isExecutionSuccessful);
                break;
            case DUMMY:
                executeDummyFunction(paragraphIndex, isIndexNull, isExecutionSuccessful);
                break;
            case DEL:
                executeDeleteFunction(paragraphIndex, isIndexNull, isExecutionSuccessful);
                break;
            case REPLACE:
                executeReplaceFunction(paragraphIndex, isIndexNull, isExecutionSuccessful);
                break;
            case INDEX:
                executeIndexFunction();
                break;
            case FORMAT_RAW:
                executeFormatRawFunction(isExecutionSuccessful);
                break;
            case FORMAT_FIX:
                executeFormatFixFunction(maxWidth, isExecutionSuccessful);
                break;
            case PRINT:
                executePrintFunction();
                break;
            case HELP:
                output.createHelpMessage();
                break;
            case EXIT:
                output.createExitMessage();
                isExitTriggered = true;
                break;
            default:
                output.createInvalidCommandMessage();
                break;
        }
    }

    /**
     * Validates the maximum width of the text editor.
     *
     * @param maxWidth The maximum width to validate. This should be a positive
     *                 integer and less than or equal to MAX_WIDTH.
     * @return Returns true if the maximum width and index are valid, false
     * otherwise.
     */
    boolean isMaxWidthValid(Integer maxWidth) {
        boolean isWidthValid = input.getIsIndexValid();
        if (isWidthValid && maxWidth == null) {
            output.createInvalidMaxWidthWarning();
            return false;
        }
        if (!isWidthValid || maxWidth <= 0) {
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
     * @param isAddFunction  A boolean indicating whether the add function is called
     *                       or not.
     * @return Returns true if the index is valid, false otherwise.
     */
    boolean isParagraphIndexValid(Integer paragraphIndex, boolean isAddFunction) {
        boolean isIndexValid = input.getIsIndexValid();
        int textSize = textManager.getText().size();
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
     * Validates the characters in the text.
     * Displays a warning message if the characters are invalid.
     *
     * @param isCharacterValid A boolean indicating whether the characters are valid
     *                         or not.
     */
    void validateCharacters(boolean isCharacterValid) {
        if (!isCharacterValid) {
            output.createDisallowedCharacterWarning();
        }
    }

    /**
     * Checks if the text is empty.
     * Displays a warning message if the text is empty and returns false.
     *
     * @return Returns true if the text is not empty, false otherwise.
     */
    boolean isTextNotEmpty() {
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
    boolean isGlossaryEmpty() {
        glossary.rebuildGlossary(textManager.getText());
        if (glossary.isGlossaryEmpty()) {
            output.createEmptyGlossaryWarning();
            return true;
        }
        return false;
    }

    /**
     * Replaces a word in the text.
     * Displays a warning message if the word is not found and returns false.
     *
     * @param paragraphIndex        The paragraph index to replace the word in.
     * @param isIndexNull           A boolean indicating whether the index is null
     *                              or not.
     * @param isExecutionSuccessful A boolean indicating whether the execution was
     *                              successful or not.
     */
    void executeReplaceFunction(Integer paragraphIndex, boolean isIndexNull, boolean isExecutionSuccessful) {
        if (isParagraphIndexValid(paragraphIndex, false) && isTextNotEmpty()) {
            OutputManager.logAndPrintInfoMessage("Replacing Section: ");
            String originalSection = input.readAndFilterUserInput();
            if (textManager.containsWord(originalSection, paragraphIndex)) {
                OutputManager.logAndPrintInfoMessage("Replacing with: ");
                String replacingText = input.readAndFilterUserInput();
                validateCharacters(input.getIsCharacterValid());
                isExecutionSuccessful = textManager.replaceParagraphSection(isIndexNull, originalSection, replacingText,
                        paragraphIndex);
            } else {
                output.createInvalidSectionWarning();
            }
        }
        output.createReplaceMessage(isExecutionSuccessful);
    }

    /**
     * Adds a new paragraph to the text.
     * Displays a warning message if the paragraph index is out of bounds and
     * returns
     * false.
     *
     * @param paragraphIndex        The paragraph index to add the new paragraph to.
     * @param isIndexNull           A boolean indicating whether the index is null
     *                              or not.
     * @param isExecutionSuccessful A boolean indicating whether the execution was
     *                              successful or not.
     */
    void executeAddFunction(Integer paragraphIndex, boolean isIndexNull, boolean isExecutionSuccessful) {
        if (isParagraphIndexValid(paragraphIndex, true)) {
            OutputManager.logAndPrintInfoMessage("Enter a Text you want to add:");
            String enteredText = input.readAndFilterUserInput();
            validateCharacters(input.getIsCharacterValid());
            isExecutionSuccessful = textManager.addNewParagraph(isIndexNull, enteredText, paragraphIndex);
        }
        output.createAddMessage(isExecutionSuccessful);
    }

    /**
     * Deletes a paragraph from the text.
     * Displays a warning message if the paragraph index is out of bounds and
     * returns
     * false.
     *
     * @param paragraphIndex        The paragraph index to delete.
     * @param isIndexNull           A boolean indicating whether the index is null
     *                              or not.
     * @param isExecutionSuccessful A boolean indicating whether the execution was
     *                              successful or not.
     */
    void executeDeleteFunction(Integer paragraphIndex, boolean isIndexNull, boolean isExecutionSuccessful) {
        if (isParagraphIndexValid(paragraphIndex, false) && isTextNotEmpty()) {
            isExecutionSuccessful = textManager.deleteParagraph(isIndexNull, paragraphIndex);
        }
        output.createDeleteMessage(isExecutionSuccessful);
    }

    /**
     * Adds a dummy paragraph to the text.
     * Displays a warning message if the paragraph index is out of bounds and
     * returns
     * false.
     *
     * @param paragraphIndex        The paragraph index to add the dummy paragraph
     *                              to.
     * @param isIndexNull           A boolean indicating whether the index is null
     *                              or not.
     * @param isExecutionSuccessful A boolean indicating whether the execution was
     *                              successful or not.
     */
    void executeDummyFunction(Integer paragraphIndex, boolean isIndexNull, boolean isExecutionSuccessful) {
        if (isParagraphIndexValid(paragraphIndex, true)) {
            isExecutionSuccessful = textManager.addDummyParagraph(isIndexNull, paragraphIndex);
        }
        output.createAddMessage(isExecutionSuccessful);
    }

    /**
     * Prints the text.
     * Displays a warning message if the text is empty.
     */
    void executePrintFunction() {
        if (isTextNotEmpty()) {
            textManager.printText();
        }
    }

    /**
     * Prints the glossary.
     * Displays a warning message if the glossary is empty.
     */
    void executeIndexFunction() {
        if (!isGlossaryEmpty()) {
            OutputManager.logAndPrintInfoMessage("Glossary:");
            glossary.printGlossary(textManager.getText());
        }
    }

    /**
     * Formats the text using the fix formatter.
     * Displays a warning message if the maximum width is invalid.
     *
     * @param maxWidth              The maximum width to format the text to.
     * @param isExecutionSuccessful A boolean indicating whether the execution was
     *                              successful or not.
     */
    void executeFormatFixFunction(Integer maxWidth, boolean isExecutionSuccessful) {
        if (isMaxWidthValid(maxWidth) && isTextNotEmpty()) {
            textManager.setIsFormatterRaw(false);
            textManager.setMaxWidth(maxWidth);
            isExecutionSuccessful = textManager.formatTextFix();
        }
        output.createFormatMessage(isExecutionSuccessful);
    }

    /**
     * Formats the text using the raw formatter.
     *
     * @param isExecutionSuccessful A boolean indicating whether the execution was
     *                              successful or not.
     */
    void executeFormatRawFunction(boolean isExecutionSuccessful) {
        if (isTextNotEmpty()) {
            textManager.setIsFormatterRaw(true);
            isExecutionSuccessful = textManager.formatTextRaw();
        }
        output.createFormatMessage(isExecutionSuccessful);
    }

}
