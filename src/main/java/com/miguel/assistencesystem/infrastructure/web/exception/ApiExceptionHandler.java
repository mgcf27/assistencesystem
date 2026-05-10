package com.miguel.assistencesystem.infrastructure.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.miguel.assistencesystem.application.dto.response.ApiErrorResponse;
import com.miguel.assistencesystem.domain.exceptions.ConflictException;
import com.miguel.assistencesystem.domain.exceptions.InvalidDomainStateException;
import com.miguel.assistencesystem.domain.exceptions.NotFoundException;
import com.miguel.assistencesystem.domain.exceptions.ValidationException;
import com.miguel.assistencesystem.domain.exceptions.authentication.InvalidCredentialsException;
import com.miguel.assistencesystem.domain.exceptions.authentication.UnauthenticatedException;
import com.miguel.assistencesystem.domain.exceptions.employee.InsufficientPermissionsException;
import com.miguel.assistencesystem.infrastructure.web.error.InfrastructureErrorCode;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(ConflictException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApiErrorResponse handleConflict(ConflictException ex, HttpServletRequest request) {
	    return ApiErrorResponse.of(
	    		HttpStatus.CONFLICT.value(),
	    		HttpStatus.CONFLICT.getReasonPhrase(),
	    		ex.getErrorCode().name(),
	    		ex.getMessage(),
	    		null,
	    		request.getRequestURI());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrorResponse handleNotFound(NotFoundException ex, HttpServletRequest request) {
		return ApiErrorResponse.of(
	    		HttpStatus.NOT_FOUND.value(),
	    		HttpStatus.NOT_FOUND.getReasonPhrase(),
	    		ex.getErrorCode().name(),
	    		ex.getMessage(),
	    		null,
	    		request.getRequestURI());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse handleValidation(ValidationException ex, HttpServletRequest request) {
		return ApiErrorResponse.of(
	    		HttpStatus.BAD_REQUEST.value(),
	    		HttpStatus.BAD_REQUEST.getReasonPhrase(),
	    		ex.getErrorCode().name(),
	    		ex.getMessage(),
	    		null,
	    		request.getRequestURI());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(InvalidDomainStateException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ApiErrorResponse handleInvalidDomainState(InvalidDomainStateException ex, HttpServletRequest request) {
		return ApiErrorResponse.of(
	    		HttpStatus.UNPROCESSABLE_ENTITY.value(),
	    		HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
	    		ex.getErrorCode().name(),
	    		ex.getMessage(),
	    		null,
	    		request.getRequestURI());
	}
	
	//=====================================================================================
	 
	@ExceptionHandler(InvalidCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrorResponse handleInvalidCredentials(InvalidCredentialsException ex, HttpServletRequest request) {
		return ApiErrorResponse.of(
	    		HttpStatus.UNAUTHORIZED.value(),
	    		HttpStatus.UNAUTHORIZED.getReasonPhrase(),
	    		ex.getErrorCode().name(),
	    		ex.getMessage(),
	    		null,
	    		request.getRequestURI());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(InsufficientPermissionsException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ApiErrorResponse handleInsufficientPermissions(InsufficientPermissionsException ex, HttpServletRequest request) {
		return ApiErrorResponse.of(
	    		HttpStatus.FORBIDDEN.value(),
	    		HttpStatus.FORBIDDEN.getReasonPhrase(),
	    		ex.getErrorCode().name(),
	    		ex.getMessage(),
	    		null,
	    		request.getRequestURI());
	}
	
	//=====================================================================================
	
	@ExceptionHandler(UnauthenticatedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrorResponse handleUnauthenticated(UnauthenticatedException ex, HttpServletRequest request ) {
		return ApiErrorResponse.of(
	    		HttpStatus.UNAUTHORIZED.value(),
	    		HttpStatus.UNAUTHORIZED.getReasonPhrase(),
	    		ex.getErrorCode().name(),
	    		ex.getMessage(),
	    		null,
	    		request.getRequestURI());
	}
	//=====================================================================================
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
		return ApiErrorResponse.of(
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				InfrastructureErrorCode.MALFORMED_REQUEST,
		        "Request body is missing or malformed.",
		        null,
		        request.getRequestURI()
				);
	}
	//=====================================================================================
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse handleMethodArgumentTypeMismatch(
	    MethodArgumentTypeMismatchException ex,
	    HttpServletRequest request) {

	    String paramName = ex.getName();

	    return ApiErrorResponse.of(
	        HttpStatus.BAD_REQUEST.value(),
	        HttpStatus.BAD_REQUEST.getReasonPhrase(),
	        InfrastructureErrorCode.INVALID_PARAMETER,
	        "Invalid value for parameter '" + paramName + "'.",
	        null,
	        request.getRequestURI()
	    );
	}
	//=====================================================================================
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ApiErrorResponse handleMethodNotSupported(
	    HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
	    return ApiErrorResponse.of(
	        HttpStatus.METHOD_NOT_ALLOWED.value(),
	        HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(),
	        InfrastructureErrorCode.METHOD_NOT_ALLOWED,
	        "HTTP method not supported for this endpoint",
	        null,
	        request.getRequestURI());
	}
	//=====================================================================================
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public ApiErrorResponse handleMediaTypeNotSupported(
	    HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
	    return ApiErrorResponse.of(
	        HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
	        HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(),
	        InfrastructureErrorCode.UNSUPPORTED_MEDIA_TYPE,
	        "Unsupported media type. Please check the Content-Type header",
	        null,
	        request.getRequestURI());
	}
	//=====================================================================================
	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrorResponse handleNoResourceFound(
	    NoResourceFoundException ex, HttpServletRequest request) {
	    return ApiErrorResponse.of(
	        HttpStatus.NOT_FOUND.value(),
	        HttpStatus.NOT_FOUND.getReasonPhrase(),
	        InfrastructureErrorCode.ROUTE_NOT_FOUND,
	        "The requested endpoint does not exist.",
	        null,
	        request.getRequestURI());
	}
	//=====================================================================================
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse handleArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		Map<String,String> errors = new HashMap<>();
		ex.getBindingResult()
		.getFieldErrors()
		.forEach(erro->errors.put(erro.getField(),erro.getDefaultMessage()));
		
		return ApiErrorResponse.of(
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				InfrastructureErrorCode.ARGUMENT_NOT_VALID,
				"One or more arguments are not valid",
				errors,
				request.getRequestURI()
				);
	}
	//=====================================================================================
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiErrorResponse handleGeneric(Exception ex, HttpServletRequest request) {
	    
	    return ApiErrorResponse.of(
	        HttpStatus.INTERNAL_SERVER_ERROR.value(),
	        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
	        InfrastructureErrorCode.INTERNAL_ERROR,
	        "An unexpected internal error occurred. Please try again later.",
	        null,
	        request.getRequestURI()
	    );
	}
}


