CREATE TABLE authentication_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    employee_account_id BIGINT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT uk_authentication_tokens_token UNIQUE (token),

    CONSTRAINT fk_auth_tokens_employee
        FOREIGN KEY (employee_account_id)
        REFERENCES employee_accounts(id)
        ON DELETE CASCADE
);