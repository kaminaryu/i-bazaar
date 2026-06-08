package com.example.demo2;

import java.util.ArrayList;

public class Seller extends Users {
    private ArrayList<Product> shopCatalog = new ArrayList<>();

    public Seller(String name, String matricNum, String password) {
        super(name, matricNum, password);
    }

    public ArrayList<Product> getShopCatalog() {
        return shopCatalog;
    }
}
