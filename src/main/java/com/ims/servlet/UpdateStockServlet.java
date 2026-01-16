package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateStockServlet")
@MultipartConfig
public class UpdateStockServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductDao productDao = new ProductDao();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int newQuantity = Integer.parseInt(request.getParameter("newQuantity"));

            if (productDao.updateProductStock(productId, newQuantity)) {
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Stock updated successfully.\"}");
            } else {
                throw new Exception("Update failed, product not found or no change in quantity.");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Failed to update stock.\"}");
            e.printStackTrace();
        }
    }
}