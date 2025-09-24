package com.example.demo;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartItemRepository repository;

    public CartService(CartItemRepository repository) {
        this.repository = repository;
    }

    public List<CartItem> getAllItems() {
        return repository.findAll();
    }

    public CartItem addItem(CartItem item) {
        return repository.save(item);
    }

    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    // Added method to retrieve a CartItem by its ID
    public CartItem getItemById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found with id: " + id));
    }
}