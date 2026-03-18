🔧 Assistance System Backend
Backend system for managing service orders in a technical assistance environment (appliance repair, warranty service, etc.).

This project is being developed as a deep learning exercise in backend engineering, focusing on how systems actually work under the hood rather than relying on framework abstractions.

⚠️ Active Development — This project is actively being developed. Some parts of the system are still evolving, and the codebase may undergo refactoring as architectural decisions are refined. Documentation and comments are still incomplete.

🎯 Project Goals
This project was built to understand and practice:

-Domain-driven design (DDD) concepts

-Layered architecture

-Manual persistence with JPA/Hibernate

-Authentication system design (without Spring Security auto-configuration)

-Session lifecycle management (login, logout, expiration, sliding)

-**Clear separation of responsibilities across layers**

The focus is not just building features, but understanding where logic belongs, how layers interact, and how persistence and authentication really behave.

🛠️ Tech Stack
Language:	Java 
Framework:	Spring Boot
Persistence:	Hibernate / JPA (manual via EntityManager)
Database:	PostgreSQL
Migrations:	Flyway
API	REST
Persistence is handled through custom DAOs, without Spring Data repositories, to maintain explicit control over queries and entity lifecycle.

🏗️ Architecture
The system follows a layered architecture with strong DDD influence:

-Domain  ← Business concepts & rules

-Application  ← Use-case orchestration

-Infrastructure   ← Technical implementation

**This is a pragmatic layered design, not strict Clean Architecture or Hexagonal Architecture.**

Application services interact directly with DAOs instead of using repository interfaces. This decision was intentional to keep the system explicit and easier to reason about while learning.

📦 Domain Layer
Contains business concepts and rules.

Examples:

-Client, Product, ServiceOrder

-EmployeeAccount, EmployeeRole

-AuthenticationFailureReason

Responsibilities:

-Enforce invariants

-Model business state transitions

-Represent core domain concepts

✅ No framework dependencies.

⚙️ Application Layer
Contains use-case orchestration.

Examples:

-AuthenticationService

-EmployeeAccountService

-ClientService, ProductService, ServiceOrderService

-SessionManager, SessionService

Responsibilities:

-Coordinate domain entities

-Enforce workflows

-Interact with persistence layer

-Apply session and authentication policies

**The application layer is where most of the system behavior is defined.**

🧱 Infrastructure Layer
Contains technical implementation details.

Examples:

-REST controllers

-AuthenticationFilter

-DAOs (EntityManager-based)

-Password hashing (BCrypt)

-Authentication token persistence

-Configuration classes

Responsibilities:

-HTTP handling

-Persistence implementation

-Security mechanisms

-Framework integration

🔐 Authentication System
Authentication was implemented from scratch, without relying on Spring Security auto-configuration.

The system uses database-backed opaque tokens.

Login Flow

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
The client receives a token and sends it in every request:


Authorization: Bearer <token>
Request Authentication Pipeline
text
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
🕒 Session Management
Sessions are stored in the authentication_tokens database table.

Fields:

-token

-employee_account_id

-expires_at

-revoked

✅ Features Implemented
-Login

-Logout (token revocation)

-Sliding session expiration

-Scheduled cleanup of expired sessions

-Thread-local authentication context

🔄 Sliding Expiration
Session expiration is dynamically extended based on activity.

Configuration:

-timeout = 30 minutes

-refreshThreshold = 10 minutes

Behavior:

If remaining lifetime ≤ 10 minutes → extend expiration to now + 30 minutes

This update is executed inside a transactional service, ensuring Hibernate dirty checking persists the change correctly.

⚡ Query Optimization
Session resolution uses projection queries to avoid unnecessary entity loading.

Flow:

load session projection
    ↓
validate session
    ↓
only load entity if refresh is required
This reduces database load during normal requests.

📊 Command / Query Separation
The system follows a CQRS-style structure:

Command services → mutations

Query services → reads

Only command services modify state.

This separation simplifies reasoning about:

-Business operations

-Auditing

-System behavior

📌 Current Status
✅ Implemented
Client management

Product management

Service order lifecycle

Employee account management

Custom authentication system

Token persistence

Logout

Sliding session expiration

Scheduled cleanup job

Concurrency-safe session handling

🚧 In Progress
Audit logging for mutation operations

Additional architectural refinements

Improved validation

Documentation and comments

💭 Project Philosophy
This project intentionally avoids hiding complexity behind frameworks.

Instead, it focuses on:

Understanding first, abstraction later

The goal is to build a strong mental model of:

Persistence behavior

Transaction boundaries

Authentication flow

Architectural decisions

📝 Notes
Code is still evolving and may change

Some features are not fully implemented yet

Documentation will be improved over time

🏁 Final Remark
This project is less about delivering a finished product and more about developing a deep understanding of backend systems.

Most of the value comes from the process:

Questioning decisions

Refining architecture

Understanding trade-offs

👨‍💻 Author: mgcf27
