package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.ProductDao;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteProductServlet")
@MultipartConfig
public class DeleteProductServlet extends HttpServlet {
    private final ProductDao productDao = new ProductDao();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            int productId = Integer.parseInt(req.getParameter("productId"));
            if (productDao.deleteProduct(productId)) {
                resp.getWriter().write("{\"status\": \"success\", \"message\": \"Product deleted.\"}");
            } else {
                resp.setStatus(400);
                resp.getWriter().write("{\"status\": \"error\", \"message\": \"Product not found.\"}");
            }
        } catch (Exception e) {
            resp.setStatus(500);
            e.printStackTrace();
        }
    }
}