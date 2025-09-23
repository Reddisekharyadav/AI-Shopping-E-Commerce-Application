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
AI-Shopping-E-Commerce-Application/
├── database.db                           # SQLite database
├── HELP.md                               # Spring Boot help documentation
├── mvnw                                  # Maven wrapper (Unix)
├── mvnw.cmd                              # Maven wrapper (Windows)
├── package.json                          # Node.js dependencies (if any)
├── pom.xml                               # Maven build configuration
├── query                                 # SQL query files
├── README.md                             # Project documentation
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── demo/             # Java source code
│   │   │               ├── DemoController.java      # Main controller
│   │   │               ├── DemoApplication.java     # Main application class
│   │   │               ├── LoginController.java     # Login/auth controller
│   │   │               ├── SignupController.java    # User registration controller
│   │   │               ├── User.java               # User entity model
│   │   │               ├── UserRepository.java     # Data access layer
│   │   │               └── UserService.java        # Business logic layer
│   │   └── resources/
│   │       ├── application.properties    # Spring Boot configuration
│   │       ├── static/                   # Static web assets (CSS, JS, images)
│   │       └── templates/                # Thymeleaf HTML templates
│   │           ├── Aboutus.html          # About us page
│   │           ├── ai_shopping.sql       # Database schema
│   │           ├── buypage.html          # Product purchase page
│   │           ├── Cart.html             # Shopping cart page
│   │           ├── chatbot.html          # AI chatbot interface
│   │           ├── Contact.html          # Contact page
│   │           ├── feedback.html         # User feedback page
│   │           ├── history.html          # Order history page
│   │           ├── home.html             # Main homepage
│   │           ├── Login.html            # User login page
│   │           ├── mrs.html              # Product recommendation page
│   │           ├── orderConfirmation.html # Order confirmation page
│   │           ├── orders.html           # Orders management page
│   │           ├── payment.html          # Payment processing page
│   │           ├── Profile.html          # User profile page
│   │           ├── Signup.html           # User registration page
│   │           ├── virtualdressing.html  # Virtual try-on feature
│   │           ├── chatbot/              # Chatbot related files
│   │           │   └── app.py           # Python chatbot backend
│   │           └── images/              # Image assets
│   │               ├── AI backround earth.jpg
│   │               ├── AI featuyte.jpg
│   │               ├── backround.png
│   │               ├── cart.jpeg
│   │               ├── carthistory.jpg
│   │               ├── contact.jpeg
│   │               ├── feedback.jpg
│   │               ├── home.png
│   │               ├── login.jpg
│   │               ├── logo.jpg
│   │               ├── logoforshopping.png
│   │               ├── profile.jpeg
│   │               └── [other product images]
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── demo/             # Test cases
│                       └── DemoApplicationTests.java
└── target/                              # Maven build output directory
    ├── classes/                         # Compiled Java classes
    ├── generated-sources/               # Generated source files
    ├── maven-status/                    # Maven build status
    ├── surefire-reports/               # Test reports
    └── test-classes/                   # Compiled test classes
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
