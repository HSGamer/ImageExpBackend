package edu.fpt.swp391.g2.imageexp.processor;

import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.entity.Like;
import me.hsgamer.hscore.database.client.sql.PreparedStatementContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The processor for working with {@link Like}
 */
public class LikeProcessor {
    private LikeProcessor() {
        // EMPTY
    }

    /**
     * Get like entity from the result set
     *
     * @param resultSet the result set
     * @return the like entity
     * @throws SQLException if there is an SQL exception
     */
    private static Like getLike(ResultSet resultSet) throws SQLException {
        Like like = new Like(resultSet.getInt("likesID"));
        like.setPostId(resultSet.getInt("postID"));
        like.setUserId(resultSet.getInt("userID"));
        return like;
    }

    /**
     * Toggle (on/off) the user likes the post
     *
     * @param userId the user's id
     * @param postId the post's id
     * @throws SQLException if there is an SQL exception
     */
    public static void toggleLike(int userId, int postId) throws SQLException {
        if (isLiked(userId, postId)) {
            try (
                    PreparedStatementContainer container = PreparedStatementContainer.of(
                            DatabaseConnector.getConnection(),
                            "delete from likes where userID = ? and postID = ?",
                            userId, postId
                    )
            ) {
                container.update();
            }
        } else {
            try (
                    PreparedStatementContainer container = PreparedStatementContainer.of(
                            DatabaseConnector.getConnection(),
                            "insert into likes(userID, postID) values (?, ?)",
                            userId, postId
                    )
            ) {
                container.update();
            }
        }
    }

    /**
     * Check if the user likes the post
     *
     * @param userId the user's id
     * @param postId the post's id
     * @return true if he does
     * @throws SQLException if there is an SQL exception
     */
    public static boolean isLiked(int userId, int postId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from likes where userID = ? and postID = ? limit 1",
                        userId, postId
                );
                ResultSet resultSet = container.query()
        ) {
            return resultSet.next();
        }
    }

    /**
     * Count the likes of the post
     *
     * @param postId the post's id
     * @return the like count
     * @throws SQLException if there is an SQL exception
     */
    public static int countLikes(int postId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select COUNT(userID) from likes where postID = ?",
                        postId
                );
                ResultSet resultSet = container.query()
        ) {
            if (!resultSet.next()) {
                return 0;
            }
            return resultSet.getInt(1);
        }
    }

    /**
     * Get the likes of the post
     *
     * @param postId the post's id
     * @return the likes
     * @throws SQLException if there is an SQL exception
     */
    public static List<Like> getLikes(int postId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from likes where postID = ?",
                        postId
                );
                ResultSet resultSet = container.query()
        ) {
            List<Like> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(getLike(resultSet));
            }
            return list;
        }
    }
}
