package com.example.demo2;

public class Order {
    private int orderId;
    private Product product;
    private int quantity;
    private double totalPrice;

    public Order(int orderId, Product product, int quantity) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
    public int getOrderId() { return orderId; }
}
