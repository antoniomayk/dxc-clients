# Client Management Microservice

A Spring Boot microservice for managing clients.

## Endpoints

- `GET /api/v1/clients`: Retrieve all clients
- `POST /api/v1/clients`: Create a new client
- `PUT /api/v1/clients/{id}`: Update an existing client
- `DELETE /api/v1/clients/{id}`: Delete a client

All endpoints require Basic Authentication.

## Tech Stack

- Spring Boot
- JDK 11
- Maven
- H2 Database (in-memory)
- Spring Security (Basic Authentication)

## Database Queries

Implemented using:

- JDBCTemplate
- JPA Repository
- Native queries

## Testing

JUnit tests are implemented for all endpoints.

## API Documentation

Swagger UI is available at:
`http://localhost:8080/swagger-ui/`

To access, use the same Basic Authentication credentials as for the API endpoints.

## Authentication

Basic Authentication is required for all endpoints and Swagger UI.

Credentials:
- Username: admin
- Password: admin

## Internationalization

The API supports multiple languages. It accepts both Portuguese (pt) and English (en).

To specify the language, use the `Accept-Language` header in your requests:
- For English: `Accept-Language: en-US`
- For Portuguese: `Accept-Language: pt-BR`

## Build and Run

```bash
mvn clean install
```
