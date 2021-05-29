package edu.fpt.swp391.g2.imageexp.database;

import edu.fpt.swp391.g2.imageexp.utils.ScriptRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The utility class for creating local database
 */
public class LocalDatabaseExecutor {
    private LocalDatabaseExecutor() {
        // EMPTY
    }

    /**
     * Create the local database
     *
     * @param connection the database connection
     * @throws SQLException if there is an SQL error
     * @throws IOException  if the internal SQL file is not found
     */
    public static void createDatabase(Connection connection) throws SQLException, IOException {
        ScriptRunner sr = new ScriptRunner(connection, false, true);
        Reader reader = new BufferedReader(new InputStreamReader(LocalDatabaseExecutor.class.getResourceAsStream("/drawingexp.sql")));
        sr.runScript(reader);
    }
}
