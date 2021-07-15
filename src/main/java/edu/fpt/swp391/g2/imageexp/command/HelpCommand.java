package edu.fpt.swp391.g2.imageexp.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The help command, list all registered commands
 */
public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void runCommand(String argument) {
        getLogger().info("Available commands: ");
        int usageLength = 0;
        int descLength = 0;
        List<Command> commands = new ArrayList<>(getInstance().getCommandManager().getCommands());
        commands.sort(Comparator.comparing(Command::getName));
        for (Command command : commands) {
            usageLength = Math.max(usageLength, command.getUsage().length());
            descLength = Math.max(descLength, command.getDescription().length());
        }
        String format = "%-" + usageLength + "s\t%-" + descLength + "s";
        for (Command command : commands) {
            getLogger().info(() -> String.format(format, command.getUsage(), command.getDescription()));
        }
    }

    @Override
    public String getDescription() {
        return "Help Command";
    }
}
