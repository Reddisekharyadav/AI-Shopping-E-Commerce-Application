package com.example.demo.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaInitializer {

    private static final Logger logger = LoggerFactory.getLogger(SchemaInitializer.class);

    @Value("${spring.cassandra.keyspace-name}")
    private String keyspace;

    @Bean
    public CommandLineRunner initializeSchema(CqlSession session) {
        return args -> {
            logger.info("=== INITIALIZING ASTRA DB SCHEMA ===");
            logger.info("Keyspace: {}", keyspace);
            logger.info("NOTE: Keyspace must be created manually in Astra DB console before running this application");

            // Create users table
            session.execute(String.format(
                    "CREATE TABLE IF NOT EXISTS %s.users (" +
                            "id UUID PRIMARY KEY, " +
                            "username TEXT, " +
                            "password TEXT" +
                            ")",
                    keyspace));
            logger.info("✓ Users table created/verified");

            // Create cart_items table
            session.execute(String.format(
                    "CREATE TABLE IF NOT EXISTS %s.cart_items (" +
                            "id UUID PRIMARY KEY, " +
                            "username TEXT, " +
                            "product_title TEXT, " +
                            "product_price DOUBLE, " +
                            "product_image TEXT, " +
                            "color TEXT, " +
                            "size TEXT, " +
                            "quantity INT, " +
                            "added_at TIMESTAMP" +
                            ")",
                    keyspace));
            logger.info("✓ Cart items table created/verified");

            // Create orders table
            session.execute(String.format(
                    "CREATE TABLE IF NOT EXISTS %s.orders (" +
                            "id UUID PRIMARY KEY, " +
                            "username TEXT, " +
                            "product_title TEXT, " +
                            "product_price DOUBLE, " +
                            "product_image TEXT, " +
                            "color TEXT, " +
                            "size TEXT, " +
                            "quantity INT, " +
                            "payment_method TEXT, " +
                            "status TEXT, " +
                            "order_date TIMESTAMP" +
                            ")",
                    keyspace));
            logger.info("✓ Orders table created/verified");

            // Create indexes for username lookups
            try {
                session.execute(String.format(
                        "CREATE INDEX IF NOT EXISTS users_username_idx ON %s.users (username)", keyspace));
                logger.info("✓ Index on users.username created/verified");
            } catch (Exception e) {
                logger.warn("Index on users.username may already exist: {}", e.getMessage());
            }

            try {
                session.execute(String.format(
                        "CREATE INDEX IF NOT EXISTS cart_items_username_idx ON %s.cart_items (username)", keyspace));
                logger.info("✓ Index on cart_items.username created/verified");
            } catch (Exception e) {
                logger.warn("Index on cart_items.username may already exist: {}", e.getMessage());
            }

            try {
                session.execute(String.format(
                        "CREATE INDEX IF NOT EXISTS orders_username_idx ON %s.orders (username)", keyspace));
                logger.info("✓ Index on orders.username created/verified");
            } catch (Exception e) {
                logger.warn("Index on orders.username may already exist: {}", e.getMessage());
            }

            logger.info("=== SCHEMA INITIALIZATION COMPLETE ===");
        };
    }
}
