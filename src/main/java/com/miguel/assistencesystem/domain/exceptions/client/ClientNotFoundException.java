package com.miguel.assistencesystem.domain.exceptions.client;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;
import com.miguel.assistencesystem.domain.exceptions.NotFoundException;

@SuppressWarnings("serial")
public class ClientNotFoundException extends NotFoundException {
    
    public ClientNotFoundException(Long id) {
        super(
        		DomainErrorCode.CLIENT_NOT_FOUND,
        		"Client not found with ID: " + id);
    }
    
    public ClientNotFoundException(String identifier) {
        super(
        		DomainErrorCode.CLIENT_NOT_FOUND,
        		"Client not found: " + identifier);
    }
}

