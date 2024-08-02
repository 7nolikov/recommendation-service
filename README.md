# Test task | Recommendation service

Investment recommendation service that helps users make better investment decisions

## Test task description
- [Functional requirements](doc/TEST_TASK_DESCRIPTION.md)
- [Code quality requirements](doc/CODE_QUALITY_CHECKLIST.md)

## Requirements

- Java 17
- Maven 3.8.6 or higher

## Getting started

- Clone the project repository:
```bash
git clone https://github.com/7nolikov/recommendation-service.git
```
- Navigate to the project directory:
```bash
cd recommendation-service
```
- Install the dependencies: 
```bash
mvn clean install
```
- Start the project:
```bash
mvn spring-boot:run
```
- Open your browser and navigate to 
[http://localhost:8080/api/recommendation-service/v1](http://localhost:8080/api/recommendation-service/v1)

## Frontend build with Maven

### Overview
This project integrates a React frontend with a Spring Boot backend. 
The frontend is built using the `frontend-maven-plugin` and `maven-resources-plugin`, 
which automates the process of building 
the React application during the Maven build process. 
The resulting static files are then served by the Spring Boot application.

### Project Structure
Backend: Spring Boot application located in `src/main/java`.
Frontend: React application located in `src/main/client/recommendation-service`.

Steps to Build and Run the Application
1. Ensure Node.js and npm are Installed
   Make sure that Node.js and npm are installed on your development machine. 
These are required to build the React application.

2. Maven Configuration
   The frontend is built using the frontend-maven-plugin. The configuration is defined in the pom.xml file.
3. Build the Application
   This will:
   - Install Node.js and npm.
   - Run npm install to install the frontend dependencies.
   - Run npm run build to build the React application.
   - Copy the generated build files to the Spring Boot static resources directory (target/classes/static).

### Important notes
The frontend is served directly by Spring Boot from the static directory.
All API calls should be prefixed with the context path /api/recommendation-service/v1.
If you need to serve the frontend from a different path, 
update the SpaController and addResourceHandlers method in your Spring Boot configuration.

## Swagger

| Description                              | URL                                                                       |
|------------------------------------------|---------------------------------------------------------------------------|
| OpenAPI JSON specification               | http://localhost:8080/api/recommendation-service/v1/api-docs              |
| OpenAPI YAML specification               | http://localhost:8080/api/recommendation-service/v1/api-docs.yaml         |                      
| Swagger UI                               | http://localhost:8080/api/recommendation-service/v1/swagger-ui/index.html |
| OpenAPI YAML specification file location | [./springdoc/openapi.yaml](./springdoc/openapi.yaml)                      |

## Project configuration

Project profiles and configuration can be found in:
- [application.properties](src/main/resources/application.properties)
- [application-dev.properties](src/main/resources/application-dev.properties)
- [application-prod.properties](src/main/resources/application-prod.properties)
- [application-local.properties](src/main/resources/application-local.properties)

Environment variables can be used to override the default configuration.

| Property name               | Description                 | Default value |
|-----------------------------|-----------------------------|---------------|
| SPRING_PROFILES_ACTIVE      | Active profile              | dev           |
| LOG_LEVEL                   | Log level                   | INFO          |
| APPLICATION_PORT            | Application port            | 8080          |
| PRICES_CSV_SOURCE_DIRECTORY | Prices CSV source directory | ./data/prices |

## Testing
This project includes both unit tests and integration tests to ensure the reliability and stability of its features. 
Here's how you can run these tests using Maven.

To run only the unit tests of the project, execute the following command in the root directory of the project:
```bash
mvn test
```

Run the following Maven command to execute both the compilation of your code and the integration tests:
```bash
mvn verify
```

If you need to build the project without running any tests (which can save time during development), 
you can skip test execution with the -DskipTests flag:
```bash
mvn clean install -DskipTests
```

## Test coverage
To generate the JaCoCo report, simply run your tests with Maven.
After the tests complete, you'll find the JaCoCo coverage report in the specified directory:
- [./target/site/jacoco/index.html](./target/site/jacoco/index.html)

## Allure Report

Allure is a flexible, lightweight multi-language test report tool,
with the possibility of adding to the report of additional information
such as screenshots, logs and so on.

After running your tests, an Allure report can be generated.
It provides a clear graphical representation of test reports in a web format view.

You can view the Allure report by opening file:
- [./target/site/allure-maven-plugin/index.html](./target/site/allure-maven-plugin/index.html)

To generate the Allure report, use the following command:

```bash
mvn allure:serve
```

## Continuous Integration

- Build docker image
```bash
docker image build -t recommendation-service:latest .
```
- Run docker image
```bash
docker run --name recommendation-service -p 8080:8080 recommendation-service:latest
```
- Run docker-compose
```bash
docker-compose up
```

## Limitations

### Date format

`ISO 8601` date format is used in the project.
This decision is driven by several key advantages that ISO 8601 offers. 
- First, it provides a human-readable format that simplifies the verification and debugging process 
for both developers and users. 
- Second, ISO 8601 inherently includes timezone information, using 'Z' to denote UTC, 
which eliminates confusion around time zone conversions and ensures consistency across global markets. 
- Additionally, the format supports high precision down to milliseconds, which is crucial for accurately recording 
and processing transactions in the fast-paced world of cryptocurrency trading. 
- ISO 8601 is also widely supported by APIs and data interchange standards, ensuring interoperability 
with various external systems and services.

## Code style

This project follows the Google Java Style Guide. All code should be formatted to conform to this
style guide.

The Maven Checkstyle plugin is used to enforce the project style guide. You can find configuration
project's `pom.xml` file and checkstyle report in file:
- [./target/site/checkstyle.html](./target/site/checkstyle.html)

You can install the Google Java Style plugin in your IDE to help you format your code:
- [google-java-format plugin](https://plugins.jetbrains.com/plugin/8527-google-java-format)

## Target architecture

![Screenshot](src/main/resources/static/schema.png)

![Screenshot](src/main/resources/static/scale_schema.png)