package com.NotFalse.app;

/**
 * Enum class for all commands, which are used in the application.
 * The enum class contains a constructor, a getter, a method to check if the
 * input is a command,
 * a method to get all commands, and a method to get the command as a String.
 */
public enum Commands {
    EXIT("exit", null),
    ADD("add", 0),
    DEL("del", 0),
    DUMMY("dummy", 0),
    INDEX("index", null),
    PRINT("print", null),
    REPLACE("replace", 0),
    HELP("help", null),
    FORMAT_RAW("format raw", null),
    FORMAT_FIX("format fix", 0),
    UNKNOWN("unknown", null);

    public final String command;
    public final Integer index;

    /**
     * Constructor for the enum class.
     *
     * @param command
     */
    Commands(String command, Integer index) {
        this.command = command;
        this.index = index;
    }

    /**
     * Takes a command as a String and checks if it is an existing command.
     *
     * @param commandString to be checked
     * @return returns a Command if existing, otherwise UNKNOWN
     */
    public static Commands parseCommand(String commandString) {
        for (Commands value : Commands.values()) {
            if (value.getCommand().equals(commandString.toLowerCase())) {
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

    /**
     * Getter for the index.
     * 
     * @return index
     */
    public Integer getIndex() {
        return index;
    }
}