package com.example.demo;

import com.example.demo.model.Product;
import com.example.demo.model.CartItem;
import com.example.demo.model.Order;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.ClothesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Controller
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
    private final ClothesService clothesService;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    public DemoController(ClothesService clothesService, CartItemRepository cartItemRepository,
            OrderRepository orderRepository) {
        this.clothesService = clothesService;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
    }

    private static final String USERNAME_SESSION_KEY = "username";

    @GetMapping("/")
    public RedirectView rootRedirect() {
        return new RedirectView("/mrs");
    }

    @GetMapping("/mrs")
    public String mrsPage() {
        return "mrs";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        // Pass logged-in username from session
        Object username = session.getAttribute(USERNAME_SESSION_KEY);
        if (username != null) {
            model.addAttribute("username", username.toString());
        }

        // Populate categories
        List<String> categories = Arrays.asList("Electronics", "Clothes", "Home Appliances");
        model.addAttribute("categories", categories);

        // Populate subcategories for clothes
        List<String> subcategories = Arrays.asList("Men", "Women", "Kids");
        model.addAttribute("subcategories", subcategories);

        // Fetch products dynamically from APIs
        List<Product> products = clothesService.fetchClothesFromApis();
        model.addAttribute("products", products);

        // Set default value for profile menu visibility
        model.addAttribute("profileMenuVisible", false);

        return "home";
    }

    @GetMapping("/buypage")
    public String buyPage(@RequestParam(required = false) String title,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String image,
            Model model) {
        logger.info("BuyPage accessed with title: {}, price: {}, image: {}", title, price, image);

        // Create a product object to pass to the template
        Product product = new Product();
        product.setTitle(title != null ? title : "Default Title");
        product.setPrice(price != null ? Double.parseDouble(price) : 0.0);
        product.setImage(image != null ? image : "/images/default-product.jpg");

        model.addAttribute("product", product);
        model.addAttribute("title", product.getTitle());
        model.addAttribute("price", product.getPrice());
        model.addAttribute("image", product.getImage());

        return "buypage";
    }

    @GetMapping("/payment")
    public String paymentPage(@RequestParam(required = false) String title,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String paymentMethod,
            HttpSession session,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("price", price);
        model.addAttribute("color", color);
        model.addAttribute("size", size);
        model.addAttribute("paymentMethod", paymentMethod);
        model.addAttribute("paymentSuccess", false); // Default value to prevent null errors
        // Pass logged-in username from session
        Object username = session.getAttribute(USERNAME_SESSION_KEY);
        if (username != null) {
            model.addAttribute("username", username.toString());
        }
        return "payment";
    }

    @PostMapping("/payment")
    public String processPayment(@RequestParam String title,
            @RequestParam String price,
            @RequestParam String image,
            @RequestParam String color,
            @RequestParam String size,
            @RequestParam String paymentMethod,
            HttpSession session) {
        // Retrieve the logged-in user
        Object username = session.getAttribute(USERNAME_SESSION_KEY);
        if (username != null) {
            // Save the order details
            Order order = new Order();
            order.setUsername(username.toString());
            order.setProductTitle(title);
            order.setProductPrice(Double.parseDouble(price));
            order.setProductImage(image);
            order.setColor(color);
            order.setSize(size);
            order.setPaymentMethod(paymentMethod);
            order.setStatus(Order.OrderStatus.CONFIRMED);
            orderRepository.save(order);
        }
        return "redirect:/orderConfirmation";
    }

    @GetMapping("/orders")
    public String ordersPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/Login";
        }

        try {
            List<Order> orders = orderRepository.findByUsernameOrderByOrderDateDesc(username);
            model.addAttribute("orders", orders);
            model.addAttribute("username", username);

            return "orders";
        } catch (Exception e) {
            logger.error("Error loading orders for user: {}", username, e);
            return "redirect:/home";
        }
    }

    @GetMapping("/orders/all")
    public String allOrdersPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/Login";
        }

        try {
            List<Order> orders = orderRepository.findByUsernameOrderByOrderDateDesc(username);
            model.addAttribute("orders", orders);
            model.addAttribute("username", username);
            logger.info("Loaded {} orders for user: {}", orders.size(), username);

            return "orders";
        } catch (Exception e) {
            logger.error("Error loading all orders for user: {}", username, e);
            return "redirect:/home";
        }
    }

    @GetMapping("/Aboutus")
    public String aboutusPage() {
        return "Aboutus";
    }

    @GetMapping("/Contact")
    public String contactPage() {
        return "Contact";
    }

    @GetMapping("/feedback")
    public String feedbackPage() {
        return "feedback";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam String title,
            @RequestParam String price,
            @RequestParam String image,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String size,
            HttpSession session,
            Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            logger.warn("User not logged in, redirecting to login");
            return "redirect:/Login";
        }

        try {
            logger.info("Adding item to cart for user: {}", username);

            CartItem cartItem = new CartItem();
            cartItem.setUsername(username);
            cartItem.setProductTitle(title);
            cartItem.setProductPrice(Double.parseDouble(price));
            cartItem.setProductImage(image);
            cartItem.setColor(color);
            cartItem.setSize(size);

            cartItemRepository.save(cartItem);
            logger.info("Item added to cart successfully for user: {}", username);

            model.addAttribute("message", "Item added to cart successfully!");
            return "redirect:/cart";

        } catch (Exception e) {
            logger.error("Error adding item to cart for user: {}", username, e);
            model.addAttribute("error", "Failed to add item to cart");
            return "redirect:/buypage?title=" + title + "&price=" + price + "&image=" + image;
        }
    }

    @PostMapping("/cart/delete")
    public String deleteCartItem(@RequestParam Long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/Login";
        }

        try {
            // Find the cart item and verify it belongs to the current user
            CartItem cartItem = cartItemRepository.findById(id).orElse(null);
            if (cartItem != null && cartItem.getUsername().equals(username)) {
                cartItemRepository.deleteById(id);
                logger.info("Cart item {} deleted successfully for user: {}", id, username);
            }
            return "redirect:/cart";
        } catch (Exception e) {
            logger.error("Error deleting cart item {} for user: {}", id, username, e);
            return "redirect:/cart";
        }
    }

    @PostMapping("/confirmOrder")
    public String confirmOrder(@RequestParam String title,
            @RequestParam String price,
            @RequestParam String image,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String size,
            @RequestParam String paymentMethod,
            HttpSession session,
            Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            logger.warn("User not logged in, redirecting to login");
            return "redirect:/Login";
        }

        try {
            logger.info("Confirming order for user: {}", username);

            Order order = new Order();
            order.setUsername(username);
            order.setProductTitle(title);
            order.setProductPrice(Double.parseDouble(price));
            order.setProductImage(image);
            order.setColor(color);
            order.setSize(size);
            order.setPaymentMethod(paymentMethod);
            order.setStatus(Order.OrderStatus.CONFIRMED);

            orderRepository.save(order);
            logger.info("Order confirmed successfully for user: {}", username);

            model.addAttribute("message", "Order placed successfully!");
            model.addAttribute("order", order);
            return "orderConfirmation";

        } catch (Exception e) {
            logger.error("Error confirming order for user: {}", username, e);
            model.addAttribute("error", "Failed to place order");
            return "redirect:/payment";
        }
    }

    @GetMapping("/cart")
    public String cartPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/Login";
        }

        try {
            List<CartItem> cartItems = cartItemRepository.findByUsername(username);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("username", username);

            // Calculate total
            double total = cartItems.stream()
                    .mapToDouble(CartItem::getProductPrice)
                    .sum();
            model.addAttribute("total", total);

            return "Cart";
        } catch (Exception e) {
            logger.error("Error loading cart for user: {}", username, e);
            return "redirect:/home";
        }
    }

    @GetMapping("/chatbot")
    public String chatbotPage() {
        return "chatbot";
    }

    @GetMapping("/orderConfirmation")
    public String orderConfirmation(HttpSession session, Model model) {
        Object username = session.getAttribute(USERNAME_SESSION_KEY);
        if (username != null) {
            model.addAttribute("username", username.toString());
            return "orderConfirmation";
        } else {
            return "redirect:/Login";
        }
    }

    @GetMapping("/Profile")
    public String profilePage() {
        return "Profile";
    }

    @GetMapping("/help")
    public String helpRedirect() {
        // redirect help to chatbot
        return "redirect:/chatbot";
    }

    @GetMapping("/favicon.ico")
    public void favicon(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @GetMapping("/robots.txt")
    public void robots(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @GetMapping("/session/heartbeat")
    public ResponseEntity<String> sessionHeartbeat(HttpSession session) {
        String username = (String) session.getAttribute(USERNAME_SESSION_KEY);
        if (username != null) {
            logger.debug("Session heartbeat for user: {}", username);
            return ResponseEntity.ok("alive");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("no-session");
    }
}