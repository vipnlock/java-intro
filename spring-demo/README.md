# Spring Demo Application

This application is to act as a makeshift for developing spring applications. Most of the examples are considered as best practice, but feel free to improve the example by bringing in your own expericence so others can benefit from it as well.

## Treated Subjects

 - Spring Boot
 - Spring Data
 - Spring MVC
 - Model Mapping with DTO
 - Unit Testing with JUnit5 and Mockito
 - Integration Testing

## Preconditions
- JDK 8
- Maven
- Your favorite IDE

#### Recomended Plugins for Jetbrains' IntelliJ
- Lombok Plugin
- Docker Integration 

## Usage
1. Change directory to desired destination
2. `git clone gitlab@gitlab.e3ag.net:e3clel/spring-demo.git`
3. `cd spring-demo`
4. `docker-compose -f docker/docker-compose.yml -p spring-demo up -d`
5. Open Project in your IDE and Run Spring Boot Application
6. Visit [localhost:8080](http://localhost:8080) to verify

## Tipps and Tricks
- Use Postman to trigger HTTP Request
- If you have Intellij's Ultimate version, use integrated [HTTP Client](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html)

