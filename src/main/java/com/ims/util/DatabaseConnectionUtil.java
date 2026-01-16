package com.ims.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtil {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/inventory_management?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String JDBC_USERNAME = "root"; // Your DB username
    private static final String JDBC_PASSWORD = "28112003"; // Your DB password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }
}