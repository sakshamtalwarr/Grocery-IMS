package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.DashboardDao;
import com.ims.model.DashboardStats;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DashboardStatsServlet")
public class DashboardStatsServlet extends HttpServlet {
    private final DashboardDao dashboardDao = new DashboardDao();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            DashboardStats stats = dashboardDao.getDashboardStats();
            response.getWriter().write(gson.toJson(stats));
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"A database error occurred.\"}");
        }
    }
}