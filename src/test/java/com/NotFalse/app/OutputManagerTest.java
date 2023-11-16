package com.NotFalse.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputManagerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        Logger.getLogger(OutputManager.class.getName()).setLevel(Level.OFF);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        Logger.getLogger(OutputManager.class.getName()).setLevel(Level.ALL);
    }

    @Test
    public void testCreateUserInfoMessage() {
        OutputManager outputManager = new OutputManager();
        outputManager.createUserInfoMessage("Test Info Message");
        assertEquals("Test Info Message", outContent.toString().trim());
    }

    @Test
    public void testCreateUserErrorMessage() {
        OutputManager outputManager = new OutputManager();
        outputManager.createUserErrorMessage("Test Error Message");
        assertEquals("Test Error Message", errContent.toString().trim());
    }

    @Test
    public void testCreateWelcomeMessage() {
        OutputManager outputManager = new OutputManager();
        outputManager.createWelcomeMessage();
        assertEquals("Welcome to the TextEditor! Created by NotFalse.", outContent.toString().trim());
    }


    @Test
    public void testCreateExitMessage() {
        OutputManager outputManager = new OutputManager();
        outputManager.createExitMessage();
        assertEquals("Exiting TextEditor...\n" +
                "Thank you for using TextEditor! Created by NotFalse.", outContent.toString().trim());
    }

    @Test
    public void testCreateAddMessage() {
        OutputManager outputManager = new OutputManager();
        outputManager.createAddMessage(true);
        assertEquals("Text has been added", outContent.toString().trim());
    }

    @Test
    public void testCreateDeleteMessage() {
        OutputManager outputManager = new OutputManager();
        outputManager.createDeleteMessage(true);
        assertEquals("Text deleted successfully!", outContent.toString().trim());
    }

    @Test
    public void testCreateDummyMessage() {
        OutputManager outputManager = new OutputManager();
        outputManager.createDummyMessage(true);
        assertEquals("Dummy text generated successfully!", outContent.toString().trim());
    }

    @Test
    public void testCreateIndexMessage() {
        OutputManager outputManager = new OutputManager();
        outputManager.createIndexMessage(true);
        assertEquals("Index generated successfully!", outContent.toString().trim());
    }

    @Test
    public void testCreatePrintMessage() {
        OutputManager outputManager = new OutputManager();
        outputManager.createPrintMessage(true);
        assertEquals("Printing text...", outContent.toString().trim());
    }

    @Test
    public void testCreateReplaceMessage() {
        OutputManager outputManager = new OutputManager();
        outputManager.createReplaceMessage(true);
        assertEquals("Text replaced successfully!", outContent.toString().trim());
    }

}
