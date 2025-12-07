## Customer CRUD
Simple CRUD application for customer management using Spring Boot, MySQL, and Docker.
This project was created for learning purposes and to practice backend development with database integration and containerization.

## Overview
The application exposes REST endpoints for creating, reading, updating, and deleting customer records.
It follows a layered architecture using JPA to manage data persistence.

## Technologies
* Java 17+
* Spring Boot
* Spring Data JPA
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
Method | Endpoint        | Description                 
--|--|--
GET | /customers | Get all customers           
GET | /customers/{id} | Get customer by ID          
POST | /customers | Create a new customer       
PUT | /customers/{id} | Update an existing customer 
DELETE | /customers/{id} | Delete a customer

## Pagination
The customer listing supports pagination and sorting through query parameters.  

**Query parameters:**
| Parameter | Type | Required | Default | Description |
|----------|------|----------|---------|-------------|
| page | Integer | No | 1 | Page number (starting from 1) |
| sizePage | Integer | No | 5 | Number of records per page |
| orderBy | String | No | name | Field used for sorting |
| direction | String | No | ASC | Sorting direction: ASC or DESC |

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
        },
        {
            "id": 2,
            "name": "Mary",
            "cpf": "0987654321",
            "income": 3500.0,
            "birthDate": "1995-05-10",
            "children": 0
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 5,
        "sort": {
            "sorted": true,
            "empty": false,
            "unsorted": false
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 2,
    "last": true,
    "size": 5,
    "number": 0,
    "sort": {
        "sorted": true,
        "empty": false,
        "unsorted": false
    },
    "numberOfElements": 2,
    "first": true,
    "empty": false
}
```

## Project structure
```
src/main/java/com/antoniojr/customer_crud/
  ├─ controller/       → REST endpoints
  ├─ dto/              → Data transfer objects
  ├─ entity/           → JPA entities
  ├─ repositories/     → Spring Data JPA repositories
  └─ services/         → Business logic

src/main/resources/
  ├─ application.properties
  ├─ application-docker.properties
  ├─ application-test.properties
  └─ data.sql
```
