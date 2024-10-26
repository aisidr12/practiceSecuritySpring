
# Practicing Spring security and Roles
This is a based project which is all about security and roles. October 2024.
### Getting Started

** **

This is a basic project which is all about security and roles. This contains 
spring security and Jwt Tokens. 

This project contains a controller which is used to get the token and also this contains
another controller to handle products.

All the resources are protected by spring security according to the roles.

- Roles are:  ROLE_ADMIN, ROLE_USER.
- The user with ROLE_ADMIN can access all the resources.
- The user with ROLE_USER can access only the products and list of products.

There is Postman collection in the project which is used to test the project.

Remember to have up the dabase with the credentials in the application.properties file.

# To Start the Project
## requirements

- you must have maven installed
- Java 17
- docker installed

## Using Docker
- remember to modify application.properties file with the database credentials
- mvn clean install
- docker build images -t spring-security-app .
- docker run -p 8080:8080 spring-security-app

# To run the project with docker-compose
- Clone the project
- Use mvn clean install to build the project
- use docker-compose build to build the docker image
- use docker-up to start the project




