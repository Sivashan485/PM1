package com.texteditor.app;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextEditorTest {

    private TextEditor textEditor;
    private TextManager textManager;

    @BeforeEach
    void setUp() {
        textManager = new TextManager();
        textEditor = new TextEditor();
    }
    @Test
    void testValidateMaxWidthWithValidWidth() {
        assertTrue(textEditor.isMaxWidthValid(10, true));
    }

    @Test
    void testValidateMaxWidthWithInvalidWidth() {
        assertFalse(textEditor.isMaxWidthValid(-1, true));
    }

    @Test
    void testValidateMaxWidthWithNullWidth() {
        assertFalse(textEditor.isMaxWidthValid(null, true));
    }

    @Test
    void testValidateMaxWidthWithInvalidIndex() {
        assertFalse(textEditor.isMaxWidthValid(10, false));
    }

    @Test
    void testValidateParagraphIndexWithValidIndex() {
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertTrue(textEditor.isParagraphIndexValid(1, textManager.getText().size(), true, false));
    }

    @Test
    void testValidateParagraphIndexWithInvalidIndex() {
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertFalse(textEditor.isParagraphIndexValid(3, textManager.getText().size(), true, false));
    }

    @Test
    void testValidateParagraphIndexWithNullIndex() {
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertTrue(textEditor.isParagraphIndexValid(null, textManager.getText().size(), true, true));
    }

    @Test
    void testValidateParagraphIndexWithInvalidIndexValidity() {
        textManager.addNewParagraph(false, "Test paragraph", 1);
        assertFalse(textEditor.isParagraphIndexValid(1, textManager.getText().size(), false, false));
    }
}
