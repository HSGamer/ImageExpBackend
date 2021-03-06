package edu.fpt.swp391.g2.imageexp.command.post;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import org.apache.logging.log4j.Level;

/**
 * The command to delete the post
 */
public class DeletePostCommand extends Command {
    public DeletePostCommand() {
        super("delete-post");
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
            PostProcessor.deletePost(id);
            getLogger().info("Successfully deleted");
        } catch (Exception e) {
            getLogger().log(Level.WARN, "There is an exception when getting data", e);
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <post_id>";
    }

    @Override
    public String getDescription() {
        return "Delete the post";
    }
}
