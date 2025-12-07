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

        // Add User-Agent header to avoid bot detection
        template.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            return execution.execute(request, body);
        });

        this.restTemplate = template;
    }

    public List<Product> fetchClothesFromApis() {
        List<Product> products = new ArrayList<>();

        // Fetch from local API endpoint (no external dependencies)
        try {
            String baseUrl = System.getProperty("app.baseUrl", "http://localhost:8080");
            Product[] localProducts = restTemplate.getForObject(baseUrl + "/api/products",
                    Product[].class);
            if (localProducts != null) {
                products.addAll(List.of(localProducts));
                logger.info("Successfully fetched {} products from local API", products.size());
            }
        } catch (Exception ex) {
            logger.error("Failed to fetch from local API: {}", ex.getMessage());

            // Fallback: Add products directly if API call fails (e.g., during startup)
            products.add(new Product("Classic White T-Shirt", 19.99,
                    "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"));
            products.add(new Product("Men's Casual Slim Fit", 15.99,
                    "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg"));
            products.add(new Product("Men's Cotton Jacket", 55.99,
                    "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"));
            products.add(new Product("Women's Short Sleeve T-Shirt", 7.95,
                    "https://fakestoreapi.com/img/51eg55uWmdL._AC_UX679_.jpg"));
            products.add(new Product("Women's Leather Jacket", 29.95,
                    "https://fakestoreapi.com/img/81XH0e8fefL._AC_UY879_.jpg"));
            products.add(new Product("Women's Rain Jacket", 39.99,
                    "https://fakestoreapi.com/img/71HblAHs5xL._AC_UY879_-2.jpg"));
            products.add(new Product("Women's Snowboard Jacket", 56.99,
                    "https://fakestoreapi.com/img/51Y5NI-I5jL._AC_UX679_.jpg"));
            products.add(new Product("Women's Floral Dress", 45.00,
                    "https://fakestoreapi.com/img/61pHAEJ4NML._AC_UX679_.jpg"));
            logger.info("Added {} fallback products", products.size());
        }

        return products;
    }
}