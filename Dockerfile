# Multi-stage Dockerfile for Spring Boot Application
FROM eclipse-temurin:17-jre-alpine AS runtime

# Set working directory
WORKDIR /app

# Copy the JAR file from target folder
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 3434

# Set environment variables (will be overridden by Azure)
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
