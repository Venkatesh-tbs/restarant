package com.rest.service;

import com.rest.models.bill;
import com.rest.models.billitem;
import com.rest.repo.billrepo;
import java.time.LocalDate;
import java.util.List;

// Service layer handling bill calculation, formatting, and saving
public class billservice {

    private final billrepo repo;
    private final String RESTAURANT_NAME = "HOTEL SOUTH SPICE RESTAURANT";

    public billservice() {
        this.repo = new billrepo();
    }

    public billservice(billrepo repo) {
        this.repo = repo;
    }

    // Generate, calculate grand total, and save a new bill
    public bill generateBill(List<billitem> items) {
        if (items == null || items.isEmpty()) {
            System.out.println("[ERROR] Cannot generate an empty bill. Please add at least one item.");
            return null;
        }

        // Calculate grand total by summing up the subtotals of all items
        double grandTotal = 0.0;
        for (billitem item : items) {
            grandTotal += item.getSubTotal();
        }

        // Automatically assign unique sequential bill number and current date
        int billNo = repo.getNextBillNo();
        String date = LocalDate.now().toString();

        bill newBill = new bill(billNo, date, items, grandTotal);
        repo.add(newBill);

        return newBill;
    }

    // Retrieve all saved past bills
    public List<bill> getAllBills() {
        return repo.getAll();
    }

    // Display the bill in a clean, professional, readable receipt format
    public void printBill(bill b) {
        if (b == null) {
            return;
        }

        System.out.println("========================================================");
        System.out.println("              " + RESTAURANT_NAME);
        System.out.println("========================================================");
        System.out.printf("Bill No: %-25d Date: %s%n", b.getBillNo(), b.getDate());
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-18s %-12s %-8s %s%n", "Item Name", "Price", "Qty", "Subtotal");
        System.out.println("--------------------------------------------------------");

        for (billitem item : b.getItems()) {
            System.out.printf("%-18s Rs.%-9.2f %-8d Rs.%.2f%n", 
                              item.getItemName(), item.getPrice(), item.getQuantity(), item.getSubTotal());
        }

        System.out.println("--------------------------------------------------------");
        System.out.printf("%-40s Rs.%.2f%n", "Grand Total:", b.getGrandTotal());
        System.out.println("========================================================");
        System.out.println("           Thank you! Please visit us again!            ");
        System.out.println("========================================================");
    }
}
