-- SQL script to update plain text passwords to BCrypt hashed passwords
-- Run these commands in MySQL after connecting to your aishopping database

USE aishopping;

-- Update existing passwords with BCrypt hashes
-- Password '143' for mango
UPDATE users SET password = '$2a$10$vH.vs0VzKGhBf5i1.RzMreJQUl6HY/KdH4MYkP0z8JwQGQMQwUx.a' WHERE username = 'mango';

-- Password '123' for marugani  
UPDATE users SET password = '$2a$10$eOZM1JX5hUxPIzQcLu3jI.LGLHhqzj7v7z4k1HGHdEzmK4/p9k9H6' WHERE username = 'marugani';

-- Password '345' for 22MIC7214
UPDATE users SET password = '$2a$10$F.YjPzHvZz3G2R/O4oT8.uBMLp6JYGLGJyQKQ4mZ4.L8vJ4QKZFsS' WHERE username = '22MIC7214';

-- Verify the updates
SELECT id, username, password FROM users;