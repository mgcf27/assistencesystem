package com.miguel.assistencesystem.infrastructure.web.error;

public final class InfrastructureErrorCode {
    private InfrastructureErrorCode() {}
    
    public static final String MALFORMED_REQUEST = "MALFORMED_REQUEST";
    public static final String METHOD_NOT_ALLOWED = "METHOD_NOT_ALLOWED";
    public static final String UNSUPPORTED_MEDIA_TYPE = "UNSUPPORTED_MEDIA_TYPE";
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";
    public static final String ROUTE_NOT_FOUND = "ROUTE_NOT_FOUND";
    public static final String INVALID_PARAMETER = "INVALID_PARAMETER";
    public static final String ARGUMENT_NOT_VALID = "ARGUMENT_NOT_VALID";
    
}