package edu.fpt.swp391.g2.imageexp.command.picture;

import edu.fpt.swp391.g2.imageexp.command.Command;
import edu.fpt.swp391.g2.imageexp.entity.Picture;
import edu.fpt.swp391.g2.imageexp.processor.GalleryProcessor;
import org.apache.logging.log4j.Level;

import java.sql.SQLException;
import java.util.List;

/**
 * The command to get all pictures
 */
public class GetAllPicturesCommand extends Command {
    public GetAllPicturesCommand() {
        super("get-all-pictures");
    }

    @Override
    public void runCommand(String argument) {
        try {
            List<Picture> pictureList = GalleryProcessor.getAllPictures();
            pictureList.forEach(getLogger()::info);
        } catch (SQLException e) {
            getLogger().log(Level.WARN, "There is an SQL exception when getting data", e);
        }
    }

    @Override
    public String getDescription() {
        return "Get all pictures";
    }
}
