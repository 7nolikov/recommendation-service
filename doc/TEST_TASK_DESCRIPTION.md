# Project description

## Goal

- It's need to build a recommendation service for cryptocurrency investments.

- Use evolution approach

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

## Future improvements and scaling suggestions

### Data Validity and Integrity

#### Data Validation: 
Ensure the CSV parser library handles malformed data gracefully and validates the integrity of the data (e.g., correct timestamp format, positive price values).

#### Historical Data Management: 
Consider how the service will manage and store historical data, especially if you're planning to support analyses over different time ranges (e.g., 6 months, a year).

#### Scalability and Performance
Redis for caching for quick data retrieval, especially for frequently requested data.

#### Load Testing: 
Implement load testing to understand how your service behaves under stress and to identify potential bottlenecks.

### Security

#### Authentication and Authorization: 
Beyond rate limiting, consider if and how you'll secure access to your API (e.g., API keys, OAuth2 for more sensitive operations).

#### Input Sanitization: 
Ensure that inputs from users, especially those used in queries or external requests, are sanitized to prevent injection attacks.

### Additional Functionalities

#### User Preferences and Profiles: 
Allow users to specify preferences or profiles that could influence recommendations (e.g., risk tolerance, investment timeframe).

#### Dynamic Recommendations: 
Consider algorithms or machine learning models that could evolve recommendations based on market trends, user feedback, or historical performance.

### Development Practices and Observability

#### Feature Flags: 
Use feature flags for rolling out new features safely and for A/B testing.

#### Monitoring and Alerting: 
Implement monitoring for application health, performance metrics, and set up alerting for critical issues.

#### Logging Standards: 
Define logging levels and standards for consistency and to ensure logs are meaningful and actionable.