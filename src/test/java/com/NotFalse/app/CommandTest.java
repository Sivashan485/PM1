package com.NotFalse.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void testParseCommand() {
        String[] testItems = {"exit", "add", "del", "dummy", "index", "print", "replace", "help", "format raw", "format fix", "unknown"};
        ApplicationCommand[] resultItems = {ApplicationCommand.EXIT, ApplicationCommand.ADD, ApplicationCommand.DEL, ApplicationCommand.DUMMY, ApplicationCommand.INDEX, ApplicationCommand.PRINT,
                ApplicationCommand.REPLACE, ApplicationCommand.HELP, ApplicationCommand.FORMAT_RAW, ApplicationCommand.FORMAT_FIX, ApplicationCommand.UNKNOWN};
        for (int i = 0; i < testItems.length; i++) {
            ApplicationCommand result = ApplicationCommand.parseCommand(testItems[i]);
            assertEquals(resultItems[i], result);
        }
    }

    @Test
    void testGetAllCommands() {

        String expected = "exit, add, del, dummy, index, print, replace, help, format raw, format fix"; // Replace with actual command names
        String actual = ApplicationCommand.getAllCommands();

        assertEquals(expected, actual);

    }

    @Test
    void testCommandExit() {
        ApplicationCommand command = ApplicationCommand.EXIT;
        assertEquals("exit", command.command);
        assertFalse(command.commandHasIndex);
        assertEquals("exit", command.getCommand());
        assertEquals("EXIT", command.toString());
        assertNotEquals("exitsufix", command.command);
        assertNotEquals("prefixexit", command.command);
    }

    @Test
    void testCommandAdd() {
        ApplicationCommand command = ApplicationCommand.ADD;
        assertEquals("add", command.command);
        assertTrue(command.commandHasIndex);
        assertEquals("add", command.getCommand());
        assertEquals("ADD", command.toString());
        assertNotEquals("addsufix", command.command);
        assertNotEquals("prefixadd", command.command);
    }

    @Test
    void testCommandDel() {
        ApplicationCommand command = ApplicationCommand.DEL;
        assertEquals("del", command.command);
        assertTrue(command.commandHasIndex);
        assertEquals("del", command.getCommand());
        assertEquals("DEL", command.toString());
        assertNotEquals("delsufix", command.command);
        assertNotEquals("prefixdel", command.command);
    }

    @Test
    void testCommandDummy() {
        ApplicationCommand command = ApplicationCommand.DUMMY;
        assertEquals("dummy", command.command);
        assertTrue(command.commandHasIndex);
        assertEquals("dummy", command.getCommand());
        assertEquals("DUMMY", command.toString());
        assertNotEquals("dummysufix", command.command);
        assertNotEquals("prefixdummy", command.command);
    }

    @Test
    void testCommandIndex() {
        ApplicationCommand command = ApplicationCommand.INDEX;
        assertEquals("index", command.command);
        assertFalse(command.commandHasIndex);
        assertEquals("index", command.getCommand());
        assertEquals("INDEX", command.toString());
        assertNotEquals("indexsufix", command.command);
        assertNotEquals("prefixindex", command.command);
    }

    @Test
    void testCommandPrint() {
        ApplicationCommand command = ApplicationCommand.PRINT;
        assertEquals("print", command.command);
        assertFalse(command.commandHasIndex);
        assertEquals("print", command.getCommand());
        assertEquals("PRINT", command.toString());
        assertNotEquals("printsufix", command.command);
        assertNotEquals("prefixprint", command.command);
    }

    @Test
    void testCommandReplace() {
        ApplicationCommand command = ApplicationCommand.REPLACE;
        assertEquals("replace", command.command);
        assertTrue(command.commandHasIndex);
        assertEquals("replace", command.getCommand());
        assertEquals("REPLACE", command.toString());
        assertNotEquals("replacesufix", command.command);
        assertNotEquals("prefixreplace", command.command);
    }

    @Test
    void testCommandHelp() {
        ApplicationCommand command = ApplicationCommand.HELP;
        assertEquals("help", command.command);
        assertFalse(command.commandHasIndex);
        assertEquals("help", command.getCommand());
        assertEquals("HELP", command.toString());
        assertNotEquals("helpsufix", command.command);
        assertNotEquals("prefixhelp", command.command);
    }

    @Test
    void testCommandFormatRaw() {
        ApplicationCommand command = ApplicationCommand.FORMAT_RAW;
        assertEquals("format raw", command.command);
        assertFalse(command.commandHasIndex);
        assertEquals("format raw", command.getCommand());
        assertEquals("FORMAT_RAW", command.toString());
        assertNotEquals("format rawsufix", command.command);
        assertNotEquals("prefixformat raw", command.command);
    }

    @Test
    void testCommandFormatFix() {
        ApplicationCommand command = ApplicationCommand.FORMAT_FIX;
        assertEquals("format fix", command.command);
        assertTrue(command.commandHasIndex);
        assertEquals("format fix", command.getCommand());
        assertEquals("FORMAT_FIX", command.toString());
        assertNotEquals("formatsufix fixsufix", command.command);
        assertNotEquals("prefixformat prefixfix", command.command);
    }

}