# Employee Management System

Designed a backend REST API built with Spring Boot, securing endpoints using JWT authentication, automating onboarding emails, and documenting APIs via OpenAPI (Swagger).

---

## Features

- **Authentication & Authorization**: JWT-based login endpoint securing application routes.
- **Employee Onboarding**: API to register new employees with automatic welcome email notifications via Spring Mail.
- **Unit Testing**: Comprehensive unit tests covering service layers using JUnit 5 and Mockito.
- **API Documentation**: Interactive OpenAPI/Swagger UI setup for quick endpoint testing.
- **Database Support**: MySQL for development runtime and H2 in-memory database for testing.

---

## Tech Stack

- **Java Version**: 17
- **Framework**: Spring Boot 3 / Spring Security
- **Database**: MySQL (Runtime), H2 (Testing)
- **OR/M**: Spring Data JPA / Hibernate
- **Authentication**: JSON Web Tokens (JJWT 0.13.0)
- **Documentation**: Springdoc OpenAPI / Swagger UI
- **Testing**: JUnit 5, Mockito, Spring Security Test
- **Utilities**: Lombok, Spring Boot Starter Mail

---

## Project Structure

```text
EmployeeManagementSystem/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── idealsoftware/
│   │   │           ├── config/           # Security & App Configurations
│   │   │           ├── controller/       # REST API Endpoints
│   │   │           ├── dto/              # Request/Response Data Objects
│   │   │           ├── entity/            # JPA Database Entities
│   │   │           ├── exception/         # Global Exception Handler & Custom Errors
│   │   │           ├── repository/        # Spring Data JPA Repositories
│   │   │           └── service/           # Business Logic Layer
│   │   │               ├── EmployeeServiceImpl.java
│   │   │               └── AuthService.java
│   │   └── resources/
│   │       └── application.properties  # App & Environment Configurations
│   └── test/
│       └── java/
│           └── com/
│               └── idealsoftware/
│                   └── service/          # Unit Tests with Mockito & JUnit
│                       ├── EmployeeServiceImplTest.java
│                       └── AuthServiceTest.java
└── pom.xml
```
---

## Getting Started
### Prerequisites
<ol>
<li> JDK 17 or higher</li>
<li> Maven 3.8+</li>
<li> MySQL Server running locally on port 3306</li>
</ol>

---

## Installation & Setup

1. **Clone the repository**:
```bash
git clone [https://github.com/your-username/EmployeeManagementSystem.git](https://github.com/your-username/EmployeeManagementSystem.git)
cd EmployeeManagementSystem
```

2. **Configure Database**: <br>
Create a MySQL database named `employee-management-system`:

```sql
CREATE DATABASE `employee-management-system`;
```
3. **Update Configuration**: <br>
   Ensure `src/main/resources/application.properties` matches your local environment details:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/employee-management-system
spring.datasource.username=root
spring.datasource.password=your_password

jwt.secretKey=your_super_secret_jwt_key

spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
```
4. **Build and Run**:
```bash
mvn clean install
mvn spring-boot:run
```

## API Documentation & Endpoint Reference
### Swagger UI
Access the interactive Swagger UI documentation once the application is running:

Swagger UI: `http://localhost:8080/swagger-ui.html` <br>
OpenAPI Specs: `http://localhost:8080/v3/api-docs` 

### Primary Endpoints

| HTTP Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/auth/login` | Authenticates user and returns a JWT token | No |
| `POST` | `/api/v1/employees` | Registers a new employee and triggers a welcome email | Yes (JWT) |