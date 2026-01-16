package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.EmployeeDao;
import com.ims.model.Employee;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/GetEmployeeDetailsServlet")
public class GetEmployeeDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final EmployeeDao employeeDao = new EmployeeDao();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            Employee employee = employeeDao.getEmployeeById(employeeId);
            if (employee != null) {
                response.getWriter().write(gson.toJson(employee));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Employee not found.\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid employee ID.\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}	