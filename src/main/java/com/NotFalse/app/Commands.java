package com.NotFalse.app;

/**
 * Enum class for all commands, which are used in the application.
 * The enum class contains a constructor, a getter, a method to check if the input is a command,
 * a method to get all commands, and a method to get the command as a String.
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
     * Checks if the input is a command. If it is, the method returns true.
     * Otherwise, the method returns false.
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

    //is this method really necessary?

    /**
     * Returns the command as a String. If the command is not found,
     * the method returns "unknown".
     *
     * @param command
     * @return
     */
    public static Commands lookupCommand(String command) {
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
            if (!command.getCommand().equals("unknown")) {
                sb.append(command.getCommand()).append(", ");
            }

        }
        // Remove the trailing comma and space
        return sb.substring(0, sb.length() - 2);

    }
}