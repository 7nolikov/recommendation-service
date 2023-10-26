# Project description

It's need to build a recommendation service for cryptocurrency investments.

- Evolution approach

## Functional requirements

- Reads **all the prices** from CSV files
- Calculates **oldest/newest/min/max** for each crypto for the whole month
- Exposes an endpoints that: 
  - will return a **descending sorted list** of all the cryptos, comparing the normalized range (i.e. (max-min)/min)
  - will return the **oldest/newest/min/max values** for a requested
    crypto
  - will return the **crypto with the highest normalized range** for a
    specific day

## Things to consider

- Option to support mutable source of data (e.g. 10 new CSV files)
- Option to support newly created crypto, ability to scale and safety from not supported crypto
- Different time ranges: 6 months, year etc.
- Rate limiter based on IP
- Add https://www.marketwatch.com/investing/stock/csv/download-data datasource

## Datasource

- CSV files
- Separate file for each crypto
- Name format: `{{crypto_name}}_values.csv`
- Data format: `timestamp | symbol | price`

## Code requirements

### Documentation
- [x] Readme contains instructions for building and running application
- [x] OpenAPI and SwaggerUI are used for documenting web API
- [x] Endpoints of web services REST naming
- [x] Javadoc is used for interfaces (public API)

### Tools
- [x] Maven checkstyle plugin
- [ ] No sonarlint issues
- [x] Lombok usage
- [x] BigDecimal usage
- [x] Do not store secrets
- [x] SOLID
- [ ] Setup db indexes
- [x] Use logging
- [x] Maven swagger plugin
- [ ] Liquibase in docker

### Project structure
- [x] CSV parser library should be used
- [x] Not exist redundant method in interfaces
- [x] Interface to separate controller and service layer
- [x] Layers organization (controllers, services, repositories)
- [ ] Separate entity/dto for controller and service layer

### Error handling
- [x] Custom runtime exception
- [x] Controller advice

### Tests
- [x] Mockito unit tests
- [ ] Test coverage
- [x] Integration tests
- [ ] Testcontainers
- [ ] Given Then approach

### Functional requirements
- [ ] Requirements fulfilled
- [ ] Cannot load additional cryptos at runtime

### Caching
- [ ] Redis

### Persistency
- [ ] Postgres

### Containerization
- [ ] Dockerfile
- [ ] Docker-compose

### Rate limiter
- [ ] Bucket4j

### CI/CD
- [ ] Github CI

