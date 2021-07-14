package edu.fpt.swp391.g2.imageexp.command.system;

import edu.fpt.swp391.g2.imageexp.command.Command;

/**
 * The command to reload the server
 */
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
        return "Reload the server";
    }
}
