package com.miguel.assistencesystem.domain.exceptions.serviceorder;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;
import com.miguel.assistencesystem.domain.exceptions.ConflictException;

@SuppressWarnings("serial")
public class ServiceOrderAlreadyOpenException extends ConflictException {
    
    public ServiceOrderAlreadyOpenException(String message) {
        super(DomainErrorCode.SERVICE_ORDER_ALREADY_OPEN, message);
    }
}
