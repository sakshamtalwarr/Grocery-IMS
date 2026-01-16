package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.BrandDao;
import com.ims.dao.CategoryDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/GetProductFormDataServlet")
public class GetProductFormDataServlet extends HttpServlet {
    private final CategoryDao categoryDao = new CategoryDao();
    private final BrandDao brandDao = new BrandDao();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            Map<String, Object> formData = new HashMap<>();
            formData.put("categories", categoryDao.getAllCategories());
            formData.put("brands", brandDao.getAllBrands());
            response.getWriter().write(gson.toJson(formData));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error fetching form data\"}");
            e.printStackTrace();
        }
    }
}