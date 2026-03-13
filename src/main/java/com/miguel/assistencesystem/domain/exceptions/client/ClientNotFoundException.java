package com.miguel.assistencesystem.domain.exceptions.client;

import com.miguel.assistencesystem.domain.exceptions.NotFoundException;

@SuppressWarnings("serial")
public class ClientNotFoundException extends NotFoundException {
    
    public ClientNotFoundException(Long id) {
        super("Client not found with ID: " + id);
    }
    
    public ClientNotFoundException(String identifier) {
        super("Client not found: " + identifier);
    }
}

