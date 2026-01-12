package com.example.demo.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.Column;
import java.time.Instant;
import java.util.UUID;

@Table("cart_items")
public class CartItem {

    @PrimaryKey
    private UUID id;

    @Column("username")
    private String username;

    @Column("product_title")
    private String productTitle;

    @Column("product_price")
    private Double productPrice;

    @Column("product_image")
    private String productImage;

    @Column
    private String color;

    @Column
    private String size;

    @Column
    private Integer quantity = 1;

    @Column("added_at")
    private Instant addedAt = Instant.now();

    // Default constructor
    public CartItem() {
        this.id = UUID.randomUUID();
    }

    // Constructor
    public CartItem(String username, String productTitle, Double productPrice, String productImage) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.addedAt = Instant.now();
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Instant getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Instant addedAt) {
        this.addedAt = addedAt;
    }
}