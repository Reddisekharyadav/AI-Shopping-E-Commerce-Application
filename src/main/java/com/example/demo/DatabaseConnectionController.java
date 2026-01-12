package com.example.demo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/database")
public class DatabaseConnectionController {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionController.class);
    private final MongoTemplate mongoTemplate;

    public DatabaseConnectionController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/status")
    public Map<String, Object> checkDatabaseConnection() {
        Map<String, Object> response = new HashMap<>();

        try {
            // Test MongoDB connection
            Document pingCommand = new Document("ping", 1);
            Document result = mongoTemplate.getDb().runCommand(pingCommand);

            boolean isConnected = result.getInteger("ok") == 1;

            if (isConnected) {
                response.put("status", "connected");
                response.put("database", mongoTemplate.getDb().getName());
                response.put("message", "Database connection successful");

                // Get collection names
                response.put("collections",
                        mongoTemplate.getDb().listCollectionNames().into(new java.util.ArrayList<>()));

                logger.info("Database connection test successful");
            } else {
                response.put("status", "error");
                response.put("message", "Database ping failed");
                logger.error("Database ping returned non-ok status");
            }

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Database connection failed: " + e.getMessage());
            logger.error("Database connection test failed", e);
        }

        return response;
    }

    @GetMapping("/users/count")
    public Map<String, Object> getUserCount() {
        Map<String, Object> response = new HashMap<>();

        try {
            long count = mongoTemplate.getCollection("users").countDocuments();
            response.put("status", "success");
            response.put("userCount", count);
            response.put("message", "User count retrieved successfully");
            logger.info("User count: {}", count);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to count users: " + e.getMessage());
            logger.error("Failed to count users", e);
        }

        return response;
    }
}
