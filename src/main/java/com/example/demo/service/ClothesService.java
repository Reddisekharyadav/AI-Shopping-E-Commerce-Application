package com.example.demo.service;

import com.example.demo.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClothesService {

    private static final Logger logger = LoggerFactory.getLogger(ClothesService.class);

    private final RestTemplate restTemplate;

    public ClothesService() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 5 seconds connection timeout
        factory.setReadTimeout(10000); // 10 seconds read timeout
        RestTemplate template = new RestTemplate(factory);
        this.restTemplate = template;
    }

    public List<Product> fetchClothesFromApis() {
        List<Product> products = new ArrayList<>();

        // Fetch from Fake Store API
        try {
            Product[] fakeStoreProducts = restTemplate.getForObject("https://fakestoreapi.com/products",
                    Product[].class);
            if (fakeStoreProducts != null) {
                products.addAll(List.of(fakeStoreProducts));
            }
        } catch (Exception ex) {
            logger.error("Failed to fetch from Fake Store API: {}", ex.getMessage());
        }

        // Fetch from Dummy JSON API
        try {
            DummyJsonResponse dummyJsonResponse = restTemplate.getForObject("https://dummyjson.com/products",
                    DummyJsonResponse.class);
            if (dummyJsonResponse != null && dummyJsonResponse.getProducts() != null) {
                products.addAll(dummyJsonResponse.getProducts());
            }
        } catch (Exception ex) {
            logger.error("Failed to fetch from Dummy JSON API: {}", ex.getMessage());
        }

        // Fallback logic: Add default products if no products were fetched
        if (products.isEmpty()) {
            logger.warn("No products fetched from APIs. Adding default products.");
            products.add(new Product("Default T-Shirt", 9.99, "/images/default-tshirt.jpg"));
            products.add(new Product("Default Jeans", 19.99, "/images/default-jeans.jpg"));
        }

        return products;
    }

    // Inner class to handle Dummy JSON API response
    private static class DummyJsonResponse {
        private List<Product> products;

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }
    }
}