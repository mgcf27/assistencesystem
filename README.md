# Assistance System Backend

Backend system for managing service orders in a technical assistance environment (appliance repair, warranty service, etc.).  
Built with a focus on **clean architecture**, **domain modeling**, and **production-grade backend design practices**.

> 🚧 This repository is actively under development. Some parts are still evolving and the codebase may change as architectural decisions are refined.

---

## 🎯 Project Goals

This project was created to practice and demonstrate:

- Domain-driven design concepts
- Layered architecture
- Explicit persistence management with JPA/Hibernate
- Custom authentication system (without Spring Security auto-configuration)
- Session-based authentication with **sliding expiration**
- Clear separation between domain, application, and infrastructure layers

The goal is not just functionality, but understanding **how backend systems are structured in professional environments**.

---

## 🛠️ Tech Stack

- **Java** 17
- **Spring Boot**
- **Hibernate / JPA**
- **PostgreSQL**
- **Flyway** (database migrations)
- **REST API**

> Persistence is handled manually using `EntityManager` and DAOs — no Spring Data repositories — to better understand how JPA actually works.

---

## 🏗️ Architecture

The system follows a layered architecture with explicit boundaries:
┌─────────────────┐
│ Domain │          ← Business model and core concepts
├─────────────────┤
│ Application │     ← Use case orchestration
├─────────────────┤
│ Infrastructure │  ← Technical implementation (web, persistence, security)
└─────────────────┘

text

### 📦 Domain
Contains the business model and core concepts:
- `EmployeeAccount`, `EmployeeRole`
- `Client`, `Product`, `ServiceOrder`
- Domain exceptions and value objects

> ✅ No framework dependencies here.

### ⚙️ Application
Contains use case orchestration:
- `AuthenticationService`
- `EmployeeAccountService`
- `ClientService`, `ProductService`, `ServiceOrderService`

**Responsibilities:**
- Orchestrating domain objects
- Coordinating infrastructure services
- Enforcing application workflows

### 🧱 Infrastructure
Contains technical implementation details:
- REST controllers
- Authentication filter
- Persistence DAOs
- Password hashing
- Session management
- Configuration classes

> Infrastructure depends on `application` and `domain`, but not the other way around.

---

## 🔐 Authentication System

Authentication was implemented **from scratch** instead of relying on Spring Security auto-configuration.

The system uses **database-backed opaque tokens** with **sliding session expiration**.

### Authentication Flow:
Login request
↓
Credential verification
↓
Token generation
↓
Token stored in database
↓
Client sends token in Authorization header
↓
Authentication filter resolves identity
↓
Identity stored in request context

text

### ✅ Features implemented:
- Login
- Logout (token revocation)
- Sliding session expiration
- Session cleanup job
- Thread-local authentication context
- Role-based authorization checks

---

## 📌 Current Status

The project is actively being developed.

### ✅ Completed features:
- Client management
- Product registration
- Service order lifecycle
- Custom authentication system
- Session persistence
- Sliding session expiration
- Cleanup job for expired sessions

### 🚧 Work in progress:
- Auditing support
- Additional architectural refinements
- Further validation and testing
- Documentation and code comments

> Because the project is evolving, some parts of the code may still be incomplete or subject to refactoring.

---

## 📖 Project Purpose

This repository serves as a **learning and experimentation environment** for building backend systems with **proper architecture** — rather than relying entirely on framework abstractions.

The focus is understanding:
- Where logic belongs
- How layers interact
- How authentication and persistence really work internally

---

## 📝 Notes

- The project currently prioritizes architecture over documentation
- Comments and additional documentation will be added progressively
- Some features may still be under active refactoring

---

## 🧑‍💻 Author

**mgcf27** 

---

