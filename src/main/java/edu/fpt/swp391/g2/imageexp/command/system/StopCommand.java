package edu.fpt.swp391.g2.imageexp.command.system;

import edu.fpt.swp391.g2.imageexp.command.Command;

/**
 * The command to stop the server
 */
public class StopCommand extends Command {
    public StopCommand() {
        super("stop");
    }

    @Override
    public void runCommand(String argument) {
        getInstance().setShuttingDown(true);
        getLogger().info("Shutting down!");
        getInstance().shutdown();
    }

    @Override
    public String getDescription() {
        return "Stop the server";
    }
}
