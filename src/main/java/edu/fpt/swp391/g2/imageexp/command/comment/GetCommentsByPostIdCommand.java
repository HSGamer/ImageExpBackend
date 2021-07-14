package edu.fpt.swp391.g2.imageexp.command.comment;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.Comment;
import edu.fpt.swp391.g2.imageexp.processor.CommentProcessor;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.List;

/**
 * The comment to get the comments from the post
 */
public class GetCommentsByPostIdCommand extends Command {
    public GetCommentsByPostIdCommand() {
        super("get-comments-by-post-id");
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
            if (!PostProcessor.getPostById(id).isPresent()) {
                getLogger().warn("The post id doesn't exist");
                return;
            }
            List<Comment> commentList = CommentProcessor.getCommentsByPostId(id);
            commentList.forEach(getLogger()::info);
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
        return "Get the comments of the post";
    }
}
