# College Community Chat Application 🎓

A real-time web-based chat application designed for college communities with role-based access control and year-wise chat rooms.

### Key Features ✨ 

Role-based Authentication (Student, Teacher, Admin) \
Real-time WebSocket Chat with multiple chat rooms \
Email Verification and password reset functionality \
Administrative Controls - block/unblock users, monitor login activity \
Year-wise Chat Rooms (General, First Year, Second Year, Third Year, Fourth Year) \
Academic Resources - Quick links to notes, PPTs, and previous year questions \
Profile Management with image upload 

### Tech Stack 🛠️ 

Backend: Spring Boot, Spring Security, Spring WebSocket, Spring Data JPA \
Frontend: Thymeleaf, HTML/CSS/JavaScript, Bootstrap \
Database: MySQL \
Email: Spring Mail (Gmail SMTP) \
Build Tool: Maven 

### Architecture 🏗️ 

Controllers: Chat, User, Admin, Teacher, Home controllers \
Security: Custom authentication with role-based access \
WebSocket: Real-time messaging without chat history storage \
Database: Two tables - user_dtls and user_sessions 
<br>
<br>
# Quick Start 🚀 

### Prerequisites

Java 17+ \
Maven 3.6+ \
MySQL 8.0+ \
Gmail account (for email features) 

### Installation

Clone the repository \
bashgit clone https://github.com/AyushAgrawal31/college-community-chat-app.git \
cd college-community-chat-app

### Setup Database

sql CREATE DATABASE college_chat_db; 

### Configure & Run Application

Copy application-example.properties to application.properties \
Update database credentials and Gmail settings \
bashmvn clean install \
mvn spring-boot:run 

### Access Application

Open browser: http://localhost:8080 or http://localhost:8081

### Database Schema 📊

user_dtls Table: 

reg_no (PK) - Student registration number \
full_name - User's full name \
email - Email address \
password - Encrypted password \
role - User role (STUDENT/TEACHER/ADMIN) \
account_non_lock - Account lock status \
enabled - Account enabled status \
verification_code - Email verification code \
profile_image - Profile image path 

user_sessions Table

id (PK) - Session ID \
email - User email \
login_time - Login timestamp \
logout_time - Logout timestamp

### User Roles 👥 

👨‍🎓 Student: Access to year-specific chat rooms and academic resources \
👨‍🏫 Teacher: Access to all chat rooms and student activity monitoring \
👨‍💼 Admin: Full system access, user management, and session monitoring

### Configuration 🔧

Email Setup (Gmail)

Enable 2-Factor Authentication \
Generate App Password: Google Account → Security → App Passwords \
Use app password in spring.mail.password

Database Configuration \
propertiesspring.datasource.url=jdbc:mysql://localhost:3306/college_chat_db \
spring.datasource.username=your_username \
spring.datasource.password=your_password 


### Features Overview 🎯 

Authentication Features

User registration with email verification \
Role-based login system \ 
Forgot password with email reset \
Account locking/unlocking by admin \
Profile image upload and management 

Chat Features

Real-time WebSocket messaging \
Multiple chat rooms (General + Year-wise) \
No chat history storage (privacy-focused) \
Online user indicators \
Message broadcasting

Administrative Features

User management dashboard \
Block/unblock user accounts \
Login activity monitoring \
Session tracking and management \
User statistics and reports

### Deployment 🚀 

Local Development \
bashmvn spring-boot:run \
Production Build \
bashmvn clean package \
java -jar target/chat-0.0.1-SNAPSHOT.jar \
🤝 Contributing

Fork the repository \
Create feature branch: git checkout -b feature/AmazingFeature \
Commit changes: git commit -m 'Add AmazingFeature' \
Push to branch: git push origin feature/AmazingFeature \
Open a Pull Request


### Developer 👨‍💻 
Ayush Agrawal \
GitHub: AyushAgrawal31 \
Email: ayushagrawal9630@gmail.com 

### 🙏 Acknowledgments 
Spring Boot community for the excellent framework \
MySQL for reliable database management \
Bootstrap for responsive UI components

Made with ❤️ for College Communities
