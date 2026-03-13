package com.miguel.assistencesystem.domain.exceptions.serviceorder;

import com.miguel.assistencesystem.domain.exceptions.ConflictException;

@SuppressWarnings("serial")
public class ServiceOrderAlreadyOpenException extends ConflictException {
    
    public ServiceOrderAlreadyOpenException(String message) {
        super(message);
    }
}
