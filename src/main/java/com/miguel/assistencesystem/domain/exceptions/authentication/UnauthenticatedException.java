package com.miguel.assistencesystem.domain.exceptions.authentication;

import com.miguel.assistencesystem.domain.exceptions.BusinessException;

@SuppressWarnings("serial")
public class UnauthenticatedException extends BusinessException {
	
	public UnauthenticatedException(String message) {
		super(message);
	}

	public UnauthenticatedException(String message,Throwable cause) {
		super(message, cause);
	}
}
