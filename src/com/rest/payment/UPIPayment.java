package com.rest.payment;

// UPIPayment class implements the Payment interface.
// It provides its own definition of the pay() method.
// This is called Method Overriding (or Method Implementation).
public class UPIPayment implements Payment {

    // The UPI ID entered by the user (e.g., "john@upi")
    private String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    // Overriding the pay() method from the Payment interface
    // This method simulates a UPI payment (academic project - no real transaction)
    @Override
    public void pay(double amount) {
        System.out.println("------------------------------------------");
        System.out.println("          Processing UPI Payment...       ");
        System.out.println("------------------------------------------");
        System.out.printf("  UPI ID     : %s%n", upiId);
        System.out.printf("  Amount     : Rs.%.2f%n", amount);
        System.out.println("  Status     : Payment Successful!");
        System.out.println("  [Simulated UPI payment - Academic project]");
        System.out.println("------------------------------------------");
    }
}
