package com.rest.payment;

// Interface: Defines a contract that all payment methods must follow.
// Any class that "implements" this interface MUST provide its own pay() method.
// This is the concept of Abstraction in OOP.
public interface Payment {

    // Abstract method: No body here. Each payment class will define HOW to pay.
    // This is what we call method overriding / implementation.
    void pay(double amount);
}
