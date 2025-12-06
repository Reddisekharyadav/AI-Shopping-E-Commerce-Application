# Dockerfile for Python Flask Chatbot
FROM python:3.12-slim

# Set working directory
WORKDIR /app

# Copy chatbot files
COPY src/main/resources/templates/chatbot/ .

# Install dependencies
RUN pip install --no-cache-dir flask flask-cors openai

# Expose Flask port
EXPOSE 5000

# Set environment variables
ENV FLASK_APP=app.py
ENV FLASK_ENV=production

# Run the Flask application
CMD ["python", "app.py"]
