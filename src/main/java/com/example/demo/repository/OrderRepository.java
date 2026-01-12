package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends CassandraRepository<Order, UUID> {
    @Query("SELECT * FROM orders WHERE username = ?0 ALLOW FILTERING")
    List<Order> findByUsername(String username);

    @Query("SELECT * FROM orders WHERE username = ?0 ALLOW FILTERING")
    List<Order> findByUsernameOrderByOrderDateDesc(String username);
}