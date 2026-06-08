package com.example.demo2;

public class Product {
    private String productID;
    private String productName;
    private double price;
    private int stock;
    private String sellerMatric; // matric number of the seller who uploaded this

    public Product(String productID, String productName, double price, int stock, String sellerMatric) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.sellerMatric = sellerMatric;
    }

    public void updateStock(int amount) {
        this.stock += amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductID() { return productID; }
    public String getProductName() { return productName; }
    public int getStock() { return stock; }
    public String getSellerMatric() { return sellerMatric; }

    // convert Object into csv
    public String toFileString() {
        return productID + "," + productName + "," + price + "," + stock + "," + sellerMatric;
    }

    // convert csv into Object
    public static Product fromFileString(String line) {
        String[] parts = line.split(",");
        return new Product(parts[0], parts[1], Double.parseDouble(parts[2]),
            Integer.parseInt(parts[3]), parts.length > 4 ? parts[4] : "");
    }
}
