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
        
        if (databaseUrl != null && databaseUrl.startsWith("postgres://")) {
            // Convert Render's postgres:// URL to jdbc:postgresql://
            databaseUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://");
        }
        
        // Parse the URL to extract components
        if (databaseUrl != null && databaseUrl.startsWith("jdbc:postgresql://")) {
            return DataSourceBuilder.create()
                .url(databaseUrl)
                .build();
        }
        
        // Fallback to default configuration
        return DataSourceBuilder.create()
            .url("jdbc:postgresql://localhost:5432/aishopping")
            .username("postgres")
            .password(System.getenv("POSTGRES_PASSWORD"))
            .build();
    }
}
