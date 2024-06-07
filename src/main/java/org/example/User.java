package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private String hashedPassword;
    private double balance;
    private List<String> transactionHistory;

    // Constructor
    public User(String name, String address, String phoneNumber, String hashedPassword) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.hashedPassword = hashedPassword;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<String> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    // Additional method to add a transaction to the history
    public void addTransaction(String transaction) {
        this.transactionHistory.add(transaction);
    }
}
