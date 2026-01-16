package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.EmployeeDao;
import com.ims.model.Employee;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UpdateMyProfileServlet")
@MultipartConfig
public class UpdateMyProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final EmployeeDao employeeDao = new EmployeeDao();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Employee currentEmployee = (Employee) session.getAttribute("loggedInUser");
            
            // Create a new employee object with updated details from the form
            Employee updatedEmployee = new Employee();
            updatedEmployee.setEmployeeId(currentEmployee.getEmployeeId()); // Use ID from session
            updatedEmployee.setName(request.getParameter("name"));
            updatedEmployee.setEmail(request.getParameter("email"));
            updatedEmployee.setPhoneNumber(request.getParameter("phone_number"));
            
            // Carry over non-editable fields
            updatedEmployee.setRole(currentEmployee.getRole());
            updatedEmployee.setSalary(currentEmployee.getSalary());
            updatedEmployee.setStatus(currentEmployee.getStatus());
            updatedEmployee.setPhotoUrl(currentEmployee.getPhotoUrl());


            if (employeeDao.updateEmployee(updatedEmployee)) {
                // IMPORTANT: Update the user object in the session as well
                session.setAttribute("loggedInUser", updatedEmployee);
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Profile updated successfully.\"}");
            } else {
                throw new SQLException("Update failed, employee not found.");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Failed to update profile.\"}");
            e.printStackTrace();
        }
    }
}