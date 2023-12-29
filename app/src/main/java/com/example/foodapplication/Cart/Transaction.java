package com.example.foodapplication.Cart;

public class Transaction {
    private double totalPrice;

    public Transaction() {
    }

    public Transaction(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
