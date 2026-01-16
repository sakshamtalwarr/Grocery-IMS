package com.ims.dao;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ims.model.Employee;
import com.ims.util.DatabaseConnectionUtil;


public class EmployeeDao {
	
	/**
	 * Retrieves a single employee's details from the database by their ID.
	 * @param employeeId The ID of the employee to retrieve.
	 * @return An Employee object if found, null otherwise.
	 */
	// UPDATE getEmployeeById()
	public Employee getEmployeeById(int employeeId) throws SQLException {
	    Employee employee = null;
	    String sql = "SELECT * FROM employees WHERE employee_id = ?";
	    try (Connection conn = DatabaseConnectionUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, employeeId);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            employee = new Employee();
	            // ... set other fields
	            employee.setSalary(rs.getDouble("salary"));
	            employee.setStatus(rs.getString("status"));
	            employee.setPhotoUrl(rs.getString("photo_url")); // <-- ADD THIS
	        }
	    }
	    return employee;
	}

	/**
	 * Updates an existing employee's details in the database.
	 * @param employee The Employee object containing the updated information.
	 * @return true if the update was successful, false otherwise.
	 */
	// UPDATE updateEmployee()
	public boolean updateEmployee(Employee employee) throws SQLException {
	    // Add photo_url to the UPDATE statement
	    String sql = "UPDATE employees SET name = ?, email = ?, role = ?, phone_number = ?, salary = ?, status = ?, photo_url = ? WHERE employee_id = ?";
	    try (Connection conn = DatabaseConnectionUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        // ... set other parameters
	        pstmt.setDouble(5, employee.getSalary());
	        pstmt.setString(6, employee.getStatus());
	        pstmt.setString(7, employee.getPhotoUrl()); // <-- ADD THIS
	        pstmt.setInt(8, employee.getEmployeeId()); // <-- Note the parameter index change
	        return pstmt.executeUpdate() > 0;
	    }
	}
	// In EmployeeDao.java, replace the checkLogin method with this one.

	public Employee checkLogin(String username, String password) {
	    Employee employee = null;
	    // Step 1: Find the user by username ONLY.
	    String sql = "SELECT * FROM employees WHERE username = ?";
	    
	    System.out.println("--- DAO DEBUG ---");
	    System.out.println("Searching for user: [" + username + "]");

	    try (Connection conn = DatabaseConnectionUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            // Step 2: User was found. Now get the stored password.
	            String storedPassword = rs.getString("password");
	            System.out.println("User FOUND in database. Stored password is: [" + storedPassword + "]");
	            System.out.println("Password from login form is: [" + password + "]");

	            // Step 3: Compare the passwords.
	            if (password != null && password.equals(storedPassword)) {
	                // Passwords match! Login is successful.
	                System.out.println("Password comparison: SUCCESS. Logging user in.");
	                employee = new Employee();
	                employee.setEmployeeId(rs.getInt("employee_id"));
	                employee.setUsername(rs.getString("username"));
	                employee.setName(rs.getString("name"));
	                employee.setEmail(rs.getString("email"));
	                employee.setRole(rs.getString("role"));
	                employee.setStatus(rs.getString("status"));
	            } else {
	                // Passwords do NOT match.
	                System.out.println("Password comparison: FAILED. Passwords do not match.");
	            }
	        } else {
	            // User was not found by username.
	            System.out.println("User NOT FOUND in database with that username.");
	        }
	    } catch (SQLException e) {
	        System.out.println("DAO DEBUG: An SQL error occurred.");
	        e.printStackTrace();
	    }
	    
	    System.out.println("--- END DAO DEBUG ---");
	    return employee;
	}

    // This method was missing from your previous file
	// In EmployeeDao.java

	// UPDATE addEmployee()

    public boolean addEmployee(Employee employee) throws SQLException {
        // This query has 9 placeholders
        String sql = "INSERT INTO employees (username, name, email, password, role, phone_number, salary, status, photo_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // FIX: Ensure all 9 parameters are set correctly
            pstmt.setString(1, employee.getUsername());
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPassword());
            pstmt.setString(5, employee.getRole());
            pstmt.setString(6, employee.getPhoneNumber());
            pstmt.setDouble(7, employee.getSalary());
            pstmt.setString(8, employee.getStatus());
            pstmt.setString(9, employee.getPhotoUrl());

            return pstmt.executeUpdate() > 0;
        }
    }
    
	
	public boolean registerEmployee(Employee employee) throws SQLException {
	    String sql = "INSERT INTO employees (username, name, email, password, role, status) VALUES (?, ?, ?, ?, ?, ?)";
	    try (Connection conn = DatabaseConnectionUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, employee.getUsername());
	        pstmt.setString(2, employee.getName());
	        pstmt.setString(3, employee.getEmail());
	        pstmt.setString(4, employee.getPassword());
	        pstmt.setString(5, employee.getRole());
	        pstmt.setString(6, employee.getStatus());

	        int rowsAffected = pstmt.executeUpdate();
	        return rowsAffected > 0;
	    }
	}
	// UPDATE getAllEmployees()
	public List<Employee> getAllEmployees() throws SQLException {
	    List<Employee> employees = new ArrayList<>();
	    // Add salary and photo_url to the SELECT statement
	    String sql = "SELECT employee_id, username, name, email, role, status, salary, photo_url FROM employees ORDER BY employee_id ASC";
	    try (Connection conn = DatabaseConnectionUtil.getConnection();
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(sql)) {
	        while (rs.next()) {
	            Employee employee = new Employee();
	            employee.setEmployeeId(rs.getInt("employee_id"));
	            employee.setUsername(rs.getString("username"));
	            employee.setName(rs.getString("name"));
	            employee.setEmail(rs.getString("email"));
	            employee.setRole(rs.getString("role"));
	            employee.setStatus(rs.getString("status"));
	            employee.setSalary(rs.getDouble("salary")); // <-- ADD THIS
	            employee.setPhotoUrl(rs.getString("photo_url")); // <-- ADD THIS
	            employees.add(employee);
	        }
	    }
	    return employees;
	}

public boolean deleteEmployee(int employeeId) throws SQLException {
    String sql = "DELETE FROM employees WHERE employee_id = ?";
    try (Connection conn = DatabaseConnectionUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, employeeId);
        return pstmt.executeUpdate() > 0;
    }
}
}