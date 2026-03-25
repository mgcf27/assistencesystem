package com.miguel.assistencesystem.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.miguel.assistencesystem.domain.audit.AuditLog;
import com.miguel.assistencesystem.domain.audit.EntityType;
import com.miguel.assistencesystem.domain.valueobjects.PageRequest;

@Repository
public class AuditLogDAO extends BaseDAO<AuditLog, Long> {
	public AuditLogDAO() {
		super(AuditLog.class);
	}
	
	public List<AuditLog> findByEntityTypeAndEntityId(EntityType entityType, Long entityId, PageRequest pageRequest){
		return em.createQuery("""
				SELECT al FROM AuditLog al
				WHERE al.entityType = :entityType
				AND al.entityId = :entityId
				ORDER BY al.occurredAt DESC
				""", AuditLog.class)
				.setParameter("entityType", entityType)
				.setParameter("entityId", entityId)
				.setFirstResult(pageRequest.offset())
				.setMaxResults(pageRequest.pageSize())
				.getResultList();
					
	}
	
	public List<AuditLog> findByOccurredAt(LocalDateTime from, LocalDateTime to, PageRequest pageRequest){
		return em.createQuery("""
				SELECT al FROM AuditLog al
				WHERE al.occurredAt BETWEEN :from AND :to
				ORDER BY al.occurredAt DESC
				""", AuditLog.class)
				.setParameter("from", from)
				.setParameter("to", to)
				.setFirstResult(pageRequest.offset())
				.setMaxResults(pageRequest.pageSize())
				.getResultList();
	}


}
