package com.ims.dao;

import com.ims.model.DashboardStats;
import com.ims.model.Product;
import com.ims.model.Sale;
import com.ims.util.DatabaseConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardDao {
    public DashboardStats getDashboardStats() throws SQLException {
        DashboardStats stats = new DashboardStats();
        String statsSql = "SELECT " +
                "(SELECT COUNT(*) FROM products) AS totalProducts, " +
                "(SELECT COUNT(*) FROM employees) AS totalEmployees, " +
                "(SELECT COUNT(*) FROM categories) AS totalCategories, " +
                "(SELECT COUNT(*) FROM products WHERE quantity < 10) AS lowStockCount;";
        String lowStockSql = "SELECT product_name, quantity FROM products WHERE quantity < 10 LIMIT 5;";

        try (Connection conn = DatabaseConnectionUtil.getConnection()) {
            try (PreparedStatement statPstmt = conn.prepareStatement(statsSql); ResultSet rs = statPstmt.executeQuery()) {
                if (rs.next()) {
                    stats.setTotalProducts(rs.getInt("totalProducts"));
                    stats.setTotalEmployees(rs.getInt("totalEmployees"));
                    stats.setTotalCategories(rs.getInt("totalCategories"));
                    stats.setLowStock(rs.getInt("lowStockCount"));
                }
            }
            List<Product> lowStockProducts = new ArrayList<>();
            try (PreparedStatement lowStockPstmt = conn.prepareStatement(lowStockSql); ResultSet rs = lowStockPstmt.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setProductName(rs.getString("product_name"));
                    p.setQuantity(rs.getInt("quantity"));
                    lowStockProducts.add(p);
                }
            }
            stats.setLowStockProducts(lowStockProducts);
            // Using dummy sales data for now
            List<Sale> sales = new ArrayList<>();
            sales.add(new Sale("2025-07-15", 1320));
            sales.add(new Sale("2025-07-16", 980));
            stats.setSales(sales);
        }
        return stats;
    }
}