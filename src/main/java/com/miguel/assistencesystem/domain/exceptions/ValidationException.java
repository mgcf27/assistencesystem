package com.miguel.assistencesystem.domain.exceptions;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;

@SuppressWarnings("serial")
public abstract class ValidationException extends BusinessException {
    
	public ValidationException(DomainErrorCode code,String message) {
        super(code, message);
    }
    
    public ValidationException(DomainErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

