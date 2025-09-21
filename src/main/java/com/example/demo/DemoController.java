package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.servlet.http.HttpSession;

@Controller
public class DemoController {
    // Removed unused orderService field and constructor

    // Removed duplicate /cart mapping to resolve ambiguous mapping error

    private final OrderService orderService;
    private final UserRepository userRepository;

    public DemoController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public RedirectView rootRedirect() {
        return new RedirectView("/mrs");
    }

    @GetMapping("/mrs")
    public String mrsPage() {
        return "mrs";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/buypage")
    public String buyPage(@RequestParam(required = false) String title,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String image,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("price", price);
        model.addAttribute("image", image);
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
        // Pass logged-in username from session
        Object username = session.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username.toString());
        }
        return "payment";
    }

    @GetMapping("/orders")
    public String ordersPage(HttpSession session, Model model) {
        Object username = session.getAttribute("username");
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