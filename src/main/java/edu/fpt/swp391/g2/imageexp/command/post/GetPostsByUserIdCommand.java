package edu.fpt.swp391.g2.imageexp.command.post;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.Post;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.List;

/**
 * The command to get the posts from the user
 */
public class GetPostsByUserIdCommand extends Command {
    public GetPostsByUserIdCommand() {
        super("get-posts-by-user-id");
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
        try {
            if (!UserProcessor.getUserById(id).isPresent()) {
                getLogger().warn("The user id doesn't exist");
                return;
            }
            List<Post> postList = PostProcessor.getPostsByUserId(id);
            postList.forEach(getLogger()::info);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <id>";
    }

    @Override
    public String getDescription() {
        return "Get the posts from the user";
    }
}
