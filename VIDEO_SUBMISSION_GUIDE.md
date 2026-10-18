# Capstone Video Submission Guide
**Applied AI Specialist – Cloud FullStack – Capstone Project (E-Bookstore API)**

This guide lists what the video must show and say. It responds to the evaluator feedback:
*API design not demonstrated, layers not evidenced, testing/validation lacking, role of IBM Bob unclear.*

**Target length:** 6–8 minutes. **Format:** MP4, screen recording with voice narration. **Resolution:** 1080p, with the font size increased so code is readable.

---

## Required content checklist

| # | Section | Time | Must show | Must say |
|---|---|---|---|---|
| 1 | Introduction | 0:30 | Title slide or README | Your name, project name, problem solved (eCommerce bookstore backend), tech stack: Java 17, Spring Boot 3.3.5, Maven, springdoc-openapi, JSON mock data store |
| 2 | Role of IBM Bob | 1:30 | Bob chat history and prompts, files Bob generated | See "IBM Bob evidence" below |
| 3 | Architecture and layers | 1:30 | Project tree in the IDE; one flow opened across all layers | See "Layer walkthrough" below |
| 4 | API design | 1:30 | Swagger UI at `http://localhost:8080/swagger-ui.html` and `openapi.yaml` | See "API design" below |
| 5 | Live testing and validation | 2:00 | Terminal with `mvn test`, then Swagger or Postman calls | See "Testing evidence" below |
| 6 | Wrap-up | 0:30 | GitHub repo page | Summary, limitations, next steps, repo URL |

---

## 1. IBM Bob evidence (the evaluator asked for this)

Show and explain:
- The Bob chat or task history, with at least 3–4 actual prompts you used (for example: generate the Spring Boot project structure, create the models and repositories, add the order service, generate the OpenAPI spec, write tests).
- A before and after: a prompt, then the code it produced.
- Which Bob modes or tools you used (Agent, Plan, Ask; file creation, running commands).
- One example where you reviewed, corrected, or refined Bob's output (a bug found, a security fix, a refactor).
- What you did yourself and what Bob did. State it plainly, for example: "Bob generated the scaffolding, services, and tests. I defined the requirements, reviewed the code, and ran the validation."

## 2. Layer walkthrough (pick one end-to-end flow)

Use **"Place an order"** or **"Add to cart"**. Open each file and explain it in one or two sentences:

| Layer | File | Explain |
|---|---|---|
| Model | `model/Order.java`, `Cart.java`, `Book.java` | Entities and fields |
| Repository | `repository/OrderRepository.java` | Data access over the JSON mock store (`config/MockDataStore.java`, `resources/mock-db/*.json`) |
| Service | `service/OrderService.java`, `CartService.java` | Business rules: totals, status changes, cancellation |
| Controller | `controller/OrderController.java` | REST endpoints, request and response handling |
| Error handling | `controller/GlobalExceptionHandler.java` | Consistent error responses |
| Config | `config/OpenApiConfig.java` | Swagger documentation setup |

Trace one request: **HTTP request → Controller → Service → Repository → JSON store → response.** Say the flow aloud.

## 3. API design (show and explain)

Show the endpoint groups and what each does. All are under `/api/v1`:

| Resource | Base path | Operations |
|---|---|---|
| Catalog | `/catalog/books`, `/books/{id}`, `/books/featured`, `/books/category/{id}` | List, view, featured, filter by category, create, update, delete |
| Categories | `/categories`, `/root`, `/{id}/children` | CRUD, category hierarchy |
| Members | `/members`, `/members/register` | Register, view, update, delete |
| Cart | `/members/{memberId}/cart`, `/items`, `/total` | View, add, update, remove items, total, clear |
| Orders | `/orders`, `/orders/member/{memberId}`, `/{orderId}/status`, `/cancel` | Create, view, list, update status, cancel |
| Shipment and payment | `/orders/{orderId}/shipment`, `/payment`, `/payment/status` | Create and view, update payment status |
| Store | `/store` | View and update store info |

Also cover:
- REST conventions: HTTP verbs (GET, POST, PUT, PATCH, DELETE), status codes (200, 201, 400, 404), JSON payloads.
- Request validation (`spring-boot-starter-validation`).
- The OpenAPI spec and Swagger UI as living documentation.

## 4. Testing evidence (the biggest gap in your feedback)

Do all of the following **on screen**:
1. Run `mvn test`. Show **BUILD SUCCESS** and the test count (`OrderControllerTest`, `EbookstoreApplicationTests`).
2. Run `mvn spring-boot:run`. Show the app started on port 8080.
3. Make at least 5 API calls in Swagger UI or Postman (the repo has `postman_collection.json`), showing the request body and response each time:
   - `GET /api/v1/catalog/books`: list books
   - `POST /api/v1/members/register`: register a member
   - `POST /api/v1/members/{id}/cart/items`: add a book to the cart
   - `POST /api/v1/orders`: place an order
   - `PATCH /api/v1/orders/{id}/status`: change the order status
4. Show **one negative case**: for example, a nonexistent book ID returning 404, or invalid input returning 400. Point out the structured error from `GlobalExceptionHandler`.
5. Say how you validated that it works: automated tests, manual API tests, the Postman collection.

## 5. Wrap-up

- Features delivered, and known limitations (for example, JSON mock data instead of a real database, no authentication yet).
- Next steps (PostgreSQL via JPA, Spring Security with OIDC, containerization).
- Show the **GitHub repository URL** and the README.

---

## Pre-recording checklist
- [ ] Ran `mvn clean test` and it passes
- [ ] App starts with `mvn spring-boot:run` and Swagger UI loads
- [ ] GitHub repo is public (or shared with the reviewer), and the README has the overview, endpoint table, run and test steps, and a "How IBM Bob was used" section
- [ ] `target/` is in `.gitignore`, and no secrets are committed
- [ ] Bob chat history is open and ready to show
- [ ] Postman collection imported
- [ ] Font size is large and notifications are off
- [ ] You speak aloud throughout; the reviewer judges what you say as well as what is on screen

## Upload checklist
- [ ] Format MP4; file size is under the portal limit (if too large, compress with HandBrake, or upload as an unlisted link)
- [ ] If the portal takes a link: YouTube *Unlisted* or Drive/Box set to "anyone with the link can view". Open it in a private window to confirm
- [ ] The GitHub URL is in the submission text
- [ ] Re-watch the video once before submitting. Audio is audible and code is readable

## Suggested narration opener
> "Hi, I'm <name>. This is my capstone: an E-Bookstore backend built with Spring Boot 3 and Java 17, developed with IBM Bob as my agentic coding assistant. I'll cover how I used Bob, the layered architecture, the REST API design, and then prove it works by running the automated tests and live API calls."
