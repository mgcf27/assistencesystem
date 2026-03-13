package com.miguel.assistencesystem.domain.exceptions;

@SuppressWarnings("serial")
public class NotFoundException extends BusinessException {
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
