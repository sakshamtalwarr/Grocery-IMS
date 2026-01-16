package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.CategoryDao;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteCategoryServlet")
@MultipartConfig
public class DeleteCategoryServlet extends HttpServlet {
    private final CategoryDao categoryDao = new CategoryDao();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            if (categoryDao.deleteCategory(categoryId)) {
                resp.getWriter().write("{\"status\": \"success\", \"message\": \"Category deleted.\"}");
            } else {
                resp.setStatus(400);
                resp.getWriter().write("{\"status\": \"error\", \"message\": \"Category not found.\"}");
            }
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"status\": \"error\", \"message\": \"Cannot delete category as it may be in use by products.\"}");
            e.printStackTrace();
        }
    }
}