package com.miguel.assistencesystem.application.query.view;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.dto.view.ServiceOrderViewDTO;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.ServiceOrderNotFoundException;
import com.miguel.assistencesystem.domain.model.ServiceOrder;
import com.miguel.assistencesystem.infrastructure.persistence.ServiceOrderJpaDAO;

@Service	
@Transactional(readOnly = true)
public class ServiceOrderViewService {
	
	private final ServiceOrderJpaDAO orderDAO;
	
	public ServiceOrderViewService(ServiceOrderJpaDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	
	public ServiceOrderViewDTO showServiceOrderById(Long id) {
		
			ServiceOrder serviceOrder = orderDAO.findById(id)
					.orElseThrow(()-> new ServiceOrderNotFoundException(id));
			
			return ServiceOrderViewDTO.fromEntity(serviceOrder);
	}
	
	public ServiceOrderViewDTO showServiceOrderProtocol(String protocol) {
		
		ServiceOrder serviceOrder = orderDAO.findByProtocolNumber(protocol)
				.orElseThrow(()-> new ServiceOrderNotFoundException(protocol));
		
		return ServiceOrderViewDTO.fromEntity(serviceOrder);
	}
	
}
