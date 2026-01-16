package com.ims.model;

import java.util.List;

public class EmployeeDashboardStats {
    private String employeeName;
    private int totalProducts;
    private int todaySalesCount;
    private int lowStockCount;
    private List<Product> lowStockProducts;
    private List<Sale> weeklyPerformance;

    // Getters and Setters for all fields...
    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public int getTotalProducts() { return totalProducts; }
    public void setTotalProducts(int totalProducts) { this.totalProducts = totalProducts; }
    public int getTodaySalesCount() { return todaySalesCount; }
    public void setTodaySalesCount(int todaySalesCount) { this.todaySalesCount = todaySalesCount; }
    public int getLowStockCount() { return lowStockCount; }
    public void setLowStockCount(int lowStockCount) { this.lowStockCount = lowStockCount; }
    public List<Product> getLowStockProducts() { return lowStockProducts; }
    public void setLowStockProducts(List<Product> lowStockProducts) { this.lowStockProducts = lowStockProducts; }
    public List<Sale> getWeeklyPerformance() { return weeklyPerformance; }
    public void setWeeklyPerformance(List<Sale> weeklyPerformance) { this.weeklyPerformance = weeklyPerformance; }
}