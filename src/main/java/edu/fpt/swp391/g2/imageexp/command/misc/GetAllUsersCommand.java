package edu.fpt.swp391.g2.imageexp.command.misc;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.User;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.List;

public class GetAllUsersCommand extends Command {
    public GetAllUsersCommand() {
        super("get-all-users");
    }

    @Override
    public void runCommand(String argument) {
        try {
            List<User> userList = UserProcessor.getAllUsers();
            for (User user : userList) {
                getLogger().info(user);
            }
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
        }
    }
}
