package com.ims.servlet;

import com.ims.dao.ProductDao;
import com.ims.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ExportDataServlet")
public class ExportDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductDao productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dataType = request.getParameter("type");

        if ("products".equals(dataType)) {
            exportProducts(response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid data type for export.");
        }
    }

    private void exportProducts(HttpServletResponse response) throws IOException {
        // Set HTTP headers to trigger a file download
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"products_export.csv\"");

        try (PrintWriter writer = response.getWriter()) {
            // Write the CSV header row
            writer.println("ProductID,ProductName,Description,Price,Quantity,CategoryID,BrandID,Status");

            // Fetch all products from the database
            List<Product> products = productDao.getAllProducts();

            // Write each product as a new row in the CSV file
            for (Product product : products) {
                writer.printf("%d,\"%s\",\"%s\",%.2f,%d,%d,%d,\"%s\"\n",
                        product.getProductId(),
                        escapeCsv(product.getProductName()),
                        escapeCsv(product.getDescription()),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getCategoryId(),
                        product.getBrandId(),
                        escapeCsv(product.getStatus()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // If something goes wrong, send a server error status
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Database error occurred during export.");
        }
    }

    /**
     * A helper method to safely handle commas and quotes within CSV data fields.
     * @param value The string to escape.
     * @return A CSV-safe string.
     */
    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        // If the value contains a comma, quote, or newline, wrap it in double quotes.
        // Also, escape any existing double quotes by doubling them up.
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}