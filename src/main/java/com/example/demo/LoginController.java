package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
// import org.slf4j.LoggerFactory; // Already imported if needed
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {
    /**
     * Logout endpoint: clears session and redirects to home page
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("User logging out and invalidating session");
        session.invalidate();
        // Forward to static home page after logout
        return "forward:/AI-shopping/AI-shopping/mrs.html";
    }

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
            @RequestParam String password) {
        logger.info("POST /login endpoint hit with username: {}", username);
        if (!authService.userExists(username)) {
            logger.warn("User does not exist: {}", username);
            return "Signup"; // This returns the Thymeleaf view from src/main/resources/templates/Signup.html
        }

        if (authService.authenticate(username, password)) {
            logger.info("User authenticated successfully: {}", username);
            // Redirect to the home page on successful login
            return "redirect:/AI-shopping/AI-shopping/home.html";
        } else {
            logger.warn("Authentication failed for username: {}", username);
            // Redirect back to login with an error parameter on failure
            return "redirect:/AI-shopping/AI-shopping/login.html";
        }
    }
}