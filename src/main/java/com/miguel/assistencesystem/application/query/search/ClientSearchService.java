package com.miguel.assistencesystem.application.query.search;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.dto.summary.ClientSummaryDTO;
import com.miguel.assistencesystem.domain.exceptions.client.ClientNotFoundException;
import com.miguel.assistencesystem.domain.valueobjects.PageRequest;
import com.miguel.assistencesystem.infrastructure.persistence.ClientJpaDAO;

@Service
@Transactional(readOnly = true)  
public class ClientSearchService {

    private final ClientJpaDAO clientDAO;
    
    public ClientSearchService(ClientJpaDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

    public List<ClientSummaryDTO> searchByName(String name, int page, int pageSize) {
        
        PageRequest pr = new PageRequest(page, pageSize);
        	
        return clientDAO.findByName(name, pr).stream()
                .map(ClientSummaryDTO::fromEntity)
                .toList();
        
        
    }

    public ClientSummaryDTO searchByCpf(String cpf) {
 
    	 return clientDAO.findByCpf(cpf)
                 .map(ClientSummaryDTO::fromEntity)
                 .orElseThrow(() -> new ClientNotFoundException(cpf));
    }

    public ClientSummaryDTO searchByPhone(String phone) {
    	
    	 return clientDAO.findByPhone(phone)
                 .map(ClientSummaryDTO::fromEntity)
                 .orElseThrow(() -> new ClientNotFoundException(phone));
    }	 
}

