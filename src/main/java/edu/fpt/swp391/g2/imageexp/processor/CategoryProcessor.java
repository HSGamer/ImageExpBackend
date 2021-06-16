package edu.fpt.swp391.g2.imageexp.processor;

import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.entity.Category;
import me.hsgamer.hscore.database.client.sql.PreparedStatementContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryProcessor {
    private static Category getCategory(ResultSet resultSet) throws SQLException {
        Category category = new Category(resultSet.getInt("categoryID"));
        category.setName(resultSet.getString("category_name"));
        return category;
    }

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
}
