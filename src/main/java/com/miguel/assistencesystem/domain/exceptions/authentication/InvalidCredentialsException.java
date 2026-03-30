package com.miguel.assistencesystem.domain.exceptions.authentication;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;
import com.miguel.assistencesystem.domain.exceptions.BusinessException;

@SuppressWarnings("serial")
public class InvalidCredentialsException extends BusinessException {
	
	public InvalidCredentialsException(String message) {
        super(DomainErrorCode.INVALID_CREDENTIALS, message);
    }
    
    public InvalidCredentialsException(String message, Throwable cause) {
        super(DomainErrorCode.INVALID_CREDENTIALS, message, cause);
    }

}
