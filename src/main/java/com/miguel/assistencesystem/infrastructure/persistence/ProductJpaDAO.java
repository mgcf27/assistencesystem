package com.miguel.assistencesystem.infrastructure.persistence;

import com.miguel.assistencesystem.application.dto.view.ClientReferenceDTO;
import com.miguel.assistencesystem.domain.model.Product;
import com.miguel.assistencesystem.domain.valueobjects.PageRequest;

import jakarta.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class ProductJpaDAO extends BaseDAO<Product, Long>{
	
	public ProductJpaDAO() {
		super(Product.class);
	}

	
	// ============ READ BY CLIENT ============
	public List<Product> findProductsByClientId(
			Long id,
			PageRequest pageRequest){ 
		return  em.createQuery(
				"SELECT p FROM Product p WHERE p.client.ClientID = :clientId", Product.class)
				.setParameter("clientId", id)
				.setFirstResult(pageRequest.offset())
				.setMaxResults(pageRequest.pageSize())
				.getResultList();				
	}
	
	public ClientReferenceDTO findOwnerReferenceByProductId(Long productId) {
	    return em.createQuery(
	        """
	        SELECT new com.miguel.assistencesystem.view.product.dto.ClientReferenceDTO(
	            c.clientId,
	            c.name
	        )
	        FROM Product p
	        JOIN p.client c
	        WHERE p.prodId = :productId
	        """,
	        ClientReferenceDTO.class
	    )
	    .setParameter("productId", productId)
	    .getSingleResult();
	}


	//========== READ BY PRODUCT FIELDS ============
	public Optional<Product> findBySerialNumber(String serialNumber){
            try {
                Product product = em.createQuery(
                    "SELECT p FROM Product p WHERE p.serialNumber = :serial", 
                    Product.class)
                    .setParameter("serial", serialNumber)
                    .getSingleResult();
                return Optional.of(product);
            } catch (NoResultException e) {
                return Optional.empty();
            }
	}
	
	/*public List<Product> findByModel(String model) {
        return em.createQuery(
                "SELECT p FROM Product p WHERE LOWER(p.model) LIKE LOWER(:model)", 
                Product.class)
                .setParameter("model", "%" + model + "%")
                .getResultList();
    }*/
	
	//====================== COMMAND SUPPORT QUERIES ======================
	public boolean existsBySerialNumber(String serialNumber) {
        Long count = em.createQuery(
                "SELECT COUNT(p) FROM Product p WHERE p.serialNumber = :serial", 
                Long.class)
                .setParameter("serial", serialNumber)
                .getSingleResult();
        return count > 0;
    }
}

