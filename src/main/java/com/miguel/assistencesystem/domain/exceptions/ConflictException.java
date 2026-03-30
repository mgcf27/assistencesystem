package com.miguel.assistencesystem.domain.exceptions;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;

@SuppressWarnings("serial")
public abstract class ConflictException extends BusinessException {
    
	public ConflictException(DomainErrorCode code,String message) {
        super(code, message);
    }
    
    public ConflictException(DomainErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
