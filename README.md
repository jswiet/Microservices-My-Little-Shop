## Microservices: My Little Shop

- A simple backend-only microservice simulating the shop side of e-commerce system.
- Built with:
    - Java 21
    - Spring Boot
    - Spring Web MVC
    - Spring WebFlux (WebClient)
    - Spring Data JPA (JPA Auditing and custom JPQL queries)
    - Spring Security, JWT
    - PostgreSQL (Docker container)
    - MongoDB (Docker container)
- This project was created to practice REST API and WebClient.
- This service runs on port 8081.

---

--> Added __GitHub Actions (CI)__ pipeline that automatically builds the project and runs tests on every push to the master branch.

--> Configured __Checkstyle__ integration with Maven to block builds containing (`// TODO`) comments.

--> __SQL (PostgreSQL)__ was used to stored user data and confirmation tokens (send via emails), to allow table joins, keep 
sensitive data secure, and ensure atomicity.

--> __NoSQL (MongoDB)__ was used to store cart items, to enable flexible cart structure.

--> Added stateless __JWT authentication__, ~~the token must be passed manually in the Authorization header. 
  The next step will be to store it in cookies.~~ the token is stored in an `HttpOnly` cookie ✅

---
- Second part: [My Little Warehouse repository](https://github.com/jswiet/Microservices-My-Little-Warehouse)
---