DELETE FROM employee_accounts;

ALTER TABLE employee_accounts
ADD COLUMN name VARCHAR(255) NOT NULL,
ADD COLUMN cpf VARCHAR(14) NOT NULL,
ADD COLUMN phone VARCHAR(20) NOT NULL,
ADD COLUMN address VARCHAR(200) NOT NULL,
ADD COLUMN role VARCHAR(20) NOT NULL,
ADD COLUMN version BIGINT NOT NULL DEFAULT 0;


ALTER TABLE employee_accounts
ADD CONSTRAINT uk_employee_accounts_cpf UNIQUE (cpf);

ALTER TABLE employee_accounts
ADD CONSTRAINT uk_employee_accounts_phone UNIQUE (phone);