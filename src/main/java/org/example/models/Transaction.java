package org.example.models;

import java.util.Date;

public class Transaction {
    private String type; 
    private double amount;
    private Date date;
    private String description;

    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.date = new Date();
        this.description = description;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
