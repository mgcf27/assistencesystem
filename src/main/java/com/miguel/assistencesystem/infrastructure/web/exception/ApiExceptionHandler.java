package com.miguel.assistencesystem.infrastructure.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.miguel.assistencesystem.application.dto.response.ApiErrorResponse;
import com.miguel.assistencesystem.domain.exceptions.ConflictException;
import com.miguel.assistencesystem.domain.exceptions.authentication.InvalidCredentialsException;
import com.miguel.assistencesystem.domain.exceptions.authentication.UnauthenticatedException;
import com.miguel.assistencesystem.domain.exceptions.client.ClientNotFoundException;
import com.miguel.assistencesystem.domain.exceptions.client.InvalidClientDataException;
import com.miguel.assistencesystem.domain.exceptions.employee.DuplicateEmployeeException;
import com.miguel.assistencesystem.domain.exceptions.employee.InsufficientPermissionsException;
import com.miguel.assistencesystem.domain.exceptions.employee.InvalidEmployeeDataException;
import com.miguel.assistencesystem.domain.exceptions.product.InvalidProductDataException;
import com.miguel.assistencesystem.domain.exceptions.product.ProductNotFoundException;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.InvalidServiceOrderDataException;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.InvalidServiceOrderStatusException;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.ServiceOrderAlreadyOpenException;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.ServiceOrderNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler({
		ClientNotFoundException.class,
	    ProductNotFoundException.class,
	    ServiceOrderNotFoundException.class
	})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrorResponse handleNotFound(RuntimeException ex) {
	    return ApiErrorResponse.of("NOT_FOUND", ex.getMessage());
	}
	
	//=====================================================================================
	@ExceptionHandler({
	    ConflictException.class,
	    ServiceOrderAlreadyOpenException.class,
	    DuplicateEmployeeException.class    
	})
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApiErrorResponse handleConflict(RuntimeException ex) {
	    return ApiErrorResponse.of("CONFLICT", ex.getMessage());
	}
	
	//=====================================================================================
	
	@ExceptionHandler({
		InvalidServiceOrderDataException.class,
	    InvalidProductDataException.class,
	    InvalidClientDataException.class,
	    InvalidEmployeeDataException.class
	})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse handleBadRequest(RuntimeException ex) {
		return ApiErrorResponse.of("BAD_REQUEST", ex.getMessage());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(InvalidServiceOrderStatusException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ApiErrorResponse handleUnprocessable(RuntimeException ex) {
		return ApiErrorResponse.of("UNPROCESSABLE_ENTITY", ex.getMessage());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiErrorResponse handleUnexpected(Exception ex) {
	    return ApiErrorResponse.of(
	        "INTERNAL_ERROR",
	        "Unexpected error occurred"
	    );
	}
	
	//=====================================================================================
	
	@ExceptionHandler(InvalidCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrorResponse handleInvalidCredentials(RuntimeException ex) {
		return ApiErrorResponse.of("UNAUTHORIZED", ex.getMessage());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(InsufficientPermissionsException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ApiErrorResponse handleInsufficientPermissions(RuntimeException ex) {
		return ApiErrorResponse.of("INSUFFICIENT_PERMISSON", ex.getMessage());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(UnauthenticatedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrorResponse handleUnauthenticated(RuntimeException ex) {
	    return ApiErrorResponse.of("UNAUTHORIZED", ex.getMessage());
	}
}

