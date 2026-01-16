package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.EmployeeDashboardDao;
import com.ims.model.Employee;
import com.ims.model.EmployeeDashboardStats;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/EmployeeDashboardServlet")
public class EmployeeDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final EmployeeDashboardDao dashboardDao = new EmployeeDashboardDao();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false); // Do not create a new session if one doesn't exist
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Authentication required. Please log in.\"}");
            return;
        }

        try {
            Employee loggedInEmployee = (Employee) session.getAttribute("loggedInUser");
            EmployeeDashboardStats stats = dashboardDao.getEmployeeDashboardStats(loggedInEmployee);
            response.getWriter().write(gson.toJson(stats));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error occurred.\"}");
            e.printStackTrace();
        }
    }
}