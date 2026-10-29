package com.rest.repo;

import com.rest.models.bill;
import com.rest.models.billitem;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Repository class managing storage and persistence of bills in bills.txt
public class billrepo {

    private final List<bill> bills = new ArrayList<>();
    private final String FILE_NAME = "bills.txt";

    public billrepo() {
        loadFromFile();
    }

    public List<bill> getAll() {
        return bills;
    }

    public bill getById(int billNo) {
        for (bill b : bills) {
            if (b.getBillNo() == billNo) {
                return b;
            }
        }
        return null;
    }

    public void add(bill b) {
        bills.add(b);
        saveToFile();
    }

    // Generate the next sequential bill number starting at 1001
    public int getNextBillNo() {
        int max = 1000;
        for (bill b : bills) {
            if (b.getBillNo() > max) {
                max = b.getBillNo();
            }
        }
        return max + 1;
    }

    // Load bills from bills.txt
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            int currentBillNo = 0;
            String currentDate = "";
            double currentGrandTotal = 0.0;
            List<billitem> currentItems = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("BILL,")) {
                    String[] parts = line.split(",");
                    currentBillNo = Integer.parseInt(parts[1].trim());
                    currentDate = parts[2].trim();
                    currentGrandTotal = Double.parseDouble(parts[3].trim());
                    currentItems = new ArrayList<>();
                } else if (line.startsWith("ITEM,")) {
                    String[] parts = line.split(",");
                    int itemId = Integer.parseInt(parts[1].trim());
                    String itemName = parts[2].trim();
                    double price = Double.parseDouble(parts[3].trim());
                    int qty = Integer.parseInt(parts[4].trim());
                    currentItems.add(new billitem(itemId, itemName, price, qty));
                } else if (line.equals("ENDBILL")) {
                    bills.add(new bill(currentBillNo, currentDate, currentItems, currentGrandTotal));
                }
            }
        } catch (Exception e) {
            // Keep loaded bills if minor read issue
        }
    }

    // Save all bills to bills.txt
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (bill b : bills) {
                writer.println("BILL," + b.getBillNo() + "," + b.getDate() + "," + b.getGrandTotal());
                for (billitem item : b.getItems()) {
                    writer.println("ITEM," + item.getItemId() + "," + item.getItemName() + "," + 
                                   item.getPrice() + "," + item.getQuantity() + "," + item.getSubTotal());
                }
                writer.println("ENDBILL");
            }
        } catch (Exception e) {
            System.out.println("Error saving bills to file: " + e.getMessage());
        }
    }
}
