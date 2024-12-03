# ContactlyApp

Contactly is a comprehensive contact management application built using Java and Spring Boot. It supports role-based access control (RBAC) with two user roles: Admin and User. The app provides features such as contact management, user authentication, audit logging, and an admin dashboard. Security features include strong password validation and JWT-based authentication.

## Features

### General Features
- **User Authentication**: Login and registration functionality for users.
- **Role-Based Access Control (RBAC)**:
  - **Admin**: Access to all contacts and the admin dashboard.
  - **User**: Manage their own contacts only.
- **Contact Management**: Create, view, update, and delete contacts.
- **Session Management**: Persistent sessions for logged-in users using JWT.
- **Audit Logging**: Entity history tracking via Hibernate Envers.

### Admin Features
- **View all contacts** and manage user data.

## Security Features
- **Email and phone number uniqueness validation** during registration.
- **Password strength validation**.
- **JWT-based authentication**.
- **Protection against brute-force login attempts**.

## Technologies Used

### Backend
- Java 17
- Spring Boot 3.3.5
- Spring Security 6.x
- Spring Data JPA
- Hibernate Envers
- Spring Web MVC

### Tools and Libraries
- **Lombok**: Simplifies boilerplate code for models.
- **Log4j2**: Advanced logging.
- **H2 Database**: For in-memory database testing.
- **Swagger/OpenAPI**: API documentation and testing.
- **Docker**: Containerized deployment.

### Testing
- **JUnit**: Unit testing.
- **Mockito**: Mocking dependencies.
- **REST Assured**: API testing.

## Installation

### Prerequisites
1. Install Java 17 or higher.
2. Install Maven 3.6+.
3. Install Docker (optional for containerized deployment).

### Steps to Run Locally
1. Clone the repository:
   ```bash
   git clone https://github.com/Contactly.git 
   cd Contactly
### Set up the database

By default, the application uses an in-memory H2 database. You can configure `application.properties` to switch to other databases like MySQL or PostgreSQL.

### Build the application

Run the following command to build the application:
mvn clean install

### Run the application Run the application by executing the following command: 
mvn spring-boot:run

### Access the app

Once the application is running, you can access it at the following endpoints:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
# API Endpoints

## AuthController

### POST /auth/register
- **Description**: Register a new user by providing their email, phone number, and other details.
- **Request Body**: `UserDTO` (email, phone number, etc.)
- **Responses**:
  - **200**: User registered successfully.
  - **400**: Bad request (e.g., email or phone number already exists).

### POST /auth/login
- **Description**: Authenticate a user with their email and password. Returns a JWT token if authentication is successful.
- **Request Body**: `LoginRequest` (email and password).
- **Responses**:
  - **200**: Login successful, JWT token returned.
  - **401**: Unauthorized (invalid email or password).
  - **500**: Unexpected server error.

---

## ContactController

### POST /contacts
- **Description**: Create a new contact for the authenticated user. If the user is a regular user, only their own contacts will be returned.
- **Request Body**: `ContactDTO` (contact details).
- **Responses**:
  - **201**: Contact created successfully.
  - **400**: Invalid request (e.g., missing required fields).

### GET /contacts
- **Description**: Retrieve a list of contacts for the authenticated user.
  - **Regular User**: Only their linked contacts will be returned.
  - **Admin User**: All contacts in the system will be returned.
- **Responses**:
  - **200**: Contacts retrieved successfully.
  - **400**: Bad request (e.g., user is not authenticated).
  - **404**: No contacts found for the user.

---

## Security and Authentication

All endpoints require JWT authentication. You need to include the JWT token in the `Authorization` header as `Bearer <token>` for accessing protected resources.

---

## Swagger UI

For detailed API documentation, refer to the Swagger UI available at the `/swagger-ui` endpoint after starting the application.
Security Overview
* JWT Authentication:
o Tokens are generated during login.
o Secured endpoints validate JWT for authentication and authorization.
* CustomUserDetailsService: Fetches user details and roles from the database.
* Filters:
o JsonAuthenticationFilter: For session management during login.
o JwtAuthenticationFilter: Validates JWT and populates the SecurityContext.





