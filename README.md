# Task Management REST API

A backend-focused Task Management system built with Spring Boot, designed with real-world backend practices in mind.

## Features
- User registration & login
- JWT-based authentication (stateless)
- Authorization with ownership checks
- User-specific tasks (no cross-user access)
- Proper HTTP status handling (401, 403, 404)
- Global exception handling
- Idempotent task creation
- Pagination and sorting
- Clean service-layer architecture

## Tech Stack
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT
- MySQL
- Maven

## Security Design
- Stateless authentication using JWT
- Authentication via SecurityContext
- Authorization enforced at service layer
- No trust in client-provided user IDs

## API Highlights
- `POST /api/v1/users` – Sign up
- `POST /api/v1/users/login` – Login (JWT)
- `POST /api/v1/tasks` – Create task (authenticated)
- `GET /api/v1/tasks` – Get logged-in user's tasks
- `PUT /api/v1/tasks/{id}` – Update own task
- `DELETE /api/v1/tasks/{id}` – Delete own task

## How to Run
1. Clone the repo
2. Configure MySQL in `application.properties`
3. Run the Spring Boot application
4. Test APIs via Postman or Swagger UI

## What I Learned
- Designing secure REST APIs
- Difference between authentication and authorization
- Handling edge cases and error states
- Writing backend code that enforces business rules
