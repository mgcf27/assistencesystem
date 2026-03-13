package com.miguel.assistencesystem.domain.exceptions;

@SuppressWarnings("serial")
public abstract class InvalidDomainStateException extends RuntimeException {
	
	public InvalidDomainStateException(String message) {
		super(message);
	}

	public InvalidDomainStateException(String message, Throwable cause) {
		super(message, cause);
	}
}
