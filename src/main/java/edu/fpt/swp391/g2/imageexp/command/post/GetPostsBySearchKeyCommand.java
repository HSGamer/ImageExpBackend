package edu.fpt.swp391.g2.imageexp.command.post;
import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.Post;
import edu.fpt.swp391.g2.imageexp.processor.PostProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.List;

public class GetPostsBySearchKeyCommand extends Command{
    public GetPostsBySearchKeyCommand() {
        super("get-posts-by-search-key");
    }

    @Override
    public void runCommand(String argument) {
        String searchKey;
        try {
            searchKey = argument.trim();
        } catch (NumberFormatException e) {
            getLogger().warn("The search key need to be a string");
            return;
        }
        try {
            if (!searchKey.isEmpty()) {
                getLogger().warn("The search key cannot be empty");
                return;
            }
            List<Post> postList = PostProcessor.getPostsBySearchKey(searchKey);
            postList.forEach(getLogger()::info);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <searchKey>";
    }
}
