
package com.miguel.assistencesystem.application.validation.client;

import java.util.ArrayList;
import java.util.List;

import com.miguel.assistencesystem.application.dto.command.ClientRegistrationDTO;
import com.miguel.assistencesystem.application.dto.command.ClientUpdateDTO;
import com.miguel.assistencesystem.application.validation.CpfValidator;
import com.miguel.assistencesystem.application.validation.EmailValidator;
import com.miguel.assistencesystem.application.validation.PhoneValidator;
import com.miguel.assistencesystem.domain.exceptions.client.DuplicateClientException;
import com.miguel.assistencesystem.domain.exceptions.client.InvalidClientDataException;
import com.miguel.assistencesystem.infrastructure.persistence.ClientJpaDAO;


public final class ClientValidator {
	
	private ClientValidator() {}

	public static void validateForRegistration(ClientRegistrationDTO clientRegistrationDto ) {
		List<String> errors = new ArrayList<>();
		
		if (clientRegistrationDto.getName() == null || clientRegistrationDto.getName().isBlank())
            errors.add("Name is required");
		if (!CpfValidator.isValid(clientRegistrationDto.getCpf()))
            errors.add("CPF is invalid");
		
		if (!PhoneValidator.isValid(clientRegistrationDto.getPhone()))
            errors.add("Phone number is invalid");
		
		if (!EmailValidator.isValid(clientRegistrationDto.getEmail()))
            errors.add("Email is invalid");
		
		if (clientRegistrationDto.getAddress()== null || clientRegistrationDto.getAddress().isBlank()) {
			errors.add("Address is required");
		}
		
		if (!errors.isEmpty())
            throw new InvalidClientDataException(errors);
	}
	
	public static void validateForUpdate(ClientUpdateDTO clientUpdateDto) {
		List<String> errors = new ArrayList<>();
		
		if (clientUpdateDto.getName() == null || clientUpdateDto.getName().isBlank())
            errors.add("Name is required");
	
		if (!PhoneValidator.isValid(clientUpdateDto.getPhone()))
            errors.add("Phone number is invalid");
		
		if (!EmailValidator.isValid(clientUpdateDto.getEmail()))
            errors.add("Email is invalid");
		
		if (clientUpdateDto.getAddress()== null || clientUpdateDto.getAddress().isBlank()) 
			errors.add("Address is required");
		
		if (!errors.isEmpty())
            throw new InvalidClientDataException(errors);		
	}
	
	public static void validateUniqueness(
			ClientRegistrationDTO clientRegistrationDto,
			ClientJpaDAO clientDAO) {
		
		List<String> uniquenessViolations = new ArrayList<>();
		
		if (clientDAO.existsByCpf(clientRegistrationDto.getCpf())){
	        uniquenessViolations.add("Cpf is already registered.");
	    }
	        
	    if(clientDAO.existsByPhone(clientRegistrationDto.getPhone())) {
	        uniquenessViolations.add("Phone is already registered");
	    }
	    
	    if(!uniquenessViolations.isEmpty()) {
	        throw new DuplicateClientException(uniquenessViolations);
	    }
	}
}
