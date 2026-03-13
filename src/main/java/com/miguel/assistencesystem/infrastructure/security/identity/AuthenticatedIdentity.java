package com.miguel.assistencesystem.infrastructure.security.identity;

import com.miguel.assistencesystem.domain.security.EmployeeRole;

public final class AuthenticatedIdentity {
	private final Long id;
	private final String email;
	private final EmployeeRole role;
	private final String token;
	
	public AuthenticatedIdentity(
			Long id,
			String email,
			EmployeeRole role,
			String token) {
		this.id = id;
		this.email = email;
		this.role = role;
		this.token = token;
	}

	public Long getId() {
        return id;
    }
	
    public String getEmail() {
        return email;
    }
    
    public EmployeeRole getRole() {
    	return role;
    }    

    public String getToken() {
    	return token;
    }
}
