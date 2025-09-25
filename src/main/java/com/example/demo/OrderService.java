package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.repository.OrderRepository;
import com.example.demo.model.*; // Add this import if Order is in the same package, or adjust the package as needed

@Service
public class OrderService {
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.findByUsername(username);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
