package com.example.demo;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;

    // Constructor to initialize userRepository
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // This in-memory map simulates a database (for demo purposes only)
    private Map<String, String> users = new ConcurrentHashMap<>();

    // @PostConstruct
    // public void initialize() {
    // // Seed a specific user to the "database"
    // // You can change "user1" and "pass1" to your desired credentials
    // registerNewUser("mango", "mango143");
    // }

    // Registers a new user if they do not already exist
    public boolean registerNewUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            return false; // User already exists
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // Consider hashing the password
        userRepository.save(newUser);
        return true;
    }

    // Returns true if the user already exists
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // Authenticates the user by checking if the password matches
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}