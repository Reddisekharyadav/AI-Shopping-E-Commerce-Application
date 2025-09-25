package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import jakarta.servlet.http.HttpSessionListener;
import jakarta.servlet.http.HttpSessionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class SessionConfig {

    private static final Logger logger = LoggerFactory.getLogger(SessionConfig.class);

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {
                logger.info("Session created: {}", se.getSession().getId());
                // Set session timeout to 24 hours (86400 seconds)
                se.getSession().setMaxInactiveInterval(86400);
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent se) {
                logger.info("Session destroyed: {}", se.getSession().getId());
            }
        };
    }
}