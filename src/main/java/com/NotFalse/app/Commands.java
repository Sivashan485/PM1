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
     * Takes a command as a String and checks if it is an existing command.
     *
     * @param command to be checked
     * @return returns a Command if existing, otherwise UNKNOWN
     */
    public static Commands lookupCommand(String command) {
        for (Commands value : Commands.values()) {
            if (value.getCommand().equals(command.toLowerCase())) {
                return value;
            }
        }
        return Commands.UNKNOWN;
    }

    /**
     * Gets all commands as a concatenation of Strings.
     *
     * @return all commands as Strings
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

    /**
     * Getter for the command, as a String.
     *
     * @return command
     */
    public String getCommand() {
        return command;
    }
}