package com.texteditor.app;

/**
 * Enum class for all commands, which are used in the application.
 * The enum class contains a constructor, a getter, a method to check if the
 * input is a command,
 * a method to get all commands, and a method to get the command as a String.
 */
public enum CommandApp {

    /**
     * The ADD command, which requires an index.
     */
    ADD("add", true),

    /**
     * The DUMMY command, which requires an index.
     */
    DUMMY("dummy", true),

    /**
     * The DEL command, which requires an index.
     */
    DEL("del", true),

    /**
     * The REPLACE command, which requires an index.
     */
    REPLACE("replace", true),

    /**
     * The INDEX command, which does not require an index.
     */
    INDEX("index", false),

    /**
     * The FORMAT_RAW command, which does not require an index.
     */
    FORMAT_RAW("format raw", false),

    /**
     * The FORMAT_FIX command, which requires an index.
     */
    FORMAT_FIX("format fix", true),

    /**
     * The PRINT command, which does not require an index.
     */
    PRINT("print", false),

    /**
     * The HELP command, which does not require an index.
     */
    HELP("help", false),

    /**
     * The EXIT command, which does not require an index.
     */
    EXIT("exit", false),

    /**
     * The UNKNOWN command, which does not require an index. This is used as a
     * default for unrecognized commands.
     */
    UNKNOWN("unknown", false);

    private final String command;
    private final boolean commandHasIndex;

    /**
     * Constructor for the enum class.
     *
     * @param command         command as a String
     * @param commandHasIndex commandHasIndex as a boolean
     */
    private CommandApp(String command, boolean commandHasIndex) {
        this.command = command;
        this.commandHasIndex = commandHasIndex;
    }

    /**
     * Takes a command as a String and checks if it is an existing command.
     *
     * @param commandString to be checked
     * @return returns a Command if existing, otherwise UNKNOWN
     */
    public static CommandApp parseCommand(String commandString) {
        for (CommandApp value : CommandApp.values()) {
            if (value.getCommand().equals(commandString.toLowerCase())) {
                return value;
            }
        }
        return CommandApp.UNKNOWN;
    }

    /**
     * Gets all commands as a concatenation of a String.
     *
     * @return all commands as a String
     */
    public static String getAllCommands() {
        StringBuilder sb = new StringBuilder();
        for (CommandApp command : CommandApp.values()) {
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