package edu.fpt.swp391.g2.imageexp.processor;

import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.entity.Comment;
import edu.fpt.swp391.g2.imageexp.utils.Utils;
import me.hsgamer.hscore.database.client.sql.PreparedStatementContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CommentProcessor {
    private CommentProcessor() {
        // EMPTY
    }

    private static Comment getComment(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment(resultSet.getInt("commentID"));
        comment.setPostId(resultSet.getInt("postId"));
        comment.setUserId(resultSet.getInt("userId"));
        comment.setComment(resultSet.getString("comment"));
        return comment;
    }

    public static List<Comment> getCommentByUserIdAndPostId(int userId, int postId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from comment where userID = ? and postID = ?",
                        userId, postId
                );
                ResultSet resultSet = container.query()
        ) {
            List<Comment> comments = new ArrayList<>();
            while (resultSet.next()) {
                comments.add(getComment(resultSet));
            }
            return comments;
        }
    }

    public static List<Comment> getCommentByUserId(int userId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from comment where userID = ?",
                        userId
                );
                ResultSet resultSet = container.query()
        ) {
            List<Comment> comments = new ArrayList<>();
            while (resultSet.next()) {
                comments.add(getComment(resultSet));
            }
            return comments;
        }
    }

    public static List<Comment> getCommentByPostId(int postId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from comment where postID = ?",
                        postId
                );
                ResultSet resultSet = container.query()
        ) {
            List<Comment> comments = new ArrayList<>();
            while (resultSet.next()) {
                comments.add(getComment(resultSet));
            }
            return comments;
        }
    }

    public static Optional<Comment> getCommentById(int commentId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from comment where commentID = ?",
                        commentId
                );
                ResultSet resultSet = container.query()
        ) {
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(getComment(resultSet));
        }
    }

    public static void addComment(int postId, int userId, String comment) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "insert into comment(postID,userID,comment) values (?,?,?)",
                        postId, userId, comment
                )
        ) {
            container.update();
        }
    }

    public static void updateComment(int commentId, String comment) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "UPDATE comment SET comment = ?, updated_at = ? WHERE commentId = ?",
                        comment, Utils.convertDateToString(new Date()), commentId
                )
        ) {
            container.update();
        }
    }

    public static void deleteComment(int commentId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "delete from comment where commentID = ?",
                        commentId
                )
        ) {
            container.update();
        }
    }


}
