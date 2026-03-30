package com.miguel.assistencesystem.application.dto.response;

import java.time.Instant;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;

public record ApiErrorResponse(
		int status,
		String error,
        DomainErrorCode code,
        String message,
        String path,
        Instant timestamp
) {
    public static ApiErrorResponse of(
    		int status,
    		String error,
    		DomainErrorCode code,
    		String message,
    		String path
    		) {
        return new ApiErrorResponse(status, error, code, message, path, Instant.now());
    }
}