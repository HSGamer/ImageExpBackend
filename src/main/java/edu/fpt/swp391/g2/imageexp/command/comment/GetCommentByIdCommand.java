package edu.fpt.swp391.g2.imageexp.command.comment;

import edu.fpt.swp391.g2.imageexp.entity.Comment;
import edu.fpt.swp391.g2.imageexp.processor.CommentProcessor;
import edu.fpt.swp391.g2.imageexp.command.Command;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.Optional;

public class GetCommentByIdCommand extends Command {
    public GetCommentByIdCommand() {
        super("get-comment-by-id");
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
        Optional<Comment> optionalComment;
        try {
            optionalComment = CommentProcessor.getCommentById(id);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
            return;
        }
        if (!optionalComment.isPresent()) {
            getLogger().warn("That comment id doesn't exist");
            return;
        }
        getLogger().info(optionalComment.get());
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <id>";
    }
}
