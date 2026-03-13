package com.miguel.assistencesystem.domain.exceptions.product;

import com.miguel.assistencesystem.domain.exceptions.ValidationException;

@SuppressWarnings("serial")
public class InvalidProductDataException extends ValidationException {

	public InvalidProductDataException(String message) {
		super(message);
	}
	
	public InvalidProductDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
