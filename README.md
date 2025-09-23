# AI Shopping E-Commerce Application

## Overview
AI Shopping is a modern e-commerce web application built with Spring Boot, Thymeleaf, and Java. It features user authentication, product browsing, cart management, order history, payment integration, and an AI-powered chatbot for enhanced shopping experience.

## Features
- User registration and login
- Profile management with profile picture
- Product catalog and search
- Shopping cart and order management
- Payment gateway integration
- Order history and feedback
- AI-powered chatbot for customer support
- Responsive UI with professional CSS and animations

## Technologies Used
- Java 17+
- Spring Boot
- Thymeleaf
- Hibernate/JPA
- MySQL/SQLite (database)
- HTML5, CSS3, JavaScript
- Framer Motion (for animations)

## Project Structure
```
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/         # Java source code (controllers, services, models)
│   │   └── resources/
│   │       ├── templates/                 # Thymeleaf HTML templates
│   │       └── static/                    # Static assets (CSS, JS, images)
│   └── test/java/com/example/demo/        # Test cases
├── pom.xml                               # Maven build file
├── application.properties                # Spring Boot configuration
├── database.db                           # SQLite database (if used)
```

## Getting Started
### Prerequisites
- Java 17 or higher
- Maven
- MySQL or SQLite (or update DB config in `application.properties`)

### Setup & Run
1. Clone the repository:
   ```sh
   git clone https://github.com/Reddisekharyadav/AI-Shopping-E-Commerce-Application.git
   cd AI-Shopping-E-Commerce-Application
   ```
2. Configure the database in `src/main/resources/application.properties`.
3. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
4. Access the app at `http://localhost:8080`

## Usage
- Register a new user or log in with existing credentials.
- Browse products, add to cart, and proceed to checkout.
- Use the chatbot for assistance.
- View and update your profile, order history, and provide feedback.

## Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License.

## Author
- Reddisekharyadav
- [GitHub](https://github.com/Reddisekharyadav)
