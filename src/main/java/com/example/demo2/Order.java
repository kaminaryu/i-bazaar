package com.example.demo2;

public class Order {
    private int orderId;
    private String productId;
    private Product product;
    private int quantity;
    private double totalPrice;
    private String status;

    public Order(int orderId, Product product, int quantity) {
        this.orderId = orderId;
        this.product = product;
        this.productId = product.getProductID();
        this.quantity = quantity;
        this.status = "Pending";
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        if (product != null) {
            totalPrice = product.getPrice() * quantity;
        }
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public int getOrderId() { return orderId; }
    public String getProductId() { return productId; }

    public void confirmOrder() {
        status = "Confirmed";
    }

    public void cancelOrder() {
        status = "Cancelled";
    }

    public void displayOrder() {
        System.out.println("Order ID: " + orderId);
        if (product != null) {
            System.out.println("Product: " + product.getProductName());
        }
        System.out.println("Quantity: " + quantity);
        System.out.println("Total: RM" + String.format("%.2f", totalPrice));
        System.out.println("Status: " + status);
    }
}
