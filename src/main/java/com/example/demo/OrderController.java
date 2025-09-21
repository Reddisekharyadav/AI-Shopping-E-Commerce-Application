package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public void createOrder(@ModelAttribute Order order, @RequestParam String username, HttpServletResponse response)
            throws IOException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            order.setUser(userOpt.get());
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found");
            return;
        }
        order.setStatus("SAVED");
        orderService.saveOrder(order);
        // Return notification and confirm button
        response.setContentType("text/html");
        response.getWriter().write(
                "<div style='max-width:400px;margin:80px auto;padding:32px;background:#fff;border-radius:16px;box-shadow:0 2px 12px #ccc;text-align:center;'>"
                        +
                        "<h2 style='color:#43cea2;'>Order Placed!</h2>" +
                        "<p>Your order has been placed and is being processed.</p>" +
                        "<button onclick=\"window.location.href='/orders'\" style='background:linear-gradient(135deg,#43cea2 0%,#185a9d 100%);color:#fff;padding:12px 32px;border:none;border-radius:8px;font-size:1.1rem;cursor:pointer;'>Go to Orders</button>"
                        +
                        "</div>");
    }

    @PostMapping("/orders/cancel/{orderId}")
    public Order cancelOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        order.setStatus("CANCELLED");
        return orderService.saveOrder(order);
    }

    @PostMapping("/orders/complete/{orderId}")
    public Order completeOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        order.setStatus("COMPLETED");
        return orderService.saveOrder(order);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

}
