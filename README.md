# 🔧 Assistance System Backend

Backend system for managing service orders in a technical assistance environment (appliance repair, warranty service, etc.).

This project is being developed as a deep learning exercise in backend engineering, focusing on how systems actually work under the hood rather than relying on framework abstractions.

> ⚠️ **Active Development** — This project is actively being developed. Some parts of the system are still evolving, and the codebase may undergo refactoring as architectural decisions are refined.

---

## 🎯 Project Goals

This project was built to understand and practice:

- Domain-driven design (DDD) concepts
- Layered architecture
- Manual persistence with JPA/Hibernate
- Authentication system design (without Spring Security auto-configuration)
- Session lifecycle management (login, logout, expiration, sliding)
- Cross-cutting concerns (audit logging, authentication context)
- Clear separation of responsibilities across layers

The focus is not just building features, but understanding **where logic belongs**, how layers interact, and how persistence and authentication really behave.

---

## 🛠️ Tech Stack

| Concern | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| Persistence | Hibernate / JPA (manual via `EntityManager`) |
| Database | PostgreSQL |
| Migrations | Flyway |
| API | REST |

Persistence is handled through custom DAOs, without Spring Data repositories, to maintain explicit control over queries and entity lifecycle.

---

## 🏗️ Architecture

The system follows a layered architecture with strong DDD influence:

```
Domain       ← Business concepts & rules
Application  ← Use-case orchestration
Infrastructure ← Technical implementation
```

This is a **pragmatic layered design**, not strict Clean Architecture or Hexagonal Architecture.

Application services interact directly with DAOs instead of using repository interfaces. This decision was intentional to keep the system explicit and easier to reason about while learning.

---

## 📦 Domain Layer

Contains business concepts and rules.

**Examples:** `Client`, `Product`, `ServiceOrder`, `EmployeeAccount`, `EmployeeRole`, `AuditLog`, `AuditAction`, `EntityType`

**Responsibilities:**
- Enforce invariants
- Model business state transitions
- Represent core domain concepts

✅ No framework dependencies.

---

## ⚙️ Application Layer

Contains use-case orchestration.

**Examples:** `AuthenticationService`, `EmployeeAccountService`, `ClientService`, `ProductService`, `ServiceOrderService`, `AuditService`, `SessionManager`, `SessionService`

**Responsibilities:**
- Coordinate domain entities
- Enforce workflows
- Interact with persistence layer
- Apply session and authentication policies
- Explicitly trigger audit logging after mutations

The application layer is where most of the system behavior is defined.

---

## 🧱 Infrastructure Layer

Contains technical implementation details.

**Examples:** REST controllers, `AuthenticationFilter`, DAOs (`EntityManager`-based), password hashing (BCrypt), authentication token persistence, configuration classes

**Responsibilities:**
- HTTP handling
- Persistence implementation
- Security mechanisms
- Framework integration

---

## 🔐 Authentication System

Authentication was implemented from scratch, without relying on Spring Security auto-configuration.

The system uses **database-backed opaque tokens**.

### Login Flow

```
AuthenticationController
        ↓
AuthenticationService
        ↓
CredentialVerifier
        ↓
PasswordHasher
        ↓
TokenGenerator
        ↓
AuthenticationToken persisted
```

The client receives a token and sends it in every request:
```
Authorization: Bearer <token>
```

### Request Authentication Pipeline

```
HTTP Request
        ↓
AuthenticationFilter
        ↓
SessionManager
        ↓
SessionService (transactional)
        ↓
AuthenticatedIdentity
        ↓
AuthenticationContext (ThreadLocal)
        ↓
Application Services
```

---

## 🕒 Session Management

Sessions are stored in the `authentication_tokens` database table.

**Fields:** `token`, `employee_account_id`, `expires_at`, `revoked`

**Features:**
- Login
- Logout (token revocation)
- Sliding session expiration
- Scheduled cleanup of expired sessions
- Thread-local authentication context

### Sliding Expiration

Session expiration is dynamically extended based on activity.

**Configuration:**
- `timeout` = 30 minutes
- `refreshThreshold` = 10 minutes

