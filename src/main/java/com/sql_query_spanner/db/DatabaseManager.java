package com.sql_query_spanner.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DEFAULT_USER = "postgres";
    private static final String DEFAULT_PASSWORD = "";

    public static Connection getConnection(String dbUrl, String dbUser, String dbPassword) throws SQLException {
        String url = dbUrl != null ? dbUrl : DEFAULT_URL;
        String user = dbUser != null ? dbUser : DEFAULT_USER;
        String password = dbPassword != null ? dbPassword : DEFAULT_PASSWORD;

        return DriverManager.getConnection(url, user, password);
    }

}