package com.texteditor.app;

/**
 * Enum class for all commands, which are used in the application.
 * The enum class contains a constructor, a getter, a method to check if the
 * input is a command,
 * a method to get all commands, and a method to get the command as a String.
 */
public enum ApplicationCommand {
    EXIT("exit", false),
    ADD("add", true),
    DEL("del", true),
    DUMMY("dummy", true),
    INDEX("index", false),
    PRINT("print", false),
    REPLACE("replace", true),
    HELP("help", false),
    FORMAT_RAW("format raw", false),
    FORMAT_FIX("format fix", true),
    UNKNOWN("unknown", false);

    public final String command;
    public final boolean commandHasIndex;

    /**
     * Constructor for the enum class.
     *
     * @param command
     * @param commandHasIndex
     */
    ApplicationCommand(String command, boolean commandHasIndex) {
        this.command = command;
        this.commandHasIndex = commandHasIndex;
    }

    /**
     * Takes a command as a String and checks if it is an existing command.
     *
     * @param commandString to be checked
     * @return returns a Command if existing, otherwise UNKNOWN
     */
    public static ApplicationCommand parseCommand(String commandString) {
        for (ApplicationCommand value : ApplicationCommand.values()) {
            if (value.getCommand().equals(commandString.toLowerCase())) {
                return value;
            }
        }
        return ApplicationCommand.UNKNOWN;
    }

    /**
     * Gets all commands as a concatenation of a String.
     *
     * @return all commands as a String
     */
    public static String getAllCommands() {
        StringBuilder sb = new StringBuilder();
        for (ApplicationCommand command : ApplicationCommand.values()) {
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
     * Getter for the commandHasIndex, as a boolean.
     *
     * @return commandHasIndex
     */
    public boolean getCommandHasIndex() {
        return commandHasIndex;
    }
}