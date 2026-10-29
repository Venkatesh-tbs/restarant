package com.rest.service;

import com.rest.models.order;
import com.rest.repo.orderrepo;
import com.rest.repo.tablerepo;
import java.util.List;

// Service layer handling business logic and validations for the 'orders' table
public class orderservice {

    private final orderrepo repo;
    private final tablerepo tableRepo;

    public orderservice(tablerepo tableRepo) {
        this.repo = new orderrepo();
        this.tableRepo = tableRepo;
    }

    public orderservice(orderrepo repo, tablerepo tableRepo) {
        this.repo = repo;
        this.tableRepo = tableRepo;
    }

    // View: Get all orders
    public List<order> getAll() {
        return repo.getAll();
    }

    // View: Get a single order by ID
    public order getById(int id) {
        if (id <= 0) {
            System.out.println("[ERROR] Order ID must be positive.");
            return null;
        }
        return repo.getById(id);
    }

    // Add: Validate inputs and create a new order
    public boolean addOrder(int id, int tableId, String customerName, double totalAmount) {
        // Validation: Order ID
        if (id <= 0) {
            System.out.println("[ERROR] Order ID must be greater than 0.");
            return false;
        }
        if (repo.getById(id) != null) {
            System.out.println("[ERROR] Order with ID " + id + " already exists.");
            return false;
        }

        // Validation: Foreign Key Check (Table must exist)
        if (tableRepo.getById(tableId) == null) {
            System.out.println("[ERROR] Cannot place order: Table ID " + tableId + " does not exist.");
            return false;
        }

        // Validation: Required Customer Name
        if (customerName == null || customerName.trim().isEmpty()) {
            System.out.println("[ERROR] Customer name cannot be empty.");
            return false;
        }

        // Validation: Total Amount
        if (totalAmount < 0) {
            System.out.println("[ERROR] Total amount cannot be negative.");
            return false;
        }

        // Create new order with default status 'Pending'
        order newOrder = new order(id, tableId, customerName.trim(), totalAmount, "Pending");
        boolean success = repo.add(newOrder);
        if (success) {
            System.out.println("[SUCCESS] Order " + id + " for customer '" + customerName.trim() + "' created successfully.");
        } else {
            System.out.println("[ERROR] Failed to save order.");
        }
        return success;
    }

    // Edit: Update details of an existing order
    public boolean updateOrder(int id, String customerName, double totalAmount, String status) {
        if (id <= 0) {
            System.out.println("[ERROR] Invalid Order ID.");
            return false;
        }
        if (repo.getById(id) == null) {
            System.out.println("[ERROR] Order with ID " + id + " not found.");
            return false;
        }
        if (customerName == null || customerName.trim().isEmpty()) {
            System.out.println("[ERROR] Customer name cannot be empty.");
            return false;
        }
        if (totalAmount < 0) {
            System.out.println("[ERROR] Total amount cannot be negative.");
            return false;
        }
        if (status == null || status.trim().isEmpty()) {
            System.out.println("[ERROR] Order status cannot be empty.");
            return false;
        }

        boolean success = repo.update(id, customerName.trim(), totalAmount, status.trim());
        if (success) {
            System.out.println("[SUCCESS] Order " + id + " updated successfully.");
        } else {
            System.out.println("[ERROR] Failed to update order.");
        }
        return success;
    }

    // Delete: Delete an order by ID
    public boolean deleteOrder(int id) {
        if (id <= 0) {
            System.out.println("[ERROR] Invalid Order ID.");
            return false;
        }
        if (repo.getById(id) == null) {
            System.out.println("[ERROR] Order with ID " + id + " does not exist.");
            return false;
        }

        boolean success = repo.delete(id);
        if (success) {
            System.out.println("[SUCCESS] Order " + id + " deleted successfully.");
        } else {
            System.out.println("[ERROR] Failed to delete order.");
        }
        return success;
    }
}
