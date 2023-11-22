package com.NotFalse.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandsTest {

    @Test
    public void testGetCommand() {
        assertEquals("exit", Commands.EXIT.getCommand());
        assertEquals("add", Commands.ADD.getCommand());
        assertEquals("del", Commands.DEL.getCommand());
        assertEquals("dummy", Commands.DUMMY.getCommand());
        assertEquals("index", Commands.INDEX.getCommand());
        assertEquals("print", Commands.PRINT.getCommand());
        assertEquals("replace", Commands.REPLACE.getCommand());
        assertEquals("help", Commands.HELP.getCommand());
        assertEquals("format raw", Commands.FORMAT_RAW.getCommand());
        assertEquals("format fix", Commands.FORMAT_FIX.getCommand());
        assertEquals("unknown", Commands.UNKNOWN.getCommand());
    }

    @Test
    public void testGetAllCommands() {
        assertEquals("exit, add, del, dummy, index, print, " +
                "replace, help, format raw, format fix", Commands.getAllCommands());
    }

    @Test
    public void testKnownCommand() {
        assertEquals(Commands.HELP, Commands.lookupCommand("Help"));
    }

    @Test
    public void testUnknownCommand() {
        assertEquals(Commands.UNKNOWN, Commands.lookupCommand("nonExistentCommand"));
    }

    /* ->> fix bug -> this test is not working
    @Test
    public void testNullInput() {
        Commands.lookupCommand(null);
    }*/

    @Test
    public void testCaseSensitivity() {
        assertEquals(Commands.EXIT, Commands.lookupCommand("EXIT"));
        assertEquals(Commands.EXIT, Commands.lookupCommand("exit"));
    }

    @Test
    public void testEmptyString() {
        assertEquals(Commands.UNKNOWN, Commands.lookupCommand(""));
    }

}
