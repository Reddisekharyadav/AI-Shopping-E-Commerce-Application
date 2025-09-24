package com.example.demo.model;

public class Product {
    private String title;
    private double price;
    private String image;

    // Default constructor
    public Product() {
    }

    // Parameterized constructor
    public Product(String title, double price, String image) {
        this.title = title;
        this.price = price;
        this.image = image;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}