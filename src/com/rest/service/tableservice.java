package com.rest.service;

import com.rest.models.table;
import com.rest.repo.tablerepo;
import java.util.List;

public class tableservice {

    private final tablerepo repo;

    // Fixed constructor: Added missing curly braces and fixed initialization
    public tableservice() {
        this.repo = new tablerepo();
    }

    // Overloaded constructor allowing injection of existing repository
    public tableservice(tablerepo repo) {
        this.repo = repo;
    }

    public tablerepo getRepo() {
        return this.repo;
    }

    // View: Get all tables (Preserved original method)
    public List<table> getAll() {
        return repo.show();
    }

    // View: Get a single table by ID
    public table getById(int id) {
        if (id <= 0) {
            System.out.println("[ERROR] Table ID must be positive.");
            return null;
        }
        return repo.getById(id);
    }

    // Add: Validate and add a new table
    public boolean addTable(int id, int capacity) {
        // Validation: ID and capacity must be positive numbers
        if (id <= 0) {
            System.out.println("[ERROR] Table ID must be greater than 0.");
            return false;
        }
        if (capacity <= 0) {
            System.out.println("[ERROR] Capacity must be greater than 0.");
            return false;
        }
        // Validation: ID must not already exist
        if (repo.getById(id) != null) {
            System.out.println("[ERROR] Table with ID " + id + " already exists.");
            return false;
        }

        table newTable = new table(id, capacity);
        boolean success = repo.add(newTable);
        if (success) {
            System.out.println("[SUCCESS] Table " + id + " added successfully.");
        } else {
            System.out.println("[ERROR] Failed to save table.");
        }
        return success;
    }

    // Edit: Update capacity and status of a table
    public boolean updateTable(int id, int newCapacity, table.Availability newStatus) {
        if (id <= 0) {
            System.out.println("[ERROR] Invalid Table ID.");
            return false;
        }
        if (newCapacity <= 0) {
            System.out.println("[ERROR] Capacity must be greater than 0.");
            return false;
        }
        if (repo.getById(id) == null) {
            System.out.println("[ERROR] Table with ID " + id + " does not exist.");
            return false;
        }

        boolean success = repo.update(id, newCapacity, newStatus);
        if (success) {
            System.out.println("[SUCCESS] Table " + id + " updated successfully.");
        } else {
            System.out.println("[ERROR] Failed to update table.");
        }
        return success;
    }

    // Delete: Delete a table by ID
    public boolean deleteTable(int id) {
        if (id <= 0) {
            System.out.println("[ERROR] Invalid Table ID.");
            return false;
        }
        if (repo.getById(id) == null) {
            System.out.println("[ERROR] Table with ID " + id + " does not exist.");
            return false;
        }

        boolean success = repo.delete(id);
        if (success) {
            System.out.println("[SUCCESS] Table " + id + " deleted successfully.");
        } else {
            System.out.println("[ERROR] Failed to delete table.");
        }
        return success;
    }

    // ---------------------------------------------------------------
    // Table Booking & Occupancy Methods (NEW)
    // ---------------------------------------------------------------

    /**
     * Book a table: Only allowed if the table is currently Available (free).
     * Changes status: free -> booked
     */
    public boolean bookTable(int id) {
        table t = repo.getById(id);
        if (t == null) {
            System.out.println("[ERROR] Table with ID " + id + " does not exist.");
            return false;
        }
        if (t.getstatus() == table.Availability.booked) {
            System.out.println("[ERROR] Table " + id + " is already Booked. Cannot book again.");
            return false;
        }
        if (t.getstatus() == table.Availability.occupy) {
            System.out.println("[ERROR] Table " + id + " is currently Occupied. Cannot book it.");
            return false;
        }
        // Table is free — go ahead and book it
        boolean success = repo.updateStatus(id, table.Availability.booked);
        if (success) {
            System.out.println("[SUCCESS] Table " + id + " has been Booked successfully.");
        } else {
            System.out.println("[ERROR] Failed to book table " + id + ".");
        }
        return success;
    }

    /**
     * Occupy a table: Only allowed if the table is currently Booked.
     * Changes status: booked -> occupy
     */
    public boolean occupyTable(int id) {
        table t = repo.getById(id);
        if (t == null) {
            System.out.println("[ERROR] Table with ID " + id + " does not exist.");
            return false;
        }
        if (t.getstatus() == table.Availability.free) {
            System.out.println("[ERROR] Table " + id + " is Available. Please book it first before occupying.");
            return false;
        }
        if (t.getstatus() == table.Availability.occupy) {
            System.out.println("[ERROR] Table " + id + " is already Occupied.");
            return false;
        }
        // Table is booked — mark it as occupied
        boolean success = repo.updateStatus(id, table.Availability.occupy);
        if (success) {
            System.out.println("[SUCCESS] Table " + id + " is now marked as Occupied. Customer has arrived.");
        } else {
            System.out.println("[ERROR] Failed to occupy table " + id + ".");
        }
        return success;
    }

    /**
     * Release a table: Allowed if the table is Booked or Occupied.
     * Changes status: booked/occupy -> free
     */
    public boolean releaseTable(int id) {
        table t = repo.getById(id);
        if (t == null) {
            System.out.println("[ERROR] Table with ID " + id + " does not exist.");
            return false;
        }
        if (t.getstatus() == table.Availability.free) {
            System.out.println("[ERROR] Table " + id + " is already Available. Nothing to release.");
            return false;
        }
        // Table is booked or occupied — release it back to free
        boolean success = repo.updateStatus(id, table.Availability.free);
        if (success) {
            System.out.println("[SUCCESS] Table " + id + " has been Released and is now Available.");
        } else {
            System.out.println("[ERROR] Failed to release table " + id + ".");
        }
        return success;
    }
}
