package com.miguel.assistencesystem.infrastructure.security.credential;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.miguel.assistencesystem.domain.security.AuthenticationFailureReason;
import com.miguel.assistencesystem.domain.security.EmployeeAccount;
import com.miguel.assistencesystem.infrastructure.persistence.EmployeeAccountDAO;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;

@Component
public class CredentialVerifier {
	
	private final EmployeeAccountDAO accountDAO;
	private final PasswordHasher passwordHasher;
	
	public CredentialVerifier(EmployeeAccountDAO accountDAO, PasswordHasher passwordHasher) {
		this.accountDAO = accountDAO;
		this.passwordHasher	= passwordHasher;
	}
	
	public AuthenticationResult verify(String email, String rawPassword) {
		
		Optional<EmployeeAccount> optional = accountDAO.findByEmail(email);
		
		if(optional.isEmpty()) {
			 return AuthenticationResult.failure(
	                    AuthenticationFailureReason.INVALID_CREDENTIALS
	            );
		}
	
		EmployeeAccount account = optional.get();
		
		if(!account.isActive()) {
			return AuthenticationResult.failure(
                    AuthenticationFailureReason.ACCOUNT_DISABLED
            );
			
		}
		
		if (!passwordHasher.matches(rawPassword, account.getPasswordHash())) {
            return AuthenticationResult.failure(
                    AuthenticationFailureReason.INVALID_CREDENTIALS
            );
        }
		
		AuthenticatedIdentity identity = new AuthenticatedIdentity(
				account.getId(),
				account.getEmail(),
				account.getEmployeeRole(),
				null);
		return AuthenticationResult.success(identity);
		
	}	
}
