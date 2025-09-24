package com.example.demo;

import com.example.demo.model.Product;
import com.example.demo.service.ClothesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Controller
public class DemoController {
    // Removed unused orderService field and constructor

    // Removed duplicate /cart mapping to resolve ambiguous mapping error

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final ClothesService clothesService;

    public DemoController(OrderService orderService, UserRepository userRepository, ClothesService clothesService) {
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.clothesService = clothesService;
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
        model.addAttribute("title", title != null ? title : "Default Title");
        model.addAttribute("price", price != null ? price : "0.0");
        model.addAttribute("image", image != null ? image : "/images/default-product.jpg");
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
            @RequestParam String color,
            @RequestParam String size,
            @RequestParam String paymentMethod,
            HttpSession session) {
        // Retrieve the logged-in user
        Object username = session.getAttribute(USERNAME_SESSION_KEY);
        if (username != null) {
            User user = userRepository.findByUsername(username.toString()).orElse(null);
            if (user != null) {
                // Save the order details
                Order order = new Order(title, new BigDecimal(price), color, size, paymentMethod, "SAVED", user);
                orderService.saveOrder(order);
            }
        }
        return "redirect:/payment?title=" + title + "&price=" + price + "&color=" + color + "&size=" + size
                + "&paymentMethod=" + paymentMethod;
    }

    @GetMapping("/orders")
    public String ordersPage(HttpSession session, Model model) {
        Object username = session.getAttribute(USERNAME_SESSION_KEY);
        if (username != null) {
            User user = userRepository.findByUsername(username.toString()).orElse(null);
            if (user != null) {
                var orders = orderService.getOrdersByUserId(user.getId());
                model.addAttribute("orders", orders);
            }
        }
        return "orders";
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

    @GetMapping("/Profile")
    public String profilePage() {
        return "Profile";
    }

    @GetMapping("/chatbot")
    public String chatbotPage() {
        return "chatbot";
    }

    @GetMapping("/help")
    public String helpRedirect() {
        // redirect help to chatbot
        return "redirect:/chatbot";
    }
    // Add endpoint for cart status display if needed
}