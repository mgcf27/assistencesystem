package com.miguel.assistencesystem.infrastructure.security.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.miguel.assistencesystem.domain.security.EmployeeAccount;
import com.miguel.assistencesystem.infrastructure.persistence.BaseDAO;

@Repository
public class EmployeeAccountDAO extends BaseDAO<EmployeeAccount, Long> {

    public EmployeeAccountDAO() {
        super(EmployeeAccount.class);
    }

    public Optional<EmployeeAccount> findByEmail(String email) {
        return em.createQuery("""
                SELECT e FROM EmployeeAccount e
                WHERE e.email = :email
                """, EmployeeAccount.class)
            .setParameter("email", email)
            .getResultStream()
            .findFirst();
    }
    
    public boolean existsByEmail(String email) {
    	Long count = em.createQuery(
    			"SELECT COUNT(e) FROM EmployeeAccount e" +
    			"WHERE e.email = :email", Long.class)
    			.setParameter("email", email)
    			.getSingleResult();
    
    	return count > 0;
    }
    
    public boolean existsByCpf(String cpf) {
    	Long count = em.createQuery(
    			"SELECT COUNT(e) FROM EmployeeAccount e" +
    			"WHERE e.cpf = :cpf", Long.class)
    			.setParameter("cpf", cpf)
    			.getSingleResult();
    	
    	return count > 0;
    			
    }
    
    public boolean existsByPhone(String phone) {
    	Long count = em.createQuery(
    			"SELECT COUNT(e) FROM EmployeeAccount e" +
    			"WHERE e.phone = :phone", Long.class)
    			.setParameter("phone", phone)
    			.getSingleResult();
    	return count > 0;
    }
}
