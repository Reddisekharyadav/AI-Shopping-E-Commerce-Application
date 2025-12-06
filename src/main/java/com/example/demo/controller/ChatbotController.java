package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotController.class);

    @Value("${openai.api.key:}")
    private String openAiApiKey;

    private RestTemplate restTemplate;

    public ChatbotController() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 5 seconds
        factory.setReadTimeout(10000); // 10 seconds
        RestTemplate template = new RestTemplate(factory);
        this.restTemplate = template;
    }

    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> ask(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");

        if (userMessage == null || userMessage.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "No user input provided"));
        }

        try {
            String response = getChatGptResponse(userMessage);
            return ResponseEntity.ok(Map.of("response", response));
        } catch (Exception e) {
            logger.error("Error calling OpenAI API", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get response from chatbot: " + e.getMessage()));
        }
    }

    private String getChatGptResponse(String userMessage) {
        if (openAiApiKey == null || openAiApiKey.isEmpty()) {
            return "Chatbot is not configured. Please set OPENAI_API_KEY environment variable.";
        }

        try {
            String url = "https://api.openai.com/v1/chat/completions";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openAiApiKey);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("messages", List.of(
                    Map.of("role", "system", "content",
                            "You are a helpful shopping assistant for an e-commerce platform."),
                    Map.of("role", "user", "content", userMessage)));
            requestBody.put("max_tokens", 500);
            requestBody.put("temperature", 0.7);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);

            if (response != null && response.containsKey("choices")) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (!choices.isEmpty()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return (String) message.get("content");
                }
            }

            return "Sorry, I couldn't generate a response.";

        } catch (Exception e) {
            logger.error("Error calling OpenAI API", e);
            return "Sorry, I'm having trouble connecting to the chat service. Please try again later.";
        }
    }
}
