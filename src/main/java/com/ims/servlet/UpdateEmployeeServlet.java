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
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UpdateEmployeeServlet")
@MultipartConfig
public class UpdateEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final EmployeeDao employeeDao = new EmployeeDao();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            Employee employee = new Employee();
            employee.setEmployeeId(Integer.parseInt(request.getParameter("employeeId")));
            employee.setName(request.getParameter("name"));
            employee.setEmail(request.getParameter("email"));
            employee.setRole(request.getParameter("role"));
            employee.setPhoneNumber(request.getParameter("phone_number"));
            employee.setSalary(Double.parseDouble(request.getParameter("salary")));
            employee.setStatus(request.getParameter("status"));
            
            // This line was missing, causing the error.
            employee.setPhotoUrl(request.getParameter("photo_url"));

            if (employeeDao.updateEmployee(employee)) {
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Employee updated successfully.\"}");
            } else {
                // This case handles if the employee ID doesn't exist in the DB
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"status\": \"error\", \"message\": \"Employee not found, could not update.\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Failed to update employee. Check server logs.\"}");
            e.printStackTrace();
        }
    }
}
