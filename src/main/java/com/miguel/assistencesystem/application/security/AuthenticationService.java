package com.miguel.assistencesystem.application.security;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.dto.command.LoginRequestDTO;
import com.miguel.assistencesystem.application.dto.response.LoginResponseDTO;
import com.miguel.assistencesystem.domain.exceptions.authentication.InvalidCredentialsException;
import com.miguel.assistencesystem.infrastructure.persistence.AuthenticationTokenDAO;
import com.miguel.assistencesystem.infrastructure.security.credential.AuthenticationResult;
import com.miguel.assistencesystem.infrastructure.security.credential.CredentialVerifier;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;
import com.miguel.assistencesystem.infrastructure.security.session.AuthenticationToken;
import com.miguel.assistencesystem.infrastructure.security.session.TokenGenerator;

@Service
@Transactional
public class AuthenticationService {
	private final CredentialVerifier credentialVerifier;
	private final TokenGenerator tokenGenerator;
	private final AuthenticationFacade authFacade;
	private final AuthenticationTokenDAO tokenDAO;
	
	public AuthenticationService(
			CredentialVerifier credentialVerifier,
			TokenGenerator tokenGenerator,
			AuthenticationFacade authFacade,
			AuthenticationTokenDAO tokenDAO) {
		
		this.credentialVerifier = credentialVerifier;
		this.tokenGenerator = tokenGenerator;
		this.authFacade  = authFacade;
		this.tokenDAO = tokenDAO;
	}
	
	public LoginResponseDTO login(LoginRequestDTO request) {

	    AuthenticationResult result =
	            credentialVerifier.verify(
	                    request.getEmail(),
	                    request.getPassword()
	            );

	    if (!result.isAuthenticated()) {

	        throw new InvalidCredentialsException("Invalid email or password");

	    }

	    String token =
	            tokenGenerator.generateFor(
	                    result.getIdentity().getId()
	            );

	    return new LoginResponseDTO(token);
	}
	
	public void logOut() {
		AuthenticatedIdentity identity = authFacade.requireAuthenticated();
		
		AuthenticationToken authToken = tokenDAO.findByToken(identity.getToken())
	            .orElseThrow(() -> new IllegalStateException("Token not found"));
		
		authToken.revoke();
	}
}
