package com.rest.models;

// Model class representing a restaurant menu item (e.g., Idli, Dosa, Pongal, Biriyani)
public class menuitem {
    // Primary Key: Unique item ID
    public int id;
    
    // Name of the menu item (e.g. Idli, Dosa)
    public String name;
    
    // Price of the menu item
    public double price;

    // Constructor to initialize a menu item
    public menuitem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item ID: " + id + " | Name: " + name + " | Price: Rs." + String.format("%.2f", price);
    }
}
