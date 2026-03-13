package com.miguel.assistencesystem.domain.exceptions;

@SuppressWarnings("serial")
public class ConflictException extends BusinessException {
    
    public ConflictException(String message) {
        super(message);
    }
    
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
