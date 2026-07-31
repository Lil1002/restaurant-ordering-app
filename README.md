# Restaurant Ordering App

A full-stack restaurant ordering application where users can browse a menu, add items to a cart, remove items to a cart, place orders, and view their order history.

---

## Features

- User registration and login with JWT authentication
- Browse menu items
- Add/Remove items to cart and place orders
- View past orders
- Checkout flow

---

## Setup

### Prerequisites

- Node.js 18+
- Java 25
- Docker + Docker Compose
- Maven

---

### SSL Keys

The SSL keys (`cert.pem`, `key.pem`, `private.pem`) are not included in the repository. You will need to generate your own or obtain them separately and place them in `back-end/auth-server-mysql/` before starting the auth server.

---

### 1. Start the Docker services (database + auth server)

From the `back-end/` directory:

```bash
# First time only — build the images from the local Dockerfiles
docker-compose build daamdb daamauth

# Start the services in detached mode (runs in the background)
docker-compose up -d daamdb daamauth

# Verify both containers are running
docker-compose ps
```

### 2. Start the backend (Spring Boot)

From the `back-end/` directory:

```bash
./mvnw spring-boot:run
```

---

### 3. Start the frontend

From the project root:

```bash
npm install
npm run dev
```

The app will be available at `http://localhost:5173`.

---

### Database Access

You can connect directly to the database if needed:

```bash
mysql -h localhost -P 3336 -u root -p daamdb
# Password: secret123
```

---

### Troubleshooting

**Check container logs:**
```bash
docker-compose logs daamdb
docker-compose logs daamauth
```

**Restart a service:**
```bash
docker-compose restart daamdb
```

**Full reset (deletes all data):**
```bash
docker-compose down -v
docker-compose build daamdb daamauth
docker-compose up -d daamdb daamauth
```

---

### Running Tests

**Frontend:**
```bash
# Run all frontend tests
npm run test

# Run with coverage report
npm run test:coverage
```

**Backend** (from the `back-end/` directory):
```bash
./mvnw test
```
---