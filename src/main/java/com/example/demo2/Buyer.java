package com.example.demo2;

import java.util.ArrayList;

public class Buyer extends Users {
    private ArrayList<Order> cart = new ArrayList<>();

    public Buyer(String name, String matricNum, String password) {
        super(name, matricNum, password);
    }

    public ArrayList<Order> getCart() {
        return cart;
    }
}
