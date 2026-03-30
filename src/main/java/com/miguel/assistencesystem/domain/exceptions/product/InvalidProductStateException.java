package com.miguel.assistencesystem.domain.exceptions.product;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;
import com.miguel.assistencesystem.domain.exceptions.InvalidDomainStateException;

@SuppressWarnings("serial")
public class InvalidProductStateException extends InvalidDomainStateException   {
	
	public InvalidProductStateException(String message) {
		super(DomainErrorCode.INVALID_PRODUCT_STATE, message);
	}

}
