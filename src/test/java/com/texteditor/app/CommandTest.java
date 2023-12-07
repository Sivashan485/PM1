package com.texteditor.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CommandTest {

    @Test
    void testGetAllCommands() {
        String expected = "add, dummy, del, replace, index, format raw, format fix, print, help, exit"; // Replace with
                                                                                                        // actual
                                                                                                        // command names
        String actual = CommandApp.getAllCommands();
        assertEquals(expected, actual);
    }

    @Test
    void testCommandExit() {
        CommandApp command = CommandApp.EXIT;
        assertEquals("exit", command.getCommand());
        assertFalse(command.getCommandHasIndex());
        assertEquals("exit", command.getCommand());
        assertEquals("EXIT", command.toString());
        assertNotEquals("exitsufix", command.getCommand());
        assertNotEquals("prefixexit", command.getCommand());
    }

    @Test
    void testCommandAdd() {
        CommandApp command = CommandApp.ADD;
        assertEquals("add", command.getCommand());
        assertTrue(command.getCommandHasIndex());
        assertEquals("add", command.getCommand());
        assertEquals("ADD", command.toString());
        assertNotEquals("addsufix", command.getCommand());
        assertNotEquals("prefixadd", command.getCommand());
    }

    @Test
    void testCommandDel() {
        CommandApp command = CommandApp.DEL;
        assertEquals("del", command.getCommand());
        assertTrue(command.getCommandHasIndex());
        assertEquals("del", command.getCommand());
        assertEquals("DEL", command.toString());
        assertNotEquals("delsufix", command.getCommand());
        assertNotEquals("prefixdel", command.getCommand());
    }

    @Test
    void testCommandDummy() {
        CommandApp command = CommandApp.DUMMY;
        assertEquals("dummy", command.getCommand());
        assertTrue(command.getCommandHasIndex());
        assertEquals("dummy", command.getCommand());
        assertEquals("DUMMY", command.toString());
        assertNotEquals("dummysufix", command.getCommand());
        assertNotEquals("prefixdummy", command.getCommand());
    }

    @Test
    void testCommandIndex() {
        CommandApp command = CommandApp.INDEX;
        assertEquals("index", command.getCommand());
        assertFalse(command.getCommandHasIndex());
        assertEquals("index", command.getCommand());
        assertEquals("INDEX", command.toString());
        assertNotEquals("indexsufix", command.getCommand());
        assertNotEquals("prefixindex", command.getCommand());
    }

    @Test
    void testCommandPrint() {
        CommandApp command = CommandApp.PRINT;
        assertEquals("print", command.getCommand());
        assertFalse(command.getCommandHasIndex());
        assertEquals("print", command.getCommand());
        assertEquals("PRINT", command.toString());
        assertNotEquals("printsufix", command.getCommand());
        assertNotEquals("prefixprint", command.getCommand());
    }

    @Test
    void testCommandReplace() {
        CommandApp command = CommandApp.REPLACE;
        assertEquals("replace", command.getCommand());
        assertTrue(command.getCommandHasIndex());
        assertEquals("replace", command.getCommand());
        assertEquals("REPLACE", command.toString());
        assertNotEquals("replacesufix", command.getCommand());
        assertNotEquals("prefixreplace", command.getCommand());
    }

    @Test
    void testCommandHelp() {
        CommandApp command = CommandApp.HELP;
        assertEquals("help", command.getCommand());
        assertFalse(command.getCommandHasIndex());
        assertEquals("help", command.getCommand());
        assertEquals("HELP", command.toString());
        assertNotEquals("helpsufix", command.getCommand());
        assertNotEquals("prefixhelp", command.getCommand());
    }

    @Test
    void testCommandFormatRaw() {
        CommandApp command = CommandApp.FORMAT_RAW;
        assertEquals("format raw", command.getCommand());
        assertFalse(command.getCommandHasIndex());
        assertEquals("format raw", command.getCommand());
        assertEquals("FORMAT_RAW", command.toString());
        assertNotEquals("format rawsufix", command.getCommand());
        assertNotEquals("prefixformat raw", command.getCommand());
    }

    @Test
    void testCommandFormatFix() {
        CommandApp command = CommandApp.FORMAT_FIX;
        assertEquals("format fix", command.getCommand());
        assertTrue(command.getCommandHasIndex());
        assertEquals("format fix", command.getCommand());
        assertEquals("FORMAT_FIX", command.toString());
        assertNotEquals("formatsufix fixsufix", command.getCommand());
        assertNotEquals("prefixformat prefixfix", command.getCommand());
    }
}