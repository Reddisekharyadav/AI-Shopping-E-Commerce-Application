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
     * GET /Login endpoint: renders the Login page
     */
    @GetMapping("/Login")
    public String showLoginPage() {
        return "Login";
    }

    /**
     * Logout endpoint: clears session and redirects to home page
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("User logging out and invalidating session");
        session.invalidate();
        // Forward to Thymeleaf template for logout page
        return "mrs";
    }

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
            @RequestParam String password,
            HttpSession session) {
        logger.info("POST /login endpoint hit with username: {}", username);
        if (!authService.userExists(username)) {
            logger.warn("User does not exist: {}", username);
            return "Signup";
        }

        if (authService.authenticate(username, password)) {
            logger.info("User authenticated successfully: {}", username);
            // Store username in session for later use
            session.setAttribute("username", username);
            return "home";
        } else {
            logger.warn("Authentication failed for username: {}", username);
            return "Login";
        }
    }
}