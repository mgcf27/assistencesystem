CREATE TABLE audit_log (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id BIGINT NOT NULL,
    occurred_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    metadata VARCHAR(500),
     
    CONSTRAINT fk_audit_log_employee 
        FOREIGN KEY (employee_id) 
        REFERENCES employee_accounts(id)
);


CREATE INDEX idx_audit_log_employee_id ON audit_log(employee_id);
CREATE INDEX idx_audit_log_entity_lookup ON audit_log(entity_type, entity_id);
CREATE INDEX idx_audit_log_occurred_at ON audit_log(occurred_at);