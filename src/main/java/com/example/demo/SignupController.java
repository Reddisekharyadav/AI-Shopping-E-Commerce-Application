package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class SignupController {

    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);
    private final AuthService authService;

    public SignupController(AuthService authService) {
        this.authService = authService;
    }

    // Map both /Signup and /signup to the same Thymeleaf template
    @GetMapping({ "/Signup", "/signup" })
    public String showSignupForm(@RequestParam(name = "error", required = false) String error,
            Model model) {
        logger.info("GET /Signup or /signup endpoint hit");
        if (error != null) {
            logger.error("Error parameter received: " + error);
            String errorMessage = "Registration failed. Please try again.";
            if ("registrationFailed".equals(error)) {
                errorMessage = "Registration failed. User may already exist or database connection issue.";
            } else if ("userExists".equals(error)) {
                errorMessage = "User already exists. Please choose a different username.";
            } else if ("databaseError".equals(error)) {
                errorMessage = "Database connection error. Please try again later.";
            }
            model.addAttribute("errorMessage", errorMessage);
        }
        return "Signup"; // Thymeleaf template: src/main/resources/templates/Signup.html
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
            @RequestParam String password) {
        logger.info("POST /signup endpoint hit with username: {}", username);
        
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            logger.error("Username is null or empty");
            return "redirect:/signup?error=registrationFailed";
        }
        
        if (password == null || password.trim().isEmpty()) {
            logger.error("Password is null or empty");
            return "redirect:/signup?error=registrationFailed";
        }
        
        try {
            // Check if user already exists first
            if (authService.userExists(username)) {
                logger.warn("User {} already exists", username);
                return "redirect:/signup?error=userExists";
            }
            
            boolean registered = authService.registerNewUser(username, password);
            if (registered) {
                logger.info("User registered successfully: {}", username);
                return "redirect:/Login?success=registration";
            } else {
                logger.warn("Registration failed for username: {}", username);
                return "redirect:/signup?error=registrationFailed";
            }
        } catch (Exception e) {
            logger.error("Exception occurred during registration for username: {}", username, e);
            return "redirect:/signup?error=databaseError";
        }
    }
}