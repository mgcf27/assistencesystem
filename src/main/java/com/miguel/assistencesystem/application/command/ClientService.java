
package com.miguel.assistencesystem.application.command;


import com.miguel.assistencesystem.application.dto.command.ClientRegistrationDTO;
import com.miguel.assistencesystem.application.dto.command.ClientUpdateDTO;
import com.miguel.assistencesystem.application.dto.response.ClientResponseDTO;
import com.miguel.assistencesystem.application.security.AuthenticationFacade;
import com.miguel.assistencesystem.application.validation.client.ClientValidator;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import com.miguel.assistencesystem.domain.exceptions.client.ClientNotFoundException;
import com.miguel.assistencesystem.domain.exceptions.ConflictException;
import com.miguel.assistencesystem.domain.model.Client;
import com.miguel.assistencesystem.infrastructure.persistence.ClientJpaDAO;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;

@Service
@Transactional
public class ClientService{
	
	private final ClientJpaDAO clientDAO;
	private final AuthenticationFacade authentication;
	
	public ClientService(ClientJpaDAO clientDAO, AuthenticationFacade authentication) {
		this.clientDAO = clientDAO;
		this.authentication = authentication;
	}
	
	public ClientResponseDTO registerClient(ClientRegistrationDTO dto) {
		AuthenticatedIdentity identity = authentication.requireAuthenticated();
		
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
        
        return ClientResponseDTO.fromEntity(client);
		
	}
	
	public ClientResponseDTO updateClient(Long id, ClientUpdateDTO dto) {
		AuthenticatedIdentity identity = authentication.requireAuthenticated();
		
		
		ClientValidator.validateForUpdate(dto);
		
		// Find the entity
        Client client = clientDAO.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with ID: " + id));
        
        // Check phone uniqueness
        clientDAO.findByPhone(dto.getPhone())
                .ifPresent(existingClient -> {
                    if (!existingClient.getId().equals(client.getId())) {
                        throw new ConflictException("Phone number already registered. Wanna see the client instead?");
                    }
                });
        
        // Update the entity
        client.updateProfile(
                dto.getName(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getEmail()
        );
        
        return ClientResponseDTO.fromEntity(client);
			
	}
}
