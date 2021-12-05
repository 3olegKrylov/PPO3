package main.java.ru.akirakozov.sd.refactoring.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    private static final String SQL_URL = "jdbc:sqlite:test.db";

    public static Statement getStatement() throws SQLException {
        return DriverManager.getConnection(SQL_URL).createStatement();
    }

    public static void initDB() throws SQLException {
        try (Statement stmt = getStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME  TEXT    NOT NULL, " +
                    " PRICE INT     NOT NULL)";

            stmt.executeUpdate(sql);
        }
    }
}
