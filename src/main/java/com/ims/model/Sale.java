package com.ims.model;

public class Sale {
    private String date;
    private double revenue;

    public Sale(String date, double revenue) {
        this.date = date;
        this.revenue = revenue;
    }
    // Getters and setters...
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public double getRevenue() { return revenue; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
}