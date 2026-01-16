package com.ims.model;

import java.util.Date;

public class PurchaseOrder {
    private int purchaseOrderId;
    private int productId;
    private String productName; // To make display easier
    private int employeeId;
    private int quantityOrdered;
    private String supplierName;
    private Date orderDate;
    private Date expectedDelivery;
    private String status;

    // Getters and Setters for all fields...
    public int getPurchaseOrderId() { return purchaseOrderId; }
    public void setPurchaseOrderId(int id) { this.purchaseOrderId = id; }
    public int getProductId() { return productId; }
    public void setProductId(int id) { this.productId = id; }
    public String getProductName() { return productName; }
    public void setProductName(String name) { this.productName = name; }
    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int id) { this.employeeId = id; }
    public int getQuantityOrdered() { return quantityOrdered; }
    public void setQuantityOrdered(int qty) { this.quantityOrdered = qty; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String name) { this.supplierName = name; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date date) { this.orderDate = date; }
    public Date getExpectedDelivery() { return expectedDelivery; }
    public void setExpectedDelivery(Date date) { this.expectedDelivery = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}