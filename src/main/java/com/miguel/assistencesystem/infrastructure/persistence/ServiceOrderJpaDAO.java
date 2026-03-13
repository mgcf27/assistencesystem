package com.miguel.assistencesystem.infrastructure.persistence;

import com.miguel.assistencesystem.domain.enums.ServiceOrderStatus;
import com.miguel.assistencesystem.domain.model.Client;
import com.miguel.assistencesystem.domain.model.ServiceOrder;
import com.miguel.assistencesystem.domain.valueobjects.PageRequest;

import jakarta.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ServiceOrderJpaDAO extends BaseDAO<ServiceOrder, Long>{
	
	public ServiceOrderJpaDAO() {
		super(ServiceOrder.class);
	}

	// ============ READ BY SO FIELDS ============
	public Optional<ServiceOrder> findByProtocolNumber(String protocolNumber) {
	    try {
	        ServiceOrder so = em.createQuery(
	                "SELECT s FROM ServiceOrder s WHERE s.protocolNumber = :protocol",
	                ServiceOrder.class)
	            .setParameter("protocol", protocolNumber)
	            .getSingleResult();
	        return Optional.of(so);
	    } catch (NoResultException e) {
	        return Optional.empty();
	    }
	}
	
	public List<ServiceOrder> findByDateRange(
			LocalDateTime from,
			LocalDateTime to,
			PageRequest pageRequest) {
		
        return em.createQuery(
                "SELECT s FROM ServiceOrder s " +
                "WHERE s.openedAt BETWEEN :from AND :to " +
                "ORDER BY s.openedAt DESC", 
                ServiceOrder.class)
                .setParameter("from", from)
                .setParameter("to", to)
                .setFirstResult(pageRequest.offset())
                .setMaxResults(pageRequest.pageSize())
                .getResultList();
    }

	public List<ServiceOrder> findByStatus(
			ServiceOrderStatus status,
			PageRequest pageRequest) {
		
        return em.createQuery(
                "SELECT s FROM ServiceOrder s WHERE s.status = :status " +
                "ORDER BY s.openedAt DESC", 
                ServiceOrder.class)
                .setParameter("status", status)
                .setFirstResult(pageRequest.offset())
                .setMaxResults(pageRequest.pageSize())
                .getResultList();
    }
	
	// ============ READ BY CLIENT ============

	public List<ServiceOrder> findServiceOrdersByClientId(
			Long clientId,
			PageRequest pageRequest) {
		
	    return em.createQuery(
	         "SELECT so FROM ServiceOrder so WHERE so.client.clientId = :clientId ORDER BY so.openedAt DESC", ServiceOrder.class)
	         .setParameter("clientId", clientId)
	         .setFirstResult(pageRequest.offset())
             .setMaxResults(pageRequest.pageSize())
             .getResultList();
	}
	
	public List<ServiceOrder> findByClientAndStatus(
			Client client,
			ServiceOrderStatus status,
			PageRequest pageRequest) {
		
        return em.createQuery(
                "SELECT s FROM ServiceOrder s " +
                "WHERE s.client = :client AND s.status = :status " +
                "ORDER BY s.openedAt DESC", 
                ServiceOrder.class)
                .setParameter("client", client)
                .setParameter("status", status)
                .setFirstResult(pageRequest.offset())
                .setMaxResults(pageRequest.pageSize())
                .getResultList();
    }
	
	
	//============ READ BY PRODUCT ============
	public List<ServiceOrder> findByProductSerialNumber(
			String serialNumber,
			PageRequest pageRequest){
		
		return em.createQuery(
				"SELECT s FROM ServiceOrder s " +
				"WHERE s.product.serialNumber = :serialNumber " +
				"ORDER BY s.openedAt DESC",
				ServiceOrder.class)
				.setParameter("serialNumber", serialNumber)
				.setFirstResult(pageRequest.offset())
	            .setMaxResults(pageRequest.pageSize())
	            .getResultList();
	}
	
	public List<ServiceOrder> findByProductId(
			Long id,
			PageRequest pageRequest){
		
		return em.createQuery(
				"SELECT s FROM ServiceOrder s " +
				"WHERE s.product.prodId = :prodId " +
				"ORDER BY s.openedAt DESC",
				ServiceOrder.class)
				.setParameter("prodId", id)
				.setFirstResult(pageRequest.offset())
	            .setMaxResults(pageRequest.pageSize())
	            .getResultList();
	}
	
	//====================== COMMAND SUPPORT QUERIES ======================
	public boolean hasOpenServiceOrderForProduct(long productId) {
	    Long count = em.createQuery(
	            "SELECT COUNT(s) FROM ServiceOrder s " +
	            "WHERE s.product.prodId = :productId " +
	            "AND (s.status = :open OR s.status = :inProgress)",
	            Long.class)
	            .setParameter("productId", productId)
	            .setParameter("open", ServiceOrderStatus.OPEN)
	            .setParameter("inProgress", ServiceOrderStatus.IN_PROGRESS)
	            .getSingleResult();
	    return count > 0;
	}
	

	public long countByStatus(ServiceOrderStatus status) {
        Long count = em.createQuery(
                "SELECT COUNT(s) FROM ServiceOrder s WHERE s.status = :status", 
                Long.class)
                .setParameter("status", status)
                .getSingleResult();
        return count;
    }
	
	public long countByProduct(Long id) {
        Long count = em.createQuery(
                "SELECT COUNT(s) FROM ServiceOrder s WHERE s.product.prodId = :id", 
                Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count;
    }
}		
