package com.miguel.assistencesystem.domain.audit;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(
	    indexes = {
	        @Index(name = "idx_audit_log_employee_id", columnList = "employee_id"),
	        @Index(name = "idx_audit_log_entity_lookup", columnList = "entity_type, entity_id"),
	        @Index(name = "idx_audit_log_occurred_at", columnList = "occurred_at")
	    }
	)
public class AuditLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "employee_id",updatable = false, nullable = false)
	private Long employeeId;
	@Enumerated(EnumType.STRING)
	@Column(name = "action",updatable = false, nullable = false)
	private AuditAction action;
	@Enumerated(EnumType.STRING)
	@Column(name = "entity_type", updatable = false, nullable = false)
	private EntityType entityType;
	@Column(name = "entity_id", updatable = false, nullable = false)
	private Long entityId;
	@Column(name = "occurred_at", updatable = false, nullable = false)
	private LocalDateTime occurredAt;
	@Column(name = "metadata")
	private String metadata;

	protected AuditLog() {}

	public static AuditLog create(
			Long employeeId,
			AuditAction action,
			EntityType entityType,
			Long entityId,
			String metadata) {
		AuditLog audit = new AuditLog();
		audit.employeeId = employeeId;
		audit.action = action;
		audit.entityType = entityType;
		audit.entityId = entityId;
		audit.occurredAt = LocalDateTime.now();
		audit.metadata = metadata;
		
		return audit;
	}
	
	public Long getEmployeeId() {return employeeId;}
	public AuditAction getAction() {return action;}
	public EntityType getEntityType() {return entityType;}
	public Long getEntityId() {return entityId;}
	public LocalDateTime getOccurredAt() {return occurredAt;}
	public String getMetadata() {return metadata;}
	
}
