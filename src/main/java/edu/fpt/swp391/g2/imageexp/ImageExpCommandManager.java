package edu.fpt.swp391.g2.imageexp;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.command.impl.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ImageExpCommandManager {
    private final Map<String, Command> commands = new HashMap<>();

    public ImageExpCommandManager() {
        addCommand(new StopCommand());
        addCommand(new EchoCommand());
        addCommand(new ChangeTextCommand());
        addCommand(new HelpCommand());
        addCommand(new ReloadCommand());
    }

    public void addCommand(Command command) {
        commands.put(command.getName(), command);
        if (command.getAliases() != null) {
            command.getAliases().forEach(s -> commands.put(s, command));
        }
    }

    public void disable() {
        commands.values().forEach(Command::disable);
        commands.clear();
    }

    public boolean handleCommand(String command, String argument) {
        if (commands.containsKey(command)) {
            commands.get(command).runCommand(argument);
            return true;
        } else {
            return false;
        }
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }
}
