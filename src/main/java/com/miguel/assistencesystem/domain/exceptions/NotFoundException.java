package com.miguel.assistencesystem.domain.exceptions;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;

@SuppressWarnings("serial")
public abstract class NotFoundException extends BusinessException {
    
    public NotFoundException(DomainErrorCode code,String message) {
        super(code, message);
    }
    
    public NotFoundException(DomainErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
