package edu.fpt.swp391.g2.imageexp.command.misc;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.User;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.Optional;

public class LoginUserCommand extends Command {
    public LoginUserCommand() {
        super("login-user");
    }

    @Override
    public void runCommand(String argument) {
        String[] split = argument.split(" ", 2);
        String email = split[0];
        String password = split.length > 1 ? split[1] : "";
        Optional<User> optionalUser;
        try {
            optionalUser = UserProcessor.loginUser(email, password);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
            return;
        }
        if (!optionalUser.isPresent()) {
            getLogger().warn("That user doesn't exist");
            return;
        }
        getLogger().info(optionalUser.get());
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <email> <password>";
    }
}
