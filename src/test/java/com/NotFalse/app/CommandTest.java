package com.NotFalse.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    @Test
    public void testGetCommand() {
        assertEquals("exit", Command.EXIT.getCommand());
        assertEquals("add", Command.ADD.getCommand());
        assertEquals("del", Command.DEL.getCommand());
        assertEquals("dummy", Command.DUMMY.getCommand());
        assertEquals("index", Command.INDEX.getCommand());
        assertEquals("print", Command.PRINT.getCommand());
        assertEquals("replace", Command.REPLACE.getCommand());
        assertEquals("help", Command.HELP.getCommand());
        assertEquals("format raw", Command.FORMAT_RAW.getCommand());
        assertEquals("format fix", Command.FORMAT_FIX.getCommand());
        assertEquals("unknown", Command.UNKNOWN.getCommand());
    }

    @Test
    public void testGetAllCommands() {
        assertEquals("exit, add, del, dummy, index, print, " +
                "replace, help, format raw, format fix", Command.getAllCommands());
    }

    @Test
    public void testKnownCommand() {
        assertEquals(Command.HELP, Command.parseCommand("Help"));
    }

    @Test
    public void testUnknownCommand() {
        assertEquals(Command.UNKNOWN, Command.parseCommand("nonExistentCommand"));
    }

    /*
     * ->> fix bug -> this test is not working
     * 
     * @Test
     * public void testNullInput() {
     * Commands.lookupCommand(null);
     * }
     */

    @Test
    public void testCaseSensitivity() {
        assertEquals(Command.EXIT, Command.parseCommand("EXIT"));
        assertEquals(Command.EXIT, Command.parseCommand("exit"));
    }

    @Test
    public void testEmptyString() {
        assertEquals(Command.UNKNOWN, Command.parseCommand(""));
    }

}
