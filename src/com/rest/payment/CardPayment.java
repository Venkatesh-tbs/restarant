package com.rest.payment;

// CardPayment class implements the Payment interface.
// It provides its own definition of the pay() method for card payments.
public class CardPayment implements Payment {

    // Last 4 digits of the card (we don't store full card numbers for security)
    private String last4Digits;

    // Card type: e.g., "Credit" or "Debit"
    private String cardType;

    public CardPayment(String cardType, String last4Digits) {
        this.cardType = cardType;
        this.last4Digits = last4Digits;
    }

    // Overriding the pay() method from the Payment interface
    // This method simulates a card payment (academic project - no real transaction)
    @Override
    public void pay(double amount) {
        System.out.println("------------------------------------------");
        System.out.println("         Processing Card Payment...       ");
        System.out.println("------------------------------------------");
        System.out.printf("  Card Type  : %s Card%n", cardType);
        System.out.printf("  Card No.   : **** **** **** %s%n", last4Digits);
        System.out.printf("  Amount     : Rs.%.2f%n", amount);
        System.out.println("  Status     : Payment Successful!");
        System.out.println("  [Simulated card payment - Academic project]");
        System.out.println("------------------------------------------");
    }
}
