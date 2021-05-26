package edu.fpt.swp391.g2.imageexp.command;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super("reload");
    }

    @Override
    public void runCommand(String argument) {
        getInstance().reload();
        getLogger().info("Reloaded");
    }

    @Override
    public String getDescription() {
        return "Reload Command";
    }
}
