package edu.fpt.swp391.g2.imageexp.processor;

import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.entity.Post;
import edu.fpt.swp391.g2.imageexp.utils.Utils;
import me.hsgamer.hscore.database.client.sql.PreparedStatementContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PostProcessor {
    private PostProcessor() {
        // EMPTY
    }

    private static Post getPost(ResultSet resultSet) throws SQLException {
        Post post = new Post(resultSet.getInt("postID"));
        post.setUserId(resultSet.getInt("userID"));
        post.setPicId(resultSet.getInt("picID"));
        post.setCategoryId(resultSet.getInt("categoryID"));
        post.setCreatedAt(Utils.getDate(resultSet.getString("created_at")));
        post.setUpdatedAt(Utils.getDate(resultSet.getString("updated_at")));
        post.setKeyword(resultSet.getString("keyword"));
        post.setStatus(resultSet.getString("status"));
        post.setLikes(resultSet.getInt("likes"));
        return post;
    }

    public static List<Post> getAllPosts() throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from post"
                );
                ResultSet resultSet = container.query()
        ) {
            List<Post> posts = new ArrayList<>();
            while (resultSet.next()) {
                posts.add(getPost(resultSet));
            }
            return posts;
        }
    }

    public static List<Post> getPostsByUserId(int userId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from post where userID = ?",
                        userId
                );
                ResultSet resultSet = container.query()
        ) {
            List<Post> posts = new ArrayList<>();
            while (resultSet.next()) {
                posts.add(getPost(resultSet));
            }
            return posts;
        }
    }

    public static List<Post> getPostsByCategoryId(int categoryId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from post where categoryID = ?",
                        categoryId
                );
                ResultSet resultSet = container.query()
        ) {
            List<Post> posts = new ArrayList<>();
            while (resultSet.next()) {
                posts.add(getPost(resultSet));
            }
            return posts;
        }
    }

    public static Optional<Post> getPostById(int postId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from post where postId = ?",
                        postId
                );
                ResultSet resultSet = container.query()
        ) {
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(getPost(resultSet));
        }
    }

    public static boolean checkPicturePosted(int picID) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from post where picID = ? limit 1",
                        picID
                );
                ResultSet resultSet = container.query()
        ) {
            return resultSet.next();
        }
    }

    public static void postPicture(int userId, int picId, int categoryID, String keyword) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "insert into post(userID, picID, categoryID, keyword, status, likes) values (?, ?, ?, ?, ?, ?)",
                        userId, picId, categoryID, keyword, "", 0
                )
        ) {
            container.update();
        }
    }

    public static void updatePost(int postId, int categoryId, String keyword) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "update post set categoryID = ?, keyword = ?, updated_at = ? where postID = ?",
                        categoryId, keyword, Utils.convertDateToString(new Date()), postId
                )
        ) {
            container.update();
        }
    }

    public static void deletePost(int postId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "delete from post where postID = ?",
                        postId
                )
        ) {
            container.update();
        }
    }
}
