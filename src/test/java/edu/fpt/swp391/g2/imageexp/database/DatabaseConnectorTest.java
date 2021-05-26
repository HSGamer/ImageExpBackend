package edu.fpt.swp391.g2.imageexp.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DatabaseConnectorTest {

    @BeforeAll
    static void setup() {
        DatabaseConnector.init();
    }

    @Test
    void getConnection() {
        Assertions.assertNotNull(DatabaseConnector.getConnection());
    }
}