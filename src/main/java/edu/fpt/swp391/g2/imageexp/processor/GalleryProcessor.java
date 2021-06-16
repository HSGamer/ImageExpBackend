package edu.fpt.swp391.g2.imageexp.processor;

import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.entity.Picture;
import edu.fpt.swp391.g2.imageexp.entity.Post;
import me.hsgamer.hscore.database.client.sql.PreparedStatementContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class GalleryProcessor {

    public static void addPicture(int userID, String picture) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "insert into picture(userID, picture)",
                        userID, picture
                )
        ) {
            container.update();
        }
    }
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

    private static Picture getPicture(ResultSet resultSet) throws SQLException {
        Picture picture = new Picture(resultSet.getInt("picID"));
        picture.setUserId(resultSet.getInt("userID"));
        picture.setPicture(resultSet.getString("picture"));
        return picture;
    }

    public static List<Picture> getAllPicture() throws SQLException {
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

    public static List<Picture> getPictureByUserId(int userId) throws SQLException {
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
