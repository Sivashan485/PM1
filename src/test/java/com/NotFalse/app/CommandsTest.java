package com.NotFalse.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals("format_raw", Commands.FORMAT_RAW.getCommand());
        assertEquals("format_fix", Commands.FORMAT_FIX.getCommand());
        assertEquals("unknown", Commands.UNKNOWN.getCommand());
    }

      @Test
      public void testGetAllCommands() {
      assertEquals("exit, add, del, dummy, index, print, " +
      "replace, help, format_raw, format_fix", Commands.getAllCommands());
      }

}
