//package com.ims.dao;
//
//import com.ims.model.Product;
//import com.ims.util.DatabaseConnectionUtil;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ProductDao {
//
//    public int addProduct(Product product) throws SQLException {
//        String sql = "INSERT INTO products (product_name, description, price, quantity, category_id, brandId, image_Path, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        int generatedId = -1;
//
//        try (Connection conn = DatabaseConnectionUtil.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            
//            pstmt.setString(1, product.getProductName());
//            pstmt.setString(2, product.getDescription());
//            pstmt.setDouble(3, product.getPrice());
//            pstmt.setInt(4, product.getQuantity());
//            pstmt.setInt(5, product.getCategoryId());
//            pstmt.setInt(6, product.getBrandId());
//            pstmt.setString(7, product.getImagePath());
//            pstmt.setString(8, product.getStatus());
//
//            int affectedRows = pstmt.executeUpdate();
//            if (affectedRows > 0) {
//                try (ResultSet rs = pstmt.getGeneratedKeys()) {
//                    if (rs.next()) {
//                        generatedId = rs.getInt(1);
//                    }
//                }
//            }
//        }
//        return generatedId;
//    }
//
//    public Product getProductById(int productId) throws SQLException {
//        Product product = null;
//        String sql = "SELECT * FROM products WHERE product_id = ?";
//
//        try (Connection conn = DatabaseConnectionUtil.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            
//            pstmt.setInt(1, productId);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    product = new Product();
//                    product.setProductId(rs.getInt("product_id"));
//                    product.setProductName(rs.getString("product_name"));
//                    product.setDescription(rs.getString("description"));
//                    product.setPrice(rs.getDouble("price"));
//                    product.setQuantity(rs.getInt("quantity"));
//                    product.setCategoryId(rs.getInt("category_id"));
//                    product.setBrandId(rs.getInt("brand_id"));
//                    product.setImagePath(rs.getString("image_Path"));
//                    product.setStatus(rs.getString("status"));
//                }
//            }
//        }
//        return product;
//    }
//
//    public boolean updateProduct(Product product) throws SQLException {
//        String sql = "UPDATE products SET product_name = ?, description = ?, price = ?, quantity = ?, category_id = ?, brandId = ?, status = ?, image_Path = ? WHERE product_id = ?";
//        
//        try (Connection conn = DatabaseConnectionUtil.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            
//            pstmt.setString(1, product.getProductName());
//            pstmt.setString(2, product.getDescription());
//            pstmt.setDouble(3, product.getPrice());
//            pstmt.setInt(4, product.getQuantity());
//            pstmt.setInt(5, product.getCategoryId());
//            pstmt.setInt(6, product.getBrandId());
//            pstmt.setString(7, product.getStatus());
//            pstmt.setString(8, product.getImagePath());
//            pstmt.setInt(9, product.getProductId());
//
//            return pstmt.executeUpdate() > 0;
//        }
//    }
//
//    public boolean deleteProduct(int productId) throws SQLException {
//        String sql = "DELETE FROM products WHERE product_id = ?";
//        try (Connection conn = DatabaseConnectionUtil.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, productId);
//            return pstmt.executeUpdate() > 0;
//        }
//    }
//
//    public List<Product> getAllProducts() throws SQLException {
//        List<Product> products = new ArrayList<>();
//        String sql = "SELECT * FROM products ORDER BY product_id ASC";
//
//        try (Connection conn = DatabaseConnectionUtil.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             ResultSet rs = pstmt.executeQuery()) {
//            
//            while (rs.next()) {
//                Product product = new Product();
//                product.setProductId(rs.getInt("product_id"));
//                product.setProductName(rs.getString("product_name"));
//                product.setDescription(rs.getString("description"));
//                product.setPrice(rs.getDouble("price"));
//                product.setQuantity(rs.getInt("quantity"));
//                product.setCategoryId(rs.getInt("category_id"));
//                product.setBrandId(rs.getInt("brand_id"));
//                product.setImagePath(rs.getString("image_Path"));
//                product.setStatus(rs.getString("status"));
//                products.add(product);
//            }
//        }
//        return products;
//    }
//}
package com.ims.dao;

import com.ims.model.Product;
import com.ims.util.DatabaseConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
	
	
	public boolean updateProductStock(int productId, int newQuantity) throws SQLException {
	    String sql = "UPDATE products SET quantity = ? WHERE product_id = ?";
	    try (Connection conn = DatabaseConnectionUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, newQuantity);
	        pstmt.setInt(2, productId);
	        return pstmt.executeUpdate() > 0;
	    }
	}

    public int addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (product_name, description, price, quantity, category_id, brand_id, image_path, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setInt(5, product.getCategoryId());
            pstmt.setInt(6, product.getBrandId());
            pstmt.setString(7, product.getImagePath());
            pstmt.setString(8, product.getStatus());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        }
        return generatedId;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY product_id ASC";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setCategoryId(rs.getInt("category_id"));
                // FIX: Changed "brandId" to "brand_id" to match database schema
                product.setBrandId(rs.getInt("brand_id")); 
                product.setImagePath(rs.getString("image_path"));
                product.setStatus(rs.getString("status"));
                products.add(product);
            }
        }
        return products;
    }
    
    // ... other methods like getProductById, updateProduct, deleteProduct should also be checked for the correct column names.
}
