package com.miguel.assistencesystem.application.command;

import com.miguel.assistencesystem.domain.audit.AuditAction;
import com.miguel.assistencesystem.domain.audit.EntityType;
import com.miguel.assistencesystem.domain.exceptions.product.ProductNotFoundException;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.ServiceOrderAlreadyOpenException;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.ServiceOrderNotFoundException;
import com.miguel.assistencesystem.domain.model.*;
import com.miguel.assistencesystem.infrastructure.persistence.ProductJpaDAO;
import com.miguel.assistencesystem.infrastructure.persistence.ServiceOrderJpaDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.audit.AuditService;
import com.miguel.assistencesystem.application.dto.command.ServiceOrderCreateDTO;
import com.miguel.assistencesystem.application.dto.response.ServiceOrderResponseDTO;
import com.miguel.assistencesystem.application.validation.serviceorder.ServiceOrderValidator;


@Service
@Transactional
public class ServiceOrderService {
	
	private final AuditService auditService;
	private final ServiceOrderJpaDAO serviceDAO; 
    private final ProductJpaDAO productDAO;
    
    public ServiceOrderService(
    		AuditService auditService,
    		ServiceOrderJpaDAO serviceDAO,
    		ProductJpaDAO productDAO) {
    	this.auditService = auditService;
    	this.productDAO = productDAO;
    	this.serviceDAO = serviceDAO;
    }

	
	public ServiceOrderResponseDTO openSO(ServiceOrderCreateDTO dto) {	
		ServiceOrderValidator.validateForCreation(dto);
        
        Product product = productDAO.findById(dto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
        
        Client client = product.getClient();
        
        if (serviceDAO.hasOpenServiceOrderForProduct(dto.getProductId())) {
            throw new ServiceOrderAlreadyOpenException("Product already has an open service order. Wanna see it instead?");
        }
        
        ServiceOrder so = ServiceOrder.open(client, product, dto.getProblemDescription());
        serviceDAO.persist(so);
        
        auditService.record(AuditAction.SERVICE_ORDER_OPENED, EntityType.SERVICE_ORDER, so.getId());
        
        return ServiceOrderResponseDTO.fromEntity(so);
	}

	public ServiceOrderResponseDTO start(Long id) {
		ServiceOrder so = serviceDAO.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
        
        so.startProgress();
        
        String metadata = "{ \"from\": \"OPEN\", \"to\": \"IN_PROGRESS\" }";
        
        auditService.record(AuditAction.SERVICE_ORDER_STATUS_CHANGED, EntityType.SERVICE_ORDER, so.getId(), metadata);
        
        return ServiceOrderResponseDTO.fromEntity(so);
	}
	
	public ServiceOrderResponseDTO waitingParts(Long id) {		
		ServiceOrder so = serviceDAO.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
        
        so.waitForParts();
        
        String metadata = "{ \"from\": \"IN_PROGRESS\", \"to\": \"WAITING_PARTS\" }";
        
        auditService.record(AuditAction.SERVICE_ORDER_STATUS_CHANGED, EntityType.SERVICE_ORDER, so.getId(), metadata);
        
        return ServiceOrderResponseDTO.fromEntity(so);
	}

	public ServiceOrderResponseDTO finish(Long id) {
		ServiceOrder so = serviceDAO.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
        
        so.finish();
        
        String metadata = "{ \"from\": \"WAITING_PARTS\", \"to\": \"FINISHED\" }";
        
        auditService.record(AuditAction.SERVICE_ORDER_STATUS_CHANGED, EntityType.SERVICE_ORDER, so.getId(), metadata);
        
        return ServiceOrderResponseDTO.fromEntity(so);
	}
	
	public ServiceOrderResponseDTO close(Long id) {
		ServiceOrder so = serviceDAO.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
        
        so.close();
        
        String metadata = "{ \"from\": \"FINISHED\", \"to\": \"CLOSED\" }";
        
        auditService.record(AuditAction.SERVICE_ORDER_STATUS_CHANGED, EntityType.SERVICE_ORDER, so.getId(), metadata);
        
        return ServiceOrderResponseDTO.fromEntity(so);
	}
	
	public ServiceOrderResponseDTO cancel(Long id) {
		ServiceOrder so = serviceDAO.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
        
        so.cancel();
        
        String metadata = "{ \"from\": \"OPEN\", \"to\": \"CANCELED\" }";
        
        auditService.record(AuditAction.SERVICE_ORDER_STATUS_CHANGED, EntityType.SERVICE_ORDER, so.getId(), metadata);
        
        return ServiceOrderResponseDTO.fromEntity(so);
    }
}
