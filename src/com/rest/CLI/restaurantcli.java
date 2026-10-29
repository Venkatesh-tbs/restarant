package com.rest.CLI;

import com.rest.models.bill;
import com.rest.models.billitem;
import com.rest.models.menuitem;
import com.rest.models.order;
import com.rest.models.table;
import com.rest.payment.CardPayment;
import com.rest.payment.CashPayment;
import com.rest.payment.Payment;
import com.rest.payment.UPIPayment;
import com.rest.repo.tablerepo;
import com.rest.service.billservice;
import com.rest.service.menuitemservice;
import com.rest.service.orderservice;
import com.rest.service.tableservice;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Beginner-friendly console CLI interface for the Restaurant Management System
public class restaurantcli {

    private final tableservice tableService;
    private final orderservice orderService;
    private final menuitemservice menuService;
    private final billservice billService;
    private final Scanner scanner;

    public restaurantcli() {
        tablerepo sharedTableRepo = new tablerepo();
        this.tableService = new tableservice(sharedTableRepo);
        this.orderService = new orderservice(sharedTableRepo);
        this.menuService = new menuitemservice();
        this.billService = new billservice();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntInput("Enter choice (1-19): ");
            System.out.println();

            switch (choice) {
                // Table Operations
                case 1:
                    viewAllTables();
                    break;
                case 2:
                    addNewTable();
                    break;
                case 3:
                    editTable();
                    break;
                case 4:
                    deleteTable();
                    break;

                // Order Operations
                case 5:
                    viewAllOrders();
                    break;
                case 6:
                    addNewOrder();
                    break;
                case 7:
                    editOrder();
                    break;
                case 8:
                    deleteOrder();
                    break;

                // Menu Item Operations
                case 9:
                    viewAllMenuItems();
                    break;
                case 10:
                    addNewMenuItem();
                    break;
                case 11:
                    editMenuItem();
                    break;
                case 12:
                    deleteMenuItem();
                    break;

                // Billing Operations
                case 13:
                    generateCustomerBill();
                    break;
                case 14:
                    viewPastBills();
                    break;

                // Table Booking & Occupancy Operations
                case 16:
                    bookTable();
                    break;
                case 17:
                    occupyTable();
                    break;
                case 18:
                    releaseTable();
                    break;

                // Exit
                case 19:
                    System.out.println("Thank you for using Restaurant Management System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("[ERROR] Invalid choice. Please choose a number between 1 and 19.");
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("==========================================");
        System.out.println("       RESTAURANT MANAGEMENT SYSTEM       ");
        System.out.println("==========================================");
        System.out.println("--- Table Management ---");
        System.out.println("1. View All Tables");
        System.out.println("2. Add New Table");
        System.out.println("3. Edit Table");
        System.out.println("4. Delete Table (with confirmation)");
        System.out.println("--- Order Management ---");
        System.out.println("5. View All Orders");
        System.out.println("6. Add New Order");
        System.out.println("7. Edit Order");
        System.out.println("8. Delete Order (with confirmation)");
        System.out.println("--- Menu Items (Idli, Dosa, Pongal, Biriyani) ---");
        System.out.println("9. View All Menu Items");
        System.out.println("10. Add New Menu Item");
        System.out.println("11. Edit Menu Item");
        System.out.println("12. Delete Menu Item (with confirmation)");
        System.out.println("--- Billing & Receipts ---");
        System.out.println("13. Generate Bill (Select Items & Calculate Total)");
        System.out.println("14. View Past Bills");
        System.out.println("--- Table Booking & Occupancy ---");
        System.out.println("16. Book Table   (Available -> Booked)");
        System.out.println("17. Occupy Table (Booked   -> Occupied)");
        System.out.println("18. Release Table (Booked/Occupied -> Available)");
        System.out.println("------------------------------------------");
        System.out.println("19. Exit");
        System.out.println("==========================================");
    }

    // --- Table Operations ---

    private void viewAllTables() {
        System.out.println("--- All Restaurant Tables ---");
        List<table> tables = tableService.getAll();
        if (tables.isEmpty()) {
            System.out.println("No tables found.");
            return;
        }
        for (table t : tables) {
            System.out.println(t);
        }
    }

    private void addNewTable() {
        System.out.println("--- Add New Table ---");
        int id = readIntInput("Enter Table ID: ");
        int capacity = readIntInput("Enter Seating Capacity: ");
        tableService.addTable(id, capacity);
    }

    private void editTable() {
        System.out.println("--- Edit Table ---");
        int id = readIntInput("Enter Table ID to edit: ");
        table existing = tableService.getById(id);
        if (existing == null) {
            System.out.println("[ERROR] Table not found with ID " + id);
            return;
        }

        System.out.println("Current Details: " + existing);
        int newCap = readIntInput("Enter new seating capacity: ");
        System.out.println("Select Status: 1. free  2. occupy");
        int statusChoice = readIntInput("Enter choice (1 or 2): ");
        table.Availability newStatus = (statusChoice == 2) ? table.Availability.occupy : table.Availability.free;

        tableService.updateTable(id, newCap, newStatus);
    }

    private void deleteTable() {
        System.out.println("--- Delete Table ---");
        int id = readIntInput("Enter Table ID to delete: ");
        table existing = tableService.getById(id);
        if (existing == null) {
            System.out.println("[ERROR] Table not found with ID " + id);
            return;
        }

        System.out.print("Are you sure you want to delete Table ID " + id + "? (yes/no): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            tableService.deleteTable(id);
        } else {
            System.out.println("[INFO] Table deletion cancelled.");
        }
    }
    
    // --- Order Operations ---

    private void viewAllOrders() {
        System.out.println("--- All Customer Orders ---");
        List<order> orders = orderService.getAll();
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        for (order ord : orders) {
            System.out.println(ord);
        }
    }

    private void addNewOrder() {
        System.out.println("--- Add New Order ---");
        int id = readIntInput("Enter Order ID: ");
        int tableId = readIntInput("Enter Table ID for this order: ");
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine().trim();
        double totalAmount = readDoubleInput("Enter Total Bill Amount: ");

        orderService.addOrder(id, tableId, customerName, totalAmount);
    }

    private void editOrder() {
        System.out.println("--- Edit Order ---");
        int id = readIntInput("Enter Order ID to edit: ");
        order existing = orderService.getById(id);
        if (existing == null) {
            System.out.println("[ERROR] Order not found with ID " + id);
            return;
        }

        System.out.println("Current Details: " + existing);
        System.out.print("Enter new Customer Name (leave empty to keep '" + existing.getCustomerName() + "'): ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) {
            newName = existing.getCustomerName();
        }

        double newAmount = readDoubleInput("Enter new Total Amount (current: " + existing.getTotalAmount() + "): ");
        System.out.print("Enter new Status (e.g. Pending / Completed / Cancelled): ");
        String newStatus = scanner.nextLine().trim();
        if (newStatus.isEmpty()) {
            newStatus = existing.getStatus();
        }

        orderService.updateOrder(id, newName, newAmount, newStatus);
    }

    private void deleteOrder() {
        System.out.println("--- Delete Order ---");
        int id = readIntInput("Enter Order ID to delete: ");
        order existing = orderService.getById(id);
        if (existing == null) {
            System.out.println("[ERROR] Order not found with ID " + id);
            return;
        }

        System.out.print("Are you sure you want to delete Order ID " + id + "? (yes/no): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            orderService.deleteOrder(id);
        } else {
            System.out.println("[INFO] Order deletion cancelled.");
        }
    }

    // --- Menu Item Operations (Idli, Dosa, Pongal, Biriyani) ---

    private void viewAllMenuItems() {
        System.out.println("--- Restaurant Menu Items ---");
        List<menuitem> items = menuService.getAll();
        if (items.isEmpty()) {
            System.out.println("No menu items found.");
            return;
        }
        for (menuitem item : items) {
            System.out.println(item);
        }
    }

    private void addNewMenuItem() {
        System.out.println("--- Add New Menu Item ---");
        int id = readIntInput("Enter Item ID: ");
        System.out.print("Enter Item Name (e.g. Idli, Dosa, Vada): ");
        String name = scanner.nextLine().trim();
        double price = readDoubleInput("Enter Item Price (in Rs.): ");

        menuService.addItem(id, name, price);
    }

    private void editMenuItem() {
        System.out.println("--- Edit Menu Item ---");
        int id = readIntInput("Enter Item ID to edit: ");
        menuitem existing = menuService.getById(id);
        if (existing == null) {
            System.out.println("[ERROR] Menu item not found with ID " + id);
            return;
        }

        System.out.println("Current Details: " + existing);
        System.out.print("Enter new Name (leave empty to keep '" + existing.getName() + "'): ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) {
            newName = existing.getName();
        }

        double newPrice = readDoubleInput("Enter new Price (current: " + existing.getPrice() + "): ");
        menuService.updateItem(id, newName, newPrice);
    }

    private void deleteMenuItem() {
        System.out.println("--- Delete Menu Item ---");
        int id = readIntInput("Enter Item ID to delete: ");
        menuitem existing = menuService.getById(id);
        if (existing == null) {
            System.out.println("[ERROR] Menu item not found with ID " + id);
            return;
        }

        System.out.print("Are you sure you want to delete Menu Item '" + existing.getName() + "' (ID " + id + ")? (yes/no): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            menuService.deleteItem(id);
        } else {
            System.out.println("[INFO] Menu item deletion cancelled.");
        }
    }

    // --- Bill Generation Operations ---

    private void generateCustomerBill() {
        System.out.println("==========================================");
        System.out.println("           GENERATE CUSTOMER BILL         ");
        System.out.println("==========================================");

        List<menuitem> availableItems = menuService.getAll();
        if (availableItems.isEmpty()) {
            System.out.println("[ERROR] No menu items available. Please add menu items first.");
            return;
        }

        // Show available menu items for user selection
        System.out.println("Available Menu Items:");
        for (menuitem item : availableItems) {
            System.out.printf("  [%d] %-12s - Rs.%.2f%n", item.getId(), item.getName(), item.getPrice());
        }
        System.out.println("------------------------------------------");

        List<billitem> selectedItems = new ArrayList<>();

        while (true) {
            int itemId = readIntInput("Enter Item ID to add to bill (or 0 to calculate total): ");
            if (itemId == 0) {
                break;
            }

            menuitem chosenItem = menuService.getById(itemId);
            if (chosenItem == null) {
                System.out.println("[ERROR] Menu item not found with ID " + itemId + ". Please choose a valid ID.");
                continue;
            }

            int qty = readIntInput("Enter quantity for " + chosenItem.getName() + ": ");
            if (qty <= 0) {
                System.out.println("[ERROR] Quantity must be at least 1.");
                continue;
            }

            // Create bill item with price from menu and calculate subtotal
            billitem bi = new billitem(chosenItem.getId(), chosenItem.getName(), chosenItem.getPrice(), qty);
            selectedItems.add(bi);

            System.out.printf("  -> Added: %d x %s @ Rs.%.2f each = Subtotal: Rs.%.2f%n%n", 
                              qty, chosenItem.getName(), chosenItem.getPrice(), bi.getSubTotal());
        }

        if (selectedItems.isEmpty()) {
            System.out.println("[INFO] No items added. Bill generation cancelled.");
            return;
        }

        // Generate, calculate grand total, save to database, and print receipt
        bill createdBill = billService.generateBill(selectedItems);
        System.out.println();
        billService.printBill(createdBill);
        System.out.println("[SUCCESS] Bill #" + createdBill.getBillNo() + " saved to database (bills.txt) successfully!");

        // Ask user to select a payment method using OOP polymorphism
        processPayment(createdBill.getGrandTotal());
    }

    private void viewPastBills() {
        System.out.println("--- Past Generated Bills ---");
        List<bill> allBills = billService.getAllBills();
        if (allBills.isEmpty()) {
            System.out.println("No past bills found.");
            return;
        }
        for (bill b : allBills) {
            billService.printBill(b);
            System.out.println();
        }
    }

    // --- Payment Processing using OOP Interface ---

    private void processPayment(double grandTotal) {
        System.out.println();
        System.out.println("==========================================" );
        System.out.println("          SELECT PAYMENT METHOD           ");
        System.out.println("==========================================" );
        System.out.println("1. UPI Payment");
        System.out.println("2. Card Payment (Credit / Debit)");
        System.out.println("3. Cash Payment");

        int choice = readIntInput("Enter choice (1-3): ");

        // Payment is a variable of type Payment (Interface).
        // We assign different class objects to it based on user choice.
        // This is called Polymorphism in OOP.
        Payment payment = null;

        if (choice == 1) {
            // UPI Payment
            System.out.print("Enter your UPI ID (e.g. john@upi): ");
            String upiId = scanner.nextLine().trim();
            payment = new UPIPayment(upiId);

        } else if (choice == 2) {
            // Card Payment
            System.out.println("Select Card Type: 1. Credit  2. Debit");
            int cardChoice = readIntInput("Enter choice (1 or 2): ");
            String cardType = (cardChoice == 2) ? "Debit" : "Credit";
            System.out.print("Enter last 4 digits of your card: ");
            String last4 = scanner.nextLine().trim();
            payment = new CardPayment(cardType, last4);

        } else if (choice == 3) {
            // Cash Payment
            double cashGiven = readDoubleInput("Enter cash amount given by customer: Rs.");
            payment = new CashPayment(cashGiven);

        } else {
            System.out.println("[ERROR] Invalid payment choice. Payment skipped.");
            return;
        }

        // Call pay() on whatever object is in the payment variable.
        // Java automatically calls the correct pay() method (UPI / Card / Cash).
        // This is called Runtime Polymorphism / Dynamic Method Dispatch.
        payment.pay(grandTotal);
    }

    // --- Table Booking & Occupancy Handlers (NEW) ---

    /**
     * Book Table (Option 16):
     * Shows the table list, then asks the user for a Table ID.
     * Only books the table if it is currently Available.
     */
    private void bookTable() {
        System.out.println("--- Book a Table ---");
        System.out.println("Current Table Status:");
        viewAllTables();
        System.out.println();
        int id = readIntInput("Enter the Table ID you want to Book: ");
        tableService.bookTable(id);
    }

    /**
     * Occupy Table (Option 17):
     * Shows the table list, then asks for a Table ID.
     * Only marks the table as Occupied if it is currently Booked.
     */
    private void occupyTable() {
        System.out.println("--- Mark Table as Occupied ---");
        System.out.println("Current Table Status:");
        viewAllTables();
        System.out.println();
        int id = readIntInput("Enter the Table ID to mark as Occupied: ");
        tableService.occupyTable(id);
    }

    /**
     * Release Table (Option 18):
     * Shows the table list, then asks for a Table ID.
     * Releases the table back to Available (works for Booked or Occupied tables).
     */
    private void releaseTable() {
        System.out.println("--- Release a Table ---");
        System.out.println("Current Table Status:");
        viewAllTables();
        System.out.println();
        int id = readIntInput("Enter the Table ID to Release: ");
        tableService.releaseTable(id);
    }

    // --- Beginner-friendly input helpers to prevent scanner crashes ---

    private int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid number format. Please enter a valid integer.");
            }
        }
    }

    private double readDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid decimal format. Please enter a valid number (e.g. 50.00).");
            }
        }
    }
}
