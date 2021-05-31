package edu.fpt.swp391.g2.imageexp.command;

import edu.fpt.swp391.g2.imageexp.entity.User;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.Optional;

public class GetUserByIdCommand extends Command {
    public GetUserByIdCommand() {
        super("get-user-by-id");
    }

    @Override
    public void runCommand(String argument) {
        int id;
        try {
            id = Integer.parseInt(argument.trim());
        } catch (NumberFormatException e) {
            getLogger().warn("The id is not a number");
            return;
        }
        Optional<User> optionalUser;
        try {
            optionalUser = UserProcessor.getUserById(id);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
            return;
        }
        if (!optionalUser.isPresent()) {
            getLogger().warn("That user id doesn't exist");
            return;
        }
        getLogger().info(optionalUser.get());
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <id>";
    }
}
