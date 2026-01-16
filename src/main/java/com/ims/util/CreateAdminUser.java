// package com.ims.util;
package com.ims.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateAdminUser {

    // Method to create a user with a plaintext password
    private static void createUser(Connection conn, String username, String name, String email, String password, String role, String status) throws SQLException {
        String insertUserSQL = "INSERT INTO employees (username, name, email, password, role, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement insertStmt = conn.prepareStatement(insertUserSQL)) {
            insertStmt.setString(1, username);
            insertStmt.setString(2, name);
            insertStmt.setString(3, email);
            insertStmt.setString(4, password); // Store the plaintext password
            insertStmt.setString(5, role);
            insertStmt.setString(6, status);

            insertStmt.executeUpdate();
            System.out.println("User '" + username + "' created successfully.");

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // MySQL error for duplicate entry
                System.out.println("User '" + username + "' already exists.");
            } else {
                throw e;
            }
        }
    }

    public static void main(String[] args) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS employees (" +
                "employee_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) NOT NULL UNIQUE, " +
                "name VARCHAR(100), " +
                "email VARCHAR(100) NOT NULL UNIQUE, " +
                "password VARCHAR(255) NOT NULL, " + // Plaintext password column
                "role VARCHAR(20) NOT NULL, " +
                "phone_number VARCHAR(20), " +
                "salary DOUBLE, " +
                "status VARCHAR(20) NOT NULL" +
                ")";

        try (Connection conn = DatabaseConnectionUtil.getConnection()) {
            if (conn == null) {
                System.err.println("Failed to connect to the database.");
                return;
            }

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS users"); // Drop the old, incorrect table
                stmt.execute("DROP TABLE IF EXISTS employees"); // Drop the old employees table to ensure a clean slate
                stmt.execute(createTableSQL);
                System.out.println("Table 'employees' created successfully.");
            }

            // Create default users with plaintext passwords
            createUser(conn, "admin", "Admin User", "admin@inventory.com", "admin123", "Admin", "Active");
            createUser(conn, "employee", "Employee User", "employee@inventory.com", "emp123", "Employee", "Active");

        } catch (SQLException e) {
            System.err.println("Database operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}