**Behavior:** If remaining lifetime ≤ 10 minutes → extend expiration to `now + 30 minutes`

This update is executed inside a transactional service, ensuring Hibernate dirty checking persists the change correctly.

### Query Optimization

Session resolution uses projection queries to avoid unnecessary entity loading.

```
load session projection
        ↓
validate session
        ↓
only load full entity if refresh is required
```

---

## 📋 Audit Logging

A cross-cutting audit mechanism that records all mutation operations without polluting domain logic.

### Architecture

```
Domain
  AuditLog      ← immutable audit entity
  AuditAction   ← business event enum
  EntityType    ← target classification enum

Application
  AuditService  ← resolves actor, constructs and persists audit records

Infrastructure
  AuditLogDAO   ← EntityManager-based persistence, indexed queries, pagination
```

### What Gets Captured

Every audit record stores:
- **Actor** — authenticated employee ID
- **Action** — semantic business event (e.g. `SERVICE_ORDER_STATUS_CHANGED`)
- **Entity** — type + ID of the affected resource
- **Timestamp** — when the event occurred
- **Metadata** — optional structured JSON (e.g. `{"from":"OPEN","to":"IN_PROGRESS"}`)

### Design Decisions

- Audit writes occur **inside the same transaction** as the business operation — consistency over performance at this stage
- **No AOP or framework magic** — services explicitly call `auditService.record(...)` after mutations
- No domain pollution — `AuditLog` is a pure domain entity with no framework dependencies
- CQRS respected — only the command side is audited

### Known Limitations & Deferred Work

| Gap | Description | Planned |
|---|---|---|
| Synchronous coupling | Every mutation does two DB writes in the same transaction. Audit volume can increase latency. | Decouple via domain events + async handler (Kafka/RabbitMQ) |
| Pagination count queries | `AuditLogDAO` queries are paginated but lack `countBy*` counterparts. Clients cannot know total pages. | Add `countByEntityTypeAndEntityId` and `countByOccurredAt`, introduce `PageResponse<T>` wrapper |
| Write-only | No application query layer or API exposure for audit logs yet. | Add `AuditQueryService` + read endpoints |
| Primitive metadata | Metadata is a raw JSON string with no schema or type safety. | Structured metadata object → serialized, possibly JSONB in DB |
| No system actor | All audits assume an authenticated identity exists. Scheduled jobs and internal processes have no actor. | Introduce a `SYSTEM` actor or nullable actor strategy |
| No failure strategy | If audit persistence fails, the entire transaction rolls back. No fallback or retry. | Define explicit failure handling strategy |

---

## 📊 Command / Query Separation

The system follows a CQRS-style structure:

- **Command services** → mutations
- **Query services** → reads

Only command services modify state. This separation simplifies reasoning about business operations, auditing, and system behavior.

---

## 📌 Current Status

### ✅ Implemented

- Client management
- Product management
- Service order lifecycle
- Employee account management
- Custom authentication system
- Token persistence
- Logout
- Sliding session expiration
- Scheduled cleanup job
- Concurrency-safe session handling
- Audit logging (write side)

### 🚧 In Progress / Deferred

- Async audit writes (event-driven decoupling)
- Audit query layer and API exposure
- Centralized authorization service
- Pagination `PageResponse<T>` wrapper and count queries
- Improved validation
- Documentation and comments

---

## 💭 Project Philosophy

This project intentionally avoids hiding complexity behind frameworks.

Instead, it focuses on: **understanding first, abstraction later.**

The goal is to build a strong mental model of:
- Persistence behavior
- Transaction boundaries
- Authentication flow
- Architectural decisions and their trade-offs

---

## 📝 Notes

- Code is still evolving and may change
- Some features are not fully implemented yet
- Architectural decisions are documented where they deviate from convention

---

## 🏁 Final Remark

This project is less about delivering a finished product and more about developing a deep understanding of backend systems.

Most of the value comes from the process: questioning decisions, refining architecture, and understanding trade-offs.

---

👨‍💻 **Author:** mgcf27
