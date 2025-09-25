package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor to initialize userRepository
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        logger.info("AuthService initialized with UserRepository and BCryptPasswordEncoder");
    }

    // Registers a new user if they do not already exist
    public boolean registerNewUser(String username, String password) {
        try {
            logger.info("Attempting to register new user: {}", username);

            if (username == null || username.trim().isEmpty()) {
                logger.error("Registration failed: Username is null or empty");
                return false;
            }

            if (password == null || password.trim().isEmpty()) {
                logger.error("Registration failed: Password is null or empty");
                return false;
            }

            if (userRepository.existsByUsername(username)) {
                logger.warn("Registration failed: User {} already exists", username);
                return false; // User already exists
            }

            User newUser = new User();
            newUser.setUsername(username.trim());
            newUser.setPassword(passwordEncoder.encode(password)); // Hash the password

            User savedUser = userRepository.save(newUser);
            logger.info("User {} registered successfully with ID: {}", username, savedUser.getId());
            return true;

        } catch (Exception e) {
            logger.error("Exception occurred while registering user {}: ", username, e);
            return false;
        }
    }

    // Returns true if the user already exists
    public boolean userExists(String username) {
        try {
            boolean exists = userRepository.existsByUsername(username);
            logger.debug("User {} exists: {}", username, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Exception occurred while checking if user {} exists: ", username, e);
            return false;
        }
    }

    // Authenticates the user by checking if the password matches
    public boolean authenticate(String username, String password) {
        try {
            logger.info("Attempting to authenticate user: {}", username);
            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                String storedPassword = user.getPassword();

                // Check if password is BCrypt hashed (starts with $2a$ or $2b$)
                boolean isBCryptHash = storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$");

                if (isBCryptHash) {
                    // Use BCrypt matching for hashed passwords
                    if (passwordEncoder.matches(password, storedPassword)) {
                        logger.info("Authentication successful for user: {} (BCrypt)", username);
                        return true;
                    }
                } else {
                    // Plain text comparison for legacy passwords
                    if (password.equals(storedPassword)) {
                        logger.info("Authentication successful for user: {} (Plain text - consider updating to BCrypt)",
                                username);
                        return true;
                    }
                }
            }
            logger.warn("Authentication failed for user: {}", username);
            return false;
        } catch (Exception e) {
            logger.error("Exception occurred while authenticating user {}: ", username, e);
            return false;
        }
    }
}