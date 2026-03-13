package com.miguel.assistencesystem.application.command;

import com.miguel.assistencesystem.domain.exceptions.product.ProductNotFoundException;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.ServiceOrderAlreadyOpenException;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.ServiceOrderNotFoundException;
import com.miguel.assistencesystem.domain.model.*;
import com.miguel.assistencesystem.infrastructure.persistence.ProductJpaDAO;
import com.miguel.assistencesystem.infrastructure.persistence.ServiceOrderJpaDAO;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.dto.command.ServiceOrderCreateDTO;
import com.miguel.assistencesystem.application.dto.response.ServiceOrderResponseDTO;
import com.miguel.assistencesystem.application.security.AuthenticationFacade;
import com.miguel.assistencesystem.application.validation.serviceorder.ServiceOrderValidator;


@Service
@Transactional
public class ServiceOrderService {
	
	private final AuthenticationFacade authentication;
	private final ServiceOrderJpaDAO serviceDAO; 
    private final ProductJpaDAO productDAO;
    
    public ServiceOrderService(
    		AuthenticationFacade authentication,
    		ServiceOrderJpaDAO serviceDAO,
    		ProductJpaDAO productDAO) {
    	this.authentication = authentication;
    	this.productDAO = productDAO;
    	this.serviceDAO = serviceDAO;
    }

	
	public ServiceOrderResponseDTO openSO(ServiceOrderCreateDTO dto) {
		
		AuthenticatedIdentity identity = authentication.requireAuthenticated();
		
		ServiceOrderValidator.validateForCreation(dto);
        
        Product product = productDAO.findById(dto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
        
        Client client = product.getClient();
        
        if (serviceDAO.hasOpenServiceOrderForProduct(dto.getProductId())) {
            throw new ServiceOrderAlreadyOpenException("Product already has an open service order. Wanna see it instead?");
        }
        
        ServiceOrder so = ServiceOrder.open(client, product, dto.getProblemDescription());
        serviceDAO.persist(so);
        
        return ServiceOrderResponseDTO.fromEntity(so);
	}

	public ServiceOrderResponseDTO start(Long id) {
		AuthenticatedIdentity identity = authentication.requireAuthenticated();
		
		ServiceOrder so = serviceDAO.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
        
        so.startProgress();
        
        return ServiceOrderResponseDTO.fromEntity(so);
	}
	
	public ServiceOrderResponseDTO waitingParts(Long id) {
		AuthenticatedIdentity identity = authentication.requireAuthenticated();
		
		ServiceOrder so = serviceDAO.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
        
        so.waitForParts();
        
        return ServiceOrderResponseDTO.fromEntity(so);
	}

	public ServiceOrderResponseDTO finish(Long id) {
		AuthenticatedIdentity identity = authentication.requireAuthenticated();
		
		ServiceOrder so = serviceDAO.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
        
        so.finish();
        
        return ServiceOrderResponseDTO.fromEntity(so);
	}
	
	public ServiceOrderResponseDTO close(Long id) {
		AuthenticatedIdentity identity = authentication.requireAuthenticated();
		
		ServiceOrder so = serviceDAO.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
        
        so.close();
        
        return ServiceOrderResponseDTO.fromEntity(so);
	}
	
	public ServiceOrderResponseDTO cancel(Long id) {
		AuthenticatedIdentity identity = authentication.requireAuthenticated();
		
		ServiceOrder so = serviceDAO.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
        
        so.cancel();
        return ServiceOrderResponseDTO.fromEntity(so);
    }
}
