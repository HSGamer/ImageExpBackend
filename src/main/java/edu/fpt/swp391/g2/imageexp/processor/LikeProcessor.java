package edu.fpt.swp391.g2.imageexp.processor;

import edu.fpt.swp391.g2.imageexp.entity.Like;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
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
        // TODO
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
        // TODO
        return false;
    }

    /**
     * Get the post id of the posts the user likes
     *
     * @param userId the user's id
     * @return the list of post ids
     * @throws SQLException if there is an SQL exception
     */
    public static List<Integer> getLikedPostIds(int userId) throws SQLException {
        // TODO
        return Collections.emptyList();
    }

    /**
     * Count the likes of the post
     *
     * @param postId the post's id
     * @return the like count
     * @throws SQLException if there is an SQL exception
     */
    public static int countLikes(int postId) throws SQLException {
        // TODO
        return 0;
    }

    /**
     * Get the likes of the post
     *
     * @param postId the post's id
     * @return the likes
     * @throws SQLException if there is an SQL exception
     */
    public static List<Like> getLikes(int postId) throws SQLException {
        // TODO
        return Collections.emptyList();
    }
}
