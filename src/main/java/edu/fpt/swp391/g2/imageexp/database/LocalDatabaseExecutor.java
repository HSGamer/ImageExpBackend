package edu.fpt.swp391.g2.imageexp.database;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;

public class LocalDatabaseExecutor {
    public static void createDatabase(Connection connection) {
        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = new BufferedReader(new InputStreamReader(LocalDatabaseExecutor.class.getResourceAsStream("/drawingexp.sql")));
        sr.runScript(reader);
    }
}
