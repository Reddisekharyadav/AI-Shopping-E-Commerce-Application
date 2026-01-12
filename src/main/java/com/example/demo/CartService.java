package com.example.demo;

import com.example.demo.model.CartItem;
import com.example.demo.repository.CartItemRepository;
import java.util.List;
import java.util.UUID;
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

    public void deleteItem(UUID id) {
        repository.deleteById(id);
    }

    // Added method to retrieve a CartItem by its ID
    public CartItem getItemById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found with id: " + id));
    }
}