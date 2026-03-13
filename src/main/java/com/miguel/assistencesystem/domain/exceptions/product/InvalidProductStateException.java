package com.miguel.assistencesystem.domain.exceptions.product;

import com.miguel.assistencesystem.domain.exceptions.InvalidDomainStateException;

@SuppressWarnings("serial")
public class InvalidProductStateException extends InvalidDomainStateException   {
	
	public InvalidProductStateException(String message) {
		super(message);
	}

}
