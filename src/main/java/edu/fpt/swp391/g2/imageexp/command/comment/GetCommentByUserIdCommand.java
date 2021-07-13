package edu.fpt.swp391.g2.imageexp.command.comment;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.Comment;
import edu.fpt.swp391.g2.imageexp.processor.CommentProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.List;

public class GetCommentByUserIdCommand extends Command {
    public GetCommentByUserIdCommand() {
        super("get-comment-by-user-id");
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
            List<Comment> commentList = CommentProcessor.getCommentsByUserId(id);
            commentList.forEach(getLogger()::info);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <id>";
    }
}
