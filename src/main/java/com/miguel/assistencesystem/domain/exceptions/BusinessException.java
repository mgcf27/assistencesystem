package com.miguel.assistencesystem.domain.exceptions;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;

@SuppressWarnings("serial")
public abstract class BusinessException extends RuntimeException {
	private final DomainErrorCode code;
    
    public BusinessException(DomainErrorCode code, String message) {
        super(message);
        this.code = code;
    }
    
    public BusinessException(DomainErrorCode code, String message, Throwable cause) {
    	super(message, cause);
    	this.code = code;
    }

    public DomainErrorCode getErrorCode() {
    	return code;
    }
}


