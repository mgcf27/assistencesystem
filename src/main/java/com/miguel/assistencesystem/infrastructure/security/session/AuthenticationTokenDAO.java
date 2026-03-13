package com.miguel.assistencesystem.infrastructure.security.session;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.miguel.assistencesystem.infrastructure.persistence.BaseDAO;

@Repository
public class AuthenticationTokenDAO extends BaseDAO<AuthenticationToken, Long> {
	public AuthenticationTokenDAO() {
        super(AuthenticationToken.class);
    }

    public Optional<AuthenticationToken> findByToken(String token) {

        return em.createQuery("""
                SELECT t FROM AuthenticationToken t
                WHERE t.token = :token
                """, AuthenticationToken.class)
                .setParameter("token", token)
                .getResultList()
                .stream()
                .findFirst();
    }
    
    public Optional<AuthenticatedSessionView> findSessionViewByToken(String token) {

        String query = """
            SELECT new com.miguel.assistencesystem.infrastructure.security.session.AuthenticatedSessionView(
                e.id,
                e.email,
                e.role,
                t.token,
                t.expiresAt,
                t.revoked
            )
            FROM AuthenticationToken t
            JOIN EmployeeAccount e
                ON e.id = t.employeeAccountId
            WHERE t.token = :token
            """;

        return em.createQuery(query, AuthenticatedSessionView.class)
                .setParameter("token", token)
                .getResultList()
                .stream()
                .findFirst();
    }
    
    
    public int deleteExpiredOrRevoked(LocalDateTime now) {

        return em.createQuery("""
            DELETE FROM AuthenticationToken t
            WHERE t.revoked = true
               OR t.expiresAt < :now
        """)
        .setParameter("now", now)
        .executeUpdate();
    }
}
