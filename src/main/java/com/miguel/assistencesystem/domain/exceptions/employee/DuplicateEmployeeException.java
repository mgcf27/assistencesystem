package com.miguel.assistencesystem.domain.exceptions.employee;

import java.util.List;

import com.miguel.assistencesystem.domain.exceptions.ConflictException;

@SuppressWarnings("serial")
public class DuplicateEmployeeException extends ConflictException {
	private final List<String> violations;
	
	public DuplicateEmployeeException(List<String> violations) {
        super("Duplicate employee data: " + String.join("; ", violations));
        this.violations = violations;
    }
	
	public List<String> getViolations() {
        return violations;
    }

}
