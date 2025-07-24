🥗 FoodSafety – Full Stack Web Application with Custom Blockchain Logic

🎓 This project was developed as my graduation project for the Computer Engineering program at Sakarya University.

A full stack web application for tracking food products using a custom-built blockchain structure in the backend.
The system helps determine whether a product is safe or unsafe by validating its transaction history through a simplified blockchain.

🔐 Key Feature
Developed a blockchain structure from scratch using Java and Spring Boot.

Each product operation is stored as a block in the chain.

Ensures data integrity, traceability, and tamper-proof tracking of food processing steps.

⚙️ Technologies Used
Frontend: React

Backend: Spring Boot (Java 17)

Database: MySQL

JPA / Hibernate

IntelliJ IDEA (for backend)

Visual Studio Code (for frontend)

🚀 How to Run the Project
📦 Backend (Spring Boot)
Open the project in IntelliJ IDEA (or your preferred IDE).

Make sure MySQL is installed and running (default port: 3306).

Create a database (name should match the one in application.properties).

Update the following in src/main/resources/application.properties:
      spring.datasource.username=your_mysql_username
      spring.datasource.password=your_mysql_password
      spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name

Run the Spring Boot application. It runs on:
      📍 http://localhost:8080


💻 Frontend (React)
Open a terminal in the React project folder.

Run the following commands:
      npm install
      npm run start
      
React app runs on:
      📍 http://localhost:3000

🛢️ Database
MySQL must be running on port 3306.

You can create the database manually using MySQL Workbench or terminal.

Table creation is handled automatically via JPA when the backend starts.
      MySQL must be running on port 3306.

The database connection settings (username, password, DB name) should be updated in:
      src/main/resources/application.properties

🎥 Demo
A short video showing the working version of the application will be added to this repository soon.
📌 The video is not uploaded yet but will be included shortly.

