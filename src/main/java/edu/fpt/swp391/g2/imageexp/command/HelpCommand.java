package edu.fpt.swp391.g2.imageexp.command;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void runCommand(String argument) {
        getLogger().info("Available commands: ");
        int usageLength = 0;
        int descLength = 0;
        for (Command command : getInstance().getCommandManager().getCommands()) {
            usageLength = Math.max(usageLength, command.getUsage().length());
            descLength = Math.max(descLength, command.getDescription().length());
        }
        String format = "%" + usageLength + "s\t%-" + descLength + "s";
        for (Command command : getInstance().getCommandManager().getCommands()) {
            getLogger().info(() -> String.format(format, command.getUsage(), command.getDescription()));
        }
    }

    @Override
    public String getDescription() {
        return "Help Command";
    }
}
