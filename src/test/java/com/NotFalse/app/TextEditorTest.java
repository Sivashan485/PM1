package com.NotFalse.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextEditorTest {

    private TextEditor textEditor;
    private InputReceiver input;
    private TextManager textManager;
    private OutputManager output;
    private GlossaryApp glossary;

    @BeforeEach
    void setUp() {
        input = new InputReceiver();
        textManager = new TextManager();
        output = new OutputManager();
        glossary = new GlossaryApp();
        textEditor = new TextEditor();
    }
    @Test
    void testValidateMaxWidthWithValidWidth() {
        assertTrue(textEditor.validateMaxWidth(10, true));
    }

    @Test
    void testValidateMaxWidthWithInvalidWidth() {
        assertFalse(textEditor.validateMaxWidth(-1, true));
    }

    @Test
    void testValidateMaxWidthWithNullWidth() {
        assertFalse(textEditor.validateMaxWidth(null, true));
    }

    @Test
    void testValidateMaxWidthWithInvalidIndex() {
        assertFalse(textEditor.validateMaxWidth(10, false));
    }

    @Test
    void testValidateParagraphIndexWithValidIndex() {
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertTrue(textEditor.validateParagraphIndex(1, textManager.getText().size(), true, false));
    }

    @Test
    void testValidateParagraphIndexWithInvalidIndex() {
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertFalse(textEditor.validateParagraphIndex(3, textManager.getText().size(), true, false));
    }

    @Test
    void testValidateParagraphIndexWithNullIndex() {
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertTrue(textEditor.validateParagraphIndex(null, textManager.getText().size(), true, true));
    }

    @Test
    void testValidateParagraphIndexWithInvalidIndexValidity() {
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertFalse(textEditor.validateParagraphIndex(1, textManager.getText().size(), false, false));
    }




}
