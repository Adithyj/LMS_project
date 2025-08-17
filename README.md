📚 Learning Management System (LMS) - Backend

This project is a backend for a Learning Management System (LMS) built with Spring Boot, Spring Security, and JPA/Hibernate.
It provides secure authentication for students and REST APIs to manage student details.

🚀 Features

Student authentication using email + password (Spring Security).

Passwords stored securely with BCrypt hashing.

Role-based authorization (ROLE_STUDENT).

RESTful CRUD APIs for Student.

Automatic redirect to /lms/students/me after successful login.

Supports stateless API use with Postman or frontend clients.

🛠️ Tech Stack

Java 17+

Spring Boot 3+

Spring Security 6

Spring Data JPA

Hibernate

H2 / MySQL (configurable)

Maven

⚙️ Project Structure
src/main/java/com/example/lms
│
├── controller
│   └── StudentController.java   # REST APIs for Student
│
├── entity
│   └── Student.java             # Student entity
│
├── repository
│   └── StudentRepository.java   # Data access layer
│
├── security
│   ├── SecurityConfig.java      # Spring Security config
│   └── StudentDetailsService.java # Custom UserDetailsService
│
└── service
    └── StudentService.java      # Business logic

🔑 Authentication Flow

Student logs in using email + password.

Spring Security checks credentials against the database.

If valid → User is authenticated and redirected to /lms/students/me.

API returns the logged-in student’s details.

🧪 API Endpoints
🔐 Authentication

POST /login → Login with email & password (handled by Spring Security).

POST /logout → Logout.

👨‍🎓 Student APIs
Method	Endpoint	Description	Role
GET	/lms/students	Get all students	STUDENT
GET	/lms/students/{id}	Get student by ID	STUDENT
GET	/lms/students/email/{email}	Get student by email	STUDENT
GET	/lms/students/me	Get current logged-in student	STUDENT
POST	/lms/students	Create a new student	PUBLIC
PUT	/lms/students/{id}	Update student	STUDENT
DELETE	/lms/students/{id}	Delete student	STUDENT
▶️ How to Run
1. Clone the repo
git clone https://github.com/your-username/lms-backend.git
cd lms-backend

2. Configure DB

Edit application.properties:

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

3. Run the project
mvn spring-boot:run

4. Access

H2 Console → http://localhost:8080/h2-console

API base URL → http://localhost:8080/lms/students

🔑 Example Login (via Postman)

Create a student (password should be BCrypt-encoded in DB).

POST /lms/students
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "$2a$10$ZpJX...",
  "role": "STUDENT"
}


Login

POST /login
Body (x-www-form-urlencoded):
  username = john@example.com
  password = yourPassword


Get current student

GET /lms/students/me
(Requires session or token)
