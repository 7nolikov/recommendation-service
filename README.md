# Test task | Recommendation service

Investment recommendation service that helps users make better investment decisions

## Test task description
[List of requirements](TEST_TASK_DESCRIPTION.md)

## Requirements

- Java 17
- Maven 3.8.6 or higher

## Getting started

1. Clone the project repository: `git clone [Project Repository URL]`
2. Navigate to the project directory: `cd [Project Directory]`
3. Install the dependencies: `mvn clean install`
4. Start the project: `mvn spring-boot:run`

## Swagger

- openapi json specification available at `{host}:{port}/api-docs`
- openapi yaml specification available at `{host}:{port}/api-docs.yaml`
- swagger ui available at `{host}:{port}/swagger-ui/index.html`
- openapi yaml specification is saved to `springdoc/openapi.yaml` during the integration-test phase of
  build

## Deployment

- Build docker image `docker image build -t recommendation-service:latest .`
- Run docker
   image `docker run --name recommendation-service -p 8080:8080 recommendation-service:latest`
- Run docker-compose `docker-compose up`

## Code style

This project follows the Google Java Style Guide. All code should be formatted to conform to this
style guide.
The Maven Checkstyle plugin is used to enforce the project style guide. You can find configuration
project's pom.xml file

## Target architecture

![Screenshot](src/main/resources/static/schema.png)

![Screenshot](src/main/resources/static/scale_schema.png)