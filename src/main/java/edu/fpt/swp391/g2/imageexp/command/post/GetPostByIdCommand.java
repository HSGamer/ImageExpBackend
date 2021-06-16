package edu.fpt.swp391.g2.imageexp.command.post;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.Post;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.Optional;

public class GetPostByIdCommand extends Command {
    public GetPostByIdCommand() {
        super("get-post-by-id");
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
        Optional<Post> optionalPost;
        try {
            optionalPost = PostProcessor.getPostById(id);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
            return;
        }
        if (!optionalPost.isPresent()) {
            getLogger().warn("That post id doesn't exist");
            return;
        }
        getLogger().info(optionalPost.get());
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <id>";
    }
}
