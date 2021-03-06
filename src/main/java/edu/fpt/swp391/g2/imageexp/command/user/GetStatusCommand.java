package edu.fpt.swp391.g2.imageexp.command.user;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.User;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.Optional;

/**
 * The command to get the status of the user
 */
public class GetStatusCommand extends Command {
    public GetStatusCommand() {
        super("get-status");
    }

    @Override
    public void runCommand(String argument) {
        Optional<User> optionalUser;
        try {
            optionalUser = UserProcessor.getUserByEmail(argument);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
            return;
        }
        if (!optionalUser.isPresent()) {
            getLogger().warn("That email doesn't exist");
            return;
        }
        getLogger().info(optionalUser.get().getStatus());
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <email>";
    }

    @Override
    public String getDescription() {
        return "Get the status of the user";
    }
}
