package edu.fpt.swp391.g2.imageexp.command.misc;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;

public class RegisterUserCommand extends Command {
    public RegisterUserCommand() {
        super("register-user");
    }

    @Override
    public void runCommand(String argument) {
        String[] split = argument.split(" ", 2);
        String email = split[0];
        String password = split.length > 1 ? split[1] : "";
        try {
            if (UserProcessor.checkEmailExists(email)) {
                getLogger().warn("That email already exists");
                return;
            }
            UserProcessor.registerUser(email, password);
            getLogger().info("Successfully registered");
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <email> <password>";
    }
}
