package com.example.demo.controller;

import com.example.demo.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    @GetMapping
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        // Men's Clothing
        products.add(new Product("Classic White T-Shirt", 19.99, "/products/tshirt1.jpg"));
        products.add(new Product("Men's Casual Slim Fit", 15.99, "/products/casual-fit.jpg"));
        products.add(new Product("Men's Cotton Jacket", 55.99, "/products/jacket1.jpg"));
        products.add(new Product("Men's Casual Premium Slim Fit T-Shirts", 22.30, "/products/tshirt1.jpg"));

        // Women's Clothing
        products.add(new Product("Women's Short Sleeve Moisture Wicking Athletic T-Shirt", 7.95,
                "/products/women-tshirt.jpg"));
        products.add(new Product("Women's Removable Hooded Faux Leather Moto Biker Jacket", 29.95,
                "/products/leather-jacket.jpg"));
        products.add(new Product("Women's Rain Jacket Windbreaker", 39.99, "/products/rain-jacket.jpg"));
        products.add(new Product("Women's 3-in-1 Snowboard Jacket", 56.99, "/products/jacket1.jpg"));

        // Dresses
        products.add(new Product("Women's Floral Print Dress", 45.00, "/products/dress1.jpg"));
        products.add(new Product("Summer Beach Dress", 35.50, "/products/beach-dress.jpg"));

        // Jeans & Pants
        products.add(new Product("Men's Slim Fit Jeans", 49.99, "/products/mens-jeans.jpg"));
        products.add(new Product("Women's High Waist Skinny Jeans", 42.99, "/products/womens-jeans.jpg"));

        // Shirts
        products.add(new Product("Men's Formal Shirt", 28.50, "/products/formal-shirt.jpg"));
        products.add(new Product("Women's Blouse", 24.99, "/products/dress1.jpg"));

        // Hoodies & Sweatshirts
        products.add(new Product("Unisex Hoodie", 38.00, "/products/hoodie.jpg"));
        products.add(new Product("Women's Sweatshirt", 32.50, "/products/sweatshirt.jpg"));

        // Shorts
        products.add(new Product("Men's Sports Shorts", 18.99, "/products/shorts-mens.jpg"));
        products.add(new Product("Women's Denim Shorts", 26.50, "/products/dress1.jpg"));

        // Activewear
        products.add(new Product("Compression T-Shirt", 21.99, "/products/compression.jpg"));
        products.add(new Product("Yoga Pants", 34.99, "/products/yoga-pants.jpg"));

        return products;
    }
}
