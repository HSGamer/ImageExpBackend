package edu.fpt.swp391.g2.imageexp.command.misc;

import edu.fpt.swp391.g2.imageexp.command.Command;

public class EchoCommand extends Command {
    public EchoCommand() {
        super("echo");
    }

    @Override
    public void runCommand(String argument) {
        getLogger().info(() -> "Echo: " + argument);
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <text>";
    }
}
