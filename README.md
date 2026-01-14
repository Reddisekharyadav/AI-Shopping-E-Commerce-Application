# AI Shopping E-Commerce Application

## 🚀 Live Demo
**URL:** https://nextgenkart-app.onrender.com

### ⚡ First Visit Loading Time
The app is hosted on Render's free tier, which means:
- **First visit:** ~30-60 seconds (server wakes from sleep)
- **Subsequent visits:** Instant loading ⚡
- The server sleeps after 15 minutes of inactivity

🛒 AI Shopping – NextGen E-Commerce Application
🚀 Live Demo

URL: https://nextgenkart-app.onrender.com

⚡ First Visit Loading Time Notice

This application is hosted on Render Free Tier, which has cold starts:

First visit: ~30–60 seconds (server wakes up)

Subsequent visits: Instant ⚡

Server sleeps after 15 minutes of inactivity

🔧 Avoid Cold Starts (Optional)
Option 1: Keep-Alive (Free)

Use a free uptime service to ping the app:

UptimeRobot: https://uptimerobot.com

Cron-job.org: https://cron-job.org

UptimeRobot Setup

Create free account

Add Monitor → HTTP(s)

URL: https://nextgenkart-app.onrender.com

Interval: 5 minutes

Save

Option 2: Upgrade Render Plan

Starter: $7/month – Always on

Standard: $25/month – Better performance

📌 Overview

AI Shopping is a modern, cloud-ready AI-powered e-commerce web application built using Spring Boot and MongoDB Atlas.
It supports user authentication, product browsing, cart management, secure payments, order history, and an AI chatbot to enhance the shopping experience.

The application is fully cloud-based, scalable, and accessible from anywhere in the world.

✨ Features

User registration & login

Secure profile management with profile picture

Product catalog & search

Shopping cart & order processing

Payment gateway integration

Order history & feedback system

AI-powered chatbot for customer support

Virtual dressing / recommendation features

Responsive UI with modern animations

Cloud database (MongoDB Atlas)

🗄️ Database: MongoDB Atlas (Cloud)
🔄 Migration Update

The project has been migrated from local SQL databases (MySQL/SQLite) to MongoDB Atlas, enabling:

🌍 Global accessibility for all users

☁️ Fully managed cloud storage

🔐 Secure connections & authentication

🚀 Better scalability and performance

✅ Benefits

No local DB setup required

Automatic backups & monitoring

High availability

Ideal for cloud deployment (Render, AWS, etc.)

🧰 Technologies Used
Backend

Java 17+

Spring Boot

Spring Data MongoDB

MongoDB Atlas (Cloud)

Frontend

Thymeleaf

HTML5, CSS3, JavaScript

Framer Motion (animations)

Tools & DevOps

Maven

Git & GitHub

Render (Deployment)
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

⚙️ MongoDB Atlas Configuration
1️⃣ Create MongoDB Atlas Cluster

Visit https://www.mongodb.com/atlas

Create a free M0 cluster

Create a database user

Add IP Access:

0.0.0.0/0

2️⃣ Update application.properties
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster-name>.mongodb.net/nextgenkart
spring.data.mongodb.database=nextgenkart


🔐 Security Tip:
Use environment variables in production instead of hard-coding credentials.

3️⃣ Maven Dependency
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

▶️ Getting Started (Local Setup)
Prerequisites

Java 17+

Maven

MongoDB Atlas account

Steps
git clone https://github.com/Reddisekharyadav/AI-Shopping-E-Commerce-Application.git
cd AI-Shopping-E-Commerce-Application
mvn clean install
mvn spring-boot:run


Access the app at:

http://localhost:8080

🧑‍💻 Usage

Register or log in

Browse products & add to cart

Complete payment

View order history

Use AI chatbot for assistance

Manage profile & feedback

☁️ Cloud Deployment

This application is cloud-ready and works seamlessly with:

Render

AWS

Azure

Any Docker-based environment

MongoDB Atlas ensures persistent, globally accessible data.

🤝 Contributing

Contributions are welcome!

Fork the repository

Create a feature branch

Commit changes

Open a Pull Request

📜 License

This project is licensed under the MIT License.

👤 Author

Reddisekharyadav
🔗 GitHub: https://github.com/Reddisekharyadav)
