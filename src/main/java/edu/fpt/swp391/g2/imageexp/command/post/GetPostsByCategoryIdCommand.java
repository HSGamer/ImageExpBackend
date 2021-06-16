package edu.fpt.swp391.g2.imageexp.command.post;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.Post;
import edu.fpt.swp391.g2.imageexp.processor.CategoryProcessor;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.List;

public class GetPostsByCategoryIdCommand extends Command {
    public GetPostsByCategoryIdCommand() {
        super("get-posts-by-category-id");
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
            if (!CategoryProcessor.getCategoryById(id).isPresent()) {
                getLogger().warn("The category id doesn't exist");
                return;
            }
            List<Post> postList = PostProcessor.getPostsByCategoryId(id);
            postList.forEach(getLogger()::info);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <category_id>";
    }
}
