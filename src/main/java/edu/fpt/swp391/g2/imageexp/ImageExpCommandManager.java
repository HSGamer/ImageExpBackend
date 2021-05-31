package edu.fpt.swp391.g2.imageexp;

import edu.fpt.swp391.g2.imageexp.command.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The command manager, which stores all terminal commands
 */
public class ImageExpCommandManager {
    private final Map<String, Command> commands = new HashMap<>();

    public ImageExpCommandManager() {
        // Add commands here
        addCommand(new StopCommand());
        addCommand(new EchoCommand());
        addCommand(new ChangeTextCommand());
        addCommand(new HelpCommand());
        addCommand(new ReloadCommand());
        addCommand(new GetUserByIdCommand());
        addCommand(new LoginUserCommand());
        addCommand(new RegisterUserCommand());
    }

    /**
     * Add a command to the manager
     *
     * @param command the command
     */
    public void addCommand(Command command) {
        commands.put(command.getName(), command);
        if (command.getAliases() != null) {
            command.getAliases().forEach(s -> commands.put(s, command));
        }
    }

    /**
     * Call when disabling the service
     */
    public void disable() {
        commands.values().forEach(Command::disable);
        commands.clear();
    }

    /**
     * Call when a command is typed
     *
     * @param command  the command
     * @param argument the argument of the command, guaranteed to be not null
     * @return whether the command was found and run
     */
    public boolean handleCommand(String command, String argument) {
        if (commands.containsKey(command)) {
            commands.get(command).runCommand(argument);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the list of commands
     *
     * @return the commands
     */
    public Collection<Command> getCommands() {
        return commands.values();
    }
}
