package com.miguel.assistencesystem.domain.exceptions.client;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;
import com.miguel.assistencesystem.domain.exceptions.ValidationException;
import java.util.List;

@SuppressWarnings("serial")
public class InvalidClientDataException extends ValidationException {
    
    private final List<String> errors;
    
    public InvalidClientDataException(List<String> errors) {
        super(
        		DomainErrorCode.INVALID_CLIENT_DATA,
        		"Invalid client data: " + String.join(", ", errors));
        this.errors = errors;
    }
    
    public List<String> getErrors() {
        return errors;
    }
}
