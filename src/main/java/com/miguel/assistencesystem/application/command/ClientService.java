
package com.miguel.assistencesystem.application.command;


import com.miguel.assistencesystem.application.audit.AuditService;
import com.miguel.assistencesystem.application.dto.command.ClientRegistrationDTO;
import com.miguel.assistencesystem.application.dto.command.ClientUpdateDTO;
import com.miguel.assistencesystem.application.dto.response.ClientResponseDTO;
import com.miguel.assistencesystem.application.validation.client.ClientValidator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.domain.exceptions.client.ClientNotFoundException;
import com.miguel.assistencesystem.domain.audit.AuditAction;
import com.miguel.assistencesystem.domain.audit.EntityType;
import com.miguel.assistencesystem.domain.exceptions.ConflictException;
import com.miguel.assistencesystem.domain.model.Client;
import com.miguel.assistencesystem.infrastructure.persistence.ClientJpaDAO;

@Service
@Transactional
public class ClientService{
	
	private final ClientJpaDAO clientDAO;
	private final AuditService auditService;
	
	public ClientService(ClientJpaDAO clientDAO, AuditService auditService) {
		this.clientDAO = clientDAO;
		this.auditService = auditService;
	}
	
	public ClientResponseDTO registerClient(ClientRegistrationDTO dto) {
		ClientValidator.validateForRegistration(dto);
		
		ClientValidator.validateUniqueness(dto, clientDAO);
        
        Client client = Client.register(
                dto.getName(),
                dto.getCpf(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getEmail()
        );
        
        clientDAO.persist(client);
        
        auditService.record(AuditAction.CLIENT_REGISTERED, EntityType.CLIENT, client.getId());
        
        return ClientResponseDTO.fromEntity(client);
		
	}
	
	public ClientResponseDTO updateClient(Long id, ClientUpdateDTO dto) {
		ClientValidator.validateForUpdate(dto);

        Client client = clientDAO.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with ID: " + id));
        
        clientDAO.findByPhone(dto.getPhone())
                .ifPresent(existingClient -> {
                    if (!existingClient.getId().equals(client.getId())) {
                        throw new ConflictException("Phone number already registered. Wanna see the client instead?");
                    }
                });
        
        client.updateProfile(
                dto.getName(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getEmail()
        );
        
        auditService.record(AuditAction.CLIENT_UPDATED, EntityType.CLIENT, client.getId());
        
        return ClientResponseDTO.fromEntity(client);
			
	}
}
