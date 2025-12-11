## Customer CRUD
Simple CRUD application for customer management using Spring Boot, MySQL, and Docker.
This project was created for learning purposes and to practice backend development with database integration and containerization.

## Overview
The application provides REST endpoints for customer and user management, built on a layered architecture using services and repositories.
Data persistence is powered by JPA/Hibernate, and security is enforced through JWT-based authentication and role-based access control.

## Technologies
* Java 17+
* Spring Boot
* Spring Data JPA
* Spring Security + OAuth2 Resource Server (JWT)
* MySQL
* Docker & Docker Compose
* Maven

## Running the project
1. Clone this repository
2. Navigate to the project root
3. Execute:  
```docker-compose up --build```

* API base URL:  
 ```http://localhost:8080```

* MySQL connection:  
```localhost:3306```

## Running locally (without Docker)
1. Install JDK 17+ and MySQL
2. Configure database access inside application.properties
3. Run the main class:  
```CustomerCrudApplication.java```

## Endpoints

### Auth Endpoints
| Method | Endpoint    | Description                              | Authorization                                                                      |
| ------ | ----------- | ---------------------------------------- | ---------------------------------------------------------------------------------- |
| POST   | `/register` | Register a new user                      | None                                                                               |
| POST   | `/login`    | Authenticate user and return JWT token   | None                                                                               |
| POST   | `/logout`   | Invalidate current JWT token (blacklist) | Requires header `Authorization: Bearer <token>` (no `@PreAuthorize` on controller) |

* Auth — Example already available for testing:
```json
{
    "username": "admin@admin.com",
    "password": "123456"
}
```

### Users Endpoints
| Method | Endpoint      | Description               | Authorization                       |
| ------ | ------------- | ------------------------- | ----------------------------------- |
| GET    | `/users`      | Get all users (paginated) | `SCOPE_NORMAL` **or** `SCOPE_ADMIN` |
| GET    | `/users/{id}` | Get user by ID            | `SCOPE_NORMAL` **or** `SCOPE_ADMIN` |
| POST   | `/users`      | Create a new user         | `SCOPE_ADMIN`                       |
| PUT    | `/users/{id}` | Update user               | `SCOPE_ADMIN`                       |
| DELETE | `/users/{id}` | Delete user               | `SCOPE_ADMIN`                       |


### Customers Endpoints
| Method | Endpoint          | Description                                                    | Authorization                                                 |
| ------ | ----------------- | -------------------------------------------------------------- | ------------------------------------------------------------- |
| GET    | `/customers`      | Get all customers (paginated, supports `page`, `size`, `sort`) | None (public in controller)                                   |
| GET    | `/customers/{id}` | Get customer by ID                                             | None (public in controller)                                   |
| POST   | `/customers`      | Create a new customer                                          | `SCOPE_NORMAL` **or** `SCOPE_ADMIN`                           |
| PUT    | `/customers/{id}` | Update an existing customer                                    | `SCOPE_ADMIN`                                                 |
| DELETE | `/customers/{id}` | Delete a customer                                              | `SCOPE_ADMIN`                                                 |

* Customers — Example Payload
```json
{
    "name": "Maria Silva",
    "cpf": "12345678910",
    "income": 1.0,
    "birthDate": "1995-05-10",
    "children": 0
}
```

## Pagination
The customer listing supports pagination and sorting through query parameters. 

**Example request:**  
```GET /customers?page=1&sizePage=5&orderBy=name&direction=ASC```  

**Example response:**
```json
{
  "content": [
    {
      "id": 1,
      "name": "John",
      "cpf": "12345678910",
      "income": 2500.0,
      "birthDate": "1990-05-10",
      "children": 2
    }
  ],
  "pageable": { ... },
  "totalPages": 1,
  "totalElements": 2
}
```

## Project structure
```
src/main/java/com/antoniojr/customer_crud/
  ├─ config/            
  ├─ controller/        
  ├─ dto/               
  ├─ entity/            
  ├─ exceptions/        
  ├─ jwt/               
  ├─ repositories/      
  ├─ services/          
  └─ CustomerCrudApplication.java 

src/main/resources/
  ├─ application.properties
  ├─ application-docker.properties
  ├─ application-test.properties
  └─ data.sql
```
