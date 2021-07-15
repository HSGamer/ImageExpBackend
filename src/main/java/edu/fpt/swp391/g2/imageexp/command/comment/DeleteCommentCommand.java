package edu.fpt.swp391.g2.imageexp.command.comment;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.processor.CommentProcessor;
import org.apache.logging.log4j.Level;

/**
 * The command to delete the comment
 */
public class DeleteCommentCommand extends Command {
    public DeleteCommentCommand() {
        super("delete-comment");
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
            if (!CommentProcessor.getCommentById(id).isPresent()) {
                getLogger().warn("The comment id doesn't exist");
                return;
            }
            CommentProcessor.deleteComment(id);
            getLogger().info("Successfully deleted");
        } catch (Exception e) {
            getLogger().log(Level.WARN, "There is an exception when getting data", e);
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <comment_id>";
    }

    @Override
    public String getDescription() {
        return "Delete the comment";
    }
}
