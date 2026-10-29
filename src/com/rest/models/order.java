package com.rest.models;

// Model class representing a customer order (corresponds to the 'orders' table)
public class order {
    // Primary Key: Unique Order ID
    public int id;
    
    // Foreign Key: References table.id
    public int tableId;
    
    // Customer Name (Required)
    public String customerName;
    
    // Total order amount
    public double totalAmount;
    
    // Order Status: e.g. "Pending", "Completed", "Cancelled"
    public String status;

    // Constructor to initialize an order
    public order(int id, int tableId, String customerName, double totalAmount, String status) {
        this.id = id;
        this.tableId = tableId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getTableId() {
        return tableId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order ID: " + id + " | Table ID: " + tableId + " | Customer: " + customerName + 
               " | Total: $" + String.format("%.2f", totalAmount) + " | Status: " + status;
    }
}
