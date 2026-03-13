package com.miguel.assistencesystem.infrastructure.security.session;

import java.time.Duration;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(
        name = "authentication_tokens",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_authentication_tokens_token",
                columnNames = "token"
        )
)
public class AuthenticationToken {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 255)
    private String token;

    @Column(name = "employee_account_id", nullable = false)
    private Long employeeAccountId;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false) 
    private boolean revoked;
    
    protected AuthenticationToken() {}
    
    public static AuthenticationToken create(
    		String token,
    		Long employeeAccountId,
    		LocalDateTime expiresAt
    		) {
    	AuthenticationToken t = new AuthenticationToken();
    	t.token = token;
    	t.employeeAccountId = employeeAccountId;
    	t.expiresAt = expiresAt;
    	t.revoked = false;
    	
    	return t;
    }
	
    public Long getId() { return id; }

    public String getToken() { return token; }

    public Long getEmployeeAccountId() { return employeeAccountId; }

    public LocalDateTime getExpiresAt() { return expiresAt; }

    public boolean isRevoked() { return revoked; }
	
    public void revoke() {
        this.revoked = true;
    }
    
    public void extendExpiration(Duration timeout) {
        this.expiresAt = LocalDateTime.now().plus(timeout);
    }
}
