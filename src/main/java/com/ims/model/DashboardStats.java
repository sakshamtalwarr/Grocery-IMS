package com.ims.model;

import java.util.List;

public class DashboardStats {
    private int totalProducts;
    private int totalEmployees;
    private int totalCategories;
    private int lowStock;
    private List<Product> lowStockProducts;
    private List<Sale> sales;
    
    // Getters and setters for all fields...
    public int getTotalProducts() { return totalProducts; }
    public void setTotalProducts(int totalProducts) { this.totalProducts = totalProducts; }
    public int getTotalEmployees() { return totalEmployees; }
    public void setTotalEmployees(int totalEmployees) { this.totalEmployees = totalEmployees; }
    public int getTotalCategories() { return totalCategories; }
    public void setTotalCategories(int totalCategories) { this.totalCategories = totalCategories; }
    public int getLowStock() { return lowStock; }
    public void setLowStock(int lowStock) { this.lowStock = lowStock; }
    public List<Product> getLowStockProducts() { return lowStockProducts; }
    public void setLowStockProducts(List<Product> lowStockProducts) { this.lowStockProducts = lowStockProducts; }
    public List<Sale> getSales() { return sales; }
    public void setSales(List<Sale> sales) { this.sales = sales; }
}