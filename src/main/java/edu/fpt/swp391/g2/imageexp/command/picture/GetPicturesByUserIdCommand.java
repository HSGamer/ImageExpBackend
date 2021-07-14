package edu.fpt.swp391.g2.imageexp.command.picture;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.Picture;
import edu.fpt.swp391.g2.imageexp.processor.GalleryProcessor;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.List;

/**
 * The command to get pictures from the user
 */
public class GetPicturesByUserIdCommand extends Command {
    public GetPicturesByUserIdCommand() {
        super("get-pictures-by-user-id");
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
            List<Picture> pictureList = GalleryProcessor.getPicturesByUserId(id);
            pictureList.forEach(getLogger()::info);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <user_id>";
    }

    @Override
    public String getDescription() {
        return "Get pictures from the user";
    }
}
