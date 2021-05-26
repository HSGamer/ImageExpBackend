package edu.fpt.swp391.g2.imageexp.database;

import edu.fpt.swp391.g2.imageexp.config.MainConfig;
import me.hsgamer.hscore.database.Driver;
import me.hsgamer.hscore.database.Setting;
import me.hsgamer.hscore.database.client.sql.java.JavaSqlClient;
import me.hsgamer.hscore.database.driver.MySqlDriver;
import me.hsgamer.hscore.database.driver.SqliteDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final Logger logger = LogManager.getLogger(DatabaseConnector.class);
    private static JavaSqlClient client;
    private static Connection connection;

    private DatabaseConnector() {
        // EMPTY
    }

    public static void init() {
        Setting setting = new Setting()
                .setHost(MainConfig.DATABASE_HOST.getValue())
                .setPort(MainConfig.DATABASE_PORT.getValue())
                .setDatabaseName(MainConfig.DATABASE_DB_NAME.getValue())
                .setUsername(MainConfig.DATABASE_USERNAME.getValue())
                .setPassword(MainConfig.DATABASE_PASSWORD.getValue());
        try {
            Driver driver;
            if (MainConfig.DATABASE_MYSQL.getValue()) {
                driver = new MySqlDriver();
            } else {
                driver = new SqliteDriver();
            }
            client = new JavaSqlClient(setting, driver);
            connection = client.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Cannot connect to the database", e);
        }
    }

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

    public static Connection getConnection() {
        return connection;
    }
}
