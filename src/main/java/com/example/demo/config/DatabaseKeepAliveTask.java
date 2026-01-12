package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoDatabase;

/**
 * Keeps MongoDB connection alive by pinging the database periodically.
 * This prevents MongoDB Atlas free tier from pausing due to inactivity.
 */
@Component
public class DatabaseKeepAliveTask {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseKeepAliveTask.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Pings the database every 5 minutes to keep the connection alive.
     * Runs at 0, 5, 10, 15, 20, 25... minutes of every hour.
     */
    @Scheduled(fixedRate = 300000) // 5 minutes in milliseconds
    public void keepDatabaseAlive() {
        try {
            MongoDatabase database = mongoTemplate.getDb();
            // Execute a simple ping command
            database.runCommand(new org.bson.Document("ping", 1));
            logger.debug("Database keep-alive ping successful");
        } catch (Exception e) {
            logger.error("Failed to ping database for keep-alive", e);
        }
    }

    /**
     * Additional health check that runs every 2 minutes.
     * Performs a lightweight query to ensure connection is active.
     */
    @Scheduled(fixedRate = 120000) // 2 minutes in milliseconds
    public void healthCheck() {
        try {
            // Simple count query to keep connection pool active
            long count = mongoTemplate.getDb().listCollectionNames().into(new java.util.ArrayList<>()).size();
            logger.debug("Database health check successful. Collections: {}", count);
        } catch (Exception e) {
            logger.warn("Database health check failed", e);
        }
    }
}
