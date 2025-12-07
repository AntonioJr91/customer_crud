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
