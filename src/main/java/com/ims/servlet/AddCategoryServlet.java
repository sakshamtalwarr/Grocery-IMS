package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.CategoryDao;
import com.ims.model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AddCategoryServlet")
@MultipartConfig
public class AddCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryDao categoryDao = new CategoryDao();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String categoryName = request.getParameter("categoryName");
            if (categoryName == null || categoryName.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"status\": \"error\", \"message\": \"Category name cannot be empty.\"}");
                return;
            }

            Category category = new Category();
            category.setCategoryName(categoryName.trim());
            categoryDao.addCategory(category);

            response.getWriter().write("{\"status\": \"success\", \"message\": \"Category added successfully!\"}");

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Database error: Could not add category. It may already exist.\"}");
            e.printStackTrace();
        }
    }
}