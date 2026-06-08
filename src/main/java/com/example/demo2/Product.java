package com.example.demo2;

public class Product {
    private String productID;
    private String productName;
    private double price;
    private String description;
    private double discount;
    private int stock;
    private String category;

    public Product(String productID, String productName, double price, int stock, String category) {
        this(productID, productName, price, "", stock, category);
    }

    public Product(String productID, String productName, double price, String description, int stock, String category) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.category = category;
        this.discount = 0.0;
    }

    public void applyDiscount(double discountPercentage) {
        this.discount = discountPercentage;
    }

    public void updateStock(int amount) {
        this.stock += amount;
    }

    public double getPrice() {
        return price * (1 - discount);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductID() { return productID; }
    public String getProductName() { return productName; }
    public int getStock() { return stock; }
    public String getCategory() { return category; }
    public String getDetails() { return description; }

    public void setDetails(String details) {
        this.description = details;
    }
}
