package com.example.demo;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");

        System.out.println("=== DATABASE CONFIGURATION ===");
        System.out.println("DATABASE_URL env variable: " + (databaseUrl != null ? "EXISTS" : "NOT SET"));
        
        if (databaseUrl != null && !databaseUrl.trim().isEmpty()) {
            System.out.println("DATABASE_URL value (masked): " + maskPassword(databaseUrl));
            System.out.println("DATABASE_URL starts with: " + (databaseUrl.length() > 20 ? databaseUrl.substring(0, 20) : databaseUrl));
            
            if (databaseUrl.startsWith("postgres://")) {
                // Convert Render's postgres:// URL to jdbc:postgresql://
                databaseUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://");
                System.out.println("Converted postgres:// to jdbc:postgresql://");
            } else if (databaseUrl.startsWith("postgresql://")) {
                // Convert postgresql:// URL to jdbc:postgresql://
                databaseUrl = "jdbc:" + databaseUrl;
                System.out.println("Added jdbc: prefix to postgresql://");
            }
            
            // Parse the URL to extract components
            if (databaseUrl.startsWith("jdbc:postgresql://")) {
                System.out.println("✅ Using DATABASE_URL from environment");
                return DataSourceBuilder.create()
                        .url(databaseUrl)
                        .build();
            }
        }

        // Fallback to default configuration
        System.out.println("⚠️ WARNING: DATABASE_URL not valid, using localhost fallback");
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/aishopping")
                .username("postgres")
                .password(System.getenv("POSTGRES_PASSWORD"))
                .build();
    }

    private String maskPassword(String url) {
        // Mask password in logs for security
        return url.replaceAll("://([^:]+):([^@]+)@", "://$1:****@");
    }
}
