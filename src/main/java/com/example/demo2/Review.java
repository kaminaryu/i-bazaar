package com.example.demo2;

import java.util.Date;

public class Review {
    private String productID;
    private String userName;
    private Date date;
    private int star;
    private String details;

    public Review(String productID, String userName, int star, String details) {
        this.productID = productID;
        this.userName = userName;
        this.star = star;
        this.details = details;
        this.date = new Date();
    }

    public void setStar(int star) {
        if (star >= 1 && star <= 5) {
            this.star = star;
        } else {
            System.out.println("Rating must be between 1 and 5.");
        }
    }

    public int getStar() { return star; }
    public void setDetails(String details) { this.details = details; }
    public String getDetails() { return details; }
    public void setDate(Date date) { this.date = date; }
    public Date getDate() { return date; }
    public String getProductID() { return productID; }
    public String getUserName() { return userName; }
}
