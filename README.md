# pulse-ops

A job queue dashboard with real-time status updates. Create background jobs via a REST API, process them asynchronously through RabbitMQ, and watch their status update live in the UI via WebSocket.

## Architecture

```
React UI  ──REST──►  Spring Boot  ──publish──►  RabbitMQ
   ▲                      │                         │
   └──WebSocket (STOMP)───┘◄──consume──────────────┘
                          │
                       Postgres
```

1. User creates a job → saved to Postgres as `PENDING`, published to RabbitMQ
2. Consumer picks it up → updates status to `RUNNING`, simulates work, then `COMPLETED`
3. Each status change is broadcast over WebSocket → UI updates in real time

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 3.5, Java 21 |
| Database | PostgreSQL 16 + Flyway migrations |
| Messaging | RabbitMQ 3 |
| WebSocket | STOMP over SockJS |
| Frontend | React 19, TypeScript, Tailwind CSS 4, Vite |

## Prerequisites

- Docker & Docker Compose
- Java 21
- Node.js 18+

## Getting Started

**1. Start infrastructure**

```bash
cp .env.example .env
docker compose up -d
```

**2. Run the backend**

```bash
cd backend
./gradlew bootRun
```

**3. Run the frontend**

```bash
cd frontend
npm install
npm run dev
```

The app will be available at `http://localhost:5173`.

## Environment Variables

Copy `.env.example` to `.env` and adjust as needed. The backend also reads `DB_PASSWORD` and `RABBITMQ_PASSWORD` from the environment (or your shell).

| Variable | Description |
|---|---|
| `POSTGRES_USER` | Postgres username |
| `POSTGRES_PASSWORD` | Postgres password |
| `POSTGRES_DB` | Database name |
| `RABBITMQ_USER` | RabbitMQ username |
| `RABBITMQ_PASSWORD` | RabbitMQ password |

## Project Structure

```
pulse-ops/
├── backend/          # Spring Boot application
│   └── src/main/java/com/pulseops/
│       ├── job/          # Job entity, controller, service, repository
│       ├── messaging/    # RabbitMQ publisher & consumer
│       └── websocket/    # STOMP WebSocket config & publisher
├── frontend/         # React + Vite application
│   └── src/
│       ├── api/          # REST API calls
│       ├── components/   # JobTable, CreateJobForm, StatusBadge
│       ├── pages/        # JobsPage
│       ├── types/        # Shared TypeScript types
│       └── websocket/    # STOMP client setup
└── docker-compose.yml
```
