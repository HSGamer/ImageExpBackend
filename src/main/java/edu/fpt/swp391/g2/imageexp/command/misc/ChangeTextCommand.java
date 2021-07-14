package edu.fpt.swp391.g2.imageexp.command.misc;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.server.handler.misc.ChangeableTextHandler;

/**
 * The command to change the changeable text
 */
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

    @Override
    public String getDescription() {
        return "Change the changeable text";
    }
}
