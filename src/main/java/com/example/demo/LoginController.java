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
    private static final String USERNAME_SESSION_KEY = "username"; // Define a constant for session key

    @GetMapping({ "/Login", "/login" })
    public String showLoginPage(@RequestParam(name = "success", required = false) String success,
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "message", required = false) String message,
            @RequestParam(name = "logout", required = false) String logout,
            Model model) {
        logger.info("showLoginPage method invoked with logout param: {}", logout); // Debugging log

        if ("registration".equals(success)) {
            model.addAttribute("successMessage", message != null ? message : "Registration successful! Please login.");
        }

        if ("userExists".equals(error)) {
            model.addAttribute("errorMessage", message != null ? message : "User already exists. Please login.");
        } else if ("userNotFound".equals(error)) {
            model.addAttribute("errorMessage", message != null ? message : "User does not exist. Please signup first.");
        } else if ("invalidCredentials".equals(error)) {
            model.addAttribute("errorMessage",
                    message != null ? message : "Invalid username or password. Please try again.");
        }

        if (logout != null) {
            model.addAttribute("successMessage", "You have been logged out successfully.");
        }

        model.addAttribute("loginForm", new LoginForm()); // Add form-backing object
        return LOGIN_VIEW; // Render the Login view
    }

    /**
     * Logout endpoint: clears session and redirects to login page with logout
     * confirmation
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        String username = (String) session.getAttribute(USERNAME_SESSION_KEY);
        logger.info("User {} logging out and invalidating session", username != null ? username : "unknown");

        try {
            // Clear all session attributes first
            session.removeAttribute(USERNAME_SESSION_KEY);
            session.removeAttribute("cart");
            session.removeAttribute("user");

            // Invalidate the entire session
            session.invalidate();
            logger.info("Session invalidated successfully, redirecting to login with logout confirmation");
        } catch (Exception e) {
            logger.error("Error during session invalidation", e);
        }

        // Redirect to login page with logout parameter
        return "redirect:/Login?logout";
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
            return "redirect:/Login?error=userNotFound&message=User does not exist. Please signup first.";
        }

        if (authService.authenticate(username, password)) {
            logger.info("User authenticated successfully: {}", username);
            // Store username in session for later use
            session.setAttribute(USERNAME_SESSION_KEY, username);
            return "redirect:/home";
        } else {
            logger.warn("Authentication failed for username: {}", username);
            return "redirect:/Login?error=invalidCredentials&message=Invalid username or password. Please try again.";
        }
    }
}