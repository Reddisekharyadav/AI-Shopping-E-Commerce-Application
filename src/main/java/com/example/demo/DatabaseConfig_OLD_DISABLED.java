package com.example.demo;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

// DISABLED: Migrated to Astra DB - PostgreSQL no longer used
// @Configuration
// @Profile("prod")
public class DatabaseConfig_OLD_DISABLED {

    @Bean
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");

        System.out.println("=== DATABASE CONFIGURATION ===");
        System.out.println("DATABASE_URL env variable: " + (databaseUrl != null ? "EXISTS" : "NOT SET"));

        if (databaseUrl != null && !databaseUrl.trim().isEmpty()) {
            System.out.println("DATABASE_URL value (masked): " + maskPassword(databaseUrl));

            // Parse PostgreSQL URL format: postgres://user:password@host:port/database
            if (databaseUrl.startsWith("postgres://") || databaseUrl.startsWith("postgresql://")) {
                try {
                    // Extract components from URL
                    String url = databaseUrl.replaceFirst("postgres(ql)?://", "");

                    // Split user:password@host:port/database
                    String[] userAndRest = url.split("@", 2);
                    if (userAndRest.length != 2) {
                        throw new IllegalArgumentException("Invalid DATABASE_URL format");
                    }

                    String[] userPass = userAndRest[0].split(":", 2);
                    String username = userPass[0];
                    String password = userPass.length > 1 ? userPass[1] : "";

                    // Split host:port/database
                    String hostAndDb = userAndRest[1];
                    String host;
                    int port = 5432; // default
                    String database;

                    // Check if port is specified
                    if (hostAndDb.contains(":") && hostAndDb.indexOf(":") < hostAndDb.indexOf("/")) {
                        String[] hostPort = hostAndDb.split("/", 2)[0].split(":", 2);
                        host = hostPort[0];
                        port = Integer.parseInt(hostPort[1]);
                        database = hostAndDb.split("/", 2)[1];
                    } else {
                        // No port specified
                        String[] parts = hostAndDb.split("/", 2);
                        host = parts[0];
                        database = parts[1];
                    }

                    // Build JDBC URL
                    String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);

                    System.out.println("Parsed - Host: " + host + ", Port: " + port + ", DB: " + database);
                    System.out.println("Final JDBC URL (masked): " + maskPassword(jdbcUrl));
                    System.out.println("✅ Using DATABASE_URL from environment");

                    return DataSourceBuilder.create()
                            .url(jdbcUrl)
                            .username(username)
                            .password(password)
                            .build();

                } catch (Exception e) {
                    System.out.println("❌ Error parsing DATABASE_URL: " + e.getMessage());
                    e.printStackTrace();
                }
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
