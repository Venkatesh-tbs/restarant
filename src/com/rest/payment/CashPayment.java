package com.rest.payment;

// CashPayment class implements the Payment interface.
// It provides its own definition of the pay() method for cash payments.
public class CashPayment implements Payment {

    // Amount given by the customer
    private double amountGiven;

    public CashPayment(double amountGiven) {
        this.amountGiven = amountGiven;
    }

    // Overriding the pay() method from the Payment interface
    // Calculates change to return to the customer
    @Override
    public void pay(double amount) {
        System.out.println("------------------------------------------");
        System.out.println("         Processing Cash Payment...       ");
        System.out.println("------------------------------------------");
        System.out.printf("  Total Bill  : Rs.%.2f%n", amount);
        System.out.printf("  Cash Given  : Rs.%.2f%n", amountGiven);

        // Check if customer gave enough cash
        if (amountGiven < amount) {
            System.out.printf("  [ERROR] Insufficient cash! Short by Rs.%.2f%n", (amount - amountGiven));
        } else {
            double change = amountGiven - amount;
            System.out.printf("  Change      : Rs.%.2f%n", change);
            System.out.println("  Status      : Payment Successful!");
        }
        System.out.println("------------------------------------------");
    }
}
