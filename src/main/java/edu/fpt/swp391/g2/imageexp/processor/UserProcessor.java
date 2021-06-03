package edu.fpt.swp391.g2.imageexp.processor;

import edu.fpt.swp391.g2.imageexp.database.DatabaseConnector;
import edu.fpt.swp391.g2.imageexp.entity.User;
import edu.fpt.swp391.g2.imageexp.utils.Utils;
import me.hsgamer.hscore.database.client.sql.PreparedStatementContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserProcessor {
    private UserProcessor() {
        // EMPTY
    }

    public static boolean checkEmailExists(String email) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(DatabaseConnector.getConnection(), "select * from user where email = ? limit 1", email);
                ResultSet resultSet = container.query()
        ) {
            return resultSet.next();
        }
    }

    public static Optional<User> getUserById(int id) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(DatabaseConnector.getConnection(), "select * from user where userid = ? limit 1", id);
                ResultSet resultSet = container.query()
        ) {
            if (!resultSet.next()) {
                return Optional.empty();
            }
            User user = new User(id);
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setAvatar(resultSet.getString("avatar"));
            user.setStatus(resultSet.getString("status"));
            return Optional.of(user);
        }
    }

    public static void registerUser(String email, String password) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "INSERT into user(email, password, username, avatar, status) values (?, ?, ?, \"\", \"\")",
                        email, Utils.hashMD5(password), email
                )
        ) {
            container.update();
        }
    }

    /**
     * Change username and avatar
     * @param email the email
     * @param username the user name
     * @param avatar the avatar
     * @throws SQLException if there is an SQL error
     */
    public static void updateUserInfo(String email, String username, String avatar) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "UPDATE user SET username = ?, avatar = ? WHERE email = ?",
                        username ,avatar ,email
                )
        ) {
            container.update();
        }
    }

    /**
     * Change user password
     * @param email user email
     * @param password user password
     * @throws SQLException if there is SQL error
     */
    public static void changePassword(String email,String password) throws SQLException{
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "UPDATE user SET password = ? WHERE email = ?",
                        Utils.hashMD5(password), email
                )
        ) {
            container.update();
        }
    }

    public static Optional<User> loginUser(String email, String password) throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from user where email = ? and password = ? limit 1",
                        email, Utils.hashMD5(password)
                );
                ResultSet resultSet = container.query()
        ) {
            if (!resultSet.next()) {
                return Optional.empty();
            }
            User user = new User(resultSet.getInt("userid"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setAvatar(resultSet.getString("avatar"));
            user.setStatus(resultSet.getString("status"));
            return Optional.of(user);
        }
    }

    public static List<User> getAllUsers() throws SQLException {
        try (
                PreparedStatementContainer container = PreparedStatementContainer.of(
                        DatabaseConnector.getConnection(),
                        "select * from user"
                );
                ResultSet resultSet = container.query()
        ) {
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("userid"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setStatus(resultSet.getString("status"));
                list.add(user);
            }
            return list;
        }
    }
}
