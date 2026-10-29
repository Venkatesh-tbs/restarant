package com.rest.repo;

import com.rest.models.table;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class tablerepo {

    // In-memory list of tables
    private final List<table> tables = new ArrayList<>();
    
    // File path used to persist table data to disk
    private final String FILE_NAME = "tables.txt";

    public tablerepo() {
        // First try to load existing records from file
        loadFromFile();

        // If file was empty or didn't exist, seed with original default tables
        if (tables.isEmpty()) {
            tables.add(new table(1, 5));
            tables.add(new table(2, 4));
            tables.add(new table(3, 7));
            saveToFile();
        }
    }

    // View: retrieve all tables (preserved existing method)
    public List<table> show() {
        return tables;
    }

    // View: find a table by its ID
    public table getById(int id) {
        for (table t : tables) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    // Add: add a new table to the repository
    public boolean add(table t) {
        // Check if ID already exists
        if (getById(t.getId()) != null) {
            return false;
        }
        tables.add(t);
        saveToFile();
        return true;
    }

    // Edit: update capacity and status of an existing table
    public boolean update(int id, int newCapacity, table.Availability newStatus) {
        table t = getById(id);
        if (t != null) {
            t.setcap(newCapacity);
            t.setstatus(newStatus);
            saveToFile();
            return true;
        }
        return false;
    }

    // Delete: remove a table by ID
    public boolean delete(int id) {
        table t = getById(id);
        if (t != null) {
            tables.remove(t);
            saveToFile();
            return true;
        }
        return false;
    }

    // Update only the status of a table (used by book/occupy/release operations)
    public boolean updateStatus(int id, table.Availability newStatus) {
        table t = getById(id);
        if (t != null) {
            t.setstatus(newStatus);
            saveToFile();
            return true;
        }
        return false;
    }

    // Load tables from text file for persistence
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0].trim());
                    int capacity = Integer.parseInt(parts[1].trim());
                    table t = new table(id, capacity);
                    // Restore the saved status for all three states
                    if (parts.length > 2) {
                        String savedStatus = parts[2].trim().toLowerCase();
                        if (savedStatus.equals("booked")) {
                            t.Book();          // sets status to booked
                        } else if (savedStatus.equals("occupy")) {
                            t.occupy();        // sets status to occupy
                        }
                        // default is already free, so no action needed for "free"
                    }
                    tables.add(t);
                }
            }
        } catch (Exception e) {
            // In case of read issue, keep list as is
        }
    }

    // Save current tables to text file so changes persist
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (table t : tables) {
                writer.println(t.getId() + "," + t.getcap() + "," + t.getstatus().name());
            }
        } catch (Exception e) {
            // In case of write issue, output error message
            System.out.println("Error saving tables to file: " + e.getMessage());
        }
    }
}