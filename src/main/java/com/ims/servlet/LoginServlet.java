package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.EmployeeDao;
import com.ims.model.Employee;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeDao employeeDao;
    private final Gson gson = new Gson();

    @Override
    public void init() {
        employeeDao = new EmployeeDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String contextPath = request.getContextPath();

        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = employeeDao.checkLogin(username, password);

        if (employee != null) {
            // Login successful
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", employee);
            session.setAttribute("userRole", employee.getRole());

            // Redirect to the correct portal based on the user's role
            if ("Admin".equals(employee.getRole())) {
                response.sendRedirect(contextPath + "/AdminPortal.html");
            } else {
                response.sendRedirect(contextPath + "/EmployeePortal.html");
            }
        } else {
            // Login failed: redirect back to the employee login page with an error flag
            // You can add JavaScript to your login page to show an alert if this flag is present
            response.sendRedirect(contextPath + "/EmployeeLogin.html?error=1");
        }
    }
    
    private static class LoginResponse {
        String status;
        String message;
        String role;
        public LoginResponse(String status, String message, String role) {
            this.status = status;
            this.message = message;
            this.role = role;
        }
    }
}