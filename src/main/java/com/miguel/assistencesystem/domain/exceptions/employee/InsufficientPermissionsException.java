package com.miguel.assistencesystem.domain.exceptions.employee;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;
import com.miguel.assistencesystem.domain.exceptions.BusinessException;

@SuppressWarnings("serial")
public class InsufficientPermissionsException extends BusinessException {
	public InsufficientPermissionsException(String message) {
		super(DomainErrorCode.INSUFFICIENT_PERMISSIONS, message);
	}
	public InsufficientPermissionsException(String message, Throwable cause) {
		super(DomainErrorCode.INSUFFICIENT_PERMISSIONS, message, cause);
	}

}
