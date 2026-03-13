package com.miguel.assistencesystem.domain.exceptions.serviceorder;

import java.util.List;

import com.miguel.assistencesystem.domain.exceptions.ValidationException;

@SuppressWarnings("serial")
public class InvalidServiceOrderDataException extends ValidationException {

	 private final List<String> errors;
	    
	 public InvalidServiceOrderDataException(List<String> errors) {
		 
        super("Invalid service order data: " + String.join(", ", errors));
        this.errors = errors;
	 }
	    
    public List<String> getErrors() {
        return errors;
    }
}
