# E-Bookstore API

A REST backend for an eCommerce bookstore, built with **Java 17** and **Spring Boot 3.3.5**.
It uses a JSON mock data store (`src/main/resources/mock-db`), so no database is needed to run it.

Capstone project: Applied AI Specialist – Cloud FullStack. Developed with **IBM Bob**.

## Features
- Book catalog: browse, filter by category, featured books, and book CRUD
- Category hierarchy (root categories and children)
- Member registration and management
- Shopping cart: add, update, remove items, and a cart total
- Orders: place, view, update status, cancel
- Shipment and payment per order
- Store information
- OpenAPI/Swagger documentation
- Centralised JSON error handling (no stack traces leaked)

## Architecture

```
Controller  ->  Service  ->  Repository  ->  MockDataStore (JSON files)
   (REST)     (business      (data access)
               rules)
```

| Layer | Package | Examples |
|---|---|---|
| Controller | `com.ebookstore.controller` | `OrderController`, `CartController`, `GlobalExceptionHandler` |
| Service | `com.ebookstore.service` | `OrderService`, `CartService`, `CatalogService`, `MemberService` |
| Repository | `com.ebookstore.repository` | `OrderRepository`, `BookRepository`, … |
| Model | `com.ebookstore.model` | `Book`, `Cart`, `Order`, `Member`, `Payment`, `Shipment`, … |
| Config | `com.ebookstore.config` | `MockDataStore`, `OpenApiConfig` |

## Run

Requires JDK 17+ and Maven.

```bash
mvn spring-boot:run
```

- API base URL: `http://localhost:8080/api/v1`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`
- OpenAPI spec file: [`src/main/resources/static/openapi.yaml`](src/main/resources/static/openapi.yaml)
- Postman collection: [`postman_collection.json`](postman_collection.json)

## Test

```bash
mvn test
```

Tests are in `src/test/java/com/ebookstore`:
- `EbookstoreApplicationTests`: application context loads
- `OrderControllerTest`: order history, empty history, null-date handling, list all orders, placing an order without a member ID

## API Endpoints (base `/api/v1`)

### Catalog
| Method | Path | Description |
|---|---|---|
| GET | `/catalog/books` | List books |
| GET | `/catalog/books/{id}` | Get a book |
| GET | `/catalog/books/featured` | Featured books |
| GET | `/catalog/books/category/{categoryId}` | Books in a category |
| POST | `/catalog/books` | Create a book |
| PUT | `/catalog/books/{id}` | Update a book |
| DELETE | `/catalog/books/{id}` | Delete a book |

### Categories
| Method | Path | Description |
|---|---|---|
| GET | `/categories` | List categories |
| GET | `/categories/root` | Root categories |
| GET | `/categories/{id}` | Get a category |
| GET | `/categories/{id}/children` | Child categories |
| POST | `/categories` | Create |
| PUT | `/categories/{id}` | Update |
| DELETE | `/categories/{id}` | Delete |

### Members
| Method | Path | Description |
|---|---|---|
| GET | `/members` | List members |
| GET | `/members/{id}` | Get a member |
| POST | `/members/register` | Register |
| PUT | `/members/{id}` | Update |
| DELETE | `/members/{id}` | Delete |

### Cart
| Method | Path | Description |
|---|---|---|
| GET | `/members/{memberId}/cart` | View cart |
| GET | `/members/{memberId}/cart/total` | Cart total |
| POST | `/members/{memberId}/cart/items` | Add item |
| PUT | `/members/{memberId}/cart/items/{bookId}` | Update item quantity |
| DELETE | `/members/{memberId}/cart/items/{bookId}` | Remove item |
| DELETE | `/members/{memberId}/cart` | Clear cart |

### Orders, shipment, payment
| Method | Path | Description |
|---|---|---|
| POST | `/orders` | Place an order |
| GET | `/orders` | List all orders |
| GET | `/orders/{orderId}` | Get an order |
| GET | `/orders/member/{memberId}` | Order history for a member |
| PATCH | `/orders/{orderId}/status` | Update order status |
| PATCH | `/orders/{orderId}/cancel` | Cancel an order |
| GET / POST | `/orders/{orderId}/shipment` | View / create shipment |
| GET / POST | `/orders/{orderId}/payment` | View / create payment |
| PATCH | `/orders/{orderId}/payment/status` | Update payment status |

### Store
| Method | Path | Description |
|---|---|---|
| GET | `/store` | Get store info |
| PUT | `/store/{id}` | Update store info |

## Error responses

Errors return JSON with no stack trace:

```json
{ "timestamp": "2025-01-01T10:00:00Z", "status": 404, "error": "Not Found", "message": "..." }
```

| Status | Cause |
|---|---|
| 400 | Invalid argument |
| 404 | Resource not found |
| 409 | Business rule violation |
| 500 | Unexpected error (generic message; details are logged server-side) |

## How IBM Bob was used

> Fill this in with your own experience before submitting. Suggested points:
> - Prompts used for scaffolding, models, services, controllers, OpenAPI spec, and tests
> - Modes used (Agent / Plan / Ask)
> - What Bob generated, and what you reviewed, changed, or fixed
> - Issues Bob helped debug (for example, null-date handling in order history)

## Limitations and next steps
- Data is stored in JSON mock files, not a database. Next step: PostgreSQL with JPA (datasource settings are already stubbed in `application.properties`).
- No authentication yet. Next step: Spring Security with OpenID Connect.
