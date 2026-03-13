package com.miguel.assistencesystem.domain.exceptions;

@SuppressWarnings("serial")
public class ValidationException extends BusinessException {
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

