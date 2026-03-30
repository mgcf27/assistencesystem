package com.miguel.assistencesystem.domain.exceptions.product;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;
import com.miguel.assistencesystem.domain.exceptions.ValidationException;

@SuppressWarnings("serial")
public class InvalidProductDataException extends ValidationException {

	public InvalidProductDataException(String message) {
		super(
				DomainErrorCode.INVALID_PRODUCT_DATA,
				message);
	}
	
	public InvalidProductDataException(String message, Throwable cause) {
		super(
				DomainErrorCode.INVALID_PRODUCT_DATA,
				message,
				cause);
	}

}
