//package com.ims.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DatabaseConnectionUtil {
//    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/inventory_management?useSSL=false&allowPublicKeyRetrieval=true";
//    private static final String JDBC_USERNAME = "root"; // Your DB username
//    private static final String JDBC_PASSWORD = "28112003"; // Your DB password
//
//    public static Connection getConnection() throws SQLException {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
//        } catch (ClassNotFoundException e) {
//            throw new SQLException("MySQL JDBC Driver not found.", e);
//        }
//    }
//}
package com.ims.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtil {

    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 1. Attempt to read settings from Railway Environment Variables
            String dbHost = System.getenv("MYSQLHOST");
            String dbPort = System.getenv("MYSQLPORT");
            String dbUser = System.getenv("MYSQLUSER");
            String dbPass = System.getenv("MYSQLPASSWORD");
            String dbName = System.getenv("MYSQLDATABASE");

            String url;
            String user;
            String pass;

            if (dbHost != null) {
                // --- CASE 1: WE ARE ON RAILWAY --- 
                // Build the URL using the cloud variables
                url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useSSL=false&allowPublicKeyRetrieval=true";
                user = dbUser;
                pass = dbPass;
                System.out.println("✅ Connecting to Railway Database at: " + dbHost);
            } else {
                // --- CASE 2: WE ARE ON LOCALHOST ---
                // Fallback to your local hardcoded settings
                url = "jdbc:mysql://localhost:3306/inventory_management?useSSL=false&allowPublicKeyRetrieval=true";
                user = "root";
                pass = "28112003"; 
                System.out.println("🏠 Connecting to Localhost Database");
            }

            // 2. Establish the connection
            return DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }
}