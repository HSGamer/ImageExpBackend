package edu.fpt.swp391.g2.imageexp.processor;

import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.entity.Picture;
import me.hsgamer.hscore.database.client.sql.BatchBuilder;
import me.hsgamer.hscore.database.client.sql.PreparedStatementContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The processor for working with {@link Picture}
 */
public class GalleryProcessor {
    private GalleryProcessor() {
        // EMPTY
    }

    /**
     * Add a picture
     *
     * @param userID  user's id
     * @param picture picture as base_64
     * @return the picture id
     * @throws SQLException sql error
     */
    public static int addPicture(int userID, String picture) throws SQLException {
        try (
                PreparedStatementContainer insertContainer = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "insert into picture(userID, picture) values (?, ?)",
                        userID, picture
                );
                PreparedStatementContainer selectContainer = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select picID from picture where userID = ? and picture = ? limit 1",
                        userID, picture
                )
        ) {
            insertContainer.update();
            ResultSet resultSet = selectContainer.query();
            if (!resultSet.next()) {
                return -1;
            }
            return resultSet.getInt("picID");
        }
    }

    /**
     * Add many pictures
     *
     * @param userID   user's id
     * @param pictures pictures as base_64
     * @throws Exception sql error
     */
    public static void addMorePictures(int userID, List<String> pictures) throws Exception {
        try (BatchBuilder batchBuilder = new BatchBuilder(DatabaseConnector.getConnection())) {
            for (String picture : pictures) {
                batchBuilder.addBatch("insert into picture(userID, picture) values (?, ?)", userID, picture);
            }
            batchBuilder.execute();
        }
    }

    /**
     * Delete the picture
     *
     * @param picID picture's id
     * @throws SQLException sql error
     */
    public static void deletePicture(int picID) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "delete from picture where picID = ?",
                        picID
                )
        ) {
            container.update();
        }
    }

    /**
     * Get the picture by its id
     *
     * @param picID picture's id
     * @return picture
     * @throws SQLException sql error
     */
    public static Optional<Picture> getPictureById(int picID) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from picture where picID = ?",
                        picID
                );
                ResultSet resultSet = container.query()
        ) {
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(getPicture(resultSet));
        }
    }

    /**
     * Get the picture from the result set
     *
     * @param resultSet the result set
     * @return picture
     * @throws SQLException sql error
     */
    private static Picture getPicture(ResultSet resultSet) throws SQLException {
        Picture picture = new Picture(resultSet.getInt("picID"));
        picture.setUserId(resultSet.getInt("userID"));
        picture.setPicture(resultSet.getString("picture"));
        return picture;
    }

    /**
     * Get all pictures
     *
     * @return list pictures
     * @throws SQLException sql error
     */
    public static List<Picture> getAllPictures() throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from picture"
                );
                ResultSet resultSet = container.query()
        ) {
            List<Picture> pictures = new ArrayList<>();
            while (resultSet.next()) {
                pictures.add(getPicture(resultSet));
            }
            return pictures;
        }
    }

    /**
     * Get the pictures from the user
     *
     * @param userId user's id
     * @return list of pictures
     * @throws SQLException sql error
     */
    public static List<Picture> getPicturesByUserId(int userId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from picture where userID = ?",
                        userId
                );
                ResultSet resultSet = container.query()
        ) {
            List<Picture> pictures = new ArrayList<>();
            while (resultSet.next()) {
                pictures.add(getPicture(resultSet));
            }
            return pictures;
        }
    }


}
