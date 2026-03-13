CREATE TABLE employee_accounts (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(150) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL
);

ALTER TABLE employee_accounts
    ADD CONSTRAINT uk_employee_accounts_email UNIQUE (email);