package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.ProductDao;
import com.ims.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AddProductServlet")
@MultipartConfig // Essential for reading data from FormData
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductDao productDao = new ProductDao();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            Product product = new Product();
            product.setProductName(request.getParameter("productName"));
            product.setDescription(request.getParameter("description"));
            product.setPrice(Double.parseDouble(request.getParameter("price")));
            product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
            product.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
            product.setBrandId(Integer.parseInt(request.getParameter("brandId")));
            
            // Set a default status for new products
            product.setStatus("Available");
            
            // Image path handling would be more complex; for now, we'll leave it null.
            product.setImagePath(null);

            productDao.addProduct(product);
            
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Product added successfully!\"}");

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Invalid number format for price or quantity.\"}");
            e.printStackTrace();
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Database error while adding product.\"}");
            e.printStackTrace();
        }
    }
}