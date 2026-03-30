package com.miguel.assistencesystem.infrastructure.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.miguel.assistencesystem.application.dto.response.ApiErrorResponse;
import com.miguel.assistencesystem.domain.enums.DomainErrorCode;
import com.miguel.assistencesystem.domain.exceptions.ConflictException;
import com.miguel.assistencesystem.domain.exceptions.InvalidDomainStateException;
import com.miguel.assistencesystem.domain.exceptions.NotFoundException;
import com.miguel.assistencesystem.domain.exceptions.ValidationException;
import com.miguel.assistencesystem.domain.exceptions.authentication.InvalidCredentialsException;
import com.miguel.assistencesystem.domain.exceptions.authentication.UnauthenticatedException;
import com.miguel.assistencesystem.domain.exceptions.employee.InsufficientPermissionsException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(ConflictException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApiErrorResponse handleConflict(ConflictException ex, HttpServletRequest request) {
	    return ApiErrorResponse.of(
	    		HttpStatus.CONFLICT.value(),
	    		HttpStatus.CONFLICT.getReasonPhrase(),
	    		ex.getErrorCode(),
	    		ex.getMessage(),
	    		request.getRequestURI());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrorResponse handleBadRequest(NotFoundException ex, HttpServletRequest request) {
		return ApiErrorResponse.of(
	    		HttpStatus.NOT_FOUND.value(),
	    		HttpStatus.NOT_FOUND.getReasonPhrase(),
	    		ex.getErrorCode(),
	    		ex.getMessage(),
	    		request.getRequestURI());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse handleUnprocessable(ValidationException ex, HttpServletRequest request) {
		return ApiErrorResponse.of(
	    		HttpStatus.BAD_REQUEST.value(),
	    		HttpStatus.BAD_REQUEST.getReasonPhrase(),
	    		ex.getErrorCode(),
	    		ex.getMessage(),
	    		request.getRequestURI());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(InvalidDomainStateException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ApiErrorResponse handleUnexpected(InvalidDomainStateException ex, HttpServletRequest request) {
		return ApiErrorResponse.of(
	    		HttpStatus.UNPROCESSABLE_ENTITY.value(),
	    		HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
	    		ex.getErrorCode(),
	    		ex.getMessage(),
	    		request.getRequestURI());
	}
	
	//=====================================================================================
	 
	@ExceptionHandler(InvalidCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrorResponse handleInvalidCredentials(InvalidCredentialsException ex, HttpServletRequest request) {
		return ApiErrorResponse.of(
	    		HttpStatus.UNAUTHORIZED.value(),
	    		HttpStatus.UNAUTHORIZED.getReasonPhrase(),
	    		ex.getErrorCode(),
	    		ex.getMessage(),
	    		request.getRequestURI());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(InsufficientPermissionsException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ApiErrorResponse handleInsufficientPermissions(InsufficientPermissionsException ex, HttpServletRequest request) {
		return ApiErrorResponse.of(
	    		HttpStatus.FORBIDDEN.value(),
	    		HttpStatus.FORBIDDEN.getReasonPhrase(),
	    		ex.getErrorCode(),
	    		ex.getMessage(),
	    		request.getRequestURI());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(UnauthenticatedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrorResponse handleUnauthenticated(UnauthenticatedException ex, HttpServletRequest request ) {
		return ApiErrorResponse.of(
	    		HttpStatus.UNAUTHORIZED.value(),
	    		HttpStatus.UNAUTHORIZED.getReasonPhrase(),
	    		ex.getErrorCode(),
	    		ex.getMessage(),
	    		request.getRequestURI());
	}

	
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiErrorResponse handleGeneric(HttpServletRequest request, Exception ex) {
	    
	    
	    return ApiErrorResponse.of(
	        HttpStatus.INTERNAL_SERVER_ERROR.value(),
	        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
	        DomainErrorCode.INTERNAL_ERROR,
	        "An unexpected internal error occurred. Please try again later.",
	        request.getRequestURI()
	    );
	}
	

}


