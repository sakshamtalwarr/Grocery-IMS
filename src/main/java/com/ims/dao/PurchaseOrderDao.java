package com.ims.dao;

import com.ims.model.PurchaseOrder;
import com.ims.util.DatabaseConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderDao {
	
	// In PurchaseOrderDao.java

	public boolean placeOrder(PurchaseOrder order) throws SQLException {
	    String sql = "INSERT INTO purchase_orders (product_id, employee_id, quantity_ordered, supplier_name, order_date, expected_delivery, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    try (Connection conn = DatabaseConnectionUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, order.getProductId());
	        pstmt.setInt(2, order.getEmployeeId());
	        pstmt.setInt(3, order.getQuantityOrdered());
	        pstmt.setString(4, order.getSupplierName());
	        pstmt.setDate(5, new java.sql.Date(order.getOrderDate().getTime()));
	        pstmt.setDate(6, new java.sql.Date(order.getExpectedDelivery().getTime()));
	        pstmt.setString(7, order.getStatus());
	        
	        return pstmt.executeUpdate() > 0;
	    }
	}
	
    public List<PurchaseOrder> getAllPurchaseOrders() throws SQLException {
        List<PurchaseOrder> orders = new ArrayList<>();
        // Join with products table to get the product name
        String sql = "SELECT po.*, p.product_name FROM purchase_orders po " +
                     "JOIN products p ON po.product_id = p.product_id " +
                     "ORDER BY po.order_date DESC";
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                PurchaseOrder po = new PurchaseOrder();
                po.setPurchaseOrderId(rs.getInt("purchase_order_id"));
                po.setProductId(rs.getInt("product_id"));
                po.setProductName(rs.getString("product_name"));
                po.setEmployeeId(rs.getInt("employee_id"));
                po.setQuantityOrdered(rs.getInt("quantity_ordered"));
                po.setSupplierName(rs.getString("supplier_name"));
                po.setOrderDate(rs.getDate("order_date"));
                po.setExpectedDelivery(rs.getDate("expected_delivery"));
                po.setStatus(rs.getString("status"));
                orders.add(po);
            }
        }
        return orders;
    }
}