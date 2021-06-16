package edu.fpt.swp391.g2.imageexp.command.picture;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.processor.GalleryProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;

public class DeletePictureCommand extends Command {
    public DeletePictureCommand() {
        super("delete-picture");
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
            if (!GalleryProcessor.getPictureById(id).isPresent()) {
                getLogger().warn("The picture id doesn't exist");
                return;
            }
            GalleryProcessor.deletePicture(id);
            getLogger().info("Successfully deleted");
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage() + " <picture_id>";
    }
}
