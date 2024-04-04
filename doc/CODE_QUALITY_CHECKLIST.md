# Code quality requirements

## Documentation
- [x] Readme contains instructions for building and running application
- [x] OpenAPI and SwaggerUI are used for documenting web API
- [x] Endpoints of web services REST naming
- [x] Javadoc is used for interfaces (public API)

## Tools
- [x] Maven checkstyle plugin with checkstyle report generation
- [ ] No sonarlint issues
- [x] Lombok usage
- [x] BigDecimal usage
- [x] Do not store secrets
- [x] SOLID
- [ ] Setup db indexes
- [x] Use logging
- [x] Maven swagger plugin
- [ ] Liquibase in docker

## Project structure
- [x] CSV parser library should be used
- [x] Not exist redundant method in interfaces
- [x] Interface to separate controller and service layer
- [x] Layers organization (controllers, services, repositories)
- [x] Separate entity/dto for controller and service layer

## Error handling
- [x] Custom runtime exception
- [x] Controller advice

## Tests
- [x] Mockito unit tests
- [x] Test coverage
- [x] Integration tests
- [ ] Testcontainers
- [ ] Given Then approach

## Functional requirements
- [ ] Requirements fulfilled
- [ ] Cannot load additional cryptos at runtime

## Caching
- [ ] Redis

## Persistence
- [ ] Postgres

## Containerization
- [ ] Dockerfile
- [ ] Docker-compose

## Rate limiter
- [ ] Bucket4j

## CI/CD
- [ ] Github CI