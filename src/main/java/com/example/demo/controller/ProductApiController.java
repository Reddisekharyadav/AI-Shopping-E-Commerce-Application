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
        products.add(new Product("Classic White T-Shirt", 19.99,
                "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"));
        products.add(
                new Product("Men's Casual Slim Fit", 15.99, "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg"));
        products.add(
                new Product("Men's Cotton Jacket", 55.99, "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"));
        products.add(new Product("Men's Casual Premium Slim Fit T-Shirts", 22.30,
                "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"));

        // Women's Clothing
        products.add(new Product("Women's Short Sleeve Moisture Wicking Athletic T-Shirt", 7.95,
                "https://fakestoreapi.com/img/51eg55uWmdL._AC_UX679_.jpg"));
        products.add(new Product("Women's Removable Hooded Faux Leather Moto Biker Jacket", 29.95,
                "https://fakestoreapi.com/img/81XH0e8fefL._AC_UY879_.jpg"));
        products.add(new Product("Women's Rain Jacket Windbreaker", 39.99,
                "https://fakestoreapi.com/img/71HblAHs5xL._AC_UY879_-2.jpg"));
        products.add(new Product("Women's 3-in-1 Snowboard Jacket", 56.99,
                "https://fakestoreapi.com/img/51Y5NI-I5jL._AC_UX679_.jpg"));

        // Dresses
        products.add(new Product("Women's Floral Print Dress", 45.00,
                "https://fakestoreapi.com/img/61pHAEJ4NML._AC_UX679_.jpg"));
        products.add(
                new Product("Summer Beach Dress", 35.50, "https://fakestoreapi.com/img/71z3kpMAYsL._AC_UY879_.jpg"));

        // Jeans & Pants
        products.add(
                new Product("Men's Slim Fit Jeans", 49.99, "https://fakestoreapi.com/img/71kWymZ+c+L._AC_SX679_.jpg"));
        products.add(new Product("Women's High Waist Skinny Jeans", 42.99,
                "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_.jpg"));

        // Shirts
        products.add(
                new Product("Men's Formal Shirt", 28.50, "https://fakestoreapi.com/img/71z3kpMAYsL._AC_UY879_.jpg"));
        products.add(new Product("Women's Blouse", 24.99, "https://fakestoreapi.com/img/61mtL65D4cL._AC_SX679_.jpg"));

        // Hoodies & Sweatshirts
        products.add(new Product("Unisex Hoodie", 38.00, "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"));
        products.add(
                new Product("Women's Sweatshirt", 32.50, "https://fakestoreapi.com/img/71HblAHs5xL._AC_UY879_-2.jpg"));

        // Shorts
        products.add(
                new Product("Men's Sports Shorts", 18.99, "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg"));
        products.add(
                new Product("Women's Denim Shorts", 26.50, "https://fakestoreapi.com/img/61pHAEJ4NML._AC_UX679_.jpg"));

        // Activewear
        products.add(
                new Product("Compression T-Shirt", 21.99, "https://fakestoreapi.com/img/51eg55uWmdL._AC_UX679_.jpg"));
        products.add(new Product("Yoga Pants", 34.99, "https://fakestoreapi.com/img/81XH0e8fefL._AC_UY879_.jpg"));

        return products;
    }
}
