package com.example.demo2;

import java.util.ArrayList;

public class Seller extends Users {
    private ArrayList<Product> shopCatalog;
    private double balance;

    public Seller(String name, String matricNum, String password, ArrayList<Product> catalog) {
        super(name, matricNum, password);
        this.shopCatalog = new ArrayList<>();
        this.balance = 0.0;
    }

    public ArrayList<Product> getShopCatalog() {
        return shopCatalog;
    }

    public double getBalance() {
        return balance;
    }

    public void addProduct(Product item) {
        shopCatalog.add(item);
    }

    public void updateProduct(String productID, String newDetails) {
        for (Product p : shopCatalog) {
            if (p.getProductID().equals(productID)) {
                p.setDetails(newDetails);
                return;
            }
        }
    }

    public void deleteProduct(String productID) {
        shopCatalog.removeIf(p -> p.getProductID().equals(productID));
    }
}
