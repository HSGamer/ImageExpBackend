package edu.fpt.swp391.g2.imageexp.command.impl;

import edu.fpt.swp391.g2.imageexp.command.Command;

public class StopCommand extends Command {
    public StopCommand() {
        super("stop");
    }

    @Override
    public void runCommand(String argument) {
        getInstance().setShuttingDown(true);
        getLogger().info("Shutting down!");
        getInstance().shutdown();
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "Stop Command";
    }
}
