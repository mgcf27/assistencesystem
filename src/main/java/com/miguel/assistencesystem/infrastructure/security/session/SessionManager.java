package com.miguel.assistencesystem.infrastructure.security.session;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.miguel.assistencesystem.infrastructure.config.SecuritySessionProperties;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;

@Component
public class SessionManager {
    private final AuthenticationTokenDAO tokenDAO;
    private final SecuritySessionProperties sessionProperties;

    public SessionManager(
            AuthenticationTokenDAO tokenDAO,
            SecuritySessionProperties sessionProperties) {
        this.tokenDAO = tokenDAO;
        this.sessionProperties = sessionProperties;
    }

    public Optional<AuthenticatedIdentity> resolveIdentityFromToken(String token) {
    	LocalDateTime now = LocalDateTime.now();
    	
        return loadSession(token)
        		.filter(session -> validateSession(session,now))
        		.map(session -> {
        			applySlidingPolicy(session,now);
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

    private void applySlidingPolicy(
    		AuthenticatedSessionView session,
    		LocalDateTime now
    		) {
        Duration remainingLifetime =
                Duration.between(now, session.expiresAt());

        if (remainingLifetime.compareTo(sessionProperties.refreshThreshold()) <= 0) {

            Optional<AuthenticationToken> token = tokenDAO.findByToken(session.token());
                    
            token.ifPresent(t-> t.extendExpiration(sessionProperties.timeout()));
        }
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