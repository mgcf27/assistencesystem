CREATE TABLE clients(
	client_id BIGSERIAL PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(200) NOT NULL,
    email VARCHAR(100),
    version BIGINT NOT NULL	
);

CREATE TABLE products (
    prod_id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    identification_status VARCHAR(50) NOT NULL,
    model VARCHAR(100) NOT NULL,
    commercial_model VARCHAR(100),
    manufacturer_code VARCHAR(50),
    serial_number VARCHAR(50) UNIQUE,
    voltage VARCHAR(20),
    version BIGINT NOT NULL,

    CONSTRAINT fk_product_client
        FOREIGN KEY (client_id)
        REFERENCES clients (client_id)
);

CREATE TABLE service_orders (
    service_order_id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    prod_id BIGINT NOT NULL,
    protocol_number VARCHAR(30) NOT NULL UNIQUE,
    problem_description TEXT,
    opened_at TIMESTAMP NOT NULL,
    closed_at TIMESTAMP,
    status VARCHAR(30) NOT NULL,
    version BIGINT NOT NULL,

    CONSTRAINT fk_so_client
        FOREIGN KEY (client_id)
        REFERENCES clients (client_id),

    CONSTRAINT fk_so_product
        FOREIGN KEY (prod_id)
        REFERENCES products (prod_id)
);