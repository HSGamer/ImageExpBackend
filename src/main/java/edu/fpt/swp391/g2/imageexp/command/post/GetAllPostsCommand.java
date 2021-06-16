package edu.fpt.swp391.g2.imageexp.command.post;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.Post;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.List;

public class GetAllPostsCommand extends Command {
    public GetAllPostsCommand() {
        super("get-all-posts");
    }

    @Override
    public void runCommand(String argument) {
        try {
            List<Post> postList = PostProcessor.getAllPosts();
            postList.forEach(getLogger()::info);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
        }
    }
}
