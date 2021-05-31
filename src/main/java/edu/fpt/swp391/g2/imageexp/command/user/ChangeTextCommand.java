package edu.fpt.swp391.g2.imageexp.command.user;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.server.handler.misc.ChangeableTextHandler;

public class ChangeTextCommand extends Command {
    public ChangeTextCommand() {
        super("change-text");
    }

    @Override
    public void runCommand(String argument) {
        ChangeableTextHandler.setText(argument);
        getLogger().info("Successfully set the text");
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <text>";
    }
}
