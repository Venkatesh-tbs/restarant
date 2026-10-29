package com.rest.repo;

import com.rest.models.menuitem;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Repository class managing CRUD operations and file persistence for menu items
public class menuitemrepo {

    // In-memory list to store menu items
    private final List<menuitem> items = new ArrayList<>();
    
    // File path used to persist menu items to disk
    private final String FILE_NAME = "menu.txt";

    public menuitemrepo() {
        // Load existing menu items from file if available
        loadFromFile();

        // Seed with requested default South Indian menu items if file is empty
        if (items.isEmpty()) {
            items.add(new menuitem(1, "Idli", 30.00));
            items.add(new menuitem(2, "Dosa", 50.00));
            items.add(new menuitem(3, "Pongal", 45.00));
            items.add(new menuitem(4, "Biriyani", 120.00));
            saveToFile();
        }
    }

    // View: Get all menu items
    public List<menuitem> getAll() {
        return items;
    }

    // View: Find a menu item by ID
    public menuitem getById(int id) {
        for (menuitem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    // Add: Insert a new menu item
    public boolean add(menuitem item) {
        if (getById(item.getId()) != null) {
            return false; // Item with this ID already exists
        }
        items.add(item);
        saveToFile();
        return true;
    }

    // Edit: Update existing menu item's name and price
    public boolean update(int id, String newName, double newPrice) {
        menuitem item = getById(id);
        if (item != null) {
            item.setName(newName);
            item.setPrice(newPrice);
            saveToFile();
            return true;
        }
        return false;
    }

    // Delete: Remove a menu item by ID
    public boolean delete(int id) {
        menuitem item = getById(id);
        if (item != null) {
            items.remove(item);
            saveToFile();
            return true;
        }
        return false;
    }

    // Load menu items from file
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    // Format: id,name,price
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        double price = Double.parseDouble(parts[2].trim());
                        items.add(new menuitem(id, name, price));
                    }
                }
            }
        } catch (Exception e) {
            // Keep current list if file read issue occurs
        }
    }

    // Save menu items to file so changes persist
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (menuitem item : items) {
                writer.println(item.getId() + "," + item.getName() + "," + item.getPrice());
            }
        } catch (Exception e) {
            System.out.println("Error saving menu items to file: " + e.getMessage());
        }
    }
}
