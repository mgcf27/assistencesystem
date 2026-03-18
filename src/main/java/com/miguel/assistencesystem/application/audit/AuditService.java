package com.miguel.assistencesystem.application.audit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.security.AuthenticationFacade;
import com.miguel.assistencesystem.domain.audit.AuditAction;
import com.miguel.assistencesystem.domain.audit.AuditLog;
import com.miguel.assistencesystem.domain.audit.EntityType;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;

@Service
public class AuditService {
	private final AuthenticationFacade authenticationFacade;
	private final AuditLogDAO auditLogDao;
	
	public AuditService(
			AuthenticationFacade authenticationFacade,
			AuditLogDAO auditLogDao) {
		this.authenticationFacade = authenticationFacade;
		this.auditLogDao = auditLogDao;
	}
	
	public void record(AuditAction action, EntityType entityType, Long entityId) {
		AuthenticatedIdentity identity = authenticationFacade.requireAuthenticated();
		
		AuditLog auditLog = AuditLog.create(
				identity.getId(),
				action,
				entityType,
				entityId,
				null);
		
		auditLogDao.persist(auditLog);
	}
	
	public void record(AuditAction action, EntityType entityType, Long entityId, String metadata) {
		AuthenticatedIdentity identity = authenticationFacade.requireAuthenticated();
		
		AuditLog auditLog = AuditLog.create(
				identity.getId(),
				action,
				entityType,
				entityId,
				metadata);
		
		auditLogDao.persist(auditLog);
	}

}
