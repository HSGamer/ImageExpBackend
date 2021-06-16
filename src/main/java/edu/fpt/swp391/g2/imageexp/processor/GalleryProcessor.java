package edu.fpt.swp391.g2.imageexp.processor;

import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.entity.Picture;
import me.hsgamer.hscore.database.client.sql.PreparedStatementContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GalleryProcessor {
    /**
     * @param userID  user's id
     * @param picture picture as base_64
     * @throws SQLException sql error
     */
    public static void addPicture(int userID, String picture) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "insert into picture(userID, picture) values (?, ?)",
                        userID, picture
                )
        ) {
            container.update();
        }
    }

    /**
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
     * @param resultSet get picture as set
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
