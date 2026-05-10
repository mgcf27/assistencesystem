package com.miguel.assistencesystem.application.dto.response;

import java.time.Instant;
import java.util.Map;

public record ApiErrorResponse(
		int status,
		String error,
        String code,
        String message,
        Map<String,String> details,
        String path,
        Instant timestamp
) {
    public static ApiErrorResponse of(
    		int status,
    		String error,
    		String code,
    		String message,
    		Map<String,String> details,
    		String path
    		) {
        return new ApiErrorResponse(status, error, code, message, details, path, Instant.now());
    }
}