package com.rest.service;

import com.rest.models.menuitem;
import com.rest.repo.menuitemrepo;
import java.util.List;

// Service layer providing business logic and validations for menu items
public class menuitemservice {

    private final menuitemrepo repo;

    public menuitemservice() {
        this.repo = new menuitemrepo();
    }

    public menuitemservice(menuitemrepo repo) {
        this.repo = repo;
    }

    // View: Get all menu items
    public List<menuitem> getAll() {
        return repo.getAll();
    }

    // View: Get a menu item by ID
    public menuitem getById(int id) {
        if (id <= 0) {
            System.out.println("[ERROR] Menu Item ID must be greater than 0.");
            return null;
        }
        return repo.getById(id);
    }

    // Add: Validate inputs and add a new menu item
    public boolean addItem(int id, String name, double price) {
        // Validation: Positive ID
        if (id <= 0) {
            System.out.println("[ERROR] Menu Item ID must be greater than 0.");
            return false;
        }
        // Validation: Unique ID
        if (repo.getById(id) != null) {
            System.out.println("[ERROR] Menu Item with ID " + id + " already exists.");
            return false;
        }
        // Validation: Non-empty Name
        if (name == null || name.trim().isEmpty()) {
            System.out.println("[ERROR] Menu item name cannot be empty.");
            return false;
        }
        // Validation: Positive Price
        if (price <= 0) {
            System.out.println("[ERROR] Menu item price must be greater than 0.");
            return false;
        }

        menuitem item = new menuitem(id, name.trim(), price);
        boolean success = repo.add(item);
        if (success) {
            System.out.println("[SUCCESS] Menu item '" + name.trim() + "' added successfully.");
        } else {
            System.out.println("[ERROR] Failed to save menu item.");
        }
        return success;
    }

    // Edit: Validate inputs and update an existing menu item
    public boolean updateItem(int id, String newName, double newPrice) {
        if (id <= 0) {
            System.out.println("[ERROR] Invalid Menu Item ID.");
            return false;
        }
        if (repo.getById(id) == null) {
            System.out.println("[ERROR] Menu item with ID " + id + " not found.");
            return false;
        }
        if (newName == null || newName.trim().isEmpty()) {
            System.out.println("[ERROR] Menu item name cannot be empty.");
            return false;
        }
        if (newPrice <= 0) {
            System.out.println("[ERROR] Menu item price must be greater than 0.");
            return false;
        }

        boolean success = repo.update(id, newName.trim(), newPrice);
        if (success) {
            System.out.println("[SUCCESS] Menu item " + id + " updated successfully.");
        } else {
            System.out.println("[ERROR] Failed to update menu item.");
        }
        return success;
    }

    // Delete: Delete a menu item by ID
    public boolean deleteItem(int id) {
        if (id <= 0) {
            System.out.println("[ERROR] Invalid Menu Item ID.");
            return false;
        }
        if (repo.getById(id) == null) {
            System.out.println("[ERROR] Menu item with ID " + id + " does not exist.");
            return false;
        }

        boolean success = repo.delete(id);
        if (success) {
            System.out.println("[SUCCESS] Menu item " + id + " deleted successfully.");
        } else {
            System.out.println("[ERROR] Failed to delete menu item.");
        }
        return success;
    }
}
