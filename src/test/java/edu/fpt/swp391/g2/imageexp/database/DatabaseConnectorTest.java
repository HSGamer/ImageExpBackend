package edu.fpt.swp391.g2.imageexp.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;

class DatabaseConnectorTest {

    @BeforeAll
    static void setup() {
        DatabaseConnector.init();
    }

    @AfterAll
    static void stop() {
        DatabaseConnector.disable();
        File file = new File("imageexp.db");
        file.delete();
    }

    @Test
    void getConnection() {
        try {
            Assertions.assertNotNull(DatabaseConnector.getConnection());
        } catch (SQLException throwables) {
            Assertions.fail(throwables);
        }
    }
}