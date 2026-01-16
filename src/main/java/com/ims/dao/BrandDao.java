package com.ims.dao;

import com.ims.model.Brand;
import com.ims.util.DatabaseConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandDao {
    public List<Brand> getAllBrands() throws SQLException {
        List<Brand> brands = new ArrayList<>();
        String sql = "SELECT * FROM brands ORDER BY brand_name ASC";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setBrandId(rs.getInt("brand_id"));
                brand.setBrandName(rs.getString("brand_name"));
                brands.add(brand);
            }
        }
        return brands;
    }
}