package com.rest.repo;

import com.rest.models.order;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Repository class managing CRUD operations and file persistence for the 'orders' table
public class orderrepo {

    // In-memory list to store order records
    private final List<order> orders = new ArrayList<>();
    
    // File path used to persist orders to disk
    private final String FILE_NAME = "orders.txt";

    public orderrepo() {
        // Load existing orders from file
        loadFromFile();

        // Seed with sample initial records if empty
        if (orders.isEmpty()) {
            orders.add(new order(101, 1, "John Doe", 25.50, "Pending"));
            orders.add(new order(102, 2, "Alice Smith", 42.00, "Completed"));
            saveToFile();
        }
    }

    // View: Get all orders
    public List<order> getAll() {
        return orders;
    }

    // View: Find an order by its ID
    public order getById(int id) {
        for (order ord : orders) {
            if (ord.getId() == id) {
                return ord;
            }
        }
        return null;
    }

    // Add: Insert a new order
    public boolean add(order ord) {
        if (getById(ord.getId()) != null) {
            return false; // Order with this ID already exists
        }
        orders.add(ord);
        saveToFile();
        return true;
    }

    // Edit: Update an existing order
    public boolean update(int id, String customerName, double totalAmount, String status) {
        order ord = getById(id);
        if (ord != null) {
            ord.setCustomerName(customerName);
            ord.setTotalAmount(totalAmount);
            ord.setStatus(status);
            saveToFile();
            return true;
        }
        return false;
    }

    // Delete: Remove an order by ID
    public boolean delete(int id) {
        order ord = getById(id);
        if (ord != null) {
            orders.remove(ord);
            saveToFile();
            return true;
        }
        return false;
    }

    // Load orders from file for persistence
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    // format: id,tableId,customerName,totalAmount,status
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        int id = Integer.parseInt(parts[0].trim());
                        int tableId = Integer.parseInt(parts[1].trim());
                        String customerName = parts[2].trim();
                        double amount = Double.parseDouble(parts[3].trim());
                        String status = parts[4].trim();
                        orders.add(new order(id, tableId, customerName, amount, status));
                    }
                }
            }
        } catch (Exception e) {
            // Silently continue if file format mismatch
        }
    }

    // Save orders to file so changes persist
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (order ord : orders) {
                writer.println(ord.getId() + "," + ord.getTableId() + "," + 
                               ord.getCustomerName() + "," + ord.getTotalAmount() + "," + ord.getStatus());
            }
        } catch (Exception e) {
            System.out.println("Error saving orders to file: " + e.getMessage());
        }
    }
}
