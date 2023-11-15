package com.NotFalse.app;

/**
 * Enum class for all commands, which are used in the application.
 */
public enum Commands {
    EXIT("exit"),
    ADD("add"),
    DEL("del"),
    DUMMY("dummy"),
    INDEX("index"),
    PRINT("print"),
    REPLACE("replace"),
    HELP("help"),
    FORMAT_RAW("format_raw"),
    FORMAT_FIX("format_fix"),
    UNKNOWN("unknown");

    public final String command;

    /**
     * Constructor for the enum class.
     *
     * @param command
     */
    Commands(String command) {
        this.command = command;
    }

    /**
     * Getter for the command, as a String.
     *
     * @return command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Checks if the input is a command.
     *
     * @param input
     * @return boolean
     */
    static boolean isCommand(String input) {
        for (Commands command : Commands.values()) {
            if (command.getCommand().equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static Commands getCommandsEnum(String command) {
        Commands[] commands = Commands.values();
        for (Commands value : commands) {
            if (value.getCommand().equals(command.toLowerCase())) {
                return value;
            }
        }
        return Commands.UNKNOWN;
    }

    /**
     * Checks if the input is a command, and if it is,
     * returns the command as a String.
     *
     * @return command
     */
    static String getAllCommands() {
        StringBuilder sb = new StringBuilder();
        for (Commands command : Commands.values()) {
            if(!command.getCommand().equals("unknown")) {
                sb.append(command.getCommand()).append(", ");
            }
        }
        // Remove the trailing comma and space
        return sb.substring(0, sb.length() - 2);

    }
}