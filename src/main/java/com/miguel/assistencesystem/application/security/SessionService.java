package com.miguel.assistencesystem.application.security;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.infrastructure.config.SecuritySessionProperties;
import com.miguel.assistencesystem.infrastructure.persistence.AuthenticationTokenDAO;
import com.miguel.assistencesystem.infrastructure.security.session.AuthenticatedSessionView;
import com.miguel.assistencesystem.infrastructure.security.session.AuthenticationToken;

@Service
@Transactional
public class SessionService {
	private final AuthenticationTokenDAO tokenDAO;
	private final SecuritySessionProperties sessionProperties;
	
	public SessionService(
			AuthenticationTokenDAO tokenDAO,
			SecuritySessionProperties sessionProperties) {
		this.tokenDAO = tokenDAO;
		this.sessionProperties = sessionProperties;
	}
	
	public void applySlidingIfNeeded(AuthenticatedSessionView session, LocalDateTime now ) {
		Duration remainingLifetime =
                Duration.between(now, session.expiresAt());
		
		if (remainingLifetime.compareTo(sessionProperties.refreshThreshold()) <= 0) {

            Optional<AuthenticationToken> token = tokenDAO.findByToken(session.token());
                    
            token.ifPresent(t-> t.extendExpiration(sessionProperties.timeout()));
        }
	}

}
