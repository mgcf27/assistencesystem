package com.miguel.assistencesystem.domain.exceptions.serviceorder;

import com.miguel.assistencesystem.domain.exceptions.NotFoundException;

@SuppressWarnings("serial")
public class ServiceOrderNotFoundException extends NotFoundException {
    
    public ServiceOrderNotFoundException(Long id) {
        super("Service order not found with ID: " + id);
    }
    
    public ServiceOrderNotFoundException(String protocolNumber) {
        super("Service order not found with protocol number: " + protocolNumber);
    }
}
