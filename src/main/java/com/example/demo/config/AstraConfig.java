package com.example.demo.config;

import com.datastax.astra.sdk.AstraClient;
import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.example.demo.repository")
public class AstraConfig {

    @Value("${astra.api.application-token}")
    private String token;

    @Value("${astra.api.database-id}")
    private String databaseId;

    @Value("${astra.api.database-region}")
    private String region;

    @Value("${spring.cassandra.keyspace-name}")
    private String keyspaceName;

    @Bean
    @Primary
    public AstraClient astraClient() {
        System.out.println("=== ASTRA DB CONNECTION ===");
        System.out.println("Database ID: " + databaseId);
        System.out.println("Region: " + region);
        System.out.println("Keyspace: " + keyspaceName);
        System.out.println("Token present: " + (token != null && !token.isEmpty()));

        return AstraClient.builder()
                .withToken(token)
                .withDatabaseId(databaseId)
                .withDatabaseRegion(region)
                .enableCql()
                .build();
    }

    @Bean
    @Primary
    public CqlSession cassandraSession() {
        CqlSession session = astraClient().cqlSession();

        // Set the default keyspace for the session
        session.execute("USE " + keyspaceName);
        System.out.println("Session configured with keyspace: " + keyspaceName);

        return session;
    }

    @Bean
    @Primary
    public CassandraTemplate cassandraTemplate() {
        return new CassandraTemplate(cassandraSession());
    }
}
