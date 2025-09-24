package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final CartService cartService; // Add CartService

    private static final String ORDERS_ATTRIBUTE = "orders"; // Define a constant for the "orders" string literal

    public OrderController(OrderService orderService, UserRepository userRepository, CartService cartService) {
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.cartService = cartService; // Initialize CartService
    }

    @PostMapping("/create")
    public void createOrder(@ModelAttribute Order order, @RequestParam(required = false) String username,
            HttpServletResponse response)
            throws IOException {
        if (username == null || username.isEmpty()) {
            logger.warn("Missing 'username' parameter");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'username' parameter");
            return;
        }

        logger.info("POST /orders/create endpoint hit with username: {}", username);
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            logger.info("User found: {}", username);
            order.setUser(userOpt.get());
        } else {
            logger.warn("User not found: {}", username);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found");
            return;
        }
        order.setStatus("SAVED");
        orderService.saveOrder(order);
        logger.info("Order saved for user: {}", username);
        // Return notification and confirm button
        response.setContentType("text/html");
        response.getWriter().write(
                "<div style='max-width:400px;margin:80px auto;padding:32px;background:#fff;border-radius:16px;box-shadow:0 2px 12px #ccc;text-align:center;'>"
                        + "<h2 style='color:#43cea2;'>Order Placed!</h2>"
                        + "<p>Your order has been placed and is being processed.</p>"
                        + "<button onclick=\"window.location.href='/orders'\" style='background:linear-gradient(135deg,#43cea2 0%,#185a9d 100%);color:#fff;padding:12px 32px;border:none;border-radius:8px;font-size:1.1rem;cursor:pointer;'>Go to Orders</button>"
                        + "</div>");
    }

    @PostMapping("/orders/cancel/{orderId}")
    public Order cancelOrder(@PathVariable Long orderId) {
        logger.info("POST /orders/cancel/{} endpoint hit", orderId);
        Order order = orderService.getOrderById(orderId);
        order.setStatus("CANCELLED");
        logger.info("Order {} cancelled", orderId);
        return orderService.saveOrder(order);
    }

    @PostMapping("/orders/complete/{orderId}")
    public Order completeOrder(@PathVariable Long orderId) {
        logger.info("POST /orders/complete/{} endpoint hit", orderId);
        Order order = orderService.getOrderById(orderId);
        order.setStatus("COMPLETED");
        logger.info("Order {} marked as completed", orderId);
        return orderService.saveOrder(order);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        logger.info("GET /orders/user/{} endpoint hit", userId);
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/all")
    public String getOrders(Model model) {
        logger.info("GET /orders/all endpoint hit");
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute(ORDERS_ATTRIBUTE, orders);
        logger.info("Orders added to model: {} orders", orders.size());
        return "orders";
    }

    @PostMapping("/confirm")
    public String confirmOrder(@RequestParam Long cartItemId, Model model) {
        logger.info("POST /orders/confirm endpoint hit with cartItemId: {}", cartItemId);
        CartItem cartItem = cartService.getItemById(cartItemId);
        Order order = new Order();
        order.setProductName(cartItem.getProductName());
        order.setPrice(cartItem.getPrice());
        order.setStatus("CONFIRMED");
        orderService.saveOrder(order);
        logger.info("Order confirmed for cartItemId: {}", cartItemId);
        cartService.deleteItem(cartItemId);
        model.addAttribute(ORDERS_ATTRIBUTE, orderService.getAllOrders());
        return OrderController.ORDERS_ATTRIBUTE;
    }
}
