package edu.fpt.swp391.g2.imageexp.database;

import edu.fpt.swp391.g2.imageexp.config.MainConfig;
import me.hsgamer.hscore.database.Setting;
import me.hsgamer.hscore.database.client.sql.java.JavaSqlClient;
import me.hsgamer.hscore.database.driver.MySqlDriver;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {
    private static JavaSqlClient client;
    private static Connection connection;

    private DatabaseConnector() {
        // EMPTY
    }

    public static void init() throws ClassNotFoundException, SQLException {
        Setting setting = new Setting()
                .setHost(MainConfig.DATABASE_HOST.getValue())
                .setPort(MainConfig.DATABASE_PORT.getValue())
                .setDatabaseName(MainConfig.DATABASE_DB_NAME.getValue())
                .setUsername(MainConfig.DATABASE_USERNAME.getValue())
                .setPassword(MainConfig.DATABASE_PASSWORD.getValue());
        client = new JavaSqlClient(setting, new MySqlDriver());
        connection = client.getConnection();
    }

    public static void disable() throws SQLException {
        client = null;
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
