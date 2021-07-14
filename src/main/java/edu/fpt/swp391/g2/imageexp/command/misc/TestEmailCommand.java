package edu.fpt.swp391.g2.imageexp.command.misc;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.email.EmailHandler;

/**
 * The command to test email
 */
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
        EmailHandler.sendEmailAsync(argument, "Test Email", "<h1>This is a test email</h1><p>If you see this then your email system is working properly</p>")
                .thenAccept(getLogger()::info);
        getLogger().info("Successfully Called");
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <email>";
    }

    @Override
    public String getDescription() {
        return "Test the email";
    }
}
