package com.miguel.assistencesystem.infrastructure.persistence;

import com.miguel.assistencesystem.domain.model.Client;
import com.miguel.assistencesystem.domain.valueobjects.PageRequest;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class ClientJpaDAO extends BaseDAO<Client, Long>{
	
	public ClientJpaDAO() {
		super(Client.class);
	}
	
	//====================== READ BY FIELDS/ VIEW ASSEMBLE QUERIES ======================
	
	public Optional<Client> findByCpf(String cpf){
		try {
			Client client = em.createQuery(
					"SELECT c FROM Client c WHERE c.cpf = :cpf",
					Client.class)
					.setParameter("cpf",cpf)
					.getSingleResult();
			return Optional.of(client);
		} catch(NoResultException e) {
			return Optional.empty();
		  }	
	}
	
	public Optional<Client> findByPhone(String phone){
		try {
			Client client = em.createQuery(
					"SELECT c FROM Client c WHERE c.phone = :phone",
					Client.class)
					.setParameter("phone", phone)
					.getSingleResult();
			return Optional.of(client);			
		} catch(NoResultException e) {
			return Optional.empty();
		}
	}
	
	public List<Client> findByName(String name, PageRequest pageRequest) {	    
	    return em.createQuery(
	            "SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(:name)",
	            Client.class)
	            .setParameter("name", "%" + name + "%")
	            .setFirstResult(pageRequest.offset())
	            .setMaxResults(pageRequest.pageSize())
	            .getResultList();	    
	}
	
	//====================== COMMAND SUPPORT QUERIES ======================
	public boolean existsByCpf(String cpf) {	    
	    Long count = em.createQuery(
	            "SELECT COUNT(c) FROM Client c WHERE c.cpf = :cpf", Long.class)
	            .setParameter("cpf", cpf)
	            .getSingleResult();
	    
	    return count > 0;
	}

	public boolean existsByPhone(String phone) {
		Long count = em.createQuery(
				"SELECT COUNT(c) FROM Client c " +
				"WHERE c.phone = :phone", Long.class)
				.setParameter("phone", phone)
				.getSingleResult();	
		
		return count > 0;
	}
	
	public long countProductsByClientId(Long id) {
	    Long count = em.createQuery(
	            "SELECT COUNT(p) FROM Product p WHERE p.client.clientId = :clientId",
	            Long.class)
	            .setParameter("clientId", id)
	            .getSingleResult();
	    return count;     
	}
}