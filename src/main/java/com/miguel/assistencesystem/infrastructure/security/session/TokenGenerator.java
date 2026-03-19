package com.miguel.assistencesystem.infrastructure.security.session;

import org.springframework.stereotype.Component;

import com.miguel.assistencesystem.infrastructure.config.SecuritySessionProperties;
import com.miguel.assistencesystem.infrastructure.persistence.AuthenticationTokenDAO;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Component
public class TokenGenerator {
	private final AuthenticationTokenDAO tokenDAO;
	private final SecuritySessionProperties sessionProperties;
    private final SecureRandom secureRandom = new SecureRandom();

    public TokenGenerator(
    		AuthenticationTokenDAO tokenDao,
    		SecuritySessionProperties sessionProperties) {
        this.tokenDAO = tokenDao;
        this.sessionProperties = sessionProperties;
    }

    public String generateFor(Long employeeAccountId) {
    	
    	String token = generateSecureToken();
    	
    	LocalDateTime expiresAt =
                LocalDateTime.now().plus(sessionProperties.timeout());
    	
    	AuthenticationToken entity =
                AuthenticationToken.create(
                        token,
                        employeeAccountId,
                        expiresAt
                );
    	
    	tokenDAO.persist(entity);
    	
    	return token;
    }

    private String generateSecureToken() {

        byte[] randomBytes = new byte[32];

        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }  
}
