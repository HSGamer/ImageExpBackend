package edu.fpt.swp391.g2.imageexp.database;

import edu.fpt.swp391.g2.imageexp.utils.ScriptRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

public class LocalDatabaseExecutor {
    private LocalDatabaseExecutor() {
        // EMPTY
    }

    public static void createDatabase(Connection connection) throws SQLException, IOException {
        ScriptRunner sr = new ScriptRunner(connection, false, true);
        Reader reader = new BufferedReader(new InputStreamReader(LocalDatabaseExecutor.class.getResourceAsStream("/drawingexp.sql")));
        sr.runScript(reader);
    }
}
