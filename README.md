# Feedbacky — Backend

[![CI](https://github.com/demirserkan/feedbacky-be/actions/workflows/ci.yml/badge.svg)](https://github.com/demirserkan/feedbacky-be/actions/workflows/ci.yml)

Feedbacky lets any web app collect user feedback with a single drop-in React component. This repository is the **REST API** that stores and serves that feedback.

The client-side component lives in [feedbacky-react-component](https://github.com/demirserkan/feedbacky-react-component) and is published on npm.

```
┌─────────────────────────┐   POST /feedbacky-api/sendFeedback   ┌──────────────────┐
│  Your web app           │ ───────────────────────────────────▶ │  feedbacky-be    │
│  <Feedbacky /> (React)  │                                       │  Spring Boot API │
└─────────────────────────┘                                       └──────────────────┘
```

## Tech stack

- Java 17, Spring Boot 2.6
- Spring Web and Spring Data JPA
- H2 in-memory database
- Bean Validation for request checks
- Swagger UI (springfox) for API docs
- JUnit 5 and MockMvc for tests, GitHub Actions for CI

## Getting started

Requires Java 17. The Maven wrapper downloads everything else.

```bash
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`. Run the tests with:

```bash
./mvnw verify
```

> Data lives in an in-memory H2 database, so it resets whenever the app restarts.

## API

| Method | Path                           | Description                  |
| ------ | ------------------------------ | ---------------------------- |
| POST   | `/feedbacky-api/sendFeedback`  | Save a feedback message      |
| GET    | `/feedbacky-api/getFeedbacks`  | List all feedback messages   |

Interactive docs are available at [`/swagger-ui.html`](http://localhost:8080/swagger-ui.html) while the app is running.

### Send feedback

```bash
curl -X POST http://localhost:8080/feedbacky-api/sendFeedback \
  -H "Content-Type: application/json" \
  -d '{"customerId": "customer-1", "message": "Love the new dashboard!"}'
```

```json
{
  "id": 1,
  "customerId": "customer-1",
  "message": "Love the new dashboard!",
  "insertDate": [2026, 9, 27, 21, 2, 58, 669529700]
}
```

Both fields are required and `message` is limited to 2000 characters. Invalid requests get `400 Bad Request`.

### List feedback

```bash
curl http://localhost:8080/feedbacky-api/getFeedbacks
```

## Configuration

Browsers can call the API only from the origins listed in `src/main/resources/application.properties`. By default these are the React dev server and Storybook:

```properties
feedbacky.cors.allowed-origins=http://localhost:3000,http://localhost:6006
```

Add your own app's origin there, separated by commas.
