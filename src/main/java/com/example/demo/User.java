package com.example.demo;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.Column;
import java.util.UUID;

@Table("users")
public class User {

    @PrimaryKey
    private UUID id;

    @Column
    private String username;

    @Column
    private String password;

    // Default constructor for Cassandra
    public User() {
        this.id = UUID.randomUUID();
    }

    // Getter for id
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    // Getters and setters for other fields
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}