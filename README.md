## 🥗 FoodSafety – Full Stack Web Application with Custom Blockchain Logic

### 🎓 Graduation Project  
This project was developed as my graduation project for the Computer Engineering program at Sakarya University.  

<br/>

A full stack web application for tracking food products using a **custom-built blockchain structure** in the backend.  
The system helps determine whether a product is **safe** or **unsafe** by validating its transaction history through a simplified blockchain.  

<br/>

### 🔐 Key Feature  
- Developed a **blockchain structure from scratch** using Java and Spring Boot.  
- Each product operation is stored as a block in the chain.  
- Ensures **data integrity**, **traceability**, and **tamper-proof tracking** of food processing steps.  

<br/>

### ⚙️ Technologies Used  
- **Frontend**: React  
- **Backend**: Spring Boot (Java 17)  
- **Database**: MySQL  
- **ORM**: JPA / Hibernate  
- **IDE**: IntelliJ IDEA (backend), Visual Studio Code (frontend)  

<br/>

### 🚀 How to Run the Project  

#### 📦 Backend (Spring Boot)  
1. Open the project in IntelliJ IDEA (or your preferred IDE).  
2. Make sure **MySQL** is installed and running (default port: `3306`).  
3. Create a database (the name must match the one in `application.properties`).  
4. Update the following in `src/main/resources/application.properties`:  

   ```properties
   spring.datasource.username=your_mysql_username  
   spring.datasource.password=your_mysql_password  
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name  

5.Run the Spring Boot application. It will be available at:
📍 http://localhost:8080

<br/>

#### 💻 Frontend (React)
1. Open a terminal in the React project folder.

2. Run the following commands:
   ```properties
   npm install  
   npm run start  

3.React app will run at:
📍 http://localhost:3000

<br/>

#### 🛢️ Database
1. MySQL must be running on port 3306.

2. You can create the database manually using MySQL Workbench or terminal.

3. The database connection settings (username, password, DB name) should be updated in:
      src/main/resources/application.properties

#### 🎥 Demo
A short video showing the working version of the application will be added to this repository soon.

📌 The video is not uploaded yet but will be included shortly.
