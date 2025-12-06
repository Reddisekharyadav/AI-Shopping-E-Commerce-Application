#!/usr/bin/env bash
# Build script for Render deployment

echo "Starting Maven build..."
./mvnw clean package -DskipTests

echo "Build completed successfully!"
