package com.miguel.assistencesystem.domain.exceptions;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;

@SuppressWarnings("serial")
public abstract class InvalidDomainStateException extends BusinessException {
	
	public InvalidDomainStateException(DomainErrorCode code,String message) {
		super(code, message);
	}

	public InvalidDomainStateException(DomainErrorCode code, String message, Throwable cause) {
		super(code, message, cause);
	}
}
