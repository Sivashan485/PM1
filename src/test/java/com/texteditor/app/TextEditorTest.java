package com.texteditor.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class TextEditorTest {

    private TextEditor textEditor;

    @BeforeEach
    void setUp() {
        textEditor = new TextEditor();
    }

    @Test
    void testIsParagraphIndexValid() {
        assertFalse(textEditor.isParagraphIndexValid(-1, false));
        assertFalse(textEditor.isParagraphIndexValid(0, false));
    }

    @Test
    void testIsMaxWidthValid() {
        assertFalse(textEditor.isMaxWidthValid(-1));
        assertFalse(textEditor.isMaxWidthValid(0));
    }
}