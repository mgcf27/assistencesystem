package com.miguel.assistencesystem.application.security;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;
import com.miguel.assistencesystem.infrastructure.security.session.AuthenticatedSessionView;
import com.miguel.assistencesystem.infrastructure.security.session.AuthenticationTokenDAO;

@Component
public class SessionManager {
    private final AuthenticationTokenDAO tokenDAO;
    private final SessionService sessionService;

    public SessionManager(
            AuthenticationTokenDAO tokenDAO,
            SessionService sessionService) {
        this.tokenDAO = tokenDAO;
        this.sessionService = sessionService;
    }

    public Optional<AuthenticatedIdentity> resolveIdentityFromToken(String token) {
    	LocalDateTime now = LocalDateTime.now();
    	
        return loadSession(token)
        		.filter(session -> validateSession(session,now))
        		.map(session -> {
        			sessionService.applySlidingIfNeeded(session, now);
        			return session;})
        		.map(this::buildIdentity);
    }    
        
        
    private Optional<AuthenticatedSessionView> loadSession(String token) {
        return tokenDAO.findSessionViewByToken(token);
    }

    private boolean validateSession(
    		AuthenticatedSessionView session,
    		LocalDateTime now) {
    	return !session.revoked() && now.isBefore(session.expiresAt());
    }

   
    private AuthenticatedIdentity buildIdentity(AuthenticatedSessionView session) {
        return new AuthenticatedIdentity(
                session.employeeId(),
                session.email(),
                session.role(),
                session.token()
        );
    }
}