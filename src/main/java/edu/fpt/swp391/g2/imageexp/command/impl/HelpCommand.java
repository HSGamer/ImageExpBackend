package edu.fpt.swp391.g2.imageexp.command.impl;

import edu.fpt.swp391.g2.imageexp.command.Command;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void runCommand(String argument) {
        getLogger().info("Available commands: ");
        for (Command command : getInstance().getCommandManager().getCommands()) {
            getLogger().info("  " + command.getUsage());
        }
    }
}
