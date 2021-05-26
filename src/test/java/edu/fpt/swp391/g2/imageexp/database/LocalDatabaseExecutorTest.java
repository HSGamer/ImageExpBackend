package edu.fpt.swp391.g2.imageexp.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LocalDatabaseExecutorTest {
    @Test
    void testSQLFileNotNull() {
        Assertions.assertNotNull(LocalDatabaseExecutor.class.getResourceAsStream("/drawingexp.sql"));
    }
}