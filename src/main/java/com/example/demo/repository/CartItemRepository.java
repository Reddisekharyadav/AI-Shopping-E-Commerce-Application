package com.example.demo.repository;

import com.example.demo.model.CartItem;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends CassandraRepository<CartItem, UUID> {
    @Query("SELECT * FROM cart_items WHERE username = ?0 ALLOW FILTERING")
    List<CartItem> findByUsername(String username);

    @Query("DELETE FROM cart_items WHERE username = ?0")
    void deleteByUsername(String username);
}