package com.miguel.assistencesystem.domain.exceptions.employee;

import java.util.List;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;
import com.miguel.assistencesystem.domain.exceptions.ValidationException;

@SuppressWarnings("serial")
public class InvalidEmployeeDataException extends ValidationException {
	private final List<String> errors;
	
	
	public InvalidEmployeeDataException(List<String> errors) {
		 super(
				 DomainErrorCode.INVALID_EMPLOYEE_DATA,
				 "Invalid employee data: " + String.join(", ", errors));
	     this.errors = errors;
	}

	public List<String> getErrors() {
        return errors;
    }

}
