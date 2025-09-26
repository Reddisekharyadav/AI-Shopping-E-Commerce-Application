package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.io.File;
import java.util.concurrent.TimeUnit;

@Service
public class ChatbotBackendService {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotBackendService.class);

    @Value("${chatbot.python.port:5000}")
    private int pythonPort;

    @Value("${chatbot.python.enabled:true}")
    private boolean pythonEnabled;

    private Process pythonProcess;
    private static final String PYTHON_APP_PATH = "src/main/resources/templates/chatbot";
    private static final String PYTHON_SCRIPT = "app.py";

    @PostConstruct
    public void startPythonChatbot() {
        if (!pythonEnabled) {
            logger.info("Python chatbot backend is disabled");
            return;
        }

        try {
            logger.info("Starting Python chatbot backend on port {}...", pythonPort);

            // Check if Python is available
            if (!isPythonAvailable()) {
                logger.error("Python is not available. Please install Python and ensure it's in PATH");
                return;
            }

            // Check if the Python script exists
            File pythonScript = new File(PYTHON_APP_PATH, PYTHON_SCRIPT);
            if (!pythonScript.exists()) {
                logger.error("Python chatbot script not found at: {}", pythonScript.getAbsolutePath());
                return;
            }

            // Set environment variables
            ProcessBuilder processBuilder = new ProcessBuilder("python", PYTHON_SCRIPT);
            processBuilder.directory(new File(PYTHON_APP_PATH));

            // Add environment variables
            processBuilder.environment().put("FLASK_ENV", "production");
            processBuilder.environment().put("FLASK_PORT", String.valueOf(pythonPort));

            // Copy OpenAI API key from system environment
            String openaiKey = System.getenv("OPENAI_API_KEY");
            if (openaiKey != null) {
                processBuilder.environment().put("OPENAI_API_KEY", openaiKey);
            } else {
                logger.warn("OPENAI_API_KEY not found in environment variables");
            }

            // Start the process
            pythonProcess = processBuilder.start();

            logger.info("Python chatbot backend started successfully!");
            logger.info("Chatbot API will be available at: http://localhost:{}", pythonPort);

            // Wait a moment to check if process started successfully
            Thread.sleep(2000);
            if (!pythonProcess.isAlive()) {
                logger.error("Python chatbot backend failed to start or exited immediately");
            }

        } catch (IOException e) {
            logger.error("Failed to start Python chatbot backend: {}", e.getMessage());
        } catch (InterruptedException e) {
            logger.error("Interrupted while starting Python chatbot backend: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @PreDestroy
    public void stopPythonChatbot() {
        if (pythonProcess != null && pythonProcess.isAlive()) {
            logger.info("Stopping Python chatbot backend...");
            pythonProcess.destroy();

            try {
                // Wait for graceful shutdown
                boolean terminated = pythonProcess.waitFor(5, TimeUnit.SECONDS);
                if (!terminated) {
                    logger.warn("Python process did not terminate gracefully, forcing shutdown...");
                    pythonProcess.destroyForcibly();
                }
                logger.info("Python chatbot backend stopped");
            } catch (InterruptedException e) {
                logger.error("Interrupted while stopping Python chatbot backend");
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean isPythonAvailable() {
        try {
            Process process = new ProcessBuilder("python", "--version").start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    public boolean isPythonBackendRunning() {
        return pythonProcess != null && pythonProcess.isAlive();
    }

    public int getPythonPort() {
        return pythonPort;
    }
}