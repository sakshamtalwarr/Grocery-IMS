package com.ims.dao;

import com.ims.model.Employee;
import com.ims.model.EmployeeDashboardStats;
import com.ims.model.Product;
import com.ims.model.Sale;
import com.ims.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDashboardDao {

    public EmployeeDashboardStats getEmployeeDashboardStats(Employee employee) throws SQLException {
        EmployeeDashboardStats stats = new EmployeeDashboardStats();
        stats.setEmployeeName(employee.getName());

        String sql = "SELECT " +
                "(SELECT COUNT(*) FROM products) AS totalProducts, " +
                "(SELECT COUNT(*) FROM products WHERE quantity < 10) AS lowStockCount;"; // Low stock threshold is 10
        
        String lowStockSql = "SELECT product_name, quantity FROM products WHERE quantity < 10 LIMIT 5;";

        try (Connection conn = DatabaseConnectionUtil.getConnection()) {
            // Get general stats
            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    stats.setTotalProducts(rs.getInt("totalProducts"));
                    stats.setLowStockCount(rs.getInt("lowStockCount"));
                }
            }
            
            // Get low stock product list
            List<Product> lowStockProducts = new ArrayList<>();
            try (PreparedStatement pstmt = conn.prepareStatement(lowStockSql); ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setProductName(rs.getString("product_name"));
                    p.setQuantity(rs.getInt("quantity"));
                    lowStockProducts.add(p);
                }
            }
            stats.setLowStockProducts(lowStockProducts);
            
            // --- DUMMY DATA FOR SALES (to be replaced later) ---
            stats.setTodaySalesCount(15); // Dummy number of sales
            List<Sale> performance = new ArrayList<>();
            performance.add(new Sale("Mon", 1200));
            performance.add(new Sale("Tue", 1500));
            performance.add(new Sale("Wed", 950));
            stats.setWeeklyPerformance(performance);
            // --- END DUMMY DATA ---
        }
        
     // In EmployeeDashboardDao.java, inside the getEmployeeDashboardStats method

     // --- DUMMY DATA FOR SALES (to be replaced later) ---
     stats.setTodaySalesCount(15); // Dummy number of sales
     List<Sale> performance = new ArrayList<>();
     performance.add(new Sale("Mon", 1200));
     performance.add(new Sale("Tue", 1550));
     performance.add(new Sale("Wed", 950));
     performance.add(new Sale("Thu", 1800));
     performance.add(new Sale("Fri", 2100));
     performance.add(new Sale("Sat", 2500));
     performance.add(new Sale("Sun", 1300));
     stats.setWeeklyPerformance(performance);
     // --- END DUMMY DATA ---
        return stats;
    }
}