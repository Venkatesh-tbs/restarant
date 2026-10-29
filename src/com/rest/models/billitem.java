package com.rest.models;

// Model class representing a single item line in a bill
public class billitem {
    public int itemId;
    public String itemName;
    public double price;
    public int quantity;
    public double subTotal;

    public billitem(int itemId, String itemName, double price, int quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.subTotal = price * quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    @Override
    public String toString() {
        return String.format("%-15s Rs.%-8.2f %-6d Rs.%.2f", itemName, price, quantity, subTotal);
    }
}
