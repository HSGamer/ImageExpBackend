package edu.fpt.swp391.g2.imageexp.command.misc;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.email.VerifyEmailHandler;

public class TestEmailCommand extends Command {
    public TestEmailCommand() {
        super("test-email");
    }

    @Override
    public void runCommand(String argument) {
        if (argument.isEmpty()) {
            getLogger().warn("You should give a email to send");
            return;
        }
        VerifyEmailHandler.sendEmailAsync(argument, "TestCode").thenAccept(unused -> getLogger().info("Finished sending email"));
        getLogger().info("Successfully Called");
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <email>";
    }
}
