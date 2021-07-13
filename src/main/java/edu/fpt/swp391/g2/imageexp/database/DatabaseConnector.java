package edu.fpt.swp391.g2.imageexp.database;

import edu.fpt.swp391.g2.imageexp.config.MainConfig;
import edu.fpt.swp391.g2.imageexp.processor.UserProcessor;
import edu.fpt.swp391.g2.imageexp.processor.VerifyProcessor;
import me.hsgamer.hscore.config.Config;
import me.hsgamer.hscore.database.Driver;
import me.hsgamer.hscore.database.Setting;
import me.hsgamer.hscore.database.client.sql.PreparedStatementContainer;
import me.hsgamer.hscore.database.client.sql.java.JavaSqlClient;
import me.hsgamer.hscore.database.driver.MySqlDriver;
import me.hsgamer.hscore.database.driver.SqliteDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The database connector, which holds the connection to the database
 */
public class DatabaseConnector {
    private static final Logger logger = LogManager.getLogger(DatabaseConnector.class);
    private static JavaSqlClient client;
    private static Connection connection;

    private DatabaseConnector() {
        // EMPTY
    }

    /**
     * Init the client and the database connection
     */
    public static void init() {
        Setting setting = new Setting()
                .setHost(MainConfig.DATABASE_HOST.getValue())
                .setPort(MainConfig.DATABASE_PORT.getValue())
                .setDatabaseName(MainConfig.DATABASE_DB_NAME.getValue())
                .setUsername(MainConfig.DATABASE_USERNAME.getValue())
                .setPassword(MainConfig.DATABASE_PASSWORD.getValue())
                .setProperty("useUnicode", "yes")
                .setProperty("characterEncoding", "UTF-8");
        try {
            Driver driver;
            if (MainConfig.DATABASE_MYSQL.getValue()) {
                driver = new MySqlDriver();
            } else {
                driver = new SqliteDriver();
            }
            client = new JavaSqlClient(setting, driver);
            connection = client.getConnection();
            if (driver instanceof SqliteDriver) {
                PreparedStatementContainer.of(connection, "PRAGMA foreign_keys = ON;").update();
            }
            if (MainConfig.DATABASE_FIRST_LOAD.getValue()) {
                logger.info("Load database at first run");
                LocalDatabaseExecutor.createDatabase(connection);
                MainConfig.DATABASE_FIRST_LOAD.setValue(false);
                Config config = MainConfig.DATABASE_FIRST_LOAD.getConfig();
                if (config != null) {
                    config.save();
                }
                if (MainConfig.DATABASE_TEST_ACCOUNT.getValue()) {
                    UserProcessor.registerUser("test@gmail.com", "AbCd!232");
                    VerifyProcessor.setVerifyState("test@gmail.com", true);
                }
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            logger.error("Cannot connect to the database", e);
        }
    }

    /**
     * Break the database connection
     */
    public static void disable() {
        client = null;
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Cannot close the database", e);
        }
    }

    /**
     * Get the database connection
     *
     * @return the database connection
     * @throws SQLException if there is an SQL error
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = client.getConnection();
        }
        return connection;
    }
}
