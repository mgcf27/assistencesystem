package com.miguel.assistencesystem.application.command;

import com.miguel.assistencesystem.domain.audit.AuditAction;
import com.miguel.assistencesystem.domain.audit.EntityType;
import com.miguel.assistencesystem.domain.enums.ServiceOrderStatus;
import com.miguel.assistencesystem.domain.exceptions.product.ProductNotFoundException;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.ServiceOrderAlreadyOpenException;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.ServiceOrderNotFoundException;
import com.miguel.assistencesystem.domain.model.*;
import com.miguel.assistencesystem.infrastructure.persistence.ProductJpaDAO;
import com.miguel.assistencesystem.infrastructure.persistence.ServiceOrderJpaDAO;

import java.util.function.Consumer;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
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
    private static final String OPEN_SO_CONSTRAINT = "ux_one_open_so_per_product";
    private static final String OPEN_SO_MESSAGE = "Product already has an open service order.";
    
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
            throw new ServiceOrderAlreadyOpenException(OPEN_SO_MESSAGE);
        }
        
        ServiceOrder so = ServiceOrder.open(client, product, dto.getProblemDescription());
        
        try {
        	serviceDAO.persist(so);
        }catch(DataIntegrityViolationException e) {
        	if(isCausedBy(e,OPEN_SO_CONSTRAINT)) {
        		throw new ServiceOrderAlreadyOpenException(OPEN_SO_MESSAGE);
        	}
        	throw e;
        }
        
        auditService.record(AuditAction.SERVICE_ORDER_OPENED, EntityType.SERVICE_ORDER, so.getId());
        
        return ServiceOrderResponseDTO.fromEntity(so);
	}

	public ServiceOrderResponseDTO start(Long id) {
		return transition(id, ServiceOrder::startProgress);
	}
	
	public ServiceOrderResponseDTO waitingParts(Long id) {		
		return transition(id, ServiceOrder::waitForParts);
	}

	public ServiceOrderResponseDTO finish(Long id) {
		return transition(id, ServiceOrder::finish);
	}
	
	public ServiceOrderResponseDTO close(Long id) {
		return transition(id, ServiceOrder::close);
	}
	
	public ServiceOrderResponseDTO cancel(Long id) {
		return transition(id, ServiceOrder::cancel);
    }


	private ServiceOrderResponseDTO transition(Long id, Consumer<ServiceOrder> action){
		ServiceOrder so = serviceDAO.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
		
		ServiceOrderStatus before = so.getStatus();
		
		action.accept(so);
		
		auditService.record( 
        		AuditAction.SERVICE_ORDER_STATUS_CHANGED,
        		EntityType.SERVICE_ORDER,
        		so.getId(),
        		"{\"from\":\"%s\",\"to\":\"%s\"}".formatted(before, so.getStatus()));
		
		return ServiceOrderResponseDTO.fromEntity(so);
	
	}

	private static boolean isCausedBy(Throwable t, String constraintName) {
		Throwable current = t;
		while(current != null) {
			if(current instanceof ConstraintViolationException cve) {
				return constraintName.equals(cve.getConstraintName());
			}
			current = current.getCause();
		}
		return false;
	}
}
