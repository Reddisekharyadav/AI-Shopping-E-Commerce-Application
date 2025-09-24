package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
// import org.slf4j.LoggerFactory; // Already imported if needed
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

@Controller
public class LoginController {
    /**
     * GET /Login endpoint: renders the Login page
     */
    private static final String LOGIN_VIEW = "Login"; // Define a constant for the Login view name

    @GetMapping("/Login")
    public String showLoginPage(Model model) {
        logger.info("showLoginPage method invoked"); // Debugging log
        model.addAttribute("loginForm", new LoginForm()); // Add form-backing object
        return LOGIN_VIEW; // Render the Login view
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
    public String login(@RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            HttpSession session) {
        if (username == null || username.isEmpty()) {
            logger.warn("Missing 'username' parameter");
            return LOGIN_VIEW;
        }

        if (password == null || password.isEmpty()) {
            logger.warn("Missing 'password' parameter");
            return LOGIN_VIEW;
        }

        logger.info("POST /login endpoint hit with username: {}", username);
        logger.info("Received password: {}", password); // Debugging purpose
        logger.info("All request parameters: username={}, password={}", username, password); // Log received parameters
        logger.debug("Checking if username parameter is present: username={}", username);
        logger.debug("Checking if password parameter is present: password={}", password);
        logger.debug("Entering login method with username: {} and password: {}", username, password);
        logger.debug("Session ID: {}", session.getId());

        if (!authService.userExists(username)) {
            logger.warn("User does not exist: {}", username);
            return "Signup";
        }

        if (authService.authenticate(username, password)) {
            logger.info("User authenticated successfully: {}", username);
            // Store username in session for later use
            session.setAttribute("username", username);
            return "redirect:/home";
        } else {
            logger.warn("Authentication failed for username: {}", username);
            return LOGIN_VIEW;
        }
    }
}