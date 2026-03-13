package com.miguel.assistencesystem.application.query.view;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.dto.view.ClientViewDTO;
import com.miguel.assistencesystem.domain.exceptions.client.ClientNotFoundException;
import com.miguel.assistencesystem.domain.model.Client;
import com.miguel.assistencesystem.domain.model.Product;
import com.miguel.assistencesystem.domain.model.ServiceOrder;
import com.miguel.assistencesystem.domain.valueobjects.PageRequest;
import com.miguel.assistencesystem.infrastructure.persistence.ClientJpaDAO;
import com.miguel.assistencesystem.infrastructure.persistence.ProductJpaDAO;
import com.miguel.assistencesystem.infrastructure.persistence.ServiceOrderJpaDAO;

@Service	
@Transactional(readOnly = true)
public class ClientViewService {
	
	private final ClientJpaDAO clientDAO;
	private final ProductJpaDAO productDAO;
	private final ServiceOrderJpaDAO serviceOrderDAO;
	
	public ClientViewService(
			ClientJpaDAO clientDAO,
			ProductJpaDAO productDAO,
			ServiceOrderJpaDAO serviceOrderDAO) {
		this.clientDAO = clientDAO;
		this.productDAO = productDAO;
		this.serviceOrderDAO = serviceOrderDAO;
	}
	
	private ClientViewDTO buildClientView(
	        Client client,
	        int prodPage,
	        int prodPageSize,
	        int soPage,
	        int soPageSize) {

	    PageRequest prodPR = new PageRequest(prodPage, prodPageSize);
	    PageRequest soPR = new PageRequest(soPage, soPageSize);

	    List<Product> products =
	            productDAO.findProductsByClientId(client.getId(), prodPR);

	    List<ServiceOrder> serviceOrders =
	            serviceOrderDAO.findServiceOrdersByClientId(client.getId(), soPR);

	    return ClientViewDTO.fromEntity(client, products, serviceOrders);
	}
	
	public ClientViewDTO showClientById(
			Long id,
			int prodPage,
			int prodPageSize,
			int soPage,
			int soPageSize) {
		
	        Client client = clientDAO.findById(id)
	            .orElseThrow(() -> new ClientNotFoundException(id));
	        
	        return buildClientView(client, prodPage, prodPageSize, soPage, soPageSize);
	}

	public ClientViewDTO showClientByCpf(
			String cpf,
			int prodPage,
			int prodPageSize,
			int soPage,
			int soPageSize) {
		
	        Client client = clientDAO.findByCpf(cpf)
	            .orElseThrow(() -> new ClientNotFoundException(cpf));
	        
	        return buildClientView(client, prodPage, prodPageSize, soPage, soPageSize);
	}

	public ClientViewDTO showClientByPhone(
			String phone,
			int prodPage,
			int prodPageSize,
			int soPage,
			int soPageSize) {
	    
	        Client client = clientDAO.findByPhone(phone)
	            .orElseThrow(() -> new ClientNotFoundException(phone));

	        return buildClientView(client, prodPage, prodPageSize, soPage, soPageSize);
	}
}
