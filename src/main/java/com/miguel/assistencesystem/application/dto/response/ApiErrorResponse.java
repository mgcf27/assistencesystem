package com.miguel.assistencesystem.application.dto.response;

import java.time.Instant;

public record ApiErrorResponse(
		int status,
		String error,
        String code,
        String message,
        String path,
        Instant timestamp
) {
    public static ApiErrorResponse of(
    		int status,
    		String error,
    		String code,
    		String message,
    		String path
    		) {
        return new ApiErrorResponse(status, error, code, message, path, Instant.now());
    }
}