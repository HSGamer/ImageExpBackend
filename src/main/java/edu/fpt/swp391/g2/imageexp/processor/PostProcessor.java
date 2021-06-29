package edu.fpt.swp391.g2.imageexp.processor;

import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.entity.Post;
import edu.fpt.swp391.g2.imageexp.utils.Utils;
import me.hsgamer.hscore.database.client.sql.BatchBuilder;
import me.hsgamer.hscore.database.client.sql.PreparedStatementContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The processor for working with {@link Post}
 */
public class PostProcessor {
    private PostProcessor() {
        // EMPTY
    }

    /**
     * Get post from result set
     *
     * @param resultSet the result set
     * @return the post
     * @throws SQLException if there is an SQL error
     */
    private static Post getPost(ResultSet resultSet) throws SQLException {
        Post post = new Post(resultSet.getInt("postID"));
        post.setUserId(resultSet.getInt("userID"));
        post.setPicId(resultSet.getInt("picID"));
        post.setTitle(resultSet.getString("title"));
        post.setDescription(resultSet.getString("description"));
        post.setCategoryIdList(getCategoryIdsFromPost(post.getId()));
        post.setCreatedAt(Utils.getDate(resultSet.getString("created_at")));
        post.setUpdatedAt(Utils.getDate(resultSet.getString("updated_at")));
        post.setKeyword(resultSet.getString("keyword"));
        post.setStatus(resultSet.getString("status"));
        return post;
    }

    /**
     * Get the category ids from the post
     *
     * @param postId the post id
     * @return the list of category ids
     * @throws SQLException if there is an SQL error
     */
    public static List<Integer> getCategoryIdsFromPost(int postId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select categoryID from postcategory where postID = ?",
                        postId
                );
                ResultSet resultSet = container.query()
        ) {
            List<Integer> categoryIdList = new ArrayList<>();
            while (resultSet.next()) {
                categoryIdList.add(resultSet.getInt("categoryID"));
            }
            return categoryIdList;
        }
    }

    /**
     * Get all posts
     *
     * @return the posts
     * @throws SQLException if there is an SQL error
     */
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

    /**
     * Get posts by user
     *
     * @param userId the user id
     * @return the posts
     * @throws SQLException if there is an SQL error
     */
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

    /**
     * Get posts by category
     *
     * @param categoryId the category id
     * @return the posts
     * @throws SQLException if there is an SQL error
     */
    public static List<Post> getPostsByCategoryId(int categoryId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select p2.* from postcategory p join post p2 on p.postID = p2.postID where p.categoryID = ?",
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

    /**
     * Get post by its id
     *
     * @param postId the id
     * @return the post
     * @throws SQLException if there is an SQL error
     */
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

    /**
     * Get post by its picture
     *
     * @param picId the picture id
     * @return the post
     * @throws SQLException if there is an SQL error
     */
    public static Optional<Post> getPostByPicId(int picId) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from post where picID = ? limit 1",
                        picId
                );
                ResultSet resultSet = container.query()
        ) {
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(getPost(resultSet));
        }
    }

    /**
     * Check if the picture is posted
     *
     * @param picId the picture id
     * @return true if it is
     * @throws SQLException if there is an SQL error
     */
    public static boolean checkPicturePosted(int picId) throws SQLException {
        return getPostByPicId(picId).isPresent();
    }

    /**
     * Post the picture
     *
     * @param userId         the user id
     * @param picId          the picture id
     * @param title          the title
     * @param description    the description
     * @param categoryIdList the list of the category ids
     * @param keyword        the keyword
     * @throws Exception if there is an error
     */
    public static void postPicture(int userId, int picId, String title, String description, List<Integer> categoryIdList, String keyword) throws Exception {
        try (
                BatchBuilder batchBuilder = new BatchBuilder(DatabaseConnector.getConnection());
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select postID from post where userID = ? and picID = ?",
                        userId, picId
                )
        ) {
            batchBuilder.addBatch(
                    "insert into post(userID, picID, title, description, keyword, status) values (?, ?, ?, ?, ?, ?)",
                    userId, picId, title, description, keyword, ""
            );
            batchBuilder.execute();
            batchBuilder.clear();
            ResultSet resultSet = container.query();
            if (!resultSet.next()) {
                throw new SQLException("The insertion is not finished");
            }
            int postId = resultSet.getInt("postID");
            for (int categoryId : categoryIdList) {
                batchBuilder.addBatch(
                        "insert into postcategory(postID, categoryID) values (?, ?)",
                        postId, categoryId
                );
            }
            batchBuilder.execute();
        }
    }

    /**
     * Update post
     *
     * @param postId         the post id
     * @param title          the title
     * @param description    the description
     * @param categoryIdList the list of the category ids
     * @param keyword        the keyword
     * @throws Exception if there is an error
     */
    public static void updatePost(int postId, String title, String description, List<Integer> categoryIdList, String keyword) throws Exception {
        try (BatchBuilder batchBuilder = new BatchBuilder(DatabaseConnector.getConnection())) {
            batchBuilder.addBatch("delete from postcategory where postID = ?", postId);
            batchBuilder.addBatch(
                    "update post set keyword = ?, title = ?, description = ?, updated_at = ? where postID = ?",
                    keyword, title, description, Utils.convertDateToString(new Date()), postId
            );
            for (int categoryId : categoryIdList) {
                batchBuilder.addBatch(
                        "insert into postcategory(postID, categoryID) values (?, ?)",
                        postId, categoryId
                );
            }
            batchBuilder.execute();
        }
    }

    /**
     * Delete the post
     *
     * @param postId the post id
     * @throws Exception if there is an error
     */
    public static void deletePost(int postId) throws Exception {
        try (BatchBuilder batchBuilder = new BatchBuilder(DatabaseConnector.getConnection())) {
            batchBuilder.addBatch("delete from postcategory where postID = ?", postId);
            batchBuilder.addBatch("delete from post where postID = ?", postId);
            batchBuilder.execute();
        }
    }
}
