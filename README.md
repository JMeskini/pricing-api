# Pricing API (Hexagonal Architecture)

This project provides a REST API to retrieve the applicable price for a product at a given date and brand. It is built with Spring Boot, uses an in-memory H2 database preloaded with sample data, and follows a Hexagonal Architecture structure.

## Requirements

- Java 21
- Maven 3.9+

## Architecture

The code is organized into three layers:

- `domain`: core business model and rules (no framework dependencies)
- `application`: use cases that orchestrate the domain
- `infrastructure`: adapters for web and persistence

Key flow:

1. The REST controller receives the request.
2. The application use case fetches candidate prices for the product/brand.
3. A stream-based selector filters by date and chooses the highest priority price.

## API

`GET /api/prices`

Query parameters:

- `date`: ISO-8601 local date-time, e.g. `2020-06-14T10:00:00`
- `productId`: product identifier (number)
- `brandId`: brand identifier (number)

Response example:

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

## API Docs

Swagger UI is available at:

- `https://pricing-api.netlify.app/`

## Decisions

- I kept a simple surrogate `id` in the `prices` table to keep the JPA mapping straightforward while keeping the business keys (`brandId`, `productId`, date range) intact.
- Date matching is inclusive of both `startDate` and `endDate` to avoid off‑by‑one surprises.
- Priority wins when multiple prices overlap; ties fall back to `priceList` for deterministic results.

## Data

The in-memory database is initialized from a CSV file. Source data lives in `src/main/resources/static/prices_.csv` and is loaded by `src/main/resources/data.sql` using H2 `CSVREAD`.

## Tests

Tests are split to cover the main quality signals:

- Unit tests for the stream-based selection algorithm
- Unit tests for the main use case
- Integration tests for the REST endpoint (five required scenarios)

Run all tests:

```bash
./mvnw test
```

Run the application:

```bash
./mvnw spring-boot:run
```

## Notes

- The selection logic is implemented with streams in `PriceSelector` to keep the rule explicit and easy to test.
