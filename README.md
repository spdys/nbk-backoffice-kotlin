## nbk-backoffice

A Spring Boot backend service for the NBK Backoffice system.

### Overview

This project provides secure user management with role-based access (Admin and Customer), JWT authentication, and customer CRUD operations exposed via REST APIs.

### API Documentation

Interactive docs: [Swagger UI](http://localhost:8080/swagger-ui/index.html)

OpenAPI spec: [OpenAPI JSON](http://localhost:8080/v3/api-docs)

### Features

- User authentication with JWT
- Admin endpoints to manage customers
- Spring Security for role-based access control
- Data seeding for default users
- Cucumber BDD tests for API scenarios

### Tech Stack

- Kotlin
- Spring Boot
- Spring Security
- JPA/Hibernate
- PostgreSQL (H2 for tests)
- Cucumber for BDD