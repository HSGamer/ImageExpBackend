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

/**
 * The processor for working with {@link Comment}
 */
public class CommentProcessor {
    private CommentProcessor() {
        // EMPTY
    }

    /**
     * Get the comment from the result set
     *
     * @param resultSet the result set
     * @return the comment
     * @throws SQLException if there is an SQL error
     */
    private static Comment getComment(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment(resultSet.getInt("commentID"));
        comment.setPostId(resultSet.getInt("postId"));
        comment.setUserId(resultSet.getInt("userId"));
        comment.setComment(resultSet.getString("comment"));
        comment.setCreatedAt(Utils.getDate(resultSet.getString("created_at")));
        comment.setUpdatedAt(Utils.getDate(resultSet.getString("updated_at")));
        return comment;
    }

    /**
     * Get the comments by the user from the post
     *
     * @param userId the user's id
     * @param postId the post id
     * @return the comments
     * @throws SQLException if there is an SQL error
     */
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

    /**
     * Get the comments by the user
     *
     * @param userId the user's id
     * @return the comments
     * @throws SQLException if there is an SQL error
     */
    public static List<Comment> getCommentsByUserId(int userId) throws SQLException {
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

    /**
     * Get the comments from the post
     *
     * @param postId the post id
     * @return the comments
     * @throws SQLException if there is an SQL error
     */
    public static List<Comment> getCommentsByPostId(int postId) throws SQLException {
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

    /**
     * Get the comment by its id
     *
     * @param commentId the comment id
     * @return the comment
     * @throws SQLException if there is an SQL error
     */
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

    /**
     * Add a comment to the post
     *
     * @param postId  the post id
     * @param userId  the id of the commenting user
     * @param comment the comment
     * @throws SQLException if there is an SQL error
     */
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

    /**
     * Update the comment
     *
     * @param commentId the comment's id
     * @param comment   the new comment
     * @throws SQLException if there is an SQL error
     */
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

    /**
     * Delete the comment
     *
     * @param commentId the comment's id
     * @throws SQLException if there is an SQL error
     */
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
