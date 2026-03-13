package com.miguel.assistencesystem.domain.exceptions.employee;

import com.miguel.assistencesystem.domain.exceptions.BusinessException;

@SuppressWarnings("serial")
public class InsufficientPermissionsException extends BusinessException {
	public InsufficientPermissionsException(String message) {
		super(message);
	}
	public InsufficientPermissionsException(String message, Throwable cause) {
		super(message, cause);
	}

}
