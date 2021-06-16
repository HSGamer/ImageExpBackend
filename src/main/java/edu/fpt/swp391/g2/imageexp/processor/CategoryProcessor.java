package edu.fpt.swp391.g2.imageexp.processor;

import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.entity.Category;
import me.hsgamer.hscore.database.client.sql.PreparedStatementContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The processor for working with {@link Category}
 */
public class CategoryProcessor {
    private CategoryProcessor() {
        // EMPTY
    }

    /**
     * Get the category from the result set
     *
     * @param resultSet the result set
     * @return the category
     * @throws SQLException if there is an SQL error
     */
    private static Category getCategory(ResultSet resultSet) throws SQLException {
        Category category = new Category(resultSet.getInt("categoryID"));
        category.setName(resultSet.getString("category_name"));
        return category;
    }

    /**
     * Add new category
     *
     * @param name the name
     * @throws SQLException if there is an SQL error
     */
    public static void addCategory(String name) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "insert into category(category_name) values (?)",
                        name
                )
        ) {
            container.update();
        }
    }

    /**
     * Check if the category exists
     *
     * @param name the name
     * @return true if it does
     * @throws SQLException if there is an SQL error
     */
    public static boolean checkCategoryExists(String name) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select category_name from category where category_name = ? limit 1",
                        name
                );
                ResultSet resultSet = container.query()
        ) {
            return resultSet.next();
        }
    }

    /**
     * Get all categories
     *
     * @return the list of categories
     * @throws SQLException if there is an SQL error
     */
    public static List<Category> getAllCategories() throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from category"
                );
                ResultSet resultSet = container.query()
        ) {
            List<Category> categories = new ArrayList<>();
            while (resultSet.next()) {
                categories.add(getCategory(resultSet));
            }
            return categories;
        }
    }

    /**
     * Get category by ID
     *
     * @param id the id
     * @return the category
     * @throws SQLException if there is an SQL error
     */
    public static Optional<Category> getCategoryById(int id) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from category where categoryID = ? limit 1",
                        id
                );
                ResultSet resultSet = container.query()
        ) {
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(getCategory(resultSet));
        }
    }

    /**
     * Check if all category ids exist
     *
     * @param categoryIdList the list of category ids
     * @return true if they do
     * @throws SQLException if there is an SQL error
     */
    public static boolean checkAllCategoriesExists(List<Integer> categoryIdList) throws SQLException {
        for (Integer id : categoryIdList) {
            if (!getCategoryById(id).isPresent()) {
                return false;
            }
        }
        return true;
    }
}
