package com.ims.dao;

import com.ims.model.Category;
import com.ims.util.DatabaseConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
	
	
	public void addCategory(Category category) throws SQLException {
	    String sql = "INSERT INTO categories (category_name) VALUES (?)";
	    try (Connection conn = DatabaseConnectionUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, category.getCategoryName());
	        pstmt.executeUpdate();
	    }
	}
	
	// In CategoryDao.java


    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY category_name ASC";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                categories.add(category);
            }
        }
        return categories;
        
    }
    public boolean deleteCategory(int categoryId) throws SQLException {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            return pstmt.executeUpdate() > 0;
        }
    }
}	

