package com.ims.servlet;

import com.ims.dao.EmployeeDao;
import com.ims.model.Employee;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeDao employeeDao;

    @Override
    public void init() {
        employeeDao = new EmployeeDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Employee newEmployee = new Employee();
        newEmployee.setUsername(username);
        newEmployee.setEmail(email);
        newEmployee.setPassword(password);
        newEmployee.setName(username); 
        newEmployee.setRole("Employee"); 
        newEmployee.setStatus("Active");

        String contextPath = request.getContextPath();
        try {
            boolean success = employeeDao.registerEmployee(newEmployee);
            if (success) {
                response.sendRedirect(contextPath + "/RegistrationSuccess.html");
            } else {
                response.sendRedirect(contextPath + "/RegisterEmployee.html?error=failed");
            }
        } catch (SQLException e) {
            // MySQL error code for duplicate entry is 1062
            if (e.getErrorCode() == 1062) {
                System.err.println("Registration failed: Duplicate entry for username or email.");
                response.sendRedirect(contextPath + "/RegisterEmployee.html?error=duplicate");
            } else {
                System.err.println("Registration failed due to SQL error: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect(contextPath + "/RegisterEmployee.html?error=db");
            }
        }
    }
